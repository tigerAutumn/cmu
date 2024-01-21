package cc.newex.dax.users.common.enums;

/**
 * 文件类型：图片，视频，文件
 *
 * @author newex-team
 * @date 2018/8/16
 */
public enum FileTypeEnum {
    /**
     * kyc2
     */
    KYC2("kyc2"),
    /**
     * 图片
     */
    IMAGE("image"),
    /**
     * 视频
     */
    VIDEO("video"),
    /**
     * 文件
     */
    FILE("file"),;

    private final String type;

    FileTypeEnum(final String type) {
        this.type = type;
    }

    public static boolean checkType(final String type) {
        for (final FileTypeEnum fileTypeEnum : FileTypeEnum.values()) {
            if (fileTypeEnum.type.equals(type)) {
                return true;
            }
        }
        return false;
    }

    public String getType() {
        return this.type;
    }
}
