package cc.newex.dax.boss.common.util;

import com.opencsv.CSVWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Objects;

/**
 * The type Csv utils.
 *
 * @author better
 * @date create in 2018/10/18 下午6:36
 */
@Slf4j
public class CsvUtils {

    /**
     * Write csv.
     *
     * @param headers csv头信息
     * @param out     输出流
     * @param data    写入的数据
     * @throws IOException the io exception
     */
    public static void writeCsv(final String[] headers, final OutputStream out, final List<String[]> data) throws IOException {

        CSVWriter csvWriter = null;
        try {
            csvWriter = new CSVWriter(new OutputStreamWriter(out), CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.DEFAULT_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.RFC4180_LINE_END);

            if (ObjectUtils.isEmpty(data)) {
                log.error("data is empty {}", data);
                return;
            }
            if (ArrayUtils.isNotEmpty(headers)) {
                csvWriter.writeNext(headers);
            }
            for (final Object[] record : data) {
                csvWriter.writeNext((String[]) record);
            }
        } finally {
            if (Objects.nonNull(csvWriter)) {
                csvWriter.flush();
                csvWriter.close();
            }
        }
    }
}
