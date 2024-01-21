package cc.newex.maker.utils;

import lombok.Data;

/**
 * Created by wj on 2018/4/27.
 */
@Data
public class ResponseResult<T> {
    private int code;
    private String msg;
    private String detailMsg;
    private T data;
}
