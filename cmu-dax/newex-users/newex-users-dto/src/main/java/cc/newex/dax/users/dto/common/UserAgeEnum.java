package cc.newex.dax.users.dto.common;

/**
 * @author gi
 * @date 9/27/18
 */
public enum  UserAgeEnum {
    LESS("less","小于18",1),
    BETWEEN("between","大于18小于60",2),
    MORE("more","大于60",3);

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getCode() {
        return code;
    }

    private String name;
    private String desc;
    private Integer code;

    UserAgeEnum(String name,String desc,Integer code){
        this.name = name;
        this.desc = desc;
        this.code = code;
    }
}
