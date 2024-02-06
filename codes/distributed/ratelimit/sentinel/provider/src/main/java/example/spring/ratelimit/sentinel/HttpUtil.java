package example.spring.ratelimit.sentinel;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Http 工具类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-11-17
 */
@Slf4j
@SuppressWarnings("unused")
public class HttpUtil {

    /**
     * HttpClient 构造器
     */
    private static final HttpClientBuilder BUILDER;
    private static final CloseableHttpClient CLIENT;

    static {
        // 创建连接池管理器
        PoolingHttpClientConnectionManager connectionManager =
            new PoolingHttpClientConnectionManager(60000, TimeUnit.MILLISECONDS);

        // 将最大连接数增加到300
        connectionManager.setMaxTotal(300);
        // 将每个路由基础的连接增加到60
        connectionManager.setDefaultMaxPerRoute(60);

        // 超时设置，连接时间设置
        RequestConfig requestConfig = RequestConfig.custom()
                                                   // 响应超时时间（从服务器获取响应数据的超时时间）
                                                   .setSocketTimeout(5000)
                                                   // 连接超时（与服务器连接超时时间：httpclient会创建一个异步线程用以创建socket连接，此处设置该socket的连接超时时间）
                                                   .setConnectTimeout(5000)
                                                   // 请求超时（从连接池中获取连接的超时时间）
                                                   .setConnectionRequestTimeout(5000)
                                                   .build();
        // 创建 HttpClient 构造器
        BUILDER = HttpClients.custom()
                             .setConnectionManager(connectionManager)
                             .setDefaultRequestConfig(requestConfig)
                             .setRetryHandler((exception, retryTimes, context) -> {

                                 boolean retry = false;
                                 HttpClientContext clientContext = HttpClientContext.adapt(context);
                                 String url = clientContext.getRequest().getRequestLine().getUri();
                                 String notRetryUrls = "";
                                 Set<String> set = new HashSet<>(StrUtil.split(notRetryUrls, ","));
                                 if (CollectionUtil.isNotEmpty(set)) {
                                     for (String element : set) {
                                         if (isRetryUrl(url, element)) {
                                             retry = true;
                                             break;
                                         }
                                     }
                                 }

                                 if (!retry) {
                                     log.warn("URL: {} 不在重试 URL 名单中！", url);
                                     return false;
                                 }
                                 if (exception instanceof InterruptedIOException) {
                                     log.warn("URL: {} 调用被中断，需要重试！", url);
                                 }
                                 if (retryTimes >= 3) {
                                     log.warn("URL: {} 已重试 {} 次，超过最大重试次数 {}！", url, retryTimes,
                                         3);
                                     return false;
                                 }
                                 return true;
                             });

        CLIENT = BUILDER.build();
    }

    public static boolean isRetryUrl(String url, String matchUrl) {
        if (StrUtil.isBlank(url)) {
            return false;
        }
        int endIndex = url.indexOf("?");
        String target = url.substring(0, endIndex > 0 ? endIndex : url.length());
        return matchUrl.equalsIgnoreCase(target);
    }

    public static String get(String url) {
        return get(url, null, null, StandardCharsets.UTF_8);
    }

    public static String get(String url, Charset charset) {
        return get(url, null, null, charset);
    }

    public static String get(String url, Map<String, String> headers) {
        return get(url, null, headers, StandardCharsets.UTF_8);
    }

    public static String get(String url, Map<String, String> headers, Charset charset) {
        return get(url, null, headers, charset);
    }

    public static String get(String url, Map<String, String> params, Map<String, String> headers) {
        return get(url, params, headers, StandardCharsets.UTF_8);
    }

