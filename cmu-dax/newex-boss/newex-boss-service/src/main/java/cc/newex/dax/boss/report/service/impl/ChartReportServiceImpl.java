package cc.newex.dax.boss.report.service.impl;

import cc.newex.commons.lang.pair.TextValuePair;
import cc.newex.commons.report.engine.data.ReportDataCell;
import cc.newex.commons.report.engine.data.ReportDataColumn;
import cc.newex.commons.report.engine.data.ReportDataRow;
import cc.newex.commons.report.engine.data.ReportDataSet;
import cc.newex.dax.boss.report.service.ChartReportService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * @author newex-team
 * @date 2017-03-18
 */
@Service("chartReportService")
public class ChartReportServiceImpl implements ChartReportService {
    @Override
    public Map<String, List<TextValuePair>> getDimColumnMap(final ReportDataSet reportDataSet) {
        final Map<String, List<String>> map = reportDataSet.getUnduplicatedNonStatColumnDataMap();
        if (map.size() < 1) {
            return new HashMap<>(0);
        }

        final List<ReportDataColumn> nonStatColumns = reportDataSet.getNonStatColumns();
        final Map<String, List<TextValuePair>> dimColumnMap = new HashMap<>(nonStatColumns.size());
        for (final ReportDataColumn column : nonStatColumns) {
            final List<TextValuePair> options = new ArrayList<>(map.get(column.getName()).size() + 1);
            options.add(new TextValuePair("全部", "all", true));
            options.addAll(map.get(column.getName()).stream()
                .map(columnValue -> new TextValuePair(columnValue, columnValue)).collect(Collectors.toList()));
            dimColumnMap.put(column.getName(), options);
        }
        return dimColumnMap;
    }

    @Override
    public JSONArray getStatColumns(final ReportDataSet reportDataSet) {
        return this.getJsonArray(reportDataSet.getStatColumns());
    }

    @Override
    public JSONArray getDimColumns(final ReportDataSet reportDataSet) {
        return this.getJsonArray(reportDataSet.getNonStatColumns());
    }

    private JSONArray getJsonArray(final List<ReportDataColumn> columns) {
        final JSONArray jsonArray = new JSONArray(columns.size());
        for (final ReportDataColumn column : columns) {
            final JSONObject object = new JSONObject();
            object.put("name", column.getName());
            object.put("text", column.getText());
            jsonArray.add(object);
        }
        return jsonArray;
    }

    @Override
    public Map<String, JSONObject> getDataRows(final ReportDataSet reportDataSet) {
        final Map<String, ReportDataRow> dataRows = reportDataSet.getRowMap();
        final List<ReportDataColumn> statColumns = reportDataSet.getStatColumns();
        final Map<String, JSONObject> rowMap = new HashMap<>(dataRows.size());

        for (final Entry<String, ReportDataRow> set : dataRows.entrySet()) {
            final JSONObject object = new JSONObject(statColumns.size());
            for (final ReportDataColumn statColumn : statColumns) {
                final ReportDataCell cell = set.getValue().getCell(statColumn.getName());
                final Object value = cell.getValue();
                object.put(cell.getName(), value == null ? 0 : value);
            }
            rowMap.put(set.getKey(), object);
        }
        return rowMap;
    }
}
