package com.kupug.kuoauth;

import com.kupug.kuoauth.utils.CollectionUtils;
import com.kupug.kuoauth.utils.JsonUtils;
import com.kupug.kuoauth.utils.StringUtils;
import com.kupug.kuoauth.utils.UrlUtils;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * http client
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
public final class KuHttpClient {

    private final static Logger LOGGER = LoggerFactory.getLogger(KuHttpClient.class);

    /**
     * 连接超时，单位毫秒
     */
    private final static long DEFAULT_TIMEOUT = 3000;

    /**
     * User-Agent
     */
    private final static String USER_AGENT = "User-Agent";

    /**
     * 模拟 User-Agent
     */
    private final static String USER_AGENT_DATA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36 KuOAuth";

    private final static MediaType CONTENT_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * 代理网络设置
     */
    private Proxy proxy;

    /**
     * 是否需要对请求参数加密处理
     */
    private boolean encode;

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

    private KuHttpClient() {
    }

    private KuHttpClient(Builder builder) {
        this.proxy = builder.proxy;
        this.encode = builder.encode;

        resetTimeout(builder);
    }

    /**
     * GET 请求
     *
     * @param url    URL
     * @param params 参数
     * @return 结果
     */
    private String get(String url, Map<String, String> params, Map<String, String> headers) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        if (CollectionUtils.isNotEmpty(params)) {
            if (encode) {
                CollectionUtils.forEach(params, urlBuilder::addEncodedQueryParameter);
            } else {
                CollectionUtils.forEach(params, urlBuilder::addQueryParameter);
            }
        }
        HttpUrl httpUrl = urlBuilder.build();

        Request.Builder requestBuilder = new Request.Builder().url(httpUrl);
        if (CollectionUtils.isNotEmpty(headers)) {
            CollectionUtils.forEach(headers, requestBuilder::addHeader);
        }

        requestBuilder = requestBuilder.get();

        return execute(requestBuilder);
    }

    /**
     * POST Body请求
     *
     * @param url  URL
     * @param data JSON 参数
     * @return 结果
     */
    private String post(String url, String data, Map<String, String> headers) {
        if (StringUtils.isEmpty(data)) {
            data = "";
        }

        RequestBody body = RequestBody.create(CONTENT_TYPE_JSON, data);

        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (CollectionUtils.isNotEmpty(headers)) {
            CollectionUtils.forEach(headers, requestBuilder::addHeader);
        }
        requestBuilder = requestBuilder.post(body);

        return execute(requestBuilder);
    }

    /**
     * POST Form请求
     *
     * @param url    URL
     * @param params form 参数
     * @return 结果
     */
    private String post(String url, Map<String, String> params, Map<String, String> headers) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        if (CollectionUtils.isNotEmpty(params)) {
            if (encode) {
                CollectionUtils.forEach(params, formBuilder::addEncoded);
            } else {
                CollectionUtils.forEach(params, formBuilder::add);
            }
        }
        FormBody body = formBuilder.build();

        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (CollectionUtils.isNotEmpty(headers)) {
            CollectionUtils.forEach(headers, requestBuilder::addHeader);
        }
        requestBuilder = requestBuilder.post(body);

        return execute(requestBuilder);
    }

    private String execute(Request.Builder requestBuilder) {

        Request request = requestBuilder
                .header(USER_AGENT, USER_AGENT_DATA)
                .build();

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient()
                .newBuilder()
                .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .writeTimeout(writeTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(readTimeout, TimeUnit.MILLISECONDS);

        OkHttpClient httpClient = Objects.nonNull(this.proxy)
                ? httpClientBuilder.proxy(proxy).build()
                : httpClientBuilder.build();

        LOGGER.debug("Request Info: {} {}, Headers: {}", request.method(), request.url(), request.headers());

        try (Response response = httpClient.newCall(request).execute()) {

            LOGGER.debug("Response: {}", response);

            if (!response.isSuccessful()) {
                try {
                    String responseBody = response.body().string();
                    LOGGER.error("Response Body: {}", responseBody);

                    if (response.code() >= 500) {
                        throw new KuOAuthException("Internal Server Error【%s】:" + responseBody);
                    }

                    return responseBody;

                } catch (EOFException e) {
                    throw new KuOAuthException(String.format("[%s]%s", response.code(), response.message()));
                }
            }

            return response.body().string();

        } catch (IOException e) {
            String errorMsg = String.format("Http Request Fail【%s】, Request Info: %s %s, Headers: %s",
                    e.getMessage(), request.method(), request.url(), request.headers());

            throw new KuOAuthException(errorMsg, e);
        }
    }

    /**
     * 重置 http 请求超时时长
     *
     * @param builder builder 建造者
     */
    private void resetTimeout(Builder builder) {
        this.connectTimeout = builder.connectTimeout > 0 ? builder.connectTimeout : DEFAULT_TIMEOUT;
        this.writeTimeout = builder.writeTimeout > 0 ? builder.writeTimeout : DEFAULT_TIMEOUT;
        this.readTimeout = builder.readTimeout > 0 ? builder.readTimeout : DEFAULT_TIMEOUT;
    }

    public static Builder builder() {
        return new KuHttpClient.Builder();
    }

    public final static class Builder {
        private String url;
        private Map<String, String> params = new LinkedHashMap<>();
        private Map<String, String> headers = new HashMap<>();
        private boolean encode = true; // 默认对URL 参数进行编码处理
        private boolean requestBody;
        private Proxy proxy;
        private long connectTimeout;
        private long readTimeout;
        private long writeTimeout;

        public Builder fromUrl(String url) {
            this.url = url;

            return this;
        }

        public Builder queryParam(String key, Object value) {
            if (StringUtils.isNotEmpty(key)) {
                this.params.put(key, String.valueOf(value));
            }

            return this;
        }

        public Builder addHeader(String key, Object value) {
            if (StringUtils.isNotEmpty(key)) {
                this.headers.put(key, String.valueOf(value));
            }

            return this;
        }

        public Builder encode(boolean isEncode) {
            this.encode = isEncode;

            return this;
        }

        public Builder requestBody(boolean isRequestBody) {
            this.requestBody = isRequestBody;

            return this;
        }

        public Builder proxy(String host, Integer port) {

            if (StringUtils.isNotEmpty(host) && Objects.nonNull(port)) {

                InetSocketAddress inetSocketAddress = new InetSocketAddress(host, port);
                this.proxy = new Proxy(Proxy.Type.HTTP, inetSocketAddress);
            }

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

        /**
         * 构造 http url
         *
         * @return url
         */
        public String build() {

            String paramString = UrlUtils.valueOf(this.params, encode);

            return UrlUtils.concat(this.url, paramString);
        }

        public String get() {
            return new KuHttpClient(this).get(url, params, headers);
        }

        public String post() {
            KuHttpClient httpClient = new KuHttpClient(this);

            if (requestBody) {
                String data = null;
                if (CollectionUtils.isNotEmpty(params)) {
                    data = JsonUtils.toJSONString(params);
                }

                return httpClient.post(url, data, headers);

            } else {
                return httpClient.post(url, params, headers);
            }
        }
    }
}
