package com.traffic.server.utils.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map.Entry;

public class HttpClient extends AbstractHttpClient {
    private HttpURLConnection conn;

    @Override
    public String request() {
        attachQs();
        try {
            initConnection();
            attachHeader();
            sendBody();
            return getResponse();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (null != conn) {
                conn.disconnect();
                conn = null;
            }
        }
    }

    @Override
    public <T> T request(Class<T> clazz) {
        return JSON.parseObject(request(), clazz);
    }

    @Override
    public <T> T request(TypeReference<T> type) {
        return JSON.parseObject(request(), type);
    }

    private void attachQs() {
        if (null == param)
            return;
        String qs = param.toQs();
        if (qs == null || qs.equals(""))
            return;
        if (!url.contains("?")) {
            url = url + "?";
        } else if (!url.endsWith("?")) {
            url = url + "&";
        }
        url = url + qs;
    }

    private void initConnection() throws IOException {
        URL url = new URL(this.url);
        conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setConnectTimeout(connectionTimeout);
        conn.setReadTimeout(readTimeout);
        conn.setRequestMethod(method.toUpperCase());
        conn.setRequestProperty("Connection", "keep-alive");
        conn.setRequestProperty("Accept", "*/*");
        conn.setRequestProperty("Content-Type", contentType);
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36");
    }

    private void attachHeader() {
        if (headers == null)
            return;
        for (Entry<String, String> entry : headers.entrySet()) {
            conn.setRequestProperty(entry.getKey(), entry.getValue());
        }
    }

    private void sendBody() throws IOException {
        if (this.body == null)
            return;
        String strBody;
        if (this.contentType.startsWith(ContentType.JSON))
            strBody = this.body.toJson();
        else
            strBody = this.body.toQs();

        if (strBody == null || strBody.equals(""))
            return;
        byte[] body = strBody.getBytes(charset);
        conn.setRequestProperty("Content-Length", String.valueOf(body.length));
        try (OutputStream out = conn.getOutputStream()) {
            out.write(body);
            out.flush();
        } catch (IOException ignored) {

        }
    }

    private String getResponse() {
        try (InputStream in = getInputStream()) {
            StringBuilder sb = new StringBuilder();
            InputStreamReader reader = new InputStreamReader(in, charset);
            BufferedReader bufReader = new BufferedReader(reader);
            String buf;
            while ((buf = bufReader.readLine()) != null) {
                sb.append(buf);
            }
            return sb.toString();
        } catch (IOException ignored) {

        }
        return null;
    }

    private InputStream getInputStream() throws IOException {
        int code = conn.getResponseCode();
        if (HttpURLConnection.HTTP_OK == code)
            return conn.getInputStream();
        else if (code >= HttpURLConnection.HTTP_BAD_REQUEST)
            return conn.getErrorStream();
        throw new IOException("bad request");
    }
}