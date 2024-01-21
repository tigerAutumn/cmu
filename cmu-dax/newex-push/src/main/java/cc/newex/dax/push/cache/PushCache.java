package cc.newex.dax.push.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.web.reactive.socket.WebSocketMessage;

import cc.newex.dax.push.bean.AskBean;
import cc.newex.dax.push.bean.FunctionType;
import cc.newex.dax.push.bean.WebSocketBean;
import cc.newex.dax.push.bean.enums.ErrorCodeEnum;
import cc.newex.dax.push.exception.PushRuntimeException;
import io.jsonwebtoken.lang.Collections;
import lombok.Builder;
import lombok.Data;
import reactor.core.publisher.UnicastProcessor;

/**
 * 缓存服务
 *
 * @author xionghui
 * @date 2018/09/13
 */
public class PushCache {
  // idle信息: access-order，访问优先
  private static final Map<String, IdleCheckBean> IDLE_MAP = new LinkedHashMap<>(16, 0.75f, true);
  private static final Lock IDLE_LOCK = new ReentrantLock();
  // 5m会话失效
  private static final long IDLE_TIME = 5L * 60 * 1000;

  // ip对应的sessionId缓存
  private static final Map<String, Map<String, Set<AskBean>>> IP_MAP = new ConcurrentHashMap<>();
  private static final Lock IP_LOCK = new ReentrantLock();
  private static final AtomicInteger IP_ATOMIC = new AtomicInteger(128);
  private static final AtomicInteger SESSION_ATOMIC = new AtomicInteger(128);

  // 保存用户登录信息
  private static final Map<String, Long> USER_MAP = new ConcurrentHashMap<>();
  // 需要带登录的session
  private static final Map<AskBean, Map<Long, Map<String, WebSocketBean>>> SESSION_USER_MAP =
      new HashMap<>();
  private static final ReadWriteLock RW_USER_LOCK = new ReentrantReadWriteLock();
  private static final Lock R_USER_LOCK = RW_USER_LOCK.readLock();
  private static final Lock W_USER_LOCK = RW_USER_LOCK.writeLock();

  // 普通session
  private static final Map<AskBean, Map<String, WebSocketBean>> SESSION_MAP = new HashMap<>();
  private static final ReadWriteLock RW_LOCK = new ReentrantReadWriteLock();
  private static final Lock R_LOCK = RW_LOCK.readLock();
  private static final Lock W_LOCK = RW_LOCK.writeLock();

  /**
   * 设置ip和session
   */
  public static void setIpSessionAtomic(Integer ip, Integer session) {
    if (ip != null && ip > 0 && ip != IP_ATOMIC.get()) {
      IP_ATOMIC.set(ip);
    }
    if (session != null && session > 0 && session != SESSION_ATOMIC.get()) {
      SESSION_ATOMIC.set(session);
    }
  }

  /**
   * 初始化ip
   */
  public static void initIp(String ip) {
    IP_LOCK.lock();
    try {
      Map<String, Set<AskBean>> sessionIdMap = IP_MAP.get(ip);
      if (sessionIdMap == null) {
        sessionIdMap = new ConcurrentHashMap<>();
        IP_MAP.put(ip, sessionIdMap);
      }
      if (sessionIdMap.size() > IP_ATOMIC.get()) {
        throw new PushRuntimeException(ErrorCodeEnum.IP_LIMIT);
      }
    } finally {
      IP_LOCK.unlock();
    }
  }

  /**
   * 增加session
   */
  public static void addIpSession(String ip, String sessionId, AskBean askBean) {
    IP_LOCK.lock();
    try {
      Map<String, Set<AskBean>> sessionIdMap = IP_MAP.get(ip);
      if (sessionIdMap == null) {
        sessionIdMap = new ConcurrentHashMap<>();
        IP_MAP.put(ip, sessionIdMap);
      }
      Set<AskBean> beanSet = sessionIdMap.get(sessionId);
      if (beanSet == null) {
        beanSet = new HashSet<>();
        sessionIdMap.put(sessionId, beanSet);
      }
      if (beanSet.size() > SESSION_ATOMIC.get()) {
        throw new PushRuntimeException(ErrorCodeEnum.SESSION_LIMIT);
      }
      beanSet.add(askBean);
    } finally {
      IP_LOCK.unlock();
    }
  }

