package cc.newex.dax.integration.domain.ip;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class IpLocation {
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
