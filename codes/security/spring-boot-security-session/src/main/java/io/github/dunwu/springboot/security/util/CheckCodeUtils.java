package io.github.dunwu.springboot.security.util;

import lombok.Data;
import lombok.ToString;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * 验证码工具
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-13
 */
public class CheckCodeUtils {

    /**
     * 验证码默认字符个数
     */
    private static final int CODE_COUNT = 6;

    /**
     * 验证码图片的默认宽度
     */
    private static final int WIDTH = 180;

    /**
     * 验证码图片的默认高度
     */
    private static final int HEIGHT = 40;

    /**
     * 验证码图片的默认干扰线数
     */
    private static final int LINE_COUNT = 40;

    private static final char[] CODE_SEQUENCE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();

    /**
     * 生成随机校验码
     *
     * @param expire 有效时间
     * @return CheckCode
     */
    public static CheckCode create(int expire) {
        return create(expire, WIDTH, HEIGHT, CODE_COUNT, LINE_COUNT);
    }

    /**
     * 生成随机校验码
     *
     * @param expire    有效时间
     * @param width     图片宽度
     * @param height    图片高度
     * @param codeCount 字符数
     * @param lineCount 生成的干扰线数
     * @return CheckCode
     */
    public static CheckCode create(int expire, int width, int height, int codeCount, int lineCount) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics2D graphics = image.createGraphics();
        Random random = new Random();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);

        // 字体高度
        int fontHeight = height - 4;
        Font font = new Font("Default", Font.PLAIN, fontHeight);
        graphics.setFont(font);

        for (int i = 0; i < lineCount; i++) {
            // x轴第一个点的位置
            int x1 = random.nextInt(width);
            // y轴第一个点的位置
            int y1 = random.nextInt(height);
            // x轴第二个点的位置
            int x2 = x1 + random.nextInt(width >> 2);
            // y轴第二个点的位置
            int y2 = y1 + random.nextInt(height >> 2);

            int red = random.nextInt(255);
            int green = random.nextInt(255);
            int blue = random.nextInt(255);

            graphics.setColor(new Color(red, green, blue));
            graphics.drawLine(x1, y1, x2, y2);
        }

        StringBuilder randomCode = new StringBuilder(codeCount);
        // 字符所在 x 坐标
        int x = (width - 10) / codeCount;
        // 字符所在 y 坐标
        int y = height - 8;
        for (int i = 0; i < codeCount; i++) {
            String strRand = String.valueOf(CODE_SEQUENCE[random.nextInt(CODE_SEQUENCE.length)]);
            int red = random.nextInt(255);
            int green = random.nextInt(255);
            int blue = random.nextInt(255);
            graphics.setColor(new Color(red, green, blue));
            graphics.drawString(strRand, i * x + 5, y);
            randomCode.append(strRand);
        }

        return new CheckCode(image, randomCode.toString(), expire);
    }

    public static void toFile(CheckCode checkCode, File output) throws IOException {
        ImageUtil.toFile(checkCode.getImage(), output, null);
    }

    public static void toOutputStream(CheckCode checkCode, OutputStream output) throws IOException {
        // ImageUtil.ImageParam params = new ImageUtil.ImageParam();
        // params.setFormat(ImageUtil.ImageTypeEnum.jpeg);
        // ImageUtil.toOutputStream(kaptcha.getImage(), output, params);
        ImageIO.write(checkCode.getImage(), "jpeg", output);
    }

    @Data
    @ToString
    public static class CheckCode implements Serializable {

        private BufferedImage image;

        private String code;

        private LocalDateTime expireTime;

        public CheckCode(BufferedImage image, String code, int expire) {
            this.image = image;
            this.code = code;
            this.expireTime = LocalDateTime.now().plusSeconds(expire);
        }

        public CheckCode(BufferedImage image, String code, LocalDateTime expireTime) {
            this.image = image;
            this.code = code;
            this.expireTime = expireTime;
        }

    }

}
