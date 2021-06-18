package com.sysbean.hazelcast;


import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;

interface StatusChangeStrategy {
    void update(ApplicationInfoManager manager, InstanceInfo.InstanceStatus status);
    boolean shouldRegister();
}

