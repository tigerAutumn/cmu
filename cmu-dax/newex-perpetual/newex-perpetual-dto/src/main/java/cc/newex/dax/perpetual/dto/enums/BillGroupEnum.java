package cc.newex.dax.perpetual.dto.enums;

/**
 * @author harry
 * @Date: 2018/5/30 17:03
 * @Description:
 */
public enum BillGroupEnum {
    GROUP_1(1,"开多","LONG"),
    GROUP_2(2,"开空","SHORT"),
    GROUP_3(3,"转入转出","DEPOSIT"),
    GROUP_4(4,"收益","RPL"),
    GROUP_5(5,"爆仓","LIQUIDATION"),
    ;
    private int groupNum;
    private String groupValCh;//中文
    private String groupValEn;//中文

    public int getGroupNum() {
        return groupNum;
    }

    public String getGroupValCh() {
        return groupValCh;
    }

    public String getGroupValEn() {
        return groupValEn;
    }

    BillGroupEnum(int groupNum, String groupValCh, String groupValEn){
        this.groupNum=groupNum;
        this.groupValCh=groupValCh;
        this.groupValEn=groupValEn;
    }


}
