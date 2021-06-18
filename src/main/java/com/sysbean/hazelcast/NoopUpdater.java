package com.sysbean.hazelcast;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;

/**
 *
 */
class NoopUpdater implements StatusChangeStrategy {

    @Override
    public void update(ApplicationInfoManager manager, InstanceInfo.InstanceStatus status) {
    }

    @Override
    public boolean shouldRegister() {
        return false;
    }
}
