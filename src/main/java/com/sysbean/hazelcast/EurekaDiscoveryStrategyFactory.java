package com.sysbean.hazelcast;

import com.google.common.collect.Lists;
import com.hazelcast.config.properties.PropertyDefinition;
import com.hazelcast.logging.ILogger;
import com.hazelcast.spi.discovery.DiscoveryNode;
import com.hazelcast.spi.discovery.DiscoveryStrategy;
import com.hazelcast.spi.discovery.DiscoveryStrategyFactory;
import com.netflix.discovery.EurekaClient;
import com.sysbean.hazelcast.config.EurekaProperties;

import java.util.Collection;
import java.util.Map;

import static com.sysbean.hazelcast.EurekaDiscoveryStrategy.EurekaDiscoveryStrategyBuilder;

public class EurekaDiscoveryStrategyFactory implements DiscoveryStrategyFactory {

    static final Collection<PropertyDefinition> PROPERTY_DEFINITIONS = Lists.newArrayList();

    static {
        PROPERTY_DEFINITIONS.addAll(EurekaProperties.HZ_PROPERTY_DEFINITIONS);
        PROPERTY_DEFINITIONS.addAll(EurekaProperties.EUREKA_CLIENT_PROPERTY_DEFINITIONS);
    }

    private static EurekaClient eurekaClient;
    private static String groupName;

    @Override
    public Class<? extends DiscoveryStrategy> getDiscoveryStrategyType() {
        return EurekaDiscoveryStrategy.class;
    }

    @Override
    public DiscoveryStrategy newDiscoveryStrategy(DiscoveryNode discoveryNode, ILogger logger, Map<String, Comparable> properties) {
        EurekaDiscoveryStrategyBuilder builder = new EurekaDiscoveryStrategy.EurekaDiscoveryStrategyBuilder();
        builder.setDiscoveryNode(discoveryNode).setILogger(logger).setProperties(properties)
                .setEurekaClient(eurekaClient).setGroupName(groupName);
        return builder.build();
    }

    @Override
    public Collection<PropertyDefinition> getConfigurationProperties() {
        return PROPERTY_DEFINITIONS;
    }

    public static void setEurekaClient(EurekaClient eurekaClient) {
        EurekaDiscoveryStrategyFactory.eurekaClient = eurekaClient;
    }

    public static void setGroupName(String groupName) {
        EurekaDiscoveryStrategyFactory.groupName = groupName;
    }
}
