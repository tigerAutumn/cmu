package cc.newex.dax.market.domain;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author 
 * @date 2018/03/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MarketProperties {
		/**
     * 
     */
	 private Long id; 
		/**
     * 查询KEY
     */
	 private String propKey; 
		/**
     * 配置信息为JSON数据
     */
	 private String propValue; 
		/**
     * 创建时间
     */
	 private Date createdDate; 
		/**
     * 更新时间
     */
	 private Date modifyDate; 
	}