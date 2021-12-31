package com.dbay.teddy.entity;

import com.dbay.teddy.utils.TeddyConf;

/**
 * @author AlexanderGuo
 */
public class Job {
    private Integer id;
    private String name;
    private String appId = "-1";
    private String state = "created";
    private String appResource;
    private String mainClass;
    private String master = "yarn";
    private String deployMode = "cluster";
    private String yarnQueue;
    private String args;
    private String config;
    private String email;
    private String webhook;
    private Integer send;
    private Integer restart;
    private Integer retries = Integer.valueOf(TeddyConf.get("auto.restart.retries"));
    public Job() {
    }

    public String getWebhook() {
        return webhook;
    }

    public void setWebhook(String webhook) {
        this.webhook = webhook;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", appId='" + appId + '\'' +
                ", state='" + state + '\'' +
                ", appResource='" + appResource + '\'' +
                ", mainClass='" + mainClass + '\'' +
                ", master='" + master + '\'' +
                ", deployMode='" + deployMode + '\'' +
                ", yarnQueue='" + yarnQueue + '\'' +
                ", args='" + args + '\'' +
                ", config='" + config + '\'' +
                ", email='" + email + '\'' +
                ", webhook='" + webhook + '\'' +
                ", send=" + send +
                ", restart=" + restart +
                ", retries=" + retries +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAppId() {
        return appId;
    }

    public String getState() {
        return state;
    }

    public String getAppResource() {
        return appResource;
    }

    public String getMainClass() {
        return mainClass;
    }

    public String getMaster() {
        return master;
    }

    public String getDeployMode() {
        return deployMode;
    }

    public String getYarnQueue() {
        return yarnQueue;
    }

    public String getArgs() {
        return args;
    }

    public String getConfig() {
        return config;
    }

    public String getEmail() {
        return email;
    }

    public Integer getSend() {
        return send;
    }

    public Integer getRestart() {
        return restart;
    }

    public Integer getRetries() {
        return retries;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setAppResource(String appResource) {
        this.appResource = appResource;
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public void setDeployMode(String deployMode) {
        this.deployMode = deployMode;
    }

    public void setYarnQueue(String yarnQueue) {
        this.yarnQueue = yarnQueue;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSend(Integer send) {
        this.send = send;
    }

    public void setRestart(Integer restart) {
        this.restart = restart;
    }

    public void setRetries(Integer retries) {
        this.retries = retries;
    }
}
