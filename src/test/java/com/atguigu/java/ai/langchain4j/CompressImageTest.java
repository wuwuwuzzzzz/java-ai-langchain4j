package com.atguigu.java.ai.langchain4j;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@SpringBootTest
public class CompressImageTest
{
    public static Image compressImage(BufferedImage image, double compressionRatio)
    {
        int targetWidth = (int) (image.getWidth() * Math.sqrt(compressionRatio));
        int targetHeight = (int) (image.getHeight() * Math.sqrt(compressionRatio));
        // 使用ImageIO进行压缩
        BufferedImage compressedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        compressedImage.getGraphics()
                       .drawImage(image.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH), 0, 0, null);
        return compressedImage;
    }

    public static long getImageSize(BufferedImage image)
    {
        File tempFile;
        try
        {
            tempFile = File.createTempFile("temp", ".tmp");
            ImageIO.write(image, "jpg", tempFile);
            long size = tempFile.length();
            tempFile.delete();
            return size;
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            return 0;
        }
    }

    @Test
    public void testCompress()
    {
        try
        {
            // 输入图片路径
            String inputImagePath = "/Users/wxz/Downloads/1739887160760.jpg";
            // 输出图片路径 
            String outputImagePath = "/Users/wxz/Downloads/1739887160760.jpg";
            // 目标压缩大小
            long targetSizeBytes = 8 * 1024L;
            // 图片原始大小
            File file = new File(inputImagePath);
            long originalSizeBytes = file.length();
            // 压缩比例
            double compressionRatio = (double) targetSizeBytes / originalSizeBytes;
            BufferedImage image = ImageIO.read(file);
            Image compressedImage = compressImage(image, compressionRatio);
            ImageIO.write((BufferedImage) compressedImage, "jpg", new File(outputImagePath));
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
