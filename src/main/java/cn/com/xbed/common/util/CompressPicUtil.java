package cn.com.xbed.common.util;

import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by YUAN on 2016/9/14.
 * 压缩文件
 */
public class CompressPicUtil {
    
    private static Logger logger = LoggerFactory.getLogger(CompressPicUtil.class);

    /**
     * 通过缩放比例来压缩照片
     * @param inputFile 输入文件
     * @param outputFile 输出文件
     * @param scale 缩放比例
     * @param quality 质量比
     * @return
     */
    public static boolean ThumbnailsCompressPic(String inputFile, String outputFile, double scale, float quality) {
        try {
            Thumbnails.of(inputFile).scale(scale).outputQuality(quality).toFile(outputFile);
            return true;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 将照片压缩到100K以内
     * @param inputFile 输入文件
     * @param outputFile 输出文件
     * @param size 缩放后的大小
     * @param quality 质量比
     * @return 0 未压缩；1 已压缩；-1 压缩失败
     */
    public static int ThumbnailsCompressPic(String inputFile, String outputFile, int size, float quality) {
        File input = new File(inputFile);
        if(input.length() < 100 * 1024) { //小于100KB，不需要压缩
            return 0;
        }
        try {
            Thumbnails.Builder<File> fileBuilder = Thumbnails.of(input).scale(1.0).outputQuality(1.0);
            BufferedImage src = fileBuilder.asBufferedImage();
            if(src.getHeight(null) > size || src.getWidth(null) > size) {
                Thumbnails.Builder<File> builder = Thumbnails.of(input);
                builder.size(size, size); //取最大的尺寸变成size，然后等比缩放
                builder.outputQuality(quality).toFile(outputFile);
            } else {
                Thumbnails.of(input).scale(1.0).outputQuality(quality).toFile(outputFile);
            }
            return 1;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return -1;
    }

    public static void main(String[] args) {
//        String srcFileName = "E:\\yuan\\人脸识别\\3104×4192.jpg";
//        String fileName = "E:\\yuan\\人脸识别\\temp\\960x1280.jpg";
//        ThumbnailsCompressPic(srcFileName, fileName, 0.6, 0.1f);

        String srcFileName = "E:\\yuan\\人脸识别\\640×1136.jpg";
        String fileName = "E:\\yuan\\人脸识别\\temp\\960x1280.jpg";
        ThumbnailsCompressPic(srcFileName, fileName, 1024, 0.6f);
    }
}
