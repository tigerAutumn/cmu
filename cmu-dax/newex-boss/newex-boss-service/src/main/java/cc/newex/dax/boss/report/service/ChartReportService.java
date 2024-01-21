package cc.newex.dax.boss.report.service;

import cc.newex.commons.lang.pair.TextValuePair;
import cc.newex.commons.report.engine.data.ReportDataSet;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * 图表报表服务类
 *
 * @author newex-team
 */
public interface ChartReportService {
    /**
     * @param reportDataSet
     * @return
     */
    Map<String, List<TextValuePair>> getDimColumnMap(ReportDataSet reportDataSet);

    /**
     * @param reportDataSet
     * @return
     */
    JSONArray getStatColumns(ReportDataSet reportDataSet);

    /**
     * @param reportDataSet
     * @return
     */
    JSONArray getDimColumns(ReportDataSet reportDataSet);

    /**
     * @param reportDataSet
     * @return
     */
    Map<String, JSONObject> getDataRows(ReportDataSet reportDataSet);
}
