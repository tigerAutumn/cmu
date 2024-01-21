package cc.newex.dax.market.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author 
 * @date 2018/03/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MarketRate {
	/**
     * 
     */
	 private Long id;
		/**
     * 汇率名
     */
	 private String rateName; 
		/**
     * 
     */
	 private Date modifyTime; 
		/**
     * 1可用0不可用
     */
	 private Integer status; 
		/**
     * 
     */
	 private Date createTime; 
		/**
     * 汇率
     */
	 private Double rateParities; 
	}