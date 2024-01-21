package cc.newex.dax.asset.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author newex-team
 * @date 2018-06-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserAssetConf {
		/**
     * 
     */
	 private Long id; 
		/**
     * 用户id
     */
	 private Long userId; 
		/**
     * 提现限额（等价BTC）
     */
	 private BigDecimal withdrawLimit;
		/**
     * 创建时间
     */
	 private Date createDate; 
		/**
     * 更新时间
     */
	 private Date updateDate; 
		
}