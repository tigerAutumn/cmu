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

package cc.newex.util;

import cc.newex.domain.RegistryCenterConfiguration;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 注册中心配置的会话声明周期.
 * 
 * @author zhangliang 
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SessionRegistryCenterConfiguration {
    
    private static RegistryCenterConfiguration regCenterConfig;
    
    /**
     * 从当前会话范围获取注册中心配置.
     *
     * @return 事件追踪数据源配置
     */
    public static RegistryCenterConfiguration getRegistryCenterConfiguration() {
        return regCenterConfig;
    }
    
    /**
     * 设置注册中心配置至当前会话范围.
     * 
     * @param regCenterConfig 注册中心配置
     */
    public static void setRegistryCenterConfiguration(final RegistryCenterConfiguration regCenterConfig) {
        SessionRegistryCenterConfiguration.regCenterConfig = regCenterConfig;
    }
}
