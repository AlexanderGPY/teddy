package com.dbay.teddy.manager;

import com.dbay.teddy.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * derby这里有一个bug，就是每次字段有修改时，必须要重新删除数据库建库，建表。但是在linux环境下，自动init数据库失败。
 * @author AlexanderGuo
 */
@Component
public class DerbyInit implements ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JobService jobService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("check db env ...");
        if(jobService.count() < 0){
            logger.info("create table job.");
            jobService.create();
        }
    }
}
