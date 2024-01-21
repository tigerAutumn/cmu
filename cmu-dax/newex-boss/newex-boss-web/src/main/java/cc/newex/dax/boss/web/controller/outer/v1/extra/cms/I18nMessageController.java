package cc.newex.dax.boss.web.controller.outer.v1.extra.cms;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.model.extra.cms.I18nMessageExtraVO;
import cc.newex.dax.extra.client.ExtraCmsI18nAdminClient;
import cc.newex.dax.extra.dto.cms.I18nLanguageDTO;
import cc.newex.dax.extra.dto.cms.I18nMessageDTO;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author liutiejun
 * @date 2018-06-07
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/extra/cms/i18n/messages")
public class I18nMessageController {

    @Autowired
    private ExtraCmsI18nAdminClient extraCmsI18nAdminClient;

    @RequestMapping(value = "/add")
    @OpLog(name = "新增I18nMessage")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_I18N_MESSAGE_ADD"})
    public ResponseResult add(final I18nMessageDTO dto) {
        final ResponseResult result = this.extraCmsI18nAdminClient.saveI18nMessage(dto);
        return ResultUtil.getCheckedResponseResult(result);

    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改I18nMessage")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_I18N_MESSAGE_EDIT"})
    public ResponseResult edit(@Valid final I18nMessageDTO i18nMessageDTO) {
        final ResponseResult result;
        if (Objects.nonNull(i18nMessageDTO.getId())) {
            result = this.extraCmsI18nAdminClient.updateI18nMessage(i18nMessageDTO);
        } else {
            result = this.extraCmsI18nAdminClient.saveI18nMessage(i18nMessageDTO);
        }
        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取I18nMessage列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_I18N_MESSAGE_VIEW"})
    public ResponseResult list(@RequestParam(value = "msgText", required = false) final String msgText, final DataGridPager<I18nMessageDTO> pager) {
        try {
            final I18nMessageDTO.I18nMessageDTOBuilder builder = I18nMessageDTO.builder();

            if (StringUtils.isNotBlank(msgText)) {
                builder.msgText(msgText);
            }

            final I18nMessageDTO i18nMessageDTO = builder.build();
            pager.setQueryParameter(i18nMessageDTO);
            final ResponseResult responseResult = this.extraCmsI18nAdminClient.listI18nMessage(pager);
            return ResultUtil.getCheckedResponseResult(responseResult);
        } catch (final Exception e) {
            log.error("get i18nMessage list api error: " + e.getMessage(), e);
        }

        return ResultUtils.success();
    }

    @GetMapping(value = "/list/all")
    @OpLog(name = "获取I18nMessage列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CMS_I18N_MESSAGE_VIEW"})
    public Object listAll() {
        try {
            final ResponseResult responseResult = this.extraCmsI18nAdminClient.listAllI18nMessage();
            if (responseResult == null) {
                return new ArrayList<I18nMessageExtraVO>();
            }
            final Object data = responseResult.getData();
            if (data == null) {
                return new ArrayList<I18nMessageExtraVO>();
            }
            return data;
        } catch (final Exception e) {
            log.error("get i18nMessage list api error: " + e.getMessage(), e);
        }
        return new ArrayList<I18nMessageExtraVO>();
    }

    @GetMapping(value = "/message")
    public ResponseResult message(@RequestParam(value = "codeId") final Integer codeId,
                                  @RequestParam(value = "code") final String code) {
        final ResponseResult language = this.extraCmsI18nAdminClient.listAllI18nLanguage();
        final ModelMapper mapper = new ModelMapper();
        final List<I18nLanguageDTO> res = mapper.map(language.getData(),
                new TypeToken<List<I18nLanguageDTO>>() {
                }.getType()
        );

        final List<I18nMessageDTO> result = new ArrayList<>();
        final ResponseResult messages = this.extraCmsI18nAdminClient.getI18nMessageByCode(code);
        final List<I18nMessageDTO> messageList = mapper.map(messages.getData(),
                new TypeToken<List<I18nMessageDTO>>() {
                }.getType()
        );
        final Map<String, I18nMessageDTO> messageMap = messageList.stream()
                .collect(Collectors.toMap(I18nMessageDTO::getLocale, Function.identity()));
        res.forEach(v -> {
            final I18nMessageDTO md = messageMap.get(v.getCode());
            final I18nMessageDTO msg = I18nMessageDTO.builder()
                    .locale(v.getCode())
                    .msgCodeId(codeId)
                    .msgCode(code)
                    .build();
            if (Objects.nonNull(md)) {
                msg.setId(md.getId());
                msg.setMsgText(md.getMsgText());
            }
            result.add(msg);
        });

        return ResultUtils.success(result);
    }

    @GetMapping(value = "/codeTree")
    public JSONArray codeTree(@RequestParam(value = "parentId") final Integer parentId) {
        final ResponseResult source = this.extraCmsI18nAdminClient.getMessageCodeByParentId(parentId);
        if (source.getCode() == 0 && source.getData() != null) {
            return (JSONArray) source.getData();
        }
        return null;
    }

    @GetMapping(value = "/generate")
    public ResponseResult generate(final HttpServletRequest request, final HttpServletResponse response) throws FileNotFoundException {
        final ResponseResult source = this.extraCmsI18nAdminClient.download();
        if (source.getCode() != 0 || source.getData() == null) {
            log.info("error");
            return ResultUtils.failure("error");
        }
        final Map<String, List<I18nMessageDTO>> mapList = JSONObject.parseObject(
                source.getData().toString(),
                new TypeToken<HashMap<String, List<I18nMessageDTO>>>() {
                }.getType()
        );
        final URL directory = ResourceUtils.getURL("classpath:");
        mapList.keySet().forEach(v -> {
            final List<I18nMessageDTO> vs = mapList.get(v);
            final File file = new File(directory.getPath() + "/static/lang/" + v + ".js");
            final FileOutputStream fileOutputStream;
            try {
                fileOutputStream = new FileOutputStream(file);
                final FileOutputStream finalFileOutputStream = fileOutputStream;
                vs.forEach(b -> {
                    try {
                        finalFileOutputStream.write((b.getMsgCode() + "=" + b.getMsgText()).getBytes());
                        finalFileOutputStream.write("\r\n".getBytes());

                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                });
                try {
                    finalFileOutputStream.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            } catch (final FileNotFoundException e) {
                e.printStackTrace();
            }

        });
        return ResultUtils.success();
    }

    @RequestMapping(value = "/download")
    public void download(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final File directory = ResourceUtils.getFile("classpath:static/lang");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/zip;charset=utf-8");
        response.setHeader("Content-Disposition", "Attachment;Filename=lang.zip");
        final Collection<File> files = FileUtils.listFiles(directory, null, true);
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(response.getOutputStream());
            for (final File f : files) {
                final String fileNameInZIP = directory.toURI().relativize(f.toURI()).getPath();
                final ZipEntry zipEntry = new ZipEntry(fileNameInZIP);
                zos.putNextEntry(zipEntry);
                final FileInputStream fileInputStream = new FileInputStream(f);
                try {
                    IOUtils.copy(fileInputStream, zos);
                } finally {
                    IOUtils.closeQuietly(fileInputStream);
                }
            }
        } finally {
            IOUtils.closeQuietly(zos);
        }
    }

}
