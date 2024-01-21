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

import java.util.Collection;


import cc.newex.service.JobAPIService;
import cc.newex.service.impl.JobAPIServiceImpl;
import com.dangdang.ddframe.job.lite.lifecycle.domain.JobBriefInfo;
import com.dangdang.ddframe.job.lite.lifecycle.domain.ShardingInfo;
import com.google.common.base.Optional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 作业维度操作的RESTful API.
 *
 * @author caohao
 */
@RestController
@RequestMapping("/api/jobs")
public final class JobOperationRestfulApi {
    
    private JobAPIService jobAPIService = new JobAPIServiceImpl();
    
    /**
     * 获取作业总数.
     * 
     * @return 作业总数
     */
    @RequestMapping(value = "/count",method = RequestMethod.GET)
    public int getJobsTotalCount() {
        return jobAPIService.getJobStatisticsAPI().getJobsTotalCount();
    }
    
    /**
     * 获取作业详情.
     * 
     * @return 作业详情集合
     */
    @RequestMapping(value = "/count",method = RequestMethod.GET,produces = APPLICATION_JSON_VALUE)
    public Collection<JobBriefInfo> getAllJobsBriefInfo() {
        return jobAPIService.getJobStatisticsAPI().getAllJobsBriefInfo();
    }
    
    /**
     * 触发作业.
     * 
     * @param jobName 作业名称
     */
    @RequestMapping(value = "/{jobName}/trigger",method = RequestMethod.POST,produces = APPLICATION_JSON_VALUE)
    public void triggerJob(@PathVariable("jobName") final String jobName) {
        jobAPIService.getJobOperatorAPI().trigger(Optional.of(jobName), Optional.<String>absent());
    }
    
    /**
     * 禁用作业.
     * 
     * @param jobName 作业名称
     */
    @RequestMapping(value = "/{jobName}/disable",method = RequestMethod.POST,consumes = APPLICATION_JSON_VALUE)
    public void disableJob(@PathVariable("jobName") final String jobName) {
        jobAPIService.getJobOperatorAPI().disable(Optional.of(jobName), Optional.<String>absent());
    }
    
    /**
     * 启用作业.
     *
     * @param jobName 作业名称
     */
    @RequestMapping(value = "/{jobName}/disable",method = RequestMethod.DELETE,consumes = APPLICATION_JSON_VALUE)
    public void enableJob(@PathVariable("jobName") final String jobName) {
        jobAPIService.getJobOperatorAPI().enable(Optional.of(jobName), Optional.<String>absent());
    }
    
    /**
     * 终止作业.
     * 
     * @param jobName 作业名称
     */
    @RequestMapping(value = "/{jobName}/shutdown",method = RequestMethod.POST,consumes = APPLICATION_JSON_VALUE)
    public void shutdownJob(@PathVariable("jobName") final String jobName) {
        jobAPIService.getJobOperatorAPI().shutdown(Optional.of(jobName), Optional.<String>absent());
    }
    
    /**
     * 获取分片信息.
     * 
     * @param jobName 作业名称
     * @return 分片信息集合
     */
    @RequestMapping(value = "/{jobName}/sharding",method = RequestMethod.GET,produces = APPLICATION_JSON_VALUE)
    public Collection<ShardingInfo> getShardingInfo(@PathVariable("jobName") final String jobName) {
        return jobAPIService.getShardingStatisticsAPI().getShardingInfo(jobName);
    }
    

    @RequestMapping(value = "/{jobName}/sharding/{item}/disable",method = RequestMethod.POST,consumes = APPLICATION_JSON_VALUE)
    public void disableSharding(@PathVariable("jobName") final String jobName, @PathVariable("item") final String item) {
        jobAPIService.getShardingOperateAPI().disable(jobName, item);
    }

    @RequestMapping(value = "/{jobName}/sharding/{item}/disable",method = RequestMethod.DELETE,consumes = APPLICATION_JSON_VALUE)
    public void enableSharding(@PathVariable("jobName") final String jobName, @PathVariable("item") final String item) {
        jobAPIService.getShardingOperateAPI().enable(jobName, item);
    }
}
