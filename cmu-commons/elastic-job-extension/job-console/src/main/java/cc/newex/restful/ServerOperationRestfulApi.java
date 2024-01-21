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
import com.dangdang.ddframe.job.lite.lifecycle.domain.ServerBriefInfo;
import com.google.common.base.Optional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 服务器维度操作的RESTful API.
 *
 * @author caohao
 */
@RestController
@RequestMapping("/api/servers")
public final class ServerOperationRestfulApi {
    
    private JobAPIService jobAPIService = new JobAPIServiceImpl();
    
    /**
     * 获取服务器总数.
     * 
     * @return 服务器总数
     */
    @RequestMapping(value = "/count",method = RequestMethod.GET)
    public int getServersTotalCount() {
        return jobAPIService.getServerStatisticsAPI().getServersTotalCount();
    }
    
    /**
     * 获取服务器详情.
     * 
     * @return 服务器详情集合
     */
    @RequestMapping(method = RequestMethod.GET,produces = APPLICATION_JSON_VALUE)
    public Collection<ServerBriefInfo> getAllServersBriefInfo() {
        return jobAPIService.getServerStatisticsAPI().getAllServersBriefInfo();
    }
    
    /**
     * 禁用作业.
     *
     * @param serverIp 服务器IP地址
     */
    @RequestMapping(value = "/{serverIp}/disable",method = RequestMethod.POST)
    public void disableServer(@PathVariable("serverIp") final String serverIp) {
        jobAPIService.getJobOperatorAPI().disable(Optional.<String>absent(), Optional.of(serverIp));
    }
    
    /**
     * 启用作业.
     *
     * @param serverIp 服务器IP地址
     */
    @RequestMapping(value = "/{serverIp}/disable",method = RequestMethod.DELETE)
    public void enableServer(@PathVariable("serverIp") final String serverIp) {
        jobAPIService.getJobOperatorAPI().enable(Optional.<String>absent(), Optional.of(serverIp));
    }
    
    /**
     * 终止作业.
     *
     * @param serverIp 服务器IP地址
     */
    @RequestMapping(value = "/{serverIp}/shutdown",method = RequestMethod.POST)
    public void shutdownServer(@PathVariable("serverIp") final String serverIp) {
        jobAPIService.getJobOperatorAPI().shutdown(Optional.<String>absent(), Optional.of(serverIp));
    }
    
    /**
     * 清理作业.
     *
     * @param serverIp 服务器IP地址
     */
    @RequestMapping(value = "/{serverIp}",method = RequestMethod.DELETE)
    public void removeServer(@PathVariable("serverIp") final String serverIp) {
        jobAPIService.getJobOperatorAPI().remove(Optional.<String>absent(), Optional.of(serverIp));
    }
    
    /**
     * 获取该服务器上注册的作业的简明信息.
     *
     * @param serverIp 服务器IP地址
     * @return 作业简明信息对象集合
     */
    @RequestMapping(value = "/{serverIp}/jobs",method = RequestMethod.GET,produces = APPLICATION_JSON_VALUE)
    public Collection<JobBriefInfo> getJobs(@PathVariable("serverIp") final String serverIp) {
        return jobAPIService.getJobStatisticsAPI().getJobsBriefInfo(serverIp);
    }
    
    /**
     * 禁用作业.
     * 
     * @param serverIp 服务器IP地址
     * @param jobName 作业名称
     */
    @RequestMapping(value = "/{serverIp}/jobs/{jobName}/disable",method = RequestMethod.POST)
    public void disableServerJob(@PathVariable("serverIp") final String serverIp, @PathVariable("jobName") final String jobName) {
        jobAPIService.getJobOperatorAPI().disable(Optional.of(jobName), Optional.of(serverIp));
    }
    
    /**
     * 启用作业.
     *
     * @param serverIp 服务器IP地址
     * @param jobName 作业名称
     */
    @RequestMapping(value = "/{serverIp}/jobs/{jobName}/disable",method = RequestMethod.DELETE)
    public void enableServerJob(@PathVariable("serverIp") final String serverIp, @PathVariable("jobName") final String jobName) {
        jobAPIService.getJobOperatorAPI().enable(Optional.of(jobName), Optional.of(serverIp));
    }
    
    /**
     * 终止作业.
     *
     * @param serverIp 服务器IP地址
     * @param jobName 作业名称
     */
    @RequestMapping(value = "/{serverIp}/jobs/{jobName}/shutdown",method = RequestMethod.POST)
    public void shutdownServerJob(@PathVariable("serverIp") final String serverIp, @PathVariable("jobName") final String jobName) {
        jobAPIService.getJobOperatorAPI().shutdown(Optional.of(jobName), Optional.of(serverIp));
    }
    
    /**
     * 清理作业.
     *
     * @param serverIp 服务器IP地址
     * @param jobName 作业名称
     */
    @RequestMapping(value = "/{serverIp}/jobs/{jobName}",method = RequestMethod.DELETE)
    public void removeServerJob(@PathVariable("serverIp") final String serverIp, @PathVariable("jobName") final String jobName) {
        jobAPIService.getJobOperatorAPI().remove(Optional.of(jobName), Optional.of(serverIp));
    }
}
