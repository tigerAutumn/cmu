package cc.newex.dax.extra.rest.controller.admin.v1.cms;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.criteria.cms.ImgInfoExample;
import cc.newex.dax.extra.domain.cms.ImgInfo;
import cc.newex.dax.extra.dto.cms.ImgInfoDTO;
import cc.newex.dax.extra.service.admin.cms.ImgInfoAdminService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author liutiejun
 * @date 2018-08-08
 */
@Slf4j
@RestController
@RequestMapping(value = "/admin/v1/extra/cms/news/img-info")
public class ImgInfoAdminController {

    @Autowired
    private ImgInfoAdminService imgInfoAdminService;

    /**
     * 保存ImgInfo
     *
     * @param imgInfoDTO
     * @return
     */
    @PostMapping(value = "/saveImgInfo")
    public ResponseResult saveImgInfo(@RequestBody final ImgInfoDTO imgInfoDTO) {
        final ModelMapper mapper = new ModelMapper();
        final ImgInfo imgInfo = mapper.map(imgInfoDTO, ImgInfo.class);

        final int save = this.imgInfoAdminService.add(imgInfo);

        return ResultUtils.success(save);
    }

    /**
     * 更新ImgInfo
     *
     * @param imgInfoDTO
     * @return
     */
    @PostMapping(value = "/updateImgInfo")
    public ResponseResult updateImgInfo(@RequestBody final ImgInfoDTO imgInfoDTO) {
        final ModelMapper mapper = new ModelMapper();
        final ImgInfo imgInfo = mapper.map(imgInfoDTO, ImgInfo.class);

        final int update = this.imgInfoAdminService.editById(imgInfo);

        return ResultUtils.success(update);
    }

    /**
     * 删除ImgInfo
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/removeImgInfo")
    public ResponseResult removeImgInfo(@RequestParam("id") final Long id) {
        final int remove = this.imgInfoAdminService.removeById(id);

        return ResultUtils.success(remove);
    }

    /**
     * List ImgInfo
     *
     * @return
     */
    @PostMapping(value = "/listImgInfo")
    public ResponseResult listImgInfo(@RequestBody final DataGridPager<ImgInfoDTO> pager) {
        final PageInfo pageInfo = pager.toPageInfo();

        final ModelMapper mapper = new ModelMapper();
        final ImgInfo imgInfo = mapper.map(pager.getQueryParameter(), ImgInfo.class);
        final ImgInfoExample example = ImgInfoExample.toExample(imgInfo);

        final List<ImgInfo> list = this.imgInfoAdminService.getByPage(pageInfo, example);
        final List<ImgInfoDTO> imgInfoDTOS = mapper.map(
                list, new TypeToken<List<ImgInfoDTO>>() {
                }.getType()
        );
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), imgInfoDTOS));
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getImgInfoById")
    public ResponseResult getImgInfoById(@RequestParam("id") final Long id) {
        final ImgInfo imgInfo = this.imgInfoAdminService.getById(id);

        final ModelMapper mapper = new ModelMapper();
        final ImgInfoDTO imgInfoDTO = mapper.map(imgInfo, ImgInfoDTO.class);

        return ResultUtils.success(imgInfoDTO);
    }

}
