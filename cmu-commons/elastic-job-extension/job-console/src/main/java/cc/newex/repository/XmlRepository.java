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

package cc.newex.repository;

/**
 * 基于XML的数据访问器.
 * 
 * @param <E> 数据类型
 * 
 * @author zhangliang
 */
public interface XmlRepository<E> {
    
    /**
     * 读取数据.
     * 
     * @return 数据
     */
    E load();
    
    /**
     * 存储数据.
     * 
     * @param entity 数据
     */
    void save(E entity);
}
