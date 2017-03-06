package cn.com.xbed.common.util;

import cn.com.xbed.common.bean.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.*;

/**
 * Created by YUAN on 2016/9/13.
 * 下载文件工具类
 */
public class DownloadUtil {

    private static final int BUFFER_SIZE = 4096;

    private static Logger logger = LoggerFactory.getLogger(DownloadUtil.class);

    /**
     * 图片转化成base64字符串
     * @return
     */
    public static String getImageStr() {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        String imgFile = "C:/Users/Star/Desktop/test.png";// 待处理的图片
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }

    /**
     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     * @param urlStr 图片地址
     * @return
     */
    public static String getImageStr(String urlStr) { 
        if ((null == urlStr) || (urlStr.length() <= 0)) {
            return null;
        }
        InputStream in = null;
        try {
            URL url = new URL(urlStr);
            
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setConnectTimeout(30000);
            urlConnection.setReadTimeout(60000);
            logger.debug("从链接：" + urlStr + "获取数据，请稍后...");

            in = urlConnection.getInputStream();
            byte[] data = new byte[in.available()];
            in.read(data);

            //对字节数组Base64编码
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(data);
        }
        catch (Exception e) {
            logger.error("下载文件异常！", e);
        } finally {
            try {
                if (null != in)
                    in.close();
            }
            catch (IOException ex) {
                logger.warn("Could not close InputStream", ex);
            }
        }
        return null;
    }

    public static int downloadFile(String urlStr, String filePath, StringBuffer fileName) {
        if ((null == urlStr) || (urlStr.length() <= 0)) {
            return -1;
        }
        URL url = null;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            logger.error("解析网址失败！请检查链接是否有误！", e);
            e.printStackTrace();
            return -1;
        }
        InputStream in = null;
        BufferedOutputStream bos = null;
        try {
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setConnectTimeout(30000);
            urlConnection.setReadTimeout(60000);
            logger.debug("从链接：" + urlStr + "获取数据，请稍后...");
            try {
                if (urlConnection.getResponseCode() != 200) {
                    logger.error("访问链接" + urlStr + "不成功！");
                    return -1;
                }
            } catch (UnknownHostException e) {
                logger.error("访问链接" + urlStr + "不成功！", e);
                return -1;
            } catch (ConnectException e) {
                logger.error("访问链接" + urlStr + "不成功！", e);
                return -1;
            }

            in = urlConnection.getInputStream();
            
            if(null != urlConnection.getHeaderField("Content-Type")) {
                String suffix = FileUtil.getSuffixByContentType(urlConnection.getHeaderField("Content-Type"));
                //设置后缀名
                fileName.append(suffix);
            }

            bos = new BufferedOutputStream(new FileOutputStream(filePath + fileName));

            int byteCount = 0;
            byte[] bytes = new byte[BUFFER_SIZE];
            int bytesRead = -1;
            while (-1 != (bytesRead = in.read(bytes))) {
                bos.write(bytes, 0, bytesRead);
                byteCount += bytesRead;
            }
            logger.debug("本次共下载 " + byteCount + " 字节的数据！");
            return byteCount;
        }
        catch (IOException e) {
            logger.error("文件I/O异常！", e);
            e.printStackTrace();
        } finally {
            try {
                if (null != in)
                    in.close();
            }
            catch (IOException ex) {
                logger.warn("Could not close InputStream", ex);
            }
            try {
                if (null != bos)
                    bos.close();
            }
            catch (IOException ex)
            {
                logger.warn("Could not close OutputStream", ex);
            }
        }
        return -1;
    }

    public static String downloadContent(String urlStr, String filePath, StringBuffer fileName) {
        if ((null == urlStr) || (urlStr.length() <= 0)) {
            return "";
        }
        InputStream in = null;
        BufferedOutputStream bos = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(30000);
            urlConnection.setReadTimeout(60000);
            logger.debug("从链接：" + urlStr + "获取数据，请稍后...");
            if (urlConnection.getResponseCode() != 200) {
                logger.error("访问链接" + urlStr + "不成功！");
                return "";
            }

            String suffix = "";
            if(null != urlConnection.getHeaderField("Content-Type")) {
                String [] contents = urlConnection.getHeaderField("Content-Type").split(";");
                for(String str : contents) {
                    suffix = FileUtil.getSuffixByContentType(str.trim());
                    if(!suffix.equals("")) {
                        break;
                    }
                }
                //设置后缀名
                fileName.append(suffix);
                System.out.println(suffix);
            }

            if(suffix.equals(".json")) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String tmp = "";
                String line = "";
                if(null != (line = reader.readLine())) {
                    tmp += line;
                }
                return tmp;
            }

            in = urlConnection.getInputStream();
            
            bos = new BufferedOutputStream(new FileOutputStream(filePath + fileName));

            int byteCount = 0;
            byte[] bytes = new byte[BUFFER_SIZE];
            int bytesRead = -1;
            while (-1 != (bytesRead = in.read(bytes))) {
                bos.write(bytes, 0, bytesRead);
                byteCount += bytesRead;
            }
            logger.info("本次共下载 " + byteCount + " 字节的数据！");
            return "success";
        } catch (Exception e) {
            logger.error("文件I/O异常！", e);
            e.printStackTrace();
        } finally {
            try {
                if (null != in)
                    in.close();
            } catch (IOException ex) {
                logger.warn("Could not close InputStream", ex);
            }
            try {
                if (null != bos)
                    bos.close();
            } catch (IOException ex) {
                logger.warn("Could not close OutputStream", ex);
            }
        }
        return "";
    }

    public static int downloadFile(String urlStr, String filePath) {
        if ((null == urlStr) || (urlStr.length() <= 0)) {
            return -1;
        }
        URL url = null;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            logger.error("解析网址失败！请检查链接是否有误！", e);
            e.printStackTrace();
            return -1;
        }
        InputStream in = null;
        BufferedOutputStream bos = null;
        try {
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setConnectTimeout(30000);
            urlConnection.setReadTimeout(60000);
            logger.debug("从链接：" + urlStr + "获取数据，请稍后...");
            try {
                if (urlConnection.getResponseCode() != 200) {
                    logger.error("访问链接" + urlStr + "不成功！");
                    return -1;
                }
            } catch (UnknownHostException e) {
                logger.error("访问链接" + urlStr + "不成功！", e);
                return -1;
            } catch (ConnectException e) {
                logger.error("访问链接" + urlStr + "不成功！", e);
                return -1;
            }

            HttpResponse response = new HttpResponse(urlConnection);

            String contentDisposition = response.getContentDisposition();

            String fileName = "";
            
            if(null != contentDisposition) {
                String[] tmps = contentDisposition.split(";");
                for(int i = 0; i < tmps.length; i++) {
                    if(tmps[i].indexOf("filename") > 0) {
                        fileName = getEqualValue(tmps[i]);
                        break;
                    }
                }
            }
            
            if(fileName.equals("")) {
                logger.warn("从链接" + urlStr + "的返回数据中获取文件名失败！");
                return -1;
            }

            in = urlConnection.getInputStream();

            bos = new BufferedOutputStream(new FileOutputStream(filePath + fileName));

            int byteCount = 0;
            byte[] bytes = new byte[BUFFER_SIZE];
            int bytesRead = -1;
            while (-1 != (bytesRead = in.read(bytes))) {
                bos.write(bytes, 0, bytesRead);
                byteCount += bytesRead;
            }
            logger.info("本次共下载 " + byteCount + " 字节的数据！");
            return byteCount;
        }
        catch (IOException e) {
            logger.error("文件I/O异常！", e);
            e.printStackTrace();
        } finally {
            try {
                if (null != in)
                    in.close();
            }
            catch (IOException ex) {
                logger.warn("Could not close InputStream", ex);
            }
            try {
                if (null != bos)
                    bos.close();
            }
            catch (IOException ex)
            {
                logger.warn("Could not close OutputStream", ex);
            }
        }
        return -1;
    }

    /**
     * 获取等号后面的值
     * @param value eg.content=123
     * @return eg.123
     */
    public static String getEqualValue(String value) {
        if(null != value && value.indexOf("=") > 0) {
            return value.split("=")[1];
        }
        return "";
    }

    public static void main(String[] args) {
//        String urlStr = "http://cdn.iciba.com/news/word/big_20160913b.jpg";
//        System.out.println(getImageStr(urlStr));
//
//        downloadFile(urlStr, "D:\\");
        
        String url = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=K8M60fQGsdzl0R3pXs1Owi2Uo9FZWax7JATEVOYct9iYq_ODQw85lFL1iT4K_AvNd_EXcmbiVQwz58xKXjzAOLAf2imTGn9ZI072w9aD05Ar7wiGVMc3CDqCsQoIafJXMTMbAFAFMF&media_id=gzKUSZ-2EkJjkc9o2w6x56yAVQhqV1G9Yy2lLbuAE45fVE_39ZXemVC7pyHBUEju";
        System.out.println(downloadContent(url, "", new StringBuffer()));
    }
}
