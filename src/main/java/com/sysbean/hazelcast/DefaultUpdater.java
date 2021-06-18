package com.sysbean.hazelcast;

import com.google.common.base.Preconditions;
import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;

/**
 *
 */
class DefaultUpdater implements StatusChangeStrategy {
    @Override
    public void update(ApplicationInfoManager manager, InstanceInfo.InstanceStatus status) {
        Preconditions.checkNotNull(manager);
        Preconditions.checkNotNull(status);

        manager.setInstanceStatus(status);
    }

    @Override
    public boolean shouldRegister() {
        return true;
    }
}
