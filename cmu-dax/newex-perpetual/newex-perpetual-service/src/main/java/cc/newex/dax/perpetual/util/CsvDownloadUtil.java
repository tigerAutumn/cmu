package cc.newex.dax.perpetual.util;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import com.alibaba.fastjson.util.IOUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 下载CSV文件
 *
 * @author liutiejun
 * @date 2018-09-28
 */
public class CsvDownloadUtil {

    public static ResponseResult download(final String fileName, final String content, final HttpServletResponse response) {
        OutputStream outputStream = null;

        try {
            response.setContentType("text/csv;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);

            outputStream = response.getOutputStream();

            //加上UTF-8文件的标识字符，以保证csv文件可以用Excel、Numbers均正常打开
            outputStream.write(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF});

            outputStream.write(content.getBytes());

            outputStream.flush();
        } catch (final IOException e) {
            return ResultUtils.failure("download error");
        } finally {
            IOUtils.close(outputStream);
        }

        return null;
    }

}
