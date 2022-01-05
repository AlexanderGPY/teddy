package com.dbay.teddy.mapper;

import com.dbay.teddy.entity.Job;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author alexanderGuo
 */
@Mapper
public interface JobMapper {

    /**
     * 返回job数量
     *
     * @return Integer
     * @throws Exception 返回count异常
     */
    @Select("select count(1) from job")
    Integer count() throws Exception;

    /**
     * ALWAYS 不允许手动指定 ID
     * DEFAULT 也可以手动指定ID
     */
    @Update("create table job(" +
            "id                 BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT," +
            "name               VARCHAR(200)," +
            "appId             VARCHAR(200)," +
            "state              VARCHAR(20)," +
            "appResource      VARCHAR(200)," +
            "mainClass         VARCHAR(200)," +
            "master             VARCHAR(20)," +
            "deployMode      VARCHAR(20)," +
            "yarnQueue         VARCHAR(20)," +
            "args               VARCHAR(3000)," +
            "config             VARCHAR(3000)," +
            "email              VARCHAR(500)," +
            "webhook              VARCHAR(500)," +
            "send               SMALLINT," +
            "restart            SMALLINT," +
            "retries            SMALLINT," +
            "modifyTime        TIMESTAMP," +
            "KEY (appId)" +
            ")" +
            "ENGINE=InnoDB DEFAULT CHARSET=utf8")
    void create();

    /**
     * 拿到job list  指定行数
     *
     * @param start 开始行数
     * @param size  结束行数
     * @return Job
     */
    @Select("select * from job ORDER BY appId LIMIT #{start}, #{size}")
    List<Job> list(@Param("start")Integer start, @Param("size")Integer size);


    /**
     * 返回所有job
     *
     * @return List<Job>
     */
    @Select("select * from job where appId != '-1'")
    List<Job> findAllWithAppId();

    /**
     * 返回所有job
     * @param appId applicationId
     * @return List<Job>
     */
    @Select("select * from job where appId = #{appId}")
    Job findOneWithAppId(@Param("appId") String appId);
    /**
     * 新增一个job
     *
     * @param job Job
     */
    @Insert({"insert into job(" +
            "name,appId,state,appResource,mainClass,master,deployMode,yarnQueue,args,config,email,webhook,send,restart,retries" +
            ") values(" +
            "#{t.name}, #{t.appId}, #{t.state},#{t.appResource}, " +
            "#{t.mainClass}, #{t.master},#{t.deployMode},#{t.yarnQueue},#{t.args},#{t.config}," +
            "#{t.email},#{t.webhook},#{t.send},#{t.restart},#{t.retries})"})
    void save(@Param("t") Job job);

    /**
     * 按照ID返回一个job
     *
     * @param id id
     * @return JOB
     */
    @Select("select * from job where id = #{id}")
    Job findOne(@Param("id") Integer id);

    /**
     * 按照ID删除Job
     *
     * @param id ID
     */
    @Delete("delete from job where id = #{id}")
    void delete(@Param("id") Integer id);

    /**
     * 更新job状态等检查信息
     *
     * @param job Job
     */
    @Insert("update job set " +
            "appId = #{t.appId}," +
            "state  = #{t.state}," +
            " retries = #{t.retries}" +
            " where id = #{t.id}")
    void update(@Param("t") Job job);

    /**
     * 更新job配置
     *
     * @param job Job
     */
    @Insert({"insert into job(" +
            "name,appId,state,appResource,mainClass,master,deployMode,yarnQueue,args,config,email,webhook,send,restart,retries" +
            ") values(" +
            "#{t.name}, #{t.appId}, #{t.state},#{t.appResource}, " +
            "#{t.mainClass}, #{t.master},#{t.deployMode},#{t.yarnQueue},#{t.args},#{t.config}," +
            "#{t.email},#{t.webhook},#{t.send},#{t.restart},#{t.retries}) " +
            "on duplicate key update " +
            "name=#{t.name},appResource=#{t.appResource},mainClass=#{t.mainClass},yarnQueue=#{t.yarnQueue}," +
            "args=#{t.args},config=#{t.config},email=#{t.email},webhook=#{t.webhook}"})
    void updateConfigs(@Param("t") Job job);
}