  /**
   * 删除session
   */
  public static void removeIpSession(String ip, String sessionId, AskBean askBean) {
    IP_LOCK.lock();
    try {
      Map<String, Set<AskBean>> sessionIdMap = IP_MAP.get(ip);
      if (sessionIdMap == null) {
        return;
      }
      Set<AskBean> beanSet = sessionIdMap.get(sessionId);
      if (beanSet == null) {
        return;
      }
      beanSet.remove(askBean);
    } finally {
      IP_LOCK.unlock();
    }
  }

  /**
   * 删除session
   */
  private static void removeIdleIpSession(String ip, String sessionId) {
    IP_LOCK.lock();
    try {
      Map<String, Set<AskBean>> sessionIdMap = IP_MAP.get(ip);
      if (sessionIdMap == null) {
        return;
      }
      sessionIdMap.remove(sessionId);
      if (sessionIdMap.size() == 0) {
        IP_MAP.remove(ip);
      }
    } finally {
      IP_LOCK.unlock();
    }
  }

  /**
   * 初始化session
   */
  public static void initIdle(String sessionId, String ip,
      UnicastProcessor<WebSocketMessage> processor) {
    IDLE_LOCK.lock();
    try {
      IDLE_MAP.put(sessionId, IdleCheckBean.builder().time(System.currentTimeMillis()).ip(ip)
          .processor(processor).build());
    } finally {
      IDLE_LOCK.unlock();
    }
  }

  /**
   * 设置或更新session的time
   */
  public static boolean updateIdle(String sessionId, UnicastProcessor<WebSocketMessage> processor) {
    IDLE_LOCK.lock();
    try {
      IdleCheckBean oldIdleCheckBean = IDLE_MAP.get(sessionId);
      if (oldIdleCheckBean == null) {
        return false;
      }
      // update time
      oldIdleCheckBean.setTime(System.currentTimeMillis());
      return true;
    } finally {
      IDLE_LOCK.unlock();
    }
  }

  /**
   * 处理失效的session
   */
  public static void dealIdle() {
    IDLE_LOCK.lock();
    try {
      List<String> keyList = new ArrayList<>();
      long now = System.currentTimeMillis();
      IDLE_MAP.forEach((k, v) -> {
        // 访问优先，当前数据不失效，后面数据肯定不会失效
        if (now - v.getTime() < IDLE_TIME) {
          return;
        }
        if (!v.getProcessor().hasCompleted()) {
          v.getProcessor().onComplete();
        }
        Long userId = USER_MAP.get(k);
        if (userId != null) {
          removeUserSession(userId, k);
        }
        removeSession(k);
        USER_MAP.remove(k);
        removeIdleIpSession(v.getIp(), k);
        keyList.add(k);
      });
      for (String key : keyList) {
        IDLE_MAP.remove(key);
      }
    } finally {
      IDLE_LOCK.unlock();
    }
  }

  private static void removeUserSession(long userId, String sessionId) {
    W_USER_LOCK.lock();
    try {
      List<AskBean> keyList = new ArrayList<>();
      SESSION_USER_MAP.forEach((k, v) -> {
        Map<String, WebSocketBean> userMap = v.get(userId);
        if (userMap != null) {
          userMap.remove(sessionId);
          if (userMap.size() == 0) {
            v.remove(userId);
          }
        }
        if (v != null && v.size() == 0) {
          keyList.add(k);
        }
      });
      for (AskBean askBean : keyList) {
        SESSION_USER_MAP.remove(askBean);
      }
    } finally {
      W_USER_LOCK.unlock();
    }
  }

  private static void removeSession(String sessionId) {
    W_LOCK.lock();
    try {
      List<AskBean> keyList = new ArrayList<>();
      SESSION_MAP.forEach((k, v) -> {
        if (v != null) {
          v.remove(sessionId);
          if (v.size() == 0) {
            keyList.add(k);
          }
        }
      });
      for (AskBean askBean : keyList) {
        SESSION_MAP.remove(askBean);
      }
    } finally {
      W_LOCK.unlock();
    }
  }

  public static void addUser(String sessionId, long userId) {
    USER_MAP.put(sessionId, userId);
  }

  public static Long getUser(String sessionId) {
    return USER_MAP.get(sessionId);
  }

  public static void removeUser(String sessionId) {
    Long userId = USER_MAP.get(sessionId);
    if (userId != null) {
      removeUserSession(userId, sessionId);
    }
    USER_MAP.remove(sessionId);
  }

