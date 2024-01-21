package com.xxl.job.admin.consts;

/**
 * @author minhan
 * @Date: 2018/7/19 15:36
 * @Description:
 */
public enum OperationEnum {
    ADD("新增"),
    EDIT("修改"),
    DELETE("删除"),
    PAUSE("暂停"),
    EXECUTE("执行"),
    RESUME("恢复"),
    TRIGGER("执行一次"),
    ;

    public String name;
    private OperationEnum(String name){
        this.name = name;
    }
}
