package com.dbay.teddy.controller;

import com.alibaba.fastjson.JSON;
import com.dbay.teddy.entity.Job;
import com.dbay.teddy.utils.Response;
import com.dbay.teddy.service.JobService;
import org.apache.spark.launcher.SparkAppHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author AlexanderGuo
 */
@RestController
@RequestMapping("job")
public class JobController {
    
    private final  static String RUNNING = "RUNNING";

    private final ConcurrentHashMap<Integer,SparkAppHandle> cache = new ConcurrentHashMap<>();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JobService jobService;

    @RequestMapping(value = "submit", method = RequestMethod.POST)
    public Response submit(@RequestBody Job   job){
        logger.warn("收到请求启动："+ job);
        if(jobService.start(job)){
            return Response.SUCCESS("启动成功，前往监控页面查看状态！");
        }
        return Response.ERROR("启动失败o(╥﹏╥)o");
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Response update(@RequestBody Job   job){
        logger.warn("收到请求更新："+ job);
        if(jobService.reconfiguring(job)){
            return Response.SUCCESS("配置已更新，前往监控页面重启！");
        }
        return Response.ERROR("配置更新失败o(╥﹏╥)o");
    }

    @RequestMapping("list")
    public Response list(Integer page, Integer size){
        return Response.SUCCESS(jobService.list(1,500));
    }

    @RequestMapping("find")
    public Response find(Integer id){
        return Response.SUCCESS(jobService.findOne(id));
    }

    @RequestMapping("delete")
    public Response delete(Integer id){
        Job job = jobService.findOne(id);
        if(RUNNING.equals(job.getState())){
            return Response.ERROR("无法删除正在运行的任务");
        }else{
            jobService.delete(id);
            return Response.SUCCESS("已删除");
        }
    }

    @RequestMapping("stop")
    public Response stop(Integer id){
        Boolean isSuccess = jobService.stop(id);
        return isSuccess?Response.SUCCESS("停止成功"):Response.ERROR("停止失败");
    }

    @RequestMapping("restart")
    public Response restart(Integer id){
        Job job = jobService.findOne(id);
        if(RUNNING.equals(job.getState())){
            return Response.ERROR("正在执行，无法重启");
        }else{
            return jobService.restart(job)?Response.SUCCESS("成功重启"):Response.ERROR("重启出错");
        }
    }


}
