package cc.newex.dax.extra.rest.controller.outer.v1.common.cms;

import cc.newex.commons.fileupload.FileUploadService;
import cc.newex.commons.support.i18n.LocaleUtils;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.common.config.CommonProperties;
import cc.newex.dax.extra.criteria.cms.DappsInfoExample;
import cc.newex.dax.extra.domain.cms.DappsInfo;
import cc.newex.dax.extra.dto.cms.DAppsDTO;
import cc.newex.dax.extra.rest.common.util.BrokerUtils;
import cc.newex.dax.extra.service.cms.DappsInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/v1/extra/cms/dapps")
public class DAppsInfoController {

    @Autowired
    private DappsInfoService dappsInfoService;

    @Resource
    private CommonProperties commonProperties;

    @Autowired
    private FileUploadService fileUploadService;

    @GetMapping(value = "/info")
    public ResponseResult<List<DAppsDTO>> getInfos(final HttpServletRequest request) {
        final Integer brokerId = BrokerUtils.getBrokerId(request);
        //获取正在上线的活动，status=1
        final String locale = LocaleUtils.getLocale();
        final DappsInfoExample example = new DappsInfoExample();
        final DappsInfoExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort desc");
        if (this.commonProperties.getOnline()) {
            //线上环境
            criteria.andStatusEqualTo(1);
        } else {
            //预发环境
            criteria.andStatusEqualTo(2);
        }
        criteria.andBrokerIdEqualTo(brokerId);
        criteria.andLocaleEqualTo(locale);
        final List<DappsInfo> result = this.dappsInfoService.getByExample(example);
        final List<DAppsDTO> dAppsDTOS = new ArrayList<>();
        result.forEach(v -> {
            final DAppsDTO vo = DAppsDTO.builder()
                    .id(v.getId())
                    .title(v.getTitle())
                    .appLink(v.getAppLink())
                    .pcLink(v.getPcLink())
                    .imageUrl(this.fileUploadService.getUrl(v.getImageUrl()))
                    .text(v.getText())
                    .product(v.getProduct().toLowerCase())
                    .currency(v.getCurrency().toLowerCase())
                    .sort(v.getSort())
                    .build();
            dAppsDTOS.add(vo);
        });
        return ResultUtils.success(dAppsDTOS);
    }
}
