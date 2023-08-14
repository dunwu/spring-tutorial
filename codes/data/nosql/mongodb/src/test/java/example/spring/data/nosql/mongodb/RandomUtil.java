package example.spring.data.nosql.mongodb;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.StrUtil;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机工具类
 *
 * @author xiaoleilu
 */
public class RandomUtil extends cn.hutool.core.util.RandomUtil {

    /**
     * 数字全集
     */
    public static final String NUMBER_SAMPLE = "0123456789";

    /**
     * 小写英文字母全集
     */
    public static final String EN_LOWER_CHAR_SAMPLE = "abcdefghijklmnopqrstuvwxyz";

    /**
     * 大写英文字母全集
     */
    public static final String EN_UPPER_CHAR_SAMPLE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 英文字母全集
     */
    public static final String EN_CHAR_SAMPLE = StrUtil.join("", EN_LOWER_CHAR_SAMPLE, EN_UPPER_CHAR_SAMPLE);

    /**
     * 用于随机选的字符和数字
     */
    public static final String WORD_SAMPLE = StrUtil.join("", EN_CHAR_SAMPLE, NUMBER_SAMPLE);

    /**
     * 创建{@link java.security.SecureRandom}，类提供加密的强随机数生成器 (RNG)<br>
     *
     * @param seed 自定义随机种子
     * @return {@link java.security.SecureRandom}
     * @since 4.6.5
     */
    public static SecureRandom createSecureRandom(byte[] seed) {
        return (null == seed) ? new SecureRandom() : new SecureRandom(seed);
    }

    public static String randomChineseBoyFirstName() {
        int count = randomInt(2, 4);

        if (2 == count) {
            String secondDictionary = "睿,浩,博,瑞,昊,杰,刚,伟,勇,林,驰,俊,明,清,正,云,鹏,晨,强";
            List<String> names = StrUtil.splitTrim(secondDictionary, ",");
            return randomInList(names);
        } else if (3 == count) {
            String secondDictionary = "子,梓,浩,宇,俊,文,志,晓,小,大";
            String thirdDictionary =
                "轩,宇,泽,杰,豪,刚,伟,勇,明,然,涵,蔼,仁,容,德,轩,贤,良,伦,正,清,义,诚,直,道,达,耀,兴,荣,华,丰,余,昌,盛,涛";
            List<String> seconds = StrUtil.splitTrim(secondDictionary, ",");
            List<String> thirds = StrUtil.splitTrim(thirdDictionary, ",");
            return randomInList(seconds) + randomInList(thirds);
        } else {
            return "";
        }
    }

    public static String randomChineseBoyName() {
        return randomChineseLastName() + randomChineseBoyFirstName();
    }

    public static String randomChineseGirlFirstName() {
        int count = randomInt(2, 4);

        if (2 == count) {
            String secondDictionary = "悦,妍,玥,蕊,欣,洁,雪,静,慧,晴,娜,玟,倩,柔,雅,丽,萍,娟";
            List<String> names = StrUtil.splitTrim(secondDictionary, ",");
            return randomInList(names);
        } else if (3 == count) {
            String secondDictionary = "雨,梓,欣,子,语,馨,思,婉,涵,婷,文,梦,玉,安";
            String thirdDictionary = "涵,萱,怡,彤,琪,文,宁,雪,彤,柔,雅,丽,曼,云,晴,琴,娜";
            List<String> seconds = StrUtil.splitTrim(secondDictionary, ",");
            List<String> thirds = StrUtil.splitTrim(thirdDictionary, ",");
            return randomInList(seconds) + randomInList(thirds);
        } else {
            return "";
        }
    }

    public static String randomChineseGirlName() {
        return randomChineseLastName() + randomChineseGirlFirstName();
    }

