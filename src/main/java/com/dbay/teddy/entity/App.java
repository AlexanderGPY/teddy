package com.dbay.teddy.entity;

import java.util.Calendar;

/**
 * @description:
 * @author:AlexanderGuo
 * @date:2022/1/6 18:40:40
 **/
public class App {
    private String id = "NONE";
    private String user = "NONE";
    private String name = "NONE";
    private String queue = "NONE";
    private String state = "NONE";
    private String finalStatus;
    private float progress;
    private String trackingUI;
    private String trackingUrl;
    private String diagnostics;
    private Long clusterId;
    private String applicationType;
    private String applicationTags;
    private Long priority;
    private Long startedTime = 0L;
    private Long finishedTime = 0L;
    private Long elapsedTime = 0L;
    private String amContainerLogs;
    private String amHostHttpAddress;
    private String amRPCAddress;
    private Integer allocatedMB;
    private Integer allocatedVCores;
    private Integer runningContainers;
    private Long memorySeconds;
    private Long vcoreSeconds;
    private float queueUsagePercentage;
    private float clusterUsagePercentage;
    private Long preemptedResourceMB;
    private Long preemptedResourceVCores;
    private Integer numNonAMContainerPreempted;
    private Integer numAMContainerPreempted;
    private String logAggregationStatus;
    private boolean unmanagedApplication;
    private String appNodeLabelExpression;
    private String amNodeLabelExpression;

    public App() {
    }

