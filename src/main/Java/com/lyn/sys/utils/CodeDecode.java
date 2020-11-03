package com.lyn.sys.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.lyn.sys.controller.FileController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.MultiPixelPackedSampleModel;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;

/**
 * @author iamhere
 * @description 解析二维码
 * @date 2020/9/24
 */
public class CodeDecode {

    // 二维码格式参数
    private static final EnumMap<DecodeHintType,Object> decodeHints = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);
    //
    static{
        decodeHints.put(DecodeHintType.CHARACTER_SET,"UTF-8");
    }

    /** @date 2020/9/24
     * @description     解析文件
     * @param   path    磁盘中之前生成的二维码文件
     * @return
     */
    public static String decodeFile(String path){
        File file = new File(path);
        if (!file.exists()){
            // 文件转图片对象
            try {
                BufferedImage image = ImageIO.read(file);
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                Binarizer binarizer = new HybridBinarizer(source);
                BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
                MultiFormatReader reader = new MultiFormatReader();
                Result result = reader.decode(binaryBitmap,decodeHints);
                String content = result.getText();
                return content;
            } catch (IOException | NotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }else {
            return null;
        }
    }


    /** @date 2020/9/24
     * @description     解析流
     * @param   null
     * @return
     */



    // 测试
    public static void main(String[] args) {
        // 磁盘中之前生成的二维码文件
        String string = decodeFile("D:/iamhere.gif");
        System.out.println(string);
    }
}
