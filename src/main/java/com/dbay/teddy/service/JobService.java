package com.dbay.teddy.service;

import com.alibaba.fastjson.JSON;
import com.dbay.teddy.entity.Job;
import com.dbay.teddy.mapper.JobMapper;
import com.dbay.teddy.utils.TeddyConf;
import org.apache.commons.lang.StringUtils;
import org.apache.spark.launcher.SparkAppHandle;
import org.apache.spark.launcher.SparkLauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author AlexanderGuo
 */
@Service
public class JobService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JobMapper jobMapper;

    public Boolean start(Job job) {
        Job handlerJob;
        try {
            handlerJob = launch(job);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
        save(handlerJob);
        return true;
    }

    public Boolean reconfiguring(Job job) {
        try {
            if ("".equals(job.getAppId())) {
                return false;
            }
            Job oldJob = findOneWithAppId(job.getAppId());
            if (Objects.nonNull(oldJob)) {
                job.setId(oldJob.getId());
                delete(oldJob.getId());
            }
            String settings = job.getConfig();
            for (String setting : StringUtils.splitByWholeSeparator(settings, ";")) {
                String[] strings = StringUtils.split(setting, "=");
                //从Config中提取Yarn队列到Job实例
                if ("spark.yarn.queue".equals(strings[0])) {
                    job.setYarnQueue(strings[1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return false;
        }
        updateConfigs(job);
        return true;
    }

    private Job launch(Job job) throws Exception {
        logger.warn("launch sparkLauncher");
        SparkLauncher launcher = new SparkLauncher()
                .setAppName(job.getName())
                .setSparkHome(TeddyConf.get("spark.home"))
                .setMaster(job.getMaster())
                .setAppResource(TeddyConf.get("lib.home") + job.getAppResource())
                .setMainClass(job.getMainClass())
                .addAppArgs(job.getArgs())
                .setDeployMode(job.getDeployMode());

        String settings = job.getConfig();
        for (String setting : StringUtils.splitByWholeSeparator(settings, ";")) {
            String[] strings = StringUtils.split(setting, "=");
            //设置参数
            launcher.setConf(strings[0], strings[1]);

            //拿到yarn队列，放入list中
            if ("spark.yarn.queue".equals(strings[0])) {
                job.setYarnQueue(strings[1]);
            }
        }

        launcher.redirectOutput(new File(TeddyConf.get("log.file")));
        launcher.redirectError(new File(TeddyConf.get("log.file")));


        logger.warn("构建：" + JSON.toJSONString(launcher));
        SparkAppHandle handler = launcher.startApplication();

        // 阻塞登到有id再返回
        while (handler.getAppId() == null) {

            logger.warn("waiting for appId: " + handler.getAppId() + " " + handler.getState());

            if (handler.getState().isFinal()) {
                throw new RuntimeException("handler " + handler.getState());
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        job.setAppId(handler.getAppId());

        logger.warn("Get appId:" + handler.getAppId());
        return job;
    }

    public void autoRestart(Job job) {
        Job handlerJob = new Job();
        try {
            handlerJob = launch(job);
            //成功的时候，回复重启数量
            handlerJob.setRetries(3);
        } catch (Exception e) {
            logger.error(e.getMessage());
            handlerJob.setRetries(handlerJob.getRetries() - 1);
        }
        update(handlerJob);
    }

    public Boolean restart(Job job) {
        boolean result = false;
        Job handlerJob = new Job();
        try {
            handlerJob = launch(job);
            result = true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        update(handlerJob);
        return result;
    }

    public Boolean stop(Job job) {
        String command = "yarn application -kill " + job.getAppId();
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        logger.info("kill " + job.getAppId());
        return true;
    }

    /**
     * 创建task表
     */
    public void create() {
        jobMapper.create();
    }

    /**
     * 查询表的个数，用于判断表是否存在
     *
     * @return 如果表不存在则为-1
     */
    public Integer count() {
        try {
            return jobMapper.count();
        } catch (Exception e) {
            return -1;
        }
    }

    public List<Job> list(Integer page, Integer size) {
        return jobMapper.list((page - 1) * size, size);
    }

    public List<Job> findAllWithAppId() {
        return jobMapper.findAllWithAppId();
    }


    public Job findOneWithAppId(String appId) {
        return jobMapper.findOneWithAppId(appId);
    }

    public void save(Job job) {
        jobMapper.save(job);
    }

    public Job findOne(Integer id) {
        return jobMapper.findOne(id);
    }

    public void delete(Integer id) {
        jobMapper.delete(id);
    }

    public Boolean stop(Integer id) {
        Job job = jobMapper.findOne(id);
        return stop(job);
    }

    public void update(Job job) {
        jobMapper.update(job);
    }

    public void updateConfigs(Job job) {
        jobMapper.updateConfigs(job);
    }

}
