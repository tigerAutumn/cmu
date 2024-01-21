package cc.newex.dax.integration.dto.ip;

import lombok.Data;

/**
 * author: lingqing.wan
 * date: 2018-04-02 下午6:20
 */
@Data
public class IpLocationDTO {
    //{"code":0,"data":{"area":"","area_id":"","city":"","city_id":"","country":"香港","country_id":"HK","county":"",
    // "county_id":"","ip":"182.16.102.188","isp":"","isp_id":"","region":"香港特别行政区","region_id":"810000"}}

    private String area;
    private String areaId;
    private String city;
    private String cityId;
    private String country;
    private String countryId;
    private String county;
    private String countyId;
    private String region;
    private String regionId;
    private String isp;
    private String ispId;
}
