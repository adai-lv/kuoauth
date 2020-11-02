package com.kupug.kuoauth.model;

/**
 * <p>
 * Http 请求参数配置
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.3
 */
public final class HttpConfig {

    /**
     * 连接超时时长，单位毫秒
     */
    private long connectTimeout;

    /**
     * 读数据超时时长，单位毫秒
     */
    private long readTimeout;

    /**
     * 写数据超时时长，单位毫秒
     */
    private long writeTimeout;

    /**
     * 代理 host
     */
    private String proxyHost;

    /**
     * 代理 port
     */
    private Integer proxyPort;

    public long getConnectTimeout() {
        return connectTimeout;
    }

    public long getReadTimeout() {
        return readTimeout;
    }

    public long getWriteTimeout() {
        return writeTimeout;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public Integer getProxyPort() {
        return proxyPort;
    }

    private HttpConfig(Builder builder) {
        this.proxyHost = builder.proxyHost;
        this.proxyPort = builder.proxyPort;
        this.connectTimeout = builder.connectTimeout;
        this.writeTimeout = builder.writeTimeout;
        this.readTimeout = builder.readTimeout;
    }

    public static Builder builder() {
        return new HttpConfig.Builder();
    }

    public final static class Builder {
        private long connectTimeout;
        private long readTimeout;
        private long writeTimeout;
        private String proxyHost;
        private Integer proxyPort;

        public Builder proxy(String host, int port) {
            this.proxyHost = host;
            this.proxyPort = port;

            return this;
        }

        /**
         * 连接、读写数据设置统一的超时时长
         *
         * @param timeoutMillis 超时时长，单位毫秒
         * @return Builder
         */
        public Builder timeout(long timeoutMillis) {
            this.connectTimeout = timeoutMillis;
            this.readTimeout = timeoutMillis;
            this.writeTimeout = timeoutMillis;

            return this;
        }

        public Builder connectTimeout(long milliseconds) {
            this.connectTimeout = milliseconds;

            return this;
        }

        public Builder readTimeout(long milliseconds) {
            this.readTimeout = milliseconds;

            return this;
        }

        public Builder writeTimeout(long milliseconds) {
            this.writeTimeout = milliseconds;

            return this;
        }

        public HttpConfig build() {
            return new HttpConfig(this);
        }
    }
}
