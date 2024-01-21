package cc.newex.jobs;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import cc.newex.extension.annotation.ElasticJobExtConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Liu Hailin
 * @create 2017-08-17 下午9:14
 **/

@Component
@ElasticJobExtConfig(cron = "0/2 * * * * ?")
public class Example implements SimpleJob {
    @Autowired
    TestBean testBean;
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println("+++++++++++"+testBean.getName());
    }
}
