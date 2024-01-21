package cc.newex.dax.boss.admin.service.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.boss.admin.criteria.PermissionExample;
import cc.newex.dax.boss.admin.data.PermissionRepository;
import cc.newex.dax.boss.admin.domain.Permission;
import cc.newex.dax.boss.admin.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 后台系统权限表 服务实现
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Slf4j
@Service
public class PermissionServiceImpl
        extends AbstractCrudService<PermissionRepository, Permission, PermissionExample, Integer>
        implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepos;

    private static final String ALL_PERMISSIONS = "all";
    private static final byte[] LOCK = new byte[0];
    private static Map<String, Permission> permissionMap;

    public PermissionServiceImpl() {
    }

    @PostConstruct
    private void loadCache() {
        synchronized (LOCK) {
            if (MapUtils.isEmpty(permissionMap)) {
                final List<Permission> list = this.permissionRepos.selectAllWithModulePath();
                permissionMap = new HashMap<>(list.size());
                for (final Permission permission : list) {
                    permissionMap.put(permission.getCode(), permission);
                }
            }
        }
    }

    @Override
    protected PermissionExample getPageExample(final String fieldName, final String keyword) {
        final PermissionExample example = new PermissionExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public void reloadCache() {
        if (permissionMap != null) {
            permissionMap.clear();
        }
        this.loadCache();
    }

    @Override
    public List<Permission> getByPage(final PageInfo pageInfo, final Integer moduleId) {
        final PermissionExample example = new PermissionExample();
        example.or().andModuleIdEqualTo(moduleId);
        return this.getByPage(pageInfo, example);
    }

    @Override
    public List<Permission> getByModuleId(final Integer moduleId) {
        final PermissionExample example = new PermissionExample();
        example.or().andModuleIdEqualTo(moduleId);
        return this.permissionRepos.selectByExample(example);
    }

    @Override
    public Map<String, String> getIdCodeMap() {
        final Map<String, String> idCodeMap = new HashMap<>(permissionMap.size());
        for (final Map.Entry<String, Permission> es : permissionMap.entrySet()) {
            idCodeMap.put(es.getValue().getId().toString(), es.getValue().getCode());
        }
        return idCodeMap;
    }

    @Override
    public String getPermissionIds(final String[] codes) {
        if (ArrayUtils.isEmpty(codes)) {
            return StringUtils.EMPTY;
        }

        final List<String> permIds = new ArrayList<>(codes.length);
        for (final String code : codes) {
            final String key = code.trim().toLowerCase();
            if (permissionMap.containsKey(key)) {
                permIds.add(String.valueOf(permissionMap.get(key).getId()));
            }
        }

        return StringUtils.join(permIds, ',');
    }

    @Override
    public String getModuleIds(final String permissionIds) {
        // 如果设置为所有权限
        if (permissionIds.contains(ALL_PERMISSIONS)) {
            return ALL_PERMISSIONS;
        }

        final Map<String, Permission> idMap = new HashMap<>(permissionMap.size());
        for (final Permission permission : permissionMap.values()) {
            idMap.put(permission.getId().toString(), permission);
        }

        final String[] idList = StringUtils.split(permissionIds, ',');

        final List<String> modulePathList = new ArrayList<>();

        for (final String id : idList) {
            final Permission permission = idMap.get(id);

            if (permission != null) {
                final String path = permission.getPath();

                log.info("moduleId: {}, path: {}", id, path);

                modulePathList.add(path);
            }
        }

        final String moduleIds = StringUtils.join(modulePathList, ',');

        log.info("moduleIds: {}", moduleIds);

        return this.distinct(StringUtils.split(moduleIds, ','));
    }

    private String distinct(final String[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            return StringUtils.EMPTY;
        }

        final Set<String> idSet = new HashSet<>(ids.length);

        Collections.addAll(idSet, ids);

        return StringUtils.join(idSet, ',');
    }
}