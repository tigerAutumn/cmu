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

package cc.newex.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cc.newex.domain.EventTraceDataSourceFactory;
import cc.newex.domain.RegistryCenterConfiguration;
import cc.newex.restful.config.EventTraceDataSourceRestfulApi;
import cc.newex.restful.config.RegistryCenterRestfulApi;
import cc.newex.service.EventTraceDataSourceConfigurationService;
import cc.newex.service.RegistryCenterConfigurationService;
import cc.newex.service.impl.EventTraceDataSourceConfigurationServiceImpl;
import cc.newex.service.impl.RegistryCenterConfigurationServiceImpl;
import com.dangdang.ddframe.job.lite.lifecycle.internal.reg.RegistryCenterFactory;
import com.dangdang.ddframe.job.reg.exception.RegException;
import com.google.common.base.Optional;
import cc.newex.domain.EventTraceDataSourceConfiguration;
import cc.newex.util.SessionEventTraceDataSourceConfiguration;
import cc.newex.util.SessionRegistryCenterConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 全局配置过滤器.
 *
 * @author caohao
 */
@Component
@WebFilter(filterName = "globalConfigurationFilter",urlPatterns = "/*")
public class GlobalConfigurationFilter implements Filter {
    
    private final RegistryCenterConfigurationService regCenterService = new RegistryCenterConfigurationServiceImpl();
    
    private final EventTraceDataSourceConfigurationService rdbService = new EventTraceDataSourceConfigurationServiceImpl();
    
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
    }
    
    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException,

        ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpSession httpSession = httpRequest.getSession();
        if (null == httpSession.getAttribute(RegistryCenterRestfulApi.REG_CENTER_CONFIG_KEY)) {
            loadActivatedRegCenter(httpSession);
        }
        if (null == httpSession.getAttribute(EventTraceDataSourceRestfulApi.DATA_SOURCE_CONFIG_KEY)) {
            loadActivatedEventTraceDataSource(httpSession);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
    
    private void loadActivatedRegCenter(final HttpSession httpSession) {
        Optional<RegistryCenterConfiguration> config = regCenterService.loadActivated();
        if (config.isPresent()) {
            String configName = config.get().getName();
            boolean isConnected = setRegistryCenterNameToSession(regCenterService.find(configName, regCenterService.loadAll()), httpSession);
            if (isConnected) {
                regCenterService.load(configName);
            }
        }
    }
    
    private boolean setRegistryCenterNameToSession(final RegistryCenterConfiguration regCenterConfig, final HttpSession session) {
        session.setAttribute(RegistryCenterRestfulApi.REG_CENTER_CONFIG_KEY, regCenterConfig);
        try {
            RegistryCenterFactory.createCoordinatorRegistryCenter(regCenterConfig.getZkAddressList(), regCenterConfig.getNamespace(), Optional.fromNullable(regCenterConfig.getDigest()));
            SessionRegistryCenterConfiguration.setRegistryCenterConfiguration((RegistryCenterConfiguration) session.getAttribute(RegistryCenterRestfulApi.REG_CENTER_CONFIG_KEY));
        } catch (final RegException ex) {
            return false;
        }
        return true;
    }
    
    private void loadActivatedEventTraceDataSource(final HttpSession httpSession) {
        Optional<EventTraceDataSourceConfiguration> config = rdbService.loadActivated();
        if (config.isPresent()) {
            String configName = config.get().getName();
            boolean isConnected = setEventTraceDataSourceNameToSession(rdbService.find(configName, rdbService.loadAll()), httpSession);
            if (isConnected) {
                rdbService.load(configName);
            }
        }
    }
    
    private boolean setEventTraceDataSourceNameToSession(final EventTraceDataSourceConfiguration dataSourceConfig, final HttpSession session) {
        session.setAttribute(EventTraceDataSourceRestfulApi.DATA_SOURCE_CONFIG_KEY, dataSourceConfig);
        try {
            EventTraceDataSourceFactory.createEventTraceDataSource(dataSourceConfig.getDriver(), dataSourceConfig.getUrl(),
                    dataSourceConfig.getUsername(), Optional.fromNullable(dataSourceConfig.getPassword()));
            SessionEventTraceDataSourceConfiguration.setDataSourceConfiguration((EventTraceDataSourceConfiguration) session.getAttribute(EventTraceDataSourceRestfulApi.DATA_SOURCE_CONFIG_KEY));
            // CHECKSTYLE:OFF
        } catch (final Exception ex) {
            // CHECKSTYLE:ON
            return false;
        }
        return true;
    }
    
    @Override
    public void destroy() {
    }
}
