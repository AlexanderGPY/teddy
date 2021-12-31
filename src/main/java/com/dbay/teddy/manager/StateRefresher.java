package com.dbay.teddy.manager;

import com.dbay.teddy.entity.Job;
import com.dbay.teddy.utils.TeddyConf;
import com.dbay.teddy.service.JobService;
import com.dbay.teddy.service.YarnService;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author AlexanderGuo
 */
@Component
public class StateRefresher implements ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private YarnService yarnService; 

    @Autowired
    private JobService jobService;

    private final ScheduledExecutorService scheduledThreadPool = new ScheduledThreadPoolExecutor(1,
            new BasicThreadFactory.Builder().namingPattern("state-refresher-pool-%d").daemon(true).build());

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        logger.info("启动 状态服务 线程");

        // 线程池代替单个线程的创建，提供更好的复用性与可控性
        scheduledThreadPool.scheduleAtFixedRate(()->{
            try {
                List<Job> jobs = jobService.findAllWithAppId();
                logger.info("监测到" + jobs.size() + "条appid不为空的task");
                jobs.forEach(t -> {
                    String state = yarnService.state(t.getAppId());
                    t.setState(state);
                    jobService.update(t);
                    logger.info("更新" + t.getId() + "的状态信息为：" + state);
                });
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        },0,Long.parseLong(TeddyConf.get("state.refresh.interval")), TimeUnit.SECONDS);
    }
}
