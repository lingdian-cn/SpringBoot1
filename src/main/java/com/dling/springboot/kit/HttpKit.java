package com.dling.springboot.kit;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;

/**
 * @description 网络工具类
 * @author dling
 * @date 2019-06-11 09:15:49
 * @since jdk8
 */
public class HttpKit {

    private static final String GET = "GET";
    private static final String POST = "POST";
    private static String CHARSET = "UTF-8";
    private static final SSLSocketFactory sslSocketFactory = initSSLSocketFactory();
    private static final HttpKit.TrustAnyHostnameVerifier trustAnyHostnameVerifier = new HttpKit.TrustAnyHostnameVerifier();

    private HttpKit() {
    }

    private static SSLSocketFactory initSSLSocketFactory() {
        try {
            TrustManager[] tm = new TrustManager[]{new HttpKit.TrustAnyTrustManager()};
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init((KeyManager[])null, tm, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    public static void setCharSet(String charSet) {
        if (charSet == null || "".equals(charSet.trim())) {
            throw new IllegalArgumentException("charSet can not be blank.");
        } else {
            CHARSET = charSet;
        }
    }

    private static HttpURLConnection getHttpConnection(String url, String method, Map<String, String> headers) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
        URL _url = new URL(url);
        HttpURLConnection conn = (HttpURLConnection)_url.openConnection();
        if (conn instanceof HttpsURLConnection) {
            ((HttpsURLConnection)conn).setSSLSocketFactory(sslSocketFactory);
            ((HttpsURLConnection)conn).setHostnameVerifier(trustAnyHostnameVerifier);
        }

        conn.setRequestMethod(method);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setConnectTimeout(19000);
        conn.setReadTimeout(19000);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");
        if (headers != null && !headers.isEmpty()) {
            Iterator var5 = headers.entrySet().iterator();

            while(var5.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry)var5.next();
                conn.setRequestProperty((String)entry.getKey(), (String)entry.getValue());
            }
        }

        return conn;
    }

    public static String get(String url, Map<String, String> queryParas, Map<String, String> headers) {
        HttpURLConnection conn = null;

        String var4;
        try {
            conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), "GET", headers);
            conn.connect();
            var4 = readResponseString(conn);
        } catch (Exception var8) {
            throw new RuntimeException(var8);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }

        }

        return var4;
    }

    public static String get(String url, Map<String, String> queryParas) {
        return get(url, queryParas, (Map)null);
    }

    public static String get(String url) {
        return get(url, (Map)null, (Map)null);
    }

    public static String post(String url, Map<String, String> queryParas, String data, Map<String, String> headers) {
        HttpURLConnection conn = null;

        String var11;
        try {
            conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), "POST", headers);
            conn.connect();
            if (data != null) {
                OutputStream out = conn.getOutputStream();
                out.write(data.getBytes(CHARSET));
                out.flush();
                out.close();
            }

            var11 = readResponseString(conn);
        } catch (Exception var9) {
            throw new RuntimeException(var9);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }

        }

        return var11;
    }

    public static String post(String url, Map<String, String> queryParas, String data) {
        return post(url, queryParas, data, (Map)null);
    }

    public static String post(String url, String data, Map<String, String> headers) {
        return post(url, (Map)null, data, headers);
    }

    public static String post(String url, String data) {
        return post(url, (Map)null, data, (Map)null);
    }

    private static String readResponseString(HttpURLConnection conn) {
        BufferedReader reader = null;

        String var4;
        try {
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), CHARSET));
            String line = reader.readLine();
            if (line != null) {
                StringBuilder ret = new StringBuilder();
                ret.append(line);

                while((line = reader.readLine()) != null) {
                    ret.append('\n').append(line);
                }

                var4 = ret.toString();
                return var4;
            }

            var4 = "";
        } catch (Exception var14) {
            throw new RuntimeException(var14);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException var13) {
                    // LogKit.error(var13.getMessage(), var13);
                    var13.printStackTrace();
                }
            }

        }

        return var4;
    }

    private static String buildUrlWithQueryString(String url, Map<String, String> queryParas) {
        if (queryParas != null && !queryParas.isEmpty()) {
            StringBuilder sb = new StringBuilder(url);
            boolean isFirst;
            if (url.indexOf(63) == -1) {
                isFirst = true;
                sb.append('?');
            } else {
                isFirst = false;
            }

            String key;
            String value;
            for(Iterator var4 = queryParas.entrySet().iterator(); var4.hasNext(); sb.append(key).append('=').append(value)) {
                Map.Entry<String, String> entry = (Map.Entry)var4.next();
                if (isFirst) {
                    isFirst = false;
                } else {
                    sb.append('&');
                }

                key = (String)entry.getKey();
                value = (String)entry.getValue();
                if (value == null || "".equals(value.trim())) {
                    try {
                        value = URLEncoder.encode(value, CHARSET);
                    } catch (UnsupportedEncodingException var9) {
                        throw new RuntimeException(var9);
                    }
                }
            }

            return sb.toString();
        } else {
            return url;
        }
    }



    /** @deprecated */
    @Deprecated
    public static String readIncommingRequestData(HttpServletRequest request) {
        return readData(request);
    }

    /**
     * 从请求对象中读取请求参数
     * post请求，传递的参数才能读取到数据
     * @param request 请求对象
     * @return String
     */
    public static String readData(HttpServletRequest request) {
        BufferedReader br = null;
        try {
            br = request.getReader();
            String line = br.readLine();
            if (line == null) {
                return "";
            } else {
                StringBuilder ret = new StringBuilder();
                ret.append(line);

                while((line = br.readLine()) != null) {
                    ret.append('\n').append(line);
                }

                return ret.toString();
            }
        } catch (IOException var4) {
            throw new RuntimeException(var4);
        }
    }

    /**
     * 按照指定字符集，从输入流中读取数据
     * @param inputStream 输入流
     * @param encode 字符集
     * @return String
     * @author dling
     */
    public static String readData(InputStream inputStream, String encode) throws IOException {
        StringWriter sw = new StringWriter();
        InputStreamReader isr = new InputStreamReader(inputStream);
        if (encode != null) {
            isr = new InputStreamReader(inputStream, encode);
        }
        char[] buffer = new char[4096];
        int len ;
        while (-1 != (len = isr.read(buffer))) {
            sw.write(buffer, 0, len);
        }
        return sw.toString();
    }

    private static class TrustAnyTrustManager implements X509TrustManager {
        private TrustAnyTrustManager() {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        private TrustAnyHostnameVerifier() {
        }

        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

}
