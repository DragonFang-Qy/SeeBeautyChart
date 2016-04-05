package com.fangzitcl.libs.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * &nbsp;&nbsp;包括:
 * <ol>
 * <li> 获取当前连接超时时间 {@link #getTimeout()}  </li>
 * <li> 设置当前连接超时时间 {@link #setTimeout(int)} </li>
 * <li> 异步Get请求 {@link #doGetAsyn(String, CallBack)}  </li>
 * <li> 异步Get请求 {@link #sendHttpConnGetAsyn(String, CallBack)} </li>
 * <li> 异步Post请求 {@link #doPostAsyn(String, String, CallBack)} </li>
 * <li> 异步Post请求 {@link #sendHttpConnPostAsyn(String, HashMap, CallBack)} </li>
 * <li> Get请求 {@link #doGet(String)} </li>
 * <li> Post请求 {@link #doPost(String, String)} </li>
 * <li> Get请求 {@link #sendHttpConnGet(String)} </li>
 * <li> Post请求 {@link #sendHttpConnPost(String, HashMap)} </li>
 * </ol>
 *
 * @class_name: UtilHttp
 * @package_name: com.fangzitcl.libs.util
 * @acthor: Fang_QingYou
 * @time: 15/12/30 上午11:52
 */
public class UtilHttp {

    private static final int length = 4 * 1024;
    private static final String HTTP_GET = "GET";
    private static final String HTTP_POST = "POST";
    private static final String ENCODING = "UTF-8";
    private static int TIMEOUT_IN_MILLIONS = 5 * 1000;// 默认5秒

    private UtilHttp() {

    }

    // 获取连接超时时间,单位秒
    public static int getTimeout() {
        return TIMEOUT_IN_MILLIONS / 1000;
    }

    // 设置连接超时时间,单位秒
    public static void setTimeout(int timeoutInMillions) {
        TIMEOUT_IN_MILLIONS = timeoutInMillions * 1000;
    }

    /**
     * 异步的Get请求
     *
     * @param urlStr
     * @param callBack
     */
    public static void doGetAsyn(final String urlStr, final CallBack callBack) {
        new Thread() {
            public void run() {
                try {
                    String result = doGet(urlStr);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            ;
        }.start();
    }

    /**
     * 异步的Get请求
     *
     * @param urlStr   请求的地址
     * @param callBack
     */
    public static void sendHttpConnGetAsyn(final String urlStr, final CallBack callBack) {
        new Thread() {
            public void run() {
                try {
                    String result = sendHttpConnGet(urlStr);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            ;
        }.start();
    }

    /**
     * 异步的Post请求
     *
     * @param urlStr   请求的地址
     * @param params
     * @param callBack
     * @throws Exception
     */
    public static void doPostAsyn(final String urlStr, final String params,
                                  final CallBack callBack) throws Exception {
        new Thread() {
            public void run() {
                try {
                    String result = doPost(urlStr, params);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            ;
        }.start();

    }

    /**
     * 异步的Post请求
     *
     * @param urlStr   请求的地址
     * @param params
     * @param callBack
     * @throws Exception
     */
    public static void sendHttpConnPostAsyn(final String urlStr, final HashMap<String, String> params,
                                            final CallBack callBack) throws Exception {
        new Thread() {
            public void run() {
                try {
                    String result = sendHttpConnPost(urlStr, params);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            ;
        }.start();

    }

    /**
     * Get请求，获得返回数据
     *
     * @param urlStr 请求的地址
     * @return
     * @throws Exception
     */
    public static String doGet(String urlStr) {
        URL url = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            conn.setRequestMethod(HTTP_GET);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[length];

                while ((len = is.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                baos.flush();
                return baos.toString();
            } else {
                throw new RuntimeException(" responseCode is not 200 ... ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
            }
            conn.disconnect();
        }

        return null;

    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     * @throws Exception
     */
    public static String doPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl
                    .openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod(HTTP_POST);
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", ENCODING);
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);

            if (param != null && !param.trim().equals("")) {
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 用 HttpURLConnection发送 get请求
     *
     * @param url
     * @return
     * @author: Fang Qingyou
     * @date 2015年6月26日下午3:28:12
     */
    public static String sendHttpConnGet(String url) {
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        String data = "";
        try {
            URL httpUrl = new URL(url);
            conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            conn.setRequestMethod(HTTP_GET);

            inputStream = conn.getInputStream();

            inputStreamReader = new InputStreamReader(
                    inputStream, ENCODING);

            bufferedReader = new BufferedReader(
                    inputStreamReader);
            String flag = "";
            while ((flag = bufferedReader.readLine()) != null) {
                data += flag;
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "error";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        } finally {
            // 用完记得关闭
            try {
                if (bufferedReader != null)
                    bufferedReader.close();

                if (inputStreamReader != null)
                    inputStreamReader.close();

                if (inputStream != null)
                    inputStream.close();

                if (conn != null)
                    conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    /**
     * 用 HttpURLConnection发送 post请求
     *
     * @param url   请求的地址
     * @param param 提交的参数&值的 map集合
     * @return
     */
    public static String sendHttpConnPost(String url,
                                          HashMap<String, String> param) {
        HttpURLConnection conn = null;
        String data = null;

        try {
            URL httpUrl = new URL(url);

            conn = (HttpURLConnection) httpUrl.openConnection();
            conn.connect();
            // 设置是否向connection输出，因为这个是post请求，参数要放在
            // http正文内，因此需要设为true
            conn.setDoOutput(true);
            // Read from the connection. Default is true.
            conn.setDoInput(true);
            conn.setRequestMethod(HTTP_POST);
            // Post 请求不能使用缓存
            conn.setUseCaches(false);

            // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
            // 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
            // 进行编码
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
            // 要注意的是connection.getOutputStream会隐含的进行connect。
            conn.connect();

            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            // The URL-encoded contend
            // 正文，正文内容其实跟get的URL中 '? '后的参数字符串一致
            String content = null;
            boolean isFist = true;
            for (Map.Entry<String, String> entrySet : param.entrySet()) {
                if (isFist) {
                    content += entrySet.getKey() + "="
                            + URLEncoder.encode(entrySet.getValue(), ENCODING);
                    isFist = false;
                } else {
                    content += "&" + entrySet.getKey() + "="
                            + URLEncoder.encode(entrySet.getValue(), ENCODING);

                }
            }
            // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写到流里面
            out.writeBytes(content);

            out.close();

            InputStream inputStream = conn.getInputStream();

            InputStreamReader inputStreamReader = new InputStreamReader(
                    inputStream, ENCODING);

            BufferedReader bufferedReader = new BufferedReader(
                    inputStreamReader);
            String flag = null;
            while ((flag = bufferedReader.readLine()) != null) {
                data += flag;
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "error";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        return data;
    }

    public interface CallBack {
        void onRequestComplete(String result);
    }


}
