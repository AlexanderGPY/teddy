package com.dbay.teddy.manager;

import com.dbay.teddy.utils.TeddyConf;
import com.dbay.teddy.entity.Job;
import com.dbay.teddy.service.JobService;
import org.apache.commons.lang3.StringUtils;
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
public class RestartManager implements ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JobService jobService;

    private final ScheduledExecutorService scheduledThreadPool = new ScheduledThreadPoolExecutor(1,
            new BasicThreadFactory.Builder().namingPattern("restart-pool-%d").daemon(true).build());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("start restarter");

        scheduledThreadPool.scheduleAtFixedRate(()->{
            try {
                List<Job> jobs = jobService.findAllWithAppId();
                logger.info("扫描到" + jobs.size() + "个任务需要检测是否重启");
                jobs.forEach(t -> {
                    if (StringUtils.isNotBlank(t.getState())
                            && !"RUNNING".equals(t.getState())
                            && t.getRestart().equals(1)
                            && t.getRetries() > 0) {
                        try {
                            logger.info("尝试重启task:" + t.getId() + ",剩余次数:" + t.getRetries());
                            jobService.autoRestart(t);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        },0,Long.parseLong(TeddyConf.get("auto.restart.interval")), TimeUnit.SECONDS);
    }
}
