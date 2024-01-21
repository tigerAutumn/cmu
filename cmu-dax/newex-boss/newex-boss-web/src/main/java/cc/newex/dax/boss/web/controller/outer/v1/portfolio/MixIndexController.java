package cc.newex.dax.boss.web.controller.outer.v1.portfolio;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.model.portfolio.MixIndexVO;
import cc.newex.dax.market.client.admin.PortfolioClient;
import cc.newex.dax.market.dto.model.IndexPortfolioConfigDTO;
import cc.newex.dax.market.dto.request.PortfolioParam;
import cc.newex.dax.market.dto.result.CoinConfigDto;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author gi
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/portfolio/mix/index")
public class MixIndexController {
    @Autowired
    private PortfolioClient portfolioClient;

    @PostMapping(value = "/add")
    @OpLog(name = "新增混合指数")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_MIX_INDEX_ADD"})
    public ResponseResult add(@Valid final MixIndexVO vo) {
        log.info("vo: {}", vo.toString());
        //判断比列之和是否为1
        List<IndexPortfolioConfigDTO> list = JSON.parseArray(vo.getConfigs(), IndexPortfolioConfigDTO.class);
        double sum = list.stream().mapToDouble(IndexPortfolioConfigDTO::getRatio).sum();
        if (sum != 1) {
            return ResultUtils.failure("ratio is error");
        }
        PortfolioParam dto = PortfolioParam.builder().build();
        dto.setPortfolioConfigList(list);
        if (Objects.nonNull(vo.getSymbol())) {
            dto.setSymbol(vo.getSymbol());
        }
        if (StringUtils.isNotEmpty(vo.getSymbolName())) {
            dto.setSymbolName(vo.getSymbolName());
        }
        try {
            dto.setInitialDate((DateUtils.parseDate(vo.getInitialDate(), "yyyy-MM-dd HH:mm:ss")).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dto.setStatus(vo.getStatus());

        ResponseResult result = portfolioClient.create(dto);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping(value = "/edit")
    @OpLog(name = "修改混合指数")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_MIX_INDEX_EDIT"})
    public ResponseResult edit(@Valid final MixIndexVO vo) {
        log.info("vo: {}", vo.toString());
        //时间，状态，比例
        PortfolioParam dto = PortfolioParam.builder().build();
        dto.setId(vo.getId());
        List<IndexPortfolioConfigDTO> list = JSON.parseArray(vo.getConfigs(), IndexPortfolioConfigDTO.class);
        double sum = list.stream().mapToDouble(IndexPortfolioConfigDTO::getRatio).sum();
        if (sum != 1) {
            return ResultUtils.failure("ratio is error");
        }
        dto.setSymbol(vo.getSymbol());
        dto.setSymbolName(vo.getSymbolName());
        dto.setPortfolioConfigList(list);
        try {
            dto.setInitialDate((DateUtils.parseDate(vo.getInitialDate(), "yyyy-MM-dd HH:mm:ss")).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dto.setStatus(vo.getStatus());
        ResponseResult result = portfolioClient.update(dto);
        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "删除混合指数")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_MIX_INDEX_DELETE"})
    public ResponseResult remove(@RequestParam(value = "id") Long id) {
        return ResultUtils.success(id);
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取指数列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_MIX_INDEX_VIEW"})
    public ResponseResult list() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ResponseResult<List<CoinConfigDto>> result = portfolioClient.list();
        List<MixIndexVO> vos = new ArrayList<>();
        result.getData().forEach(v -> {
            MixIndexVO vo = new MixIndexVO();
            vo.setId(v.getId());
            vo.setSymbol(v.getSymbol());
            vo.setSymbolName(v.getSymbolName());
            vo.setInitialDate(sdf.format(new Date(v.getInitialDate())));
            vo.setStatus(v.getStatus());
            vo.setCurrency(v.getPortfolioConfigList().stream().map(IndexPortfolioConfigDTO::getSymbol).collect(Collectors.toList()));
            vo.setConfigs(JSONArray.toJSONString(v.getPortfolioConfigList()));
            vos.add(vo);
        });
        return ResultUtils.success(vos);
    }

    @GetMapping(value = "/currency")
    @OpLog(name = "获取币种信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_MIX_INDEX_VIEW"})
    public JSONArray currency() {
        JSONArray jsonArray = new JSONArray();
        ResponseResult<List<CoinConfigDto>> list = portfolioClient.coinList();
        if (list.getCode() == 0) {
            list.getData().forEach(v -> {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code", v.getSymbol());
                jsonObject.put("currency", v.getSymbolName());
                jsonArray.add(jsonObject);
            });
        }
        return jsonArray;
    }

}