    public static String randomChineseLastName() {
        String dictionary =
            "李,王,张,刘,陈,杨,黄,赵,周,吴,徐,孙,朱,马,胡,郭,林,何,高,梁,郑,罗,宋,谢,唐,韩,曹,许,邓,萧,冯,曾,程,蔡,彭,潘,袁,于,董,余,苏,叶,吕,魏,蒋,田,杜,丁,沈,姜,范,江,"
                + "傅,钟,卢,汪,戴,崔,任,陆,廖,姚,方,金,邱,夏,谭,韦,贾,邹,石,熊,孟,秦,阎,薛,侯,雷,白,龙,段,郝,孔,邵,史,毛,常,万,顾,赖,武,康,贺,严,尹,钱,施,牛,洪,龚,汤,"
                + "陶,黎,温,莫,易,樊,乔,文,安,殷,颜,庄,章,鲁,倪,庞,邢,俞,翟,蓝,聂,齐,向,申,葛,岳";
        List<String> names = StrUtil.splitTrim(dictionary, ",");
        return randomInList(names);
    }

    public static String randomChineseLetterString(final int min, final int max) {
        char begin = '\u4E00';
        char end = '\u9FA5';
        StringBuilder sb = new StringBuilder();
        for (int index = begin; index <= end; index++) {
            sb.append(index);
        }

        int count = randomInt(min, max);
        return randomString(sb.toString(), count);
    }

    /**
     * 获得指定范围内的随机数
     *
     * @param min 最小数（包含）
     * @param max 最大数（不包含）
     * @return 随机数
     */
    public static int randomInt(int min, int max) {
        return getRandom().nextInt(min, max);
    }

    /**
     * 获得一个随机的字符串
     *
     * @param sample 随机字符选取的样本
     * @param length 字符串的长度
     * @return 随机字符串
     */
    public static String randomString(final String sample, int length) {
        return randomString(sample, 0, length);
    }

    public static String randomString(final String sample, final int min, final int max) {
        if (StrUtil.isEmpty(sample)) {
            return StrUtil.EMPTY;
        }

        if (min < 0 || max < 0 || max < min) {
            return StrUtil.EMPTY;
        }

        int length = getRandom().nextInt(min, max);
        final StringBuilder sb = new StringBuilder(length);
        int sampleLength = sample.length();
        for (int i = 0; i < length; i++) {
            int number = randomInt(sampleLength);
            sb.append(sample.charAt(number));
        }
        return sb.toString();
    }

    public static String randomChineseName() {
        boolean isGirl = randomBoolean();
        return randomChineseName(isGirl);
    }

    public static String randomChineseName(final boolean isGirl) {
        if (isGirl) {
            return randomChineseGirlName();
        } else {
            return randomChineseBoyName();
        }
    }

    public static String randomChineseSimpleLetterString(final int min, final int max) {
        int count = randomInt(min, max);
        StringBuilder sb = new StringBuilder();
        for (char index = 0; index < count; index++) {
            sb.append(randomChineseSimpleLetter());
        }
        return sb.toString();
    }

    /**
     * 随机获取任意简体字符
     *
     * @see <a href= "https://baike.baidu.com/item/信息交换用汉字编码字符集?fromtitle=GB2312&fromid=483170">百度词条-GB2312</a>
     */
    public static String randomChineseSimpleLetter() {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) randomInt(0xB0, 0xF7);
        bytes[1] = (byte) randomInt(0xA1, 0xFF);