    public static String get(String url, Map<String, String> params, Map<String, String> headers, Charset charset) {

        String finalUrl = url;
        if (MapUtil.isNotEmpty(params)) {
            if (MapUtil.isNotEmpty(params)) {
                finalUrl = url + "?" + MapUtil.joinIgnoreNull(params, "&", "=");
            }
        }

        log.info("GET 请求 url: {}, header: {}", finalUrl, JSONUtil.toJsonStr(headers));

        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(finalUrl);
            if (MapUtil.isNotEmpty(headers)) {
                for (Map.Entry<String, String> head : headers.entrySet()) {
                    httpGet.addHeader(head.getKey(), head.getValue());
                }
            }
            response = getClient().execute(httpGet);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity, charset);
        } catch (Exception e) {
            String msg = StrUtil.format("请求 url: {} 异常！exception: {}", finalUrl, e.getMessage());
            log.error(msg, e);
            return null;
        } finally {
            IoUtil.close(response);
        }
    }

    public static String post(String url, Map<String, String> params) {
        return post(url, params, null, StandardCharsets.UTF_8);
    }

    public static String post(String url, Map<String, String> params, Charset charset) {
        return post(url, params, null, charset);
    }

    public static String post(String url, Map<String, String> params, Map<String, String> headers) {
        return post(url, params, headers, StandardCharsets.UTF_8);
    }

    public static String post(String url, Map<String, String> params, Map<String, String> headers, Charset charset) {

        CloseableHttpResponse response = null;
        try {
            List<NameValuePair> list = new ArrayList<>();
            for (Map.Entry<String, String> e : params.entrySet()) {
                list.add(new BasicNameValuePair(e.getKey(), e.getValue()));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, StandardCharsets.UTF_8);

            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(entity);
            if (MapUtil.isNotEmpty(headers)) {
                for (Map.Entry<String, String> head : headers.entrySet()) {
                    httpPost.addHeader(head.getKey(), head.getValue());
                }
            }
            response = getClient().execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            return EntityUtils.toString(httpEntity, charset);
        } catch (Exception e) {
            String msg = StrUtil.format("请求 url: {} 异常！exception: {}", url, e.getMessage());
            log.error(msg, e);
            return null;
        } finally {
            IoUtil.close(response);
        }
    }

    public static String postJson(String url, String json) {
        return postJson(url, json, StandardCharsets.UTF_8);
    }

    public static String postJson(String url, String json, Charset charset) {
        return postJson(url, json, null, charset);
    }

    public static String postJson(String url, String json, Map<String, String> headers) {
        return postJson(url, json, headers, StandardCharsets.UTF_8);
    }

    public static String postJson(String url, String json, Map<String, String> headers, Charset charset) {

        log.info("POST 请求 url: {}, json: {}, header: {}", url, json, JSONUtil.toJsonStr(headers));

        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            if (null != headers) {
                for (Map.Entry<String, String> head : headers.entrySet()) {
                    httpPost.addHeader(head.getKey(), head.getValue());
                }
            }
            StringEntity entity = new StringEntity(json, charset);
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            response = getClient().execute(httpPost);
            HttpEntity respEntity = response.getEntity();
            String result = EntityUtils.toString(respEntity, charset);
            EntityUtils.consume(respEntity);
            return result;
        } catch (Exception e) {
            String msg = StrUtil.format("请求 url: {} 异常！exception: {}", url, e.getMessage());
            log.error(msg, e);
            return null;
        } finally {
            IoUtil.close(response);
        }
    }

    private static CloseableHttpClient getClient() {
        return CLIENT;
    }

    /**
     * 从http返回zip流中读取String
     */
    public static String getStringZip(String url, Map<String, String> headers) {
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            if (null != headers) {
                for (Map.Entry<String, String> head : headers.entrySet()) {
                    httpGet.addHeader(head.getKey(), head.getValue());
                }
            }
            response = getClient().execute(httpGet);
            HttpEntity entity = response.getEntity();
            return zipToString(entity.getContent());
        } catch (Exception e) {
            String msg = StrUtil.format("请求 url: {} 异常！exception: {}", url, e.getMessage());
            log.error(msg, e);
            return null;
        } finally {
            IoUtil.close(response);
        }
    }

    /**
     * ZIP流转String字符串
     */
    public static String zipToString(final InputStream stream) throws IOException {

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            final byte[] buffer = new byte[1024];
            int read;
            while ((read = stream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, read);
            }
        } finally {
            stream.close();
        }
        final ZipInputStream inputStream = new ZipInputStream(new ByteArrayInputStream(outputStream.toByteArray()));
        final StringWriter writer = new StringWriter();
        try {
            ZipEntry entry = inputStream.getNextEntry();
            if (entry != null) {
                final char[] buffer = new char[1024];
                final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            }
            return writer.toString();
        } finally {
            IoUtil.close(inputStream);
            IoUtil.close(outputStream);
            IoUtil.close(writer);
        }
    }

    public static void getFile(String url, File file) {
        CloseableHttpResponse response = null;

        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            response = getClient().execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                log.error("get url {} errorCode {} ", url, statusCode);
                return;
            }
            HttpEntity httpEntity = response.getEntity();
            inputStream = httpEntity.getContent();
            FileUtil.writeFromStream(inputStream, file);
            EntityUtils.consume(httpEntity);
        } catch (Exception e) {
            String msg = StrUtil.format("请求 url: {} 异常！exception: {}", url, e.getMessage());
            log.error(msg, e);
        } finally {
            IoUtil.close(inputStream);
            IoUtil.close(response);
        }
    }

    public static void getFile(String url, String file) {
        getFile(url, new File(file));
    }

    public static boolean verify(String url) {
        CloseableHttpResponse response = null;
        try {
            response = getClient().execute(new HttpHead(url));
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                log.warn("校验 url: {} 不通过！状态码: {}", url, statusCode);
                return false;
            }
        } catch (Exception e) {
            log.error("校验 url: {} 异常！", url, e);
        } finally {
            IoUtil.close(response);
        }
        return true;
    }

    public static String getQueryStringByMap(Map<String, Object> params) {
        if (MapUtil.isEmpty(params)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                continue;
            }
            if (first) {
                sb.append(key).append("=").append(value);
            } else {
                sb.append("&").append(key).append("=").append(value);
            }
            first = false;
        }
        return sb.toString();
    }

    public static String getQueryStringByObj(Object object) {
        Map<String, Object> propertiesMap = getParamMap(object);
        if (MapUtil.isEmpty(propertiesMap)) {
            return StrUtil.EMPTY;
        }
        return getQueryStringByMap(propertiesMap);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map<String, Object> getParamMap(Object object) {
        if (object == null) {
            return null;
        }
        TreeMap<String, Object> treeMap;
        if (object instanceof Map) {
            treeMap = new TreeMap<>((Map) object);
        } else {
            treeMap = new TreeMap<>(BeanUtil.beanToMap(object));
        }
        return treeMap;
    }

    public static Map<String, List<String>> getHeaderFields(String url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) (new URL(url)).openConnection();
            conn.setInstanceFollowRedirects(false);
            conn.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
            conn.setConnectTimeout(5000);
            return conn.getHeaderFields();
        } catch (IOException e) {
            log.error("http connection error", e);
        } finally {
            if (null != conn) {
                try {
                    conn.disconnect();
                } catch (Exception e) {
                    log.error("http connection close error", e);
                }
            }
        }
        return new HashMap<>(0);
    }

}
