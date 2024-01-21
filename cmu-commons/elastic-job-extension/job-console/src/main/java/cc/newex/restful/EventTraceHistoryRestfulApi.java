/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package cc.newex.restful;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import cc.newex.service.EventTraceDataSourceConfigurationService;
import cc.newex.service.impl.EventTraceDataSourceConfigurationServiceImpl;
import com.alibaba.druid.pool.DruidDataSource;

import com.dangdang.ddframe.job.event.rdb.JobEventRdbSearch;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbSearch.Condition;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbSearch.Result;
import com.dangdang.ddframe.job.event.type.JobExecutionEvent;
import com.dangdang.ddframe.job.event.type.JobStatusTraceEvent;
import com.google.common.base.Strings;
import cc.newex.domain.EventTraceDataSourceConfiguration;
import cc.newex.util.SessionEventTraceDataSourceConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 事件追踪历史记录的RESTful API.
 *
 * @author zhangxinguo
 */
@RestController
@RequestMapping("/api/event-trace")
public final class EventTraceHistoryRestfulApi {

    private EventTraceDataSourceConfiguration eventTraceDataSourceConfiguration = SessionEventTraceDataSourceConfiguration
        .getEventTraceDataSourceConfiguration();

    private EventTraceDataSourceConfigurationService eventTraceDataSourceConfigurationService = new EventTraceDataSourceConfigurationServiceImpl();

    /**
     * 查询作业执行事件.
     *
     * @param uriInfo 查询条件
     * @return 运行痕迹事件结果集
     * @throws ParseException 解析异常
     */
    @RequestMapping(value ="/execution", method = RequestMethod.GET,consumes = APPLICATION_JSON_VALUE,produces = APPLICATION_JSON_VALUE)
    public Result<JobExecutionEvent> findJobExecutionEvents(final HttpServletRequest uriInfo) throws ParseException {
        if (!eventTraceDataSourceConfigurationService.loadActivated().isPresent()) {
            return new Result<>(0, new ArrayList<JobExecutionEvent>());
        }
        JobEventRdbSearch jobEventRdbSearch = new JobEventRdbSearch(setUpEventTraceDataSource());
        return jobEventRdbSearch.findJobExecutionEvents(buildCondition(uriInfo, new String[]{"jobName", "ip", "isSuccess"}));
    }

    /**
     * 查询作业状态事件.
     *
     * @param uriInfo 查询条件
     * @return 运行痕迹事件结果集
     * @throws ParseException 解析异常
     */
    @RequestMapping(value ="/status", method = RequestMethod.GET,consumes = APPLICATION_JSON_VALUE,produces = APPLICATION_JSON_VALUE)
    public Result<JobStatusTraceEvent> findJobStatusTraceEvents(final HttpServletRequest uriInfo) throws ParseException {
        if (!eventTraceDataSourceConfigurationService.loadActivated().isPresent()) {
            return new Result<>(0, new ArrayList<JobStatusTraceEvent>());
        }
        JobEventRdbSearch jobEventRdbSearch = new JobEventRdbSearch(setUpEventTraceDataSource());
        return jobEventRdbSearch.findJobStatusTraceEvents(buildCondition(uriInfo, new String[]{"jobName", "source", "executionType", "state"}));
    }

    private DataSource setUpEventTraceDataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(eventTraceDataSourceConfiguration.getUrl());
        datasource.setDriverClassName(eventTraceDataSourceConfiguration.getDriver());
        datasource.setUsername(eventTraceDataSourceConfiguration.getUsername());
        datasource.setPassword(eventTraceDataSourceConfiguration.getPassword());
        datasource.setTestOnBorrow(true);
        return datasource;
    }

    private Condition buildCondition(final HttpServletRequest info, final String[] params) throws ParseException {
        int perPage = 10;
        int page = 1;
        if (!Strings.isNullOrEmpty(info.getParameterMap().get("per_page")[0])) {
            perPage = Integer.parseInt(info.getParameterMap().get("per_page")[0]);
        }
        if (!Strings.isNullOrEmpty(info.getParameterMap().get("page")[0])) {
            page = Integer.parseInt(info.getParameterMap().get("page")[0]);
        }
        String sort = info.getParameterMap().get("sort")[0];
        String order = info.getParameterMap().get("order")[0];
        Date startTime = null;
        Date endTime = null;
        Map<String, Object> fields = getQueryParameters(info, params);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (!Strings.isNullOrEmpty(info.getParameterMap().get("startTime")[0])) {
            startTime = simpleDateFormat.parse(info.getParameterMap().get("startTime")[0]);
        }
        if (!Strings.isNullOrEmpty(info.getParameterMap().get("endTime")[0])) {
            endTime = simpleDateFormat.parse(info.getParameterMap().get("endTime")[0]);
        }
        return new Condition(perPage, page, sort, order, startTime, endTime, fields);
    }

    private Map<String, Object> getQueryParameters(final HttpServletRequest info, final String[] params) {
        final Map<String, Object> result = new HashMap<>();
        for (String each : params) {
            if (!Strings.isNullOrEmpty(info.getParameterMap().get(each)[0])) {
                result.put(each, info.getParameterMap().get(each)[0]);
            }
        }
        return result;
    }
}
