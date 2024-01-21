package com.xxl.job.admin.core.route;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.biz.model.TriggerParam;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created by xuxueli on 17/3/10.
 */
@Slf4j
public abstract class ExecutorRouter {

    /**
     * route run executor
     *
     * @param triggerParam
     * @param addressList
     * @return  ReturnT.content: final address
     */
    public abstract ReturnT<String> routeRun(TriggerParam triggerParam, ArrayList<String> addressList);

}
