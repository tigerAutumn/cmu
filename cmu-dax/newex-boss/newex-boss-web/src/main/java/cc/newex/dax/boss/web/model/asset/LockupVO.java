//package cc.newex.dax.boss.web.model.asset;
//
//import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.Date;
//
///**
// * 锁仓
// *
// * @author liutiejun
// * @date 2018-07-04
// */
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor(access = AccessLevel.PRIVATE)
//public class LockupVO {
//
//    private Integer id;
//
//    /**
//     * 锁仓用户
//     */
//    private String uid;
//
//    /**
//     * 锁仓类型
//     */
//    private Integer lockupType;
//
//    /**
//     * 锁仓币种
//     */
//    private Integer currencyType;
//
//    /**
//     * 锁仓数量
//     */
//    private Integer amount;
//
//    /**
//     * 备注说明
//     */
//    private String remark;
//
//    /**
//     * 创建时间
//     */
//    private Date createdDate;
//
//    /**
//     * 更新时间
//     */
//    private Date updatedDate;
//
//    public static LockupVO getInstance() {
//        return LockupVO.builder()
//                .id(0)
//                .uid("")
//                .lockupType(0)
//                .currencyType(0)
//                .amount(0)
//                .remark("")
//                .createdDate(new Date())
//                .updatedDate(new Date())
//                .build();
//    }
//
//}
