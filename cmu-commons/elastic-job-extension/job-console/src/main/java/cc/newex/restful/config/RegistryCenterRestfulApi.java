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

package cc.newex.restful.config;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cc.newex.service.impl.RegistryCenterConfigurationServiceImpl;
import com.dangdang.ddframe.job.lite.lifecycle.internal.reg.RegistryCenterFactory;
import com.dangdang.ddframe.job.reg.exception.RegException;
import com.google.common.base.Optional;
import cc.newex.domain.RegistryCenterConfiguration;
import cc.newex.service.RegistryCenterConfigurationService;
import cc.newex.util.SessionRegistryCenterConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 注册中心配置的RESTful API.
 *
 * @author caohao
 */
@RestController
@RequestMapping("/api/registry-center")
public final class RegistryCenterRestfulApi {
    
    public static final String REG_CENTER_CONFIG_KEY = "reg_center_config_key";
    
    private RegistryCenterConfigurationService regCenterService = new RegistryCenterConfigurationServiceImpl();
    
    /**
     * 判断是否存在已连接的注册中心配置.
     *
     * @param request HTTP请求
     * @return 是否存在已连接的注册中心配置
     */
    @RequestMapping(value = "activated",method = RequestMethod.GET)
    public boolean activated(final HttpServletRequest request) {
        return regCenterService.loadActivated().isPresent();
    }
    
    /**
     * 读取注册中心配置集合.
     * 
     * @param request HTTP请求
     * @return 注册中心配置集合
     */
    @RequestMapping(method = RequestMethod.GET,produces = APPLICATION_JSON_VALUE)
    public Collection<RegistryCenterConfiguration> load(final HttpServletRequest request) {
        Optional<RegistryCenterConfiguration> regCenterConfig = regCenterService.loadActivated();
        if (regCenterConfig.isPresent()) {
            setRegistryCenterNameToSession(regCenterConfig.get(), request.getSession());
        }
        return regCenterService.loadAll().getRegistryCenterConfiguration();
    }
    
    /**
     * 添加注册中心.
     * 
     * @param config 注册中心配置
     * @return 是否添加成功
     */
    @RequestMapping(method = RequestMethod.POST,produces = APPLICATION_JSON_VALUE)
    public boolean add(final RegistryCenterConfiguration config) {
        return regCenterService.add(config);
    }
    
    /**
     * 删除注册中心.
     *
     * @param config 注册中心配置
     */
    @RequestMapping(method = RequestMethod.DELETE,consumes = APPLICATION_JSON_VALUE)
    public void delete(final RegistryCenterConfiguration config) {
        regCenterService.delete(config.getName());
    }

    @RequestMapping(value ="/connect", method = RequestMethod.POST,consumes = APPLICATION_JSON_VALUE,produces = APPLICATION_JSON_VALUE)
    public boolean connect(final RegistryCenterConfiguration config, final HttpServletRequest request) {
        boolean isConnected = setRegistryCenterNameToSession(regCenterService.find(config.getName(), regCenterService.loadAll()), request.getSession());
        if (isConnected) {
            regCenterService.load(config.getName());
        }
        return isConnected;
    }
    
    private boolean setRegistryCenterNameToSession(final RegistryCenterConfiguration regCenterConfig, final HttpSession session) {
        session.setAttribute(REG_CENTER_CONFIG_KEY, regCenterConfig);
        try {
            RegistryCenterFactory.createCoordinatorRegistryCenter(regCenterConfig.getZkAddressList(), regCenterConfig.getNamespace(), Optional.fromNullable(regCenterConfig.getDigest()));
            SessionRegistryCenterConfiguration.setRegistryCenterConfiguration((RegistryCenterConfiguration) session.getAttribute(REG_CENTER_CONFIG_KEY));
        } catch (final RegException ex) {
            return false;
        }
        return true;
    }
}
