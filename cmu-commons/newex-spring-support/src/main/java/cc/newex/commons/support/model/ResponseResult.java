package cc.newex.commons.support.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * ResponseBody注解返回的JSON对象类
 *
 * @author newex-team
 * @date 2017/12/09
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult<T> implements Serializable {
    /**
     * 0表示成功，>0表示失败,<0系统保留
     */
    private int code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;
}
