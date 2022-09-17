package io.github.dunwu.springboot.security.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * 图片处理工具类
 *
 * @author zhangpeng
 * @see <a href="https://github.com/coobird/thumbnailator/">thumbnailator</a>
 * @since 2017/2/13.
 */
public class ImageUtil {

    public static byte[] bufferedImageToBytes(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, format, os);
        byte[] bytes = os.toByteArray();
        os.flush();
        os.close();
        return bytes;
    }

    public static final InputStream bytes2InputStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }

    public static BufferedImage bytesToBufferedImage(byte[] bytes) throws IOException {
        InputStream in = new ByteArrayInputStream(bytes);
        BufferedImage image = ImageIO.read(in);
        in.close();
        return image;
    }

    public static void outputStreamToBytes(OutputStream os, byte[] bytes) throws IOException {
        os.write(bytes);
        os.flush();
        os.close();
    }

    public static void toFile(String input, String output, ImageParam params) throws IOException {
        Thumbnails.Builder builder = Thumbnails.of(input);
        fillBuilderWithParams(builder, params);
        builder.toFile(output);
    }

    private static void fillBuilderWithParams(Thumbnails.Builder builder, ImageParam params) {
        builder.scale(1.0);

        if (params == null) {
            return;
        }
        // 按照一定规则改变原图尺寸
        if (null != params.getWidth() && null != params.getHeight()) {
            builder.size(params.getWidth(), params.getHeight());
        } else if (null != params.getXscale() && null != params.getYscale()) {
            builder.scale(params.getXscale(), params.getYscale());
        } else if (null != params.getScale()) {
            builder.scale(params.getScale(), params.getScale());
        }

        // 设置图片旋转角度
        if (null != params.getRotate()) {
            builder.rotate(params.getRotate());
        }

        // 设置图片压缩质量
        if (null != params.getQuality()) {
            builder.outputQuality(params.getQuality());
        }

        // 设置图片格式
        if (null != params.getFormat()) {
            builder.outputFormat(params.getFormat().name());
        }

        // 设置水印
        if (null != params.getWaterMark()) {
            Positions pos = getPostionsByCode(params.getWaterMark().getPosition());
            builder.watermark(pos, params.getWaterMark().getImage(), params.getWaterMark().getOpacity());
        }
    }

    /**
     * 将位置类型码转换为 thumbnailator 可以识别的位置类型
     *
     * @param positions PositionsEnum
     * @return Positions
     */
    private static Positions getPostionsByCode(WaterMarkPositionsEnum positions) {
        switch (positions) {
            case TOP_LEFT:
                return Positions.TOP_LEFT;
            case TOP_CENTER:
                return Positions.TOP_CENTER;
            case TOP_RIGHT:
                return Positions.TOP_RIGHT;
            case CENTER_LEFT:
                return Positions.CENTER_LEFT;
            case CENTER:
                return Positions.CENTER;
            case CENTER_RIGHT:
                return Positions.CENTER_RIGHT;
            case BOTTOM_LEFT:
                return Positions.BOTTOM_LEFT;
            case BOTTOM_CENTER:
                return Positions.BOTTOM_CENTER;
            case BOTTOM_RIGHT:
                return Positions.BOTTOM_RIGHT;
            default:
                return null;
        }
    }

    public static void toFile(File input, File output, ImageParam params) throws IOException {
        Thumbnails.Builder builder = Thumbnails.of(input);
        fillBuilderWithParams(builder, params);
        builder.toFile(output);
    }

    public static void toFile(InputStream input, File output, ImageParam params) throws IOException {
        Thumbnails.Builder builder = Thumbnails.of(input);
        fillBuilderWithParams(builder, params);
        builder.toFile(output);
    }

    public static void toFile(BufferedImage input, File output, ImageParam params) throws IOException {
        Thumbnails.Builder builder = Thumbnails.of(input);
        fillBuilderWithParams(builder, params);
        builder.toFile(output);
    }

    public static void toFiles(List<File> input, List<File> output, ImageParam params) throws IOException {
        Thumbnails.Builder builder = Thumbnails.fromFiles(input);
        fillBuilderWithParams(builder, params);
        builder.toFiles(output);
    }

    public static void toOutputStream(String input, OutputStream output, ImageParam params) throws IOException {
        Thumbnails.Builder builder = Thumbnails.of(input);
        fillBuilderWithParams(builder, params);
        builder.toOutputStream(output);
    }

    public static void toOutputStream(File input, OutputStream output, ImageParam params) throws IOException {
        Thumbnails.Builder builder = Thumbnails.of(input);
        fillBuilderWithParams(builder, params);
        builder.toOutputStream(output);
    }

    public static void toOutputStream(InputStream input, OutputStream output, ImageParam params) throws IOException {
        Thumbnails.Builder builder = Thumbnails.of(input);
        fillBuilderWithParams(builder, params);
        builder.toOutputStream(output);
    }

    public static void toOutputStream(BufferedImage input, OutputStream output, ImageParam params) throws IOException {
        Thumbnails.Builder builder = Thumbnails.of(input);
        fillBuilderWithParams(builder, params);
        builder.toOutputStream(output);
    }

    public static void toOutputStreams(List<InputStream> input, List<OutputStream> output, ImageParam params)
        throws IOException {
        Thumbnails.Builder builder = Thumbnails.fromInputStreams(input);
        fillBuilderWithParams(builder, params);
        builder.toOutputStreams(output);
    }

    /**
     * 水印位置枚举
     */
    public enum WaterMarkPositionsEnum {

        /**
         * 左上
         */
        TOP_LEFT,

        /**
         * 上中
         */
        TOP_CENTER,

        /**
         * 上右
         */
        TOP_RIGHT,

        /**
         * 中左
         */
        CENTER_LEFT,

        /**
         * 正中
         */
        CENTER,

        /**
         * 中右
         */
        CENTER_RIGHT,

        /**
         * 底左
         */
        BOTTOM_LEFT,

        /**
         * 底中
         */
        BOTTOM_CENTER,

        /**
         * 底右
         */
        BOTTOM_RIGHT
    }

    /**
     * 文件类型枚举
     */
    public enum ImageTypeEnum {

        /**
         * png
         */
        png,

        /**
         * jpg
         */
        jpg,

        /**
         * jpeg
         */
        jpeg,

        /**
         * bmp
         */
        bmp,

        /**
         * gif
         */
        gif
    }

    /**
     * 图片处理参数
     */
    public static class ImageParam implements Serializable {

        private static final long serialVersionUID = 1295017827817501070L;

        /**
         * 图片宽度
         */
        private Integer width;

        /**
         * 图片高度
         */
        private Integer height;

        /**
         * 宽度比例
         */
        private Double xscale;

        /**
         * 高度比例
         */
        private Double yscale;

        /**
         * 总比例，相当于将xscale和yscale都设为同比例
         */
        private Double scale;

        /**
         * 旋转角度，范围为[0.0, 360.0]
         */
        private Double rotate;

        /**
         * 压缩质量，范围为[0.0, 1.0]
         */
        private Double quality;

        /**
         * 图片格式，支持jpg,jpeg,png,bmp,gif
         */
        private ImageTypeEnum format;

        /**
         * 水印信息
         */
        private WaterMark waterMark;

        public ImageParam() {
        }

        public ImageParam(Integer width, Integer height, Double scale, Double rotate, Double quality,
            ImageTypeEnum format, WaterMark waterMark) {
            this.width = width;
            this.height = height;
            this.scale = scale;
            this.rotate = rotate;
            this.quality = quality;
            this.format = format;
            this.waterMark = waterMark;
        }

        public ImageParam(Integer width, Integer height, Double xscale, Double yscale, Double rotate, Double quality,
            ImageTypeEnum format, WaterMark waterMark) {
            this.width = width;
            this.height = height;
            this.xscale = xscale;
            this.yscale = yscale;
            this.rotate = rotate;
            this.quality = quality;
            this.format = format;
            this.waterMark = waterMark;
        }

        public Integer getWidth() {
            return width;
        }

        public ImageParam setWidth(Integer width) {
            this.width = width;
            return this;
        }

        public Integer getHeight() {
            return height;
        }

        public ImageParam setHeight(Integer height) {
            this.height = height;
            return this;
        }

        public Double getXscale() {
            return xscale;
        }

        public ImageParam setXscale(Double xscale) {
            this.xscale = xscale;
            return this;
        }

        public Double getYscale() {
            return yscale;
        }

        public ImageParam setYscale(Double yscale) {
            this.yscale = yscale;
            return this;
        }

        public Double getScale() {
            return scale;
        }

        public ImageParam setScale(Double scale) {
            this.scale = scale;
            return this;
        }

        public Double getRotate() {
            return rotate;
        }

        public ImageParam setRotate(Double rotate) {
            this.rotate = rotate;
            return this;
        }

        public Double getQuality() {
            return quality;
        }

        public ImageParam setQuality(Double quality) {
            this.quality = quality;
            return this;
        }

        public ImageTypeEnum getFormat() {
            return format;
        }

        public ImageParam setFormat(ImageTypeEnum format) {
            this.format = format;
            return this;
        }

        public WaterMark getWaterMark() {
            return waterMark;
        }

        public ImageParam setWaterMark(WaterMark waterMark) {
            this.waterMark = waterMark;
            return this;
        }

        /**
         * 水印信息
         */
        public class WaterMark {

            private WaterMarkPositionsEnum position;

            private BufferedImage image;

            private Float opacity;

            public WaterMark() {
            }

            public WaterMark(WaterMarkPositionsEnum position, BufferedImage image, Float opacity) {
                this.position = position;
                this.image = image;
                this.opacity = opacity;
            }

            public WaterMarkPositionsEnum getPosition() {
                return position;
            }

            public void setPosition(WaterMarkPositionsEnum position) {
                this.position = position;
            }

            public BufferedImage getImage() {
                return image;
            }

            public void setImage(BufferedImage image) {
                this.image = image;
            }

            public Float getOpacity() {
                return opacity;
            }

            public void setOpacity(Float opacity) {
                this.opacity = opacity;
            }

        }

    }

}
