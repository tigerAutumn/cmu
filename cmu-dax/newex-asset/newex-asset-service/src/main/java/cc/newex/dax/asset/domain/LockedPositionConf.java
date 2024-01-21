package cc.newex.dax.asset.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 锁仓配置表
 * @author newex-team
 * @date 2018-07-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LockedPositionConf {
	/**
	 *
     */
	private Long id;
	/**
     * 锁仓类型
     */
	private String lockPositionName;
	/**
     * 币种
     */
	private Integer currency;
	/**
     * 锁仓数量
     */
	private BigDecimal amount;
	/**
     * 是否参与分红
     */
	private Integer dividend;
	/**
     * 描述
     */
	private String remark;
	/**
	 * 解锁时间
	 */
	private String releaseDate;
	/**
	 * 解锁类型
	 */
	private Integer releaseType;
	/**
	 *
	 */
	private Date createDate;
	/**
	 *
	 */
	private Date updateDate;
		
}