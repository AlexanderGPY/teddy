package com.dbay.teddy.manager;

import com.alibaba.fastjson.JSON;
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
public class AlertManager implements ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private WebHookSender webHookSender;
    
    @Autowired
    private JobService jobService;

    private final ScheduledExecutorService scheduledThreadPool = new ScheduledThreadPoolExecutor(1,
            new BasicThreadFactory.Builder().namingPattern("alert-pool-%d").daemon(true).build());

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        logger.info("启动 告警服务 线程");

        scheduledThreadPool.scheduleAtFixedRate(()->{
            try {
                List<Job> jobs = jobService.findAllWithAppId();
                jobs.forEach(t -> {
                    if (StringUtils.isNotBlank(t.getState())
                            && !"RUNNING".equals(t.getState())
                            && t.getSend() == 1) {
                        logger.error("检测到异常任务");
                        //emailSender.send(t.getEmail(), t.getName() + "状态异常", JSON.toJSONString("state:"+ t.getState()));
                        webHookSender.wxRobotSend(t.getWebhook(), t.getName() + "状态异常", JSON.toJSONString("state:"+t.getState()));

                    }
                });
            }catch(Exception e){
                logger.error(e.getMessage());
            }
        },0, Long.parseLong(TeddyConf.get("alert.interval")), TimeUnit.SECONDS);
    }
}
