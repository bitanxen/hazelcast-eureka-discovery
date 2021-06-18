package com.sysbean.hazelcast;

import com.google.common.collect.Lists;
import com.hazelcast.config.properties.PropertyDefinition;
import com.hazelcast.logging.ILogger;
import com.hazelcast.spi.discovery.DiscoveryNode;
import com.hazelcast.spi.discovery.DiscoveryStrategy;
import com.hazelcast.spi.discovery.DiscoveryStrategyFactory;
import com.sysbean.hazelcast.config.EurekaProperties;

import java.util.Collection;
import java.util.Map;

public class EurekaDiscoveryStrategyFactory implements DiscoveryStrategyFactory {

    static final Collection<PropertyDefinition> PROPERTY_DEFINITIONS = Lists.newArrayList();

    static {
        PROPERTY_DEFINITIONS.addAll(EurekaProperties.HZ_PROPERTY_DEFINITIONS);
        PROPERTY_DEFINITIONS.addAll(EurekaProperties.EUREKA_CLIENT_PROPERTY_DEFINITIONS);
    }

    @Override
    public Class<? extends DiscoveryStrategy> getDiscoveryStrategyType() {
        return null;
    }

    @Override
    public DiscoveryStrategy newDiscoveryStrategy(DiscoveryNode discoveryNode, ILogger logger, Map<String, Comparable> properties) {
        return null;
    }

    @Override
    public Collection<PropertyDefinition> getConfigurationProperties() {
        return null;
    }
}