        try {
            return new String(bytes, "GB2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String randomDate(final Date min, final Date max) {
        return randomDate(min, max, DatePattern.NORM_DATETIME_PATTERN);
    }

    public static String randomDate(final Date min, final Date max, final String pattern) {
        return randomDate(DateUtil.toLocalDateTime(min), DateUtil.toLocalDateTime(max), pattern);
    }

    public static String randomDate(final LocalDateTime min, final LocalDateTime max, final String pattern) {
        long minSeconds = min.toEpochSecond(ZoneOffset.UTC);
        long maxSeconds = max.toEpochSecond(ZoneOffset.UTC);
        long random = ThreadLocalRandom.current().nextLong(minSeconds, maxSeconds);
        LocalDateTime localDate = LocalDateTime.ofEpochSecond(random, 1, ZoneOffset.UTC);
        return localDate.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static Date randomDate(final LocalDateTime min, final LocalDateTime max) {
        long minSeconds = min.toEpochSecond(ZoneOffset.UTC);
        long maxSeconds = max.toEpochSecond(ZoneOffset.UTC);
        long random = ThreadLocalRandom.current().nextLong(minSeconds, maxSeconds);
        LocalDateTime localDate = LocalDateTime.ofEpochSecond(random, 1, ZoneOffset.UTC);
        return DateUtil.toDate(localDate);
    }

    public static String randomDomain() {
        String domain1 = randomString(2, 11);
        String domain2 = randomString(2, 4);
        return new StringBuilder().append(domain1.toLowerCase()).append(".")
                                  .append(domain2.toLowerCase()).toString();
    }

    /**
     * 随机获得列表中的元素
     *
     * @param <T>  元素类型
     * @param list 列表
     * @return 随机元素
     */
    public static <T> T randomEle(List<T> list) {
        return randomEle(list, list.size());
    }

    /**
     * 随机获得列表中的元素
     *
     * @param <T>   元素类型
     * @param list  列表
     * @param limit 限制列表的前N项
     * @return 随机元素
     */
    public static <T> T randomEle(List<T> list, int limit) {
        return list.get(randomInt(limit));
    }

    /**
     * 随机获得数组中的元素
     *
     * @param <T>   元素类型
     * @param array 列表
     * @return 随机元素
     * @since 3.3.0
     */
    public static <T> T randomEle(T[] array) {
        return randomEle(array, array.length);
    }

    /**
     * 随机获得数组中的元素
     *
     * @param <T>   元素类型
     * @param array 列表
     * @param limit 限制列表的前N项
     * @return 随机元素
     * @since 3.3.0
     */
    public static <T> T randomEle(T[] array, int limit) {
        return array[randomInt(limit)];
    }

    /**
     * 随机获得列表中的一定量的不重复元素，返回Set
     *
     * @param <T>        元素类型
     * @param collection 列表
     * @param count      随机取出的个数
     * @return 随机元素
     * @throws IllegalArgumentException 需要的长度大于给定集合非重复总数
     */
    public static <T> Set<T> randomEleSet(Collection<T> collection, int count) {
        final ArrayList<T> source = CollectionUtil.distinct(collection);
        if (count > source.size()) {
            throw new IllegalArgumentException("Count is larger than collection distinct size !");
        }

        final HashSet<T> result = new HashSet<>(count);
        int limit = source.size();
        while (result.size() < count) {
            result.add(randomEle(source, limit));
        }

        return result;
    }

    /**
     * 随机获得列表中的一定量元素
     *
     * @param <T>   元素类型
     * @param list  列表
     * @param count 随机取出的个数
     * @return 随机元素
     */
    public static <T> List<T> randomEles(List<T> list, int count) {
        final List<T> result = new ArrayList<>(count);
        int limit = list.size();
        while (result.size() < count) {
            result.add(randomEle(list, limit));
        }

        return result;
    }

    public static String randomEmail() {
        String name = randomString(2, 11);
        return new StringBuilder().append(name.toLowerCase()).append("@")
                                  .append(randomDomain()).toString();
    }

    public static <E extends Enum<E>> E randomEnum(final Class<E> enumClass) {
        List<E> list = new ArrayList<>(EnumSet.allOf(enumClass));
        int index = randomInt(0, list.size());
        return list.get(index);
    }

    public static String randomFirstName() {
        return randomFirstName(randomBoolean());
    }

    public static String randomFirstName(final boolean isGirl) {
        if (isGirl) {
            return randomGirlFirstName();
        } else {
            return randomBoyFirstName();
        }
    }

    public static String randomBoyFirstName() {
        String dictionary =
            "Dylan,Jacob,Ethan,Gabriel,Nicholas,Jack,Joshua,Caleb,Ryan,Andrew,Caden,Tyler,Michael,Jaden,Zachary,"
                + "Connor,Logan,Aiden,Noah,Alexande,Jackson,Brayden,Lucas,William,Nathan,Joseph,Justin,Daniel,"
                + "Benjamin,Christopher,James,Gavin,Evan,Austin,Cameron,Brandon,Mason,Luke,Anthony,Christian,"
                + "Jonathan,Owen,David,John,Matthew,Samuel,Sean,Hunter,Elijah,Thomas";
        List<String> names = StrUtil.splitTrim(dictionary, ",");
        return randomInList(names);
    }

    public static String randomGirlFirstName() {
        String dictionary =
            "Lily,Emily,Sophia,Isabella,Ava,Emma,Kaitlyn,Hannah,Hailey,Olivia,Sarah,Abigail,Madeline,Madison,Kaylee,"
                + "Ella,Riley,Alexis,Alyssa,Samantha,Lauren,Mia,Grace,Chloe,Ashley,Brianna,Jessica,Elizabeth,Taylor,"
                + "Makayla,Makenzie,Anna,Zoe,Kayla,Sydney,Megan,Natalie,Kylie,ulia,Avery,Katherine,Isabel,Victoria,"
                + "Morgan,Kyra,Jasmine,Allison,Savannah,JRachel,Jordan";
        List<String> names = StrUtil.splitTrim(dictionary, ",");
        return randomInList(names);
    }

    public static String randomIpv4() {
        int[][] range = { { 607649792, 608174079 }, // 36.56.0.0-36.63.255.255
            { 1038614528, 1039007743 }, // 61.232.0.0-61.237.255.255
            { 1783627776, 1784676351 }, // 106.80.0.0-106.95.255.255
            { 2035023872, 2035154943 }, // 121.76.0.0-121.77.255.255
            { 2078801920, 2079064063 }, // 123.232.0.0-123.235.255.255
            { -1950089216, -1948778497 }, // 139.196.0.0-139.215.255.255
            { -1425539072, -1425014785 }, // 171.8.0.0-171.15.255.255
            { -1236271104, -1235419137 }, // 182.80.0.0-182.92.255.255
            { -770113536, -768606209 }, // 210.25.0.0-210.47.255.255
            { -569376768, -564133889 }, // 222.16.0.0-222.95.255.255
        };

        Random rdint = new Random();
        int index = rdint.nextInt(10);
        String ip = num2ip(range[index][0]
            + new Random().nextInt(range[index][1] - range[index][0]));
        return ip;
    }

    private static String num2ip(final int ip) {
        int[] b = new int[4];
        String result = "";
        b[0] = (ip >> 24) & 0xff;
        b[1] = ((ip >> 16) & 0xff);
        b[2] = ((ip >> 8) & 0xff);
        b[3] = (ip & 0xff);
        result = Integer.toString(b[0]) + "." + Integer.toString(b[1]) + "."
            + Integer.toString(b[2]) + "." + Integer.toString(b[3]);
        return result;
    }

    public static String randomLastName() {
        String dictionary =
            "Smith,Jones,Williams,Taylor,Brown,Davies,Evans,Wilson,Thomas,Johnson,Roberts,Robinson,Thompson,Wright,"
                + "Walker,White,Edwards,Hughes,Green,Hall,Lewis,Harris,Clarke,Patel,Jackson";
        List<String> names = StrUtil.splitTrim(dictionary, ",");
        return randomInList(names);
    }

    public static <T> T randomInArray(final T[] list) {
        return randomInList(Arrays.asList(list));
    }

    public static <T> T randomInList(final List<T> list) {
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        int index = randomInt(0, list.size());
        return list.get(index);
    }

    public static String randomMac() {
        return randomMac("");
    }

    public static String randomMac(final String separator) {
        Random random = new Random();
        String[] mac = { String.format("%02x", random.nextInt(0xff)),
            String.format("%02x", random.nextInt(0xff)),
            String.format("%02x", random.nextInt(0xff)),
            String.format("%02x", random.nextInt(0xff)),
            String.format("%02x", random.nextInt(0xff)),
            String.format("%02x", random.nextInt(0xff)) };
        return String.join(separator, mac);
    }

    public static String randomString(final int min, final int max) {
        return randomString(WORD_SAMPLE, min, max);
    }

    public static String randomNumString(final int min, final int max) {
        return randomString(NUMBER_SAMPLE, min, max);
    }

    /**
     * 使用性能更好的SHA1PRNG, Tomcat的sessionId生成也用此算法. 但JDK7中，需要在启动参数加入 -Djava.security=file:/dev/./urandom （中间那个点很重要）
     * 详见：《SecureRandom的江湖偏方与真实效果》http://calvin1978.blogcn.com/articles/securerandom.html
     */
    public static SecureRandom secureRandom() {
        try {
            return SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            return new SecureRandom();
        }
    }

    /**
     * 返回无锁的 ThreadLocalRandom
     */
    public static ThreadLocalRandom threadLocalRandom() {
        return ThreadLocalRandom.current();
    }

}
