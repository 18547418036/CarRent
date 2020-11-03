package com.lyn.sys.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javafx.beans.binding.When;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumMap;

/**
 * @author iamhere
 * @description 二维码生成
 * @date 2020/9/24
 */
public class ZhengXin {

    private static final int BLACK = 0xFF000000; // 图案色
    private static final int WHITE = 0xFFFFFFFF; // 背景色

    // 二维码格式参数
    private static final EnumMap<EncodeHintType,Object> hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);

    // 二维码基础配置
    static{
        // 二维码纠错级别【 L：70%、M：15%、Q：25%、H：30%】
        // 纠错信息同样存储在二维码中，纠错级别越高，纠错信息占用的空间越多，即可存储的信息越少，H扫描最快，表示用户只扫描整个二维码的70%即可。
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 二维码白边大小【1、2、3、4】，默认4最大
        hints.put(EncodeHintType.MARGIN,1);
        // 设置放入字符的编码
        hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");
    }

    /** @date 2020/9/24
     * @description 生成二维码保存到D盘
     * @param   null
     * @return
     */
    public static void createCodeToD(String content,int width,int height,String savePath,String imageType){
        //
        try {
            // 创建二维码显示区域
            BitMatrix encode = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            // 获取宽高
            int code_width = encode.getWidth();
            int code_height = encode.getHeight();
            // 创建图片
            BufferedImage image = new BufferedImage(code_width,code_height,BufferedImage.TYPE_INT_RGB);
            // 生成二维码放入图片中
            for (int i = 0; i < code_width; i++){
                for (int j = 0; j < code_height; j++){
                    image.setRGB(i,j,encode.get(i,j)?BLACK: WHITE);
                }
            }
            // 创建二维码生成目标路径
            File file = new File(savePath);
            if (!file.exists()){
                file.createNewFile();
            }
            // 将二维码图片生成到目标路径
            ImageIO.write(image,imageType,file);

        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }

    }

    /** @date 2020/9/24
     * @description 生成二维码返回图片对象【servlet中可以调用，从而显示到jsp中】
     * @param   null
     * @return
     */
    public static BufferedImage createCodeToJsp(String content, int width, int height) {
        try {
            BitMatrix encode = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            // 得到二维码的宽度
            int code_width = encode.getWidth();
            int code_height = encode.getHeight();
            // 创建图片
            BufferedImage image = new BufferedImage(code_width, code_height, BufferedImage.TYPE_INT_RGB);
            // 把二维码里面的信息写到图片里面
            for (int i = 0; i < code_width; i++) {
                for (int j = 0; j < code_height; j++) {
                    image.setRGB(i, j, encode.get(i, j) ? BLACK : WHITE);
                }
            }
            return image;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /** @date 2020/9/24
     * @description 生成带logo的二维码
     * @param   logoStream logo的流对象
     * @return
     */
    public static void createCode_logoToDisk(String content, int width, int height, String savePath, String imageType, InputStream logoStream) throws IOException {
        // 生成二维码图片【调用之前生成二维码的图片】
        BufferedImage codeNormal = createCodeToJsp(content, width, height);
        //
        if (codeNormal!=null){
            if (logoStream!=null){
                // 画笔
                Graphics2D graphics = codeNormal.createGraphics();
                // 得到logo图片信息
                BufferedImage logoImage = null; // logo图片对象
                try {
                    logoImage = ImageIO.read(logoStream);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                // 得到logo原始宽高
                int logo_weidth_old = logoImage.getWidth();
                int logo_height_old = logoImage.getHeight();
                // 得到二维码宽高
                int code_width = codeNormal.getWidth();
                int code_height = codeNormal.getHeight();
                // logo在二维码中存在的最大值
                int logo_width_max = code_width/5;
                int logo_height_max = code_height/5;
                // logo在二维码中的可用宽高
                int logo_width = logo_width_max < logo_height_old?logo_width_max:logo_weidth_old;
                int logo_height = logo_height_max < logo_height_old?logo_height_max:logo_height_old;
                // logo开始点坐标
                int logo_x = (code_width - logo_width)/2;
                int logo_y = (code_height - logo_height)/2;
                // 画图
                graphics.drawImage(logoImage,logo_x,logo_y,logo_width,logo_height,null);
                // 生效
                graphics.dispose();


                // 创建二维码生成目标路径
                File file = new File(savePath);
                if (!file.exists()){
                        file.createNewFile();
                }
                // 将二维码图片生成到目标路径
                ImageIO.write(codeNormal,imageType,file);

            }else {
                System.out.println("失败");
            }
        }

    }


    // 测试
    public static void main(String[] args) {
        // toD("iamhere",400,400,"D:/iamhere.gif","JPEG");
    }
}