    public static String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        return days + "天" + hours + "小时" + minutes + "分钟"
                + seconds + "秒";
    }

    public String totalRunningTime() {
        long totalTime;
        if (this.getFinishedTime() > 0L) {
            totalTime = this.getFinishedTime() - this.getStartedTime();
        } else {
            totalTime = Calendar.getInstance().getTimeInMillis() - this.getStartedTime();
        }
        return formatDuring(totalTime);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFinalStatus() {
        return finalStatus;
    }

    public void setFinalStatus(String finalStatus) {
        this.finalStatus = finalStatus;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public String getTrackingUI() {
        return trackingUI;
    }

    public void setTrackingUI(String trackingUI) {
        this.trackingUI = trackingUI;
    }

    public String getTrackingUrl() {
        return trackingUrl;
    }

    public void setTrackingUrl(String trackingUrl) {
        this.trackingUrl = trackingUrl;
    }

    public String getDiagnostics() {
        return diagnostics;
    }

    public void setDiagnostics(String diagnostics) {
        this.diagnostics = diagnostics;
    }

    public Long getClusterId() {
        return clusterId;
    }

    public void setClusterId(Long clusterId) {
        this.clusterId = clusterId;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getApplicationTags() {
        return applicationTags;
    }

    public void setApplicationTags(String applicationTags) {
        this.applicationTags = applicationTags;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public Long getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(Long startedTime) {
        this.startedTime = startedTime;
    }

    public Long getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Long finishedTime) {
        this.finishedTime = finishedTime;
    }

    public Long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(Long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public String getAmContainerLogs() {
        return amContainerLogs;
    }

    public void setAmContainerLogs(String amContainerLogs) {
        this.amContainerLogs = amContainerLogs;
    }

    public String getAmHostHttpAddress() {
        return amHostHttpAddress;
    }

    public void setAmHostHttpAddress(String amHostHttpAddress) {
        this.amHostHttpAddress = amHostHttpAddress;
    }

    public String getAmRPCAddress() {
        return amRPCAddress;
    }

    public void setAmRPCAddress(String amRPCAddress) {
        this.amRPCAddress = amRPCAddress;
    }

    public Integer getAllocatedMB() {
        return allocatedMB;
    }

    public void setAllocatedMB(Integer allocatedMB) {
        this.allocatedMB = allocatedMB;
    }

    public Integer getAllocatedVCores() {
        return allocatedVCores;
    }

    public void setAllocatedVCores(Integer allocatedVCores) {
        this.allocatedVCores = allocatedVCores;
    }

    public Integer getRunningContainers() {
        return runningContainers;
    }

    public void setRunningContainers(Integer runningContainers) {
        this.runningContainers = runningContainers;
    }

    public Long getMemorySeconds() {
        return memorySeconds;
    }

    public void setMemorySeconds(Long memorySeconds) {
        this.memorySeconds = memorySeconds;
    }

    public Long getVcoreSeconds() {
        return vcoreSeconds;
    }

    public void setVcoreSeconds(Long vcoreSeconds) {
        this.vcoreSeconds = vcoreSeconds;
    }

    public float getQueueUsagePercentage() {
        return queueUsagePercentage;
    }

    public void setQueueUsagePercentage(float queueUsagePercentage) {
        this.queueUsagePercentage = queueUsagePercentage;
    }

    public float getClusterUsagePercentage() {
        return clusterUsagePercentage;
    }

    public void setClusterUsagePercentage(float clusterUsagePercentage) {
        this.clusterUsagePercentage = clusterUsagePercentage;
    }

    public Long getPreemptedResourceMB() {
        return preemptedResourceMB;
    }

    public void setPreemptedResourceMB(Long preemptedResourceMB) {
        this.preemptedResourceMB = preemptedResourceMB;
    }

    public Long getPreemptedResourceVCores() {
        return preemptedResourceVCores;
    }

    public void setPreemptedResourceVCores(Long preemptedResourceVCores) {
        this.preemptedResourceVCores = preemptedResourceVCores;
    }

    public Integer getNumNonAMContainerPreempted() {
        return numNonAMContainerPreempted;
    }

    public void setNumNonAMContainerPreempted(Integer numNonAMContainerPreempted) {
        this.numNonAMContainerPreempted = numNonAMContainerPreempted;
    }

    public Integer getNumAMContainerPreempted() {
        return numAMContainerPreempted;
    }

    public void setNumAMContainerPreempted(Integer numAMContainerPreempted) {
        this.numAMContainerPreempted = numAMContainerPreempted;
    }

    public String getLogAggregationStatus() {
        return logAggregationStatus;
    }

    public void setLogAggregationStatus(String logAggregationStatus) {
        this.logAggregationStatus = logAggregationStatus;
    }

    public boolean isUnmanagedApplication() {
        return unmanagedApplication;
    }

    public void setUnmanagedApplication(boolean unmanagedApplication) {
        this.unmanagedApplication = unmanagedApplication;
    }

    public String getAppNodeLabelExpression() {
        return appNodeLabelExpression;
    }

    public void setAppNodeLabelExpression(String appNodeLabelExpression) {
        this.appNodeLabelExpression = appNodeLabelExpression;
    }

    public String getAmNodeLabelExpression() {
        return amNodeLabelExpression;
    }

    public void setAmNodeLabelExpression(String amNodeLabelExpression) {
        this.amNodeLabelExpression = amNodeLabelExpression;
    }

    @Override
    public String toString() {
        return "App{" +
                "id='" + id + '\'' +
                ", user='" + user + '\'' +
                ", name='" + name + '\'' +
                ", queue='" + queue + '\'' +
                ", state='" + state + '\'' +
                ", finalStatus='" + finalStatus + '\'' +
                ", progress=" + progress +
                ", trackingUI='" + trackingUI + '\'' +
                ", trackingUrl='" + trackingUrl + '\'' +
                ", diagnostics='" + diagnostics + '\'' +
                ", clusterId=" + clusterId +
                ", applicationType='" + applicationType + '\'' +
                ", applicationTags='" + applicationTags + '\'' +
                ", priority=" + priority +
                ", startedTime=" + startedTime +
                ", finishedTime=" + finishedTime +
                ", elapsedTime=" + elapsedTime +
                ", amContainerLogs='" + amContainerLogs + '\'' +
                ", amHostHttpAddress='" + amHostHttpAddress + '\'' +
                ", amRPCAddress='" + amRPCAddress + '\'' +
                ", allocatedMB=" + allocatedMB +
                ", allocatedVCores=" + allocatedVCores +
                ", runningContainers=" + runningContainers +
                ", memorySeconds=" + memorySeconds +
                ", vcoreSeconds=" + vcoreSeconds +
                ", queueUsagePercentage=" + queueUsagePercentage +
                ", clusterUsagePercentage=" + clusterUsagePercentage +
                ", preemptedResourceMB=" + preemptedResourceMB +
                ", preemptedResourceVCores=" + preemptedResourceVCores +
                ", numNonAMContainerPreempted=" + numNonAMContainerPreempted +
                ", numAMContainerPreempted=" + numAMContainerPreempted +
                ", logAggregationStatus='" + logAggregationStatus + '\'' +
                ", unmanagedApplication=" + unmanagedApplication +
                ", appNodeLabelExpression='" + appNodeLabelExpression + '\'' +
                ", amNodeLabelExpression='" + amNodeLabelExpression + '\'' +
                '}';
    }
}
