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
     * 超时时长，单位毫秒
     */
    private long timeout;

    /**
     * 代理 host
     */
    private String proxyHost;

    /**
     * 代理 port
     */
    private Integer proxyPort;

    public long getTimeout() {
        return timeout;
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
        this.timeout = builder.timeout;
    }

    public static Builder builder() {
        return new HttpConfig.Builder();
    }

    public final static class Builder {
        private long timeout;
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
            this.timeout = timeoutMillis;

            return this;
        }

        public HttpConfig build() {
            return new HttpConfig(this);
        }
    }
}