  public static void addUserSession(AskBean askBean, long userId, WebSocketBean webSocketBean) {
    W_USER_LOCK.lock();
    try {
      Map<Long, Map<String, WebSocketBean>> sessionMap = SESSION_USER_MAP.get(askBean);
      if (sessionMap == null) {
        sessionMap = new HashMap<>();
        SESSION_USER_MAP.put(askBean, sessionMap);
      }
      Map<String, WebSocketBean> userMap = sessionMap.get(userId);
      if (userMap == null) {
        userMap = new HashMap<>();
        sessionMap.put(userId, userMap);
      }
      userMap.put(webSocketBean.getSession().getId(), webSocketBean);
    } finally {
      W_USER_LOCK.unlock();
    }
  }

  public static void removeUserSession(AskBean askBean, long userId, WebSocketBean webSocketBean) {
    W_USER_LOCK.lock();
    try {
      Map<Long, Map<String, WebSocketBean>> sessionMap = SESSION_USER_MAP.get(askBean);
      if (sessionMap != null) {
        Map<String, WebSocketBean> userMap = sessionMap.get(userId);
        if (userMap != null) {
          userMap.remove(webSocketBean.getSession().getId());
          if (userMap.size() == 0) {
            sessionMap.remove(userId);
          }
        }
        if (sessionMap.size() == 0) {
          SESSION_USER_MAP.remove(askBean);
        }
      }
    } finally {
      W_USER_LOCK.unlock();
    }
  }

  public static List<WebSocketBean> getUserSession(AskBean askBean, long userId) {
    R_USER_LOCK.lock();
    try {
      Map<Long, Map<String, WebSocketBean>> sessionMap;
      if (FunctionType.ORDERS.equals(askBean.getType())) {
        sessionMap = SESSION_USER_MAP
            .get(AskBean.builder().biz(askBean.getBiz()).type(FunctionType.ORDERS).build());
      } else if (FunctionType.CONDITION_ORDERS.equals(askBean.getType())) {
        sessionMap = SESSION_USER_MAP.get(
            AskBean.builder().biz(askBean.getBiz()).type(FunctionType.CONDITION_ORDERS).build());
      } else if (FunctionType.ASSETS.equals(askBean.getType())) {
        sessionMap = SESSION_USER_MAP
            .get(AskBean.builder().biz(askBean.getBiz()).type(FunctionType.ASSETS).build());
      } else {
        sessionMap = SESSION_USER_MAP.get(askBean);
      }
      if (Collections.isEmpty(sessionMap)) {
        return null;
      }
      Map<String, WebSocketBean> userMap = sessionMap.get(userId);
      if (Collections.isEmpty(userMap)) {
        return null;
      }
      final List<WebSocketBean> sessionList = new ArrayList<>();
      userMap.forEach((k, v) -> sessionList.add(v));
      return sessionList;
    } finally {
      R_USER_LOCK.unlock();
    }
  }

  public static void addSession(AskBean askBean, WebSocketBean webSocketBean) {
    W_LOCK.lock();
    try {
      Map<String, WebSocketBean> sessionMap = SESSION_MAP.get(askBean);
      if (sessionMap == null) {
        sessionMap = new HashMap<>();
        SESSION_MAP.put(askBean, sessionMap);
      }
      sessionMap.put(webSocketBean.getSession().getId(), webSocketBean);
    } finally {
      W_LOCK.unlock();
    }
  }

  public static void removeSession(AskBean askBean, WebSocketBean webSocketBean) {
    W_LOCK.lock();
    try {
      Map<String, WebSocketBean> sessionMap = SESSION_MAP.get(askBean);
      if (sessionMap != null) {
        sessionMap.remove(webSocketBean.getSession().getId());
        if (sessionMap.size() == 0) {
          SESSION_MAP.remove(askBean);
        }
      }
    } finally {
      W_LOCK.unlock();
    }
  }

  public static List<WebSocketBean> getSession(AskBean askBean) {
    R_LOCK.lock();
    try {
      Map<String, WebSocketBean> sessionMap = SESSION_MAP.get(askBean);
      if (Collections.isEmpty(sessionMap)) {
        return null;
      }
      final List<WebSocketBean> sessionList = new ArrayList<>();
      sessionMap.forEach((k, v) -> sessionList.add(v));
      return sessionList;
    } finally {
      R_LOCK.unlock();
    }
  }

  /**
   * idle缓存
   *
   * @author xionghui
   * @date 2018/09/18
   */
  @Data
  @Builder
  private static class IdleCheckBean {
    long time;
    String ip;
    UnicastProcessor<WebSocketMessage> processor;
  }
}
