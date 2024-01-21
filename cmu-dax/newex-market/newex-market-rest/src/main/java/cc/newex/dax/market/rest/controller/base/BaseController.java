package cc.newex.dax.market.rest.controller.base;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公共Action控制器类
 *
 * @param <Service>
 * @param <Model>
 * @param <Example>
 * @param <Type>
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
public class BaseController<Service extends CrudService<Model, Example, Type>, Model, Example, Type> {
    @Autowired
    protected Service service;
}
