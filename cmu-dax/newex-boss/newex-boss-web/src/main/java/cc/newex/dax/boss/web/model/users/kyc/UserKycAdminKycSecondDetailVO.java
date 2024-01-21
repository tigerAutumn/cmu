//package cc.newex.dax.boss.web.model.users.kyc;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class UserKycAdminKycSecondDetailVO {
//    /**
//     * id
//     */
//    private Long id;
//    /**
//     * 用户 id
//     */
//    private Long userId;
//    /**
//     * 手机号
//     */
//    private String mobile;
//    /**
//     * 邮箱
//     */
//    private String email;
//    /**
//     * 姓
//     */
//    private String firstName;
//    /**
//     * 中间名
//     */
//    private String middleName;
//    /**
//     * 名
//     */
//    private String lastName;
//    /**
//     * 选择国家/地区，156是中国
//     */
//    private Integer country;
//    /**
//     * 证件类型1：身份证，2：护照，3：驾驶证
//     */
//    private String cardType;
//    /**
//     * 证件号码
//     */
//    private String cardNumber;
//    /**
//     * 性别1:男 2:女
//     */
//    private String gender;
//    /**
//     * 证件有效期
//     */
//    private String validDate;
//    /**
//     * 证件信息照片
//     */
//    private String cardImg;
//    /**
//     * 身份证正面照片
//     */
//    private String frontImg;
//    /**
//     * 身份证背面照片
//     */
//    private String backImg;
//    /**
//     * 手持证件照片
//     */
//    private String handsImg;
//    /**
//     * 住址证明照片
//     */
//    private String addressImg;
//    /**
//     * 地址 1
//     */
//    private String address1;
//    /**
//     * 地址 2
//     */
//    private String address2;
//    /**
//     * 地址 3
//     */
//    private String address3;
//    /**
//     * 城市
//     */
//    private String city;
//    /**
//     * 邮编
//     */
//    private String zipCode;
//    /**
//     * kyc等级
//     */
//    private Integer level;
//    /**
//     * kyc1状态 认证状态0：初始值，1：通过，2：驳回，3：其他异常
//     */
//    private Integer firstStatus;
//    /**
//     * kyc2状态 认证状态0：初始值，1：通过，2：驳回，3：其他异常
//     */
//    private Integer status;
//    /**
//     * 用户审核记录
//     */
//    @Builder.Default
//    private List<UserKycEventVO> userKycEventList = new ArrayList<>();
//
//    @Data
//    @Builder
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class UserKycEventVO implements Serializable {
//        /**
//         * 认证备注信息(内部查看)
//         */
//        private String remarks;
//        /**
//         * 驳回原因(用户查看)
//         */
//        private String rejectReason;
//        /**
//         * 受理客服id
//         */
//        private Long dealUserId;
//        /**
//         * 受理客服姓名
//         */
//        private String dealUserName;
//        /**
//         * 处理时间
//         */
//        private Date createdDate;
//    }
//}
