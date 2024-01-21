package cc.newex.dax.boss.web.common.enums;

/**
 * @author newex-team
 * @date 2018/11/27 14:10
 */
public enum LockAssetTypeEnum {

    LUCKWIN(1,"luckwin"),
    UNLOCK_NOT_BY_STAGES(2,"一次性解锁"),
    UNLOCK_BY_STAGES(3,"分期解锁");

    private String comment;
    private Integer type;
    LockAssetTypeEnum(Integer type,String comment){

        this.type = type;
        this.comment = comment;
    }

    public Integer getType(){
        return this.type;
    }

    public String getComment(){
        return this.comment;
    }

}
