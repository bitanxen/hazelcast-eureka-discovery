package com.sysbean.hazelcast.config;

import com.netflix.appinfo.EurekaAccept;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.discovery.EurekaClientConfig;
import com.netflix.discovery.shared.transport.DefaultEurekaTransportConfig;
import com.netflix.discovery.shared.transport.EurekaTransportConfig;
import org.apache.commons.configuration.MapConfiguration;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.sysbean.hazelcast.config.PropertyBasedEurekaClientConfigConstants.*;

/**
 * A Map-based implementation of eureka client configuration as required by
 * {@link EurekaClientConfig}.
 *
 * <p>Adapted from Eureka's DefaultEurekaClientConfig</p>
 */
public class PropertyBasedEurekaClientConfig implements EurekaClientConfig {

    /**
     * Default Zone
     */
    public static final String DEFAULT_ZONE = "defaultZone";

    private final String namespace;
    private final DynamicPropertyFactory configInstance;
    private final EurekaTransportConfig transportConfig;

    public PropertyBasedEurekaClientConfig(String namespace, final Map<String, Object> properties) {
        this.namespace = namespace.endsWith(".")
                ? namespace
                : namespace + ".";

        DynamicPropertyFactory.initWithConfigurationSource(new MapConfiguration(properties));
        DynamicPropertyFactory configInstance = DynamicPropertyFactory.getInstance();
        this.configInstance = configInstance;
        this.transportConfig = new DefaultEurekaTransportConfig(namespace, configInstance);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.netflix.discovery.EurekaClientConfig#getRegistryFetchIntervalSeconds
     * ()
     */
    @Override
    public int getRegistryFetchIntervalSeconds() {
        return configInstance.getIntProperty(
                namespace + REGISTRY_REFRESH_INTERVAL_KEY, 30).get();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.netflix.discovery.EurekaClientConfig#
     * getInstanceInfoReplicationIntervalSeconds()
     */
    @Override
    public int getInstanceInfoReplicationIntervalSeconds() {
        return configInstance.getIntProperty(
                namespace + REGISTRATION_REPLICATION_INTERVAL_KEY, 30).get();
    }

    @Override
    public int getInitialInstanceInfoReplicationIntervalSeconds() {
        return configInstance.getIntProperty(
                namespace + INITIAL_REGISTRATION_REPLICATION_DELAY_KEY, 40).get();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.netflix.discovery.EurekaClientConfig#getDnsPollIntervalSeconds()
     */
    @Override
    public int getEurekaServiceUrlPollIntervalSeconds() {
        return configInstance.getIntProperty(
                namespace + EUREKA_SERVER_URL_POLL_INTERVAL_KEY, 5 * 60 * 1000).get() / 1000;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.netflix.discovery.EurekaClientConfig#getProxyHost()
     */
    @Override
    public String getProxyHost() {
        return configInstance.getStringProperty(
                namespace + EUREKA_SERVER_PROXY_HOST_KEY, null).get();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.netflix.discovery.EurekaClientConfig#getProxyPort()
     */
    @Override
    public String getProxyPort() {
        return configInstance.getStringProperty(
                namespace + EUREKA_SERVER_PROXY_PORT_KEY, null).get();
    }

    @Override
    public String getProxyUserName() {
        return configInstance.getStringProperty(
                namespace + EUREKA_SERVER_PROXY_USERNAME_KEY, null).get();
    }

    @Override
    public String getProxyPassword() {
        return configInstance.getStringProperty(
                namespace + EUREKA_SERVER_PROXY_PASSWORD_KEY, null).get();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.netflix.discovery.EurekaClientConfig#shouldGZipContent()
     */
    @Override
    public boolean shouldGZipContent() {
        return configInstance.getBooleanProperty(
                namespace + EUREKA_SERVER_GZIP_CONTENT_KEY, true).get();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.netflix.discovery.EurekaClientConfig#getDSServerReadTimeout()
     */
    @Override
    public int getEurekaServerReadTimeoutSeconds() {
        return configInstance.getIntProperty(
                namespace + EUREKA_SERVER_READ_TIMEOUT_KEY, 8).get();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.netflix.discovery.EurekaClientConfig#getDSServerConnectTimeout()
     */
    @Override
    public int getEurekaServerConnectTimeoutSeconds() {
        return configInstance.getIntProperty(
                namespace + EUREKA_SERVER_CONNECT_TIMEOUT_KEY, 5).get();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.netflix.discovery.EurekaClientConfig#getBackupRegistryImpl()
     */
    @Override
    public String getBackupRegistryImpl() {
        return configInstance.getStringProperty(namespace + BACKUP_REGISTRY_CLASSNAME_KEY,
                null).get();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.netflix.discovery.EurekaClientConfig#getDSServerTotalMaxConnections()
     */
    @Override
    public int getEurekaServerTotalConnections() {
        return configInstance.getIntProperty(
                namespace + EUREKA_SERVER_MAX_CONNECTIONS_KEY, 200).get();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.netflix.discovery.EurekaClientConfig#getDSServerConnectionsPerHost()
     */
    @Override
    public int getEurekaServerTotalConnectionsPerHost() {
        return configInstance.getIntProperty(
                namespace + EUREKA_SERVER_MAX_CONNECTIONS_PER_HOST_KEY, 50).get();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.netflix.discovery.EurekaClientConfig#getDSServerURLContext()
     */
    @Override
    public String getEurekaServerURLContext() {
        return configInstance.getStringProperty(
                namespace + EUREKA_SERVER_URL_CONTEXT_KEY,
                configInstance.getStringProperty(namespace + EUREKA_SERVER_FALLBACK_URL_CONTEXT_KEY, null)
                        .get()).get();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.netflix.discovery.EurekaClientConfig#getDSServerPort()
     */
    @Override
    public String getEurekaServerPort() {
        return configInstance.getStringProperty(
                namespace + EUREKA_SERVER_PORT_KEY,
                configInstance.getStringProperty(namespace + EUREKA_SERVER_FALLBACK_PORT_KEY, null)
                        .get()).get();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.netflix.discovery.EurekaClientConfig#getDSServerDomain()
     */
    @Override
    public String getEurekaServerDNSName() {
        return configInstance.getStringProperty(
                namespace + EUREKA_SERVER_DNS_NAME_KEY,
                configInstance
                        .getStringProperty(namespace + EUREKA_SERVER_FALLBACK_DNS_NAME_KEY, null)
                        .get()).get();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.netflix.discovery.EurekaClientConfig#shouldUseDns()
     */
    @Override
    public boolean shouldUseDnsForFetchingServiceUrls() {
        return configInstance.getBooleanProperty(namespace + SHOULD_USE_DNS_KEY,
                false).get();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.netflix.discovery.EurekaClientConfig#getDiscoveryRegistrationEnabled
     * ()
     */
    @Override
    public boolean shouldRegisterWithEureka() {
        return configInstance.getBooleanProperty(
                namespace + REGISTRATION_ENABLED_KEY, true).get();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.netflix.discovery.EurekaClientConfig#shouldPreferSameZoneDS()
     */
    @Override
    public boolean shouldPreferSameZoneEureka() {
        return configInstance.getBooleanProperty(namespace + SHOULD_PREFER_SAME_ZONE_SERVER_KEY,
                true).get();
    }

    @Override
    public boolean allowRedirects() {
        return configInstance.getBooleanProperty(namespace + SHOULD_ALLOW_REDIRECTS_KEY, false).get();
    }

    /*
         * (non-Javadoc)
         *
         * @see com.netflix.discovery.EurekaClientConfig#shouldLogDeltaDiff()
         */
    @Override
    public boolean shouldLogDeltaDiff() {
        return configInstance.getBooleanProperty(
                namespace + SHOULD_LOG_DELTA_DIFF_KEY, false).get();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.netflix.discovery.EurekaClientConfig#shouldDisableDelta()
     */
    @Override
    public boolean shouldDisableDelta() {
        return configInstance.getBooleanProperty(namespace + SHOULD_DISABLE_DELTA_KEY,
                false).get();
    }

    @Nullable
    @Override
    public String fetchRegistryForRemoteRegions() {
        return configInstance.getStringProperty(namespace + SHOULD_FETCH_REMOTE_REGION_KEY, null).get();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.netflix.discovery.EurekaClientConfig#getRegion()
     */
    @Override
    public String getRegion() {
        DynamicStringProperty defaultEurekaRegion = configInstance.getStringProperty(
                CLIENT_REGION_FALLBACK_KEY, Values.DEFAULT_CLIENT_REGION);
        return configInstance.getStringProperty(namespace + CLIENT_REGION_KEY, defaultEurekaRegion.get()).get();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.netflix.discovery.EurekaClientConfig#getAvailabilityZones()
     */
    @Override
    public String[] getAvailabilityZones(String region) {
        return configInstance
                .getStringProperty(
                        namespace + region + "." + CONFIG_AVAILABILITY_ZONE_PREFIX,
                        DEFAULT_ZONE).get().split(",");
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.netflix.discovery.EurekaClientConfig#getEurekaServerServiceUrls()
     */
    @Override
    public List<String> getEurekaServerServiceUrls(String myZone) {
        String serviceUrls = configInstance.getStringProperty(
                namespace + CONFIG_EUREKA_SERVER_SERVICE_URL_PREFIX + "." + myZone, null).get();
        if (serviceUrls == null || serviceUrls.isEmpty()) {
            serviceUrls = configInstance.getStringProperty(
                    namespace + CONFIG_EUREKA_SERVER_SERVICE_URL_PREFIX + ".default", null).get();

        }
        if (serviceUrls != null) {
            return Arrays.asList(serviceUrls.split(","));
        }

        return new ArrayList<String>();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.netflix.discovery.EurekaClientConfig#shouldFilterOnlyUpInstances()
     */
    @Override
    public boolean shouldFilterOnlyUpInstances() {
        return configInstance.getBooleanProperty(
                namespace + SHOULD_FILTER_ONLY_UP_INSTANCES_KEY, true).get();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.netflix.discovery.EurekaClientConfig#getEurekaConnectionIdleTimeout()
     */
    @Override
    public int getEurekaConnectionIdleTimeoutSeconds() {
        return configInstance.getIntProperty(
                namespace + EUREKA_SERVER_CONNECTION_IDLE_TIMEOUT_KEY, 30)
                .get();
    }

    @Override
    public boolean shouldFetchRegistry() {
        return configInstance.getBooleanProperty(
                namespace + FETCH_REGISTRY_ENABLED_KEY, true).get();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.netflix.discovery.EurekaClientConfig#getRegistryRefreshSingleVipAddress()
     */
    @Override
    public String getRegistryRefreshSingleVipAddress() {
        return configInstance.getStringProperty(
                namespace + FETCH_SINGLE_VIP_ONLY_KEY, null).get();
    }

    /**
     * (non-Javadoc)
     *
     * @see EurekaClientConfig#getHeartbeatExecutorThreadPoolSize()
     */
    @Override
    public int getHeartbeatExecutorThreadPoolSize() {
        return configInstance.getIntProperty(
                namespace + HEARTBEAT_THREADPOOL_SIZE_KEY, Values.DEFAULT_EXECUTOR_THREAD_POOL_SIZE).get();
    }

    @Override
    public int getHeartbeatExecutorExponentialBackOffBound() {
        return configInstance.getIntProperty(
                namespace + HEARTBEAT_BACKOFF_BOUND_KEY, Values.DEFAULT_EXECUTOR_THREAD_POOL_BACKOFF_BOUND).get();
    }

    /**
     * (non-Javadoc)
     *
     * @see EurekaClientConfig#getCacheRefreshExecutorThreadPoolSize()
     */
    @Override
    public int getCacheRefreshExecutorThreadPoolSize() {
        return configInstance.getIntProperty(
                namespace + CACHEREFRESH_THREADPOOL_SIZE_KEY, Values.DEFAULT_EXECUTOR_THREAD_POOL_SIZE).get();
    }

    @Override
    public int getCacheRefreshExecutorExponentialBackOffBound() {
        return configInstance.getIntProperty(
                namespace + CACHEREFRESH_BACKOFF_BOUND_KEY, Values.DEFAULT_EXECUTOR_THREAD_POOL_BACKOFF_BOUND).get();
    }

    @Override
    public String getDollarReplacement() {
        return configInstance.getStringProperty(
                namespace + CONFIG_DOLLAR_REPLACEMENT_KEY, Values.CONFIG_DOLLAR_REPLACEMENT).get();
    }

    @Override
    public String getEscapeCharReplacement() {
        return configInstance.getStringProperty(
                namespace + CONFIG_ESCAPE_CHAR_REPLACEMENT_KEY, Values.CONFIG_ESCAPE_CHAR_REPLACEMENT).get();
    }

    @Override
    public boolean shouldOnDemandUpdateStatusChange() {
        return configInstance.getBooleanProperty(
                namespace + SHOULD_ONDEMAND_UPDATE_STATUS_KEY, true).get();
    }

    @Override
    public String getEncoderName() {
        return configInstance.getStringProperty(
                namespace + CLIENT_ENCODER_NAME_KEY, null).get();
    }

    @Override
    public String getDecoderName() {
        return configInstance.getStringProperty(
                namespace + CLIENT_DECODER_NAME_KEY, null).get();
    }

    @Override
    public String getClientDataAccept() {
        return configInstance.getStringProperty(
                namespace + CLIENT_DATA_ACCEPT_KEY, EurekaAccept.full.name()).get();
    }

    @Override
    public String getExperimental(String name) {
        return configInstance.getStringProperty(namespace + CONFIG_EXPERIMENTAL_PREFIX + "." + name, null).get();
    }

    @Override
    public EurekaTransportConfig getTransportConfig() {
        return transportConfig;
    }
}
