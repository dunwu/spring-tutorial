package example.spring.web.form.validator.util;

import cn.hutool.core.util.StrUtil;

import java.util.regex.Pattern;

/**
 * 文本校验工具类
 * <p>
 * 主要基于正则表达式工具 {@link RegexUtil} 对常用的校验场景进行校验
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-12-09
 */
public class ValidatorUtil {

    private ValidatorUtil() { }

    // 字符校验
    // ------------------------------------------------------------------------------

    /**
     * 校验文本中是否包含禁止的字符
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean hasForbiddenChar(final String text, final CharSequence... forbiddenChars) {
        if (forbiddenChars == null || StrUtil.isBlank(text)) {
            return false;
        }
        String forbidden = String.join("", forbiddenChars);
        String regex = String.format("[%s]+", forbidden);
        return RegexUtil.contains(text, regex);
    }

    /**
     * 校验文本中是否包含中文字符
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean hasChineseChar(final String text) {
        return RegexUtil.contains(text, RegexUtil.ZH_CHAR);
    }

    /**
     * 校验全是汉字字符
     * <p>
     * 描述：校验字符串中只能有中文字符（不包括中文标点符号）。
     * <p>
     * 匹配：春眠不觉晓
     * <p>
     * 不匹配：12345 | abcdef
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean isAllChineseChar(final String text) {
        return RegexUtil.matches(text, RegexUtil.ZH_CHAR);
    }

    /**
     * 校验文本中是否包含英文字符
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean hasEnglishChar(final String text) {
        return RegexUtil.contains(text, RegexUtil.EN_CHAR);
    }

    /**
     * 校验全是英文字母
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean isAllEnglishChar(final String text) {
        return RegexUtil.matches(text, RegexUtil.EN_CHAR);
    }

    /**
     * 校验全是单词字符，即只能由数字、英文字母或下划线组成
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean isAllGeneralAndChineseChar(final String text) {
        return RegexUtil.matches(text, RegexUtil.ALL_GENERAL_AND_ZH_CHAR);
    }

    /**
     * 校验全是单词字符，即只能由数字、英文字母或下划线组成
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean isAllGeneralChar(final String text) {
        return RegexUtil.matches(text, RegexUtil.GENERAL_CHAR);
    }

    /**
     * 校验全是小写字母
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean isAllLowerEnglishChar(final String text) {
        return RegexUtil.matches(text, RegexUtil.EN_LOWER_CHAR);
    }

    /**
     * 校验全是大写字母
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean isAllUpperEnglishChar(final String text) {
        return RegexUtil.matches(text, RegexUtil.EN_UPPER_CHAR);
    }

    /**
     * 校验15位身份证号有效
     * <p>
     * 描述：由15位数字组成。
     * <p>
     * 排列顺序从左至右依次为：六位数字地区码；六位数字出生日期；三位顺序号，其中15位男为单数，女为双数。
     * <p>
     * 匹配：110001700101031
     * <p>
     * 不匹配：110001701501031
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean isCitizenId15(final String text) {
        return RegexUtil.matches(text, RegexUtil.CITIZEN_ID15);
    }

    /**
     * 校验18位身份证号有效
     * <p>
     * 描述：由十七位数字本体码和一位数字校验码组成。
     * <p>
     * 排列顺序从左至右依次为：六位数字地区码；八位数字出生日期；三位数字顺序码和一位数字校验码（也可能是X）。
     * <p>
     * 匹配：110001199001010310 | 11000019900101015X
     * <p>
     * 不匹配：990000199001010310 | 110001199013010310
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean isCitizenId18(final String text) {
        return RegexUtil.matches(text, RegexUtil.CITIZEN_ID18);
    }

    // 数字校验
    // ------------------------------------------------------------------------------

    /**
     * 校验日期。
     * <p>
     * 日期满足以下条件：
     * <ul>
     *   <li>格式yyyy-MM-dd或yyyy-M-d 连字符可以没有或是“-”、“/”、“.”之一</li>
     *   <li>闰年的二月可以有29日；而平年不可以。</li>
     *   <li>一、三、五、七、八、十、十二月为31日。</li>
     *   <li>四、六、九、十一月为30日。</li>
     * </ul>
     * <p>
     * 匹配：2016/1/1 | 2016/01/01 | 20160101 | 2016-01-01 | 2016.01.01 | 2000-02-29
     * <p>
     * 不匹配：2001-02-29 | 2016/12/32 | 2016/6/31 | 2016/13/1 | 2016/0/1
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean isDate(final String text) {
        return RegexUtil.matches(text, RegexUtil.DATE);
    }

    /**
     * 校验文本是否为N位的数字
     *
     * @param text 被校验的文本
     * @param n    位数
     * @return boolean
     */
    public static boolean isDigitNumber(final String text, final int n) {
        String format = String.format(RegexUtil.NUMBER_DIGIT, n);
        return RegexUtil.matches(text, format);
    }

    /**
     * 校验有效邮箱
     * <p>
     * 描述：不允许使用IP作为域名
     * <p>
     * 如 : hello@154.145.68.12 '@'符号前的邮箱用户和'.'符号前的域名(domain)
     * <p>
     * 必须满足以下条件：
     * <ul>
     *   <li>字符只能是英文字母、数字、下划线'_'、'.'、'-'；</li>
     *   <li>首字符必须为字母或数字； '_'、'.'、'-' 不能连续出现。</li>
     *   <li>域名的根域只能为字母，且至少为两个字符。</li>
     * </ul>
     * <p>
     * 匹配：he_llo@worl.d.com | hel.l-o@wor-ld.museum | h1ello@123.com
     * <p>
     * 不匹配：hello@worl_d.com | he&llo@world.co1 | .hello@wor#.co.uk
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean isEmail(final String text) {
        return RegexUtil.matches(text, RegexUtil.EMAIL, Pattern.CASE_INSENSITIVE);
    }

    /**
     * 校验文本是否为浮点数
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean isFloat(final String text) {
        return RegexUtil.matches(text, RegexUtil.FLOAT);
    }

    /**
     * 校验 HTTP/HTTPS URL
     *
     * @param text 被校验的文本
     * @return boolean
     * @see <a href="https://stackoverflow.com/questions/3809401/what-is-a-good-regular-expression-to-match-a-url">what-is-a-good-regular-expression-to-match-a-url</a>
     * <p>
     * 匹配：
     * <ul>
     *   <li>http://www.foufos.gr</li>
     *   <li>https://www.foufos.gr</li>
     *   <li>http://foufos.gr</li>
     *   <li>http://www.foufos.gr/kino</li>
     *   <li>http://werer.gr</li>
     *   <li>www.foufos.gr</li>
     *   <li>www.mp3.com</li>
     *   <li>www.t.co</li>
     *   <li>http://t.co</li>
     *   <li>http://www.t.co</li>
     *   <li>https://www.t.co</li>
     *   <li>www.aa.com</li>
     *   <li>http://aa.com</li>
     *   <li>http://www.aa.com</li>
     *   <li>https://www.aa.com</li>
     * </ul>
     * <p>
     * 不匹配：
     * <ul>
     *   <li>www.foufos</li>
     *   <li>www.foufos-.gr</li>
     *   <li>www.-foufos.gr</li>
     *   <li>foufos.gr</li>
     *   <li>http://www.foufos</li>
     *   <li>http://foufos</li>
     *   <li>www.mp3#.com</li>
     * </ul>
     */
    public static boolean isHttpUrl(final String text) {
        return RegexUtil.matches(text, RegexUtil.URL_HTTP);
    }

    /**
     * 校验文本是否为整数
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean isInteger(final String text) {
        return RegexUtil.matches(text, RegexUtil.INTEGER);
    }

    /**
     * 校验 IPv4
     * <p>
     * 描述：IP地址是一个32位的二进制数，通常被分割为4个“8位二进制数”（也就是4个字节）。
     * <p>
     * IP地址通常用“点分十进制”表示成（a.b.c.d）的形式，其中，a,b,c,d都是0~255之间的十进制整数。
     * <p>
     * 匹配：0.0.0.0 | 255.255.255.255 | 127.0.0.1
     * <p>
     * 不匹配：10.10.10 | 10.10.10.256
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean isIpv4(final String text) {
        return RegexUtil.matches(text, RegexUtil.IPV4);
    }

    /**
     * 校验IPv6
     * <p>
     * 描述：IPv6的128位地址通常写成8组，每组为四个十六进制数的形式。
     * <p>
     * IPv6地址可以表示为以下形式：
     * <ul>
     *   <li>IPv6 地址 零压缩 IPv6 地址(section 2.2 of rfc5952)</li>
     *   <li>带有本地链接区域索引的 IPv6 地址 (section 11 of rfc4007)</li>
     *   <li>嵌入IPv4的 IPv6 地址(section 2 of rfc6052 映射IPv4的 IPv6 地址 (section 2.1 of rfc2765)</li>
     *   <li>翻译IPv4的 IPv6 地址 (section 2.1 of rfc2765)</li>
     * </ul>
     * <pre>
     * 匹配：1:2:3:4:5:6:7:8 | 1:: | 1::8 | 1::6:7:8 | 1::5:6:7:8 |
     * 	 1::4:5:6:7:8 | 1::3:4:5:6:7:8 | ::2:3:4:5:6:7:8 | 1:2:3:4:5:6:7: |
     * 	 1:2:3:4:5:6::8 | 1:2:3:4:5::8 | 1:2:3:4::8 | 1:2:3::8 | 1:2::8 |
     * 	 1::8 | ::8 | fe80::7:8%1 | ::255.255.255.255 | 2001:db8:3:4::192.0.2.33 |
     * 	 64:ff9b::192.0.2.33
     * 不匹配：1.2.3.4.5.6.7.8 | 1::2::3
     * </pre>
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean isIpv6(final String text) {
        return RegexUtil.matches(text, RegexUtil.IPV6);
    }

    /**
     * 校验字符串至少是N位的数字
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean isLeastDigitNumber(final String text, int n) {
        String format = String.format(RegexUtil.NUMBER_DIGIT_MORE, n);
        return RegexUtil.matches(text, format);
    }

    /**
     * 校验文本长度在指定范围下
     *
     * @param text 被校验的文本
     * @param min  最小长度
     * @param max  最大长度
     * @return boolean
     */
    public static boolean isLengthInRange(final String text, final int min, final int max) {
        String format = String.format(RegexUtil.TEXT_LENGTH_RANGE, min, max);
        return RegexUtil.matches(text, format);
    }

    /**
     * 校验 Mac 地址
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean isMac(final String text) {
        return RegexUtil.matches(text, RegexUtil.MAC, Pattern.CASE_INSENSITIVE);
    }

    public static boolean isMarkdownImageTag(final String text) {
        return RegexUtil.matches(text, RegexUtil.REGEX_MARKDOWN_IMAGE_TAG);
    }

    /**
     * 校验中国手机号码
     * <p>
     * 匹配所有支持短信功能的号码（手机卡 + 上网卡）
     *
     * @param text 被校验的文本
     * @return boolean
     * @see <a href="https://github.com/VincentSit/ChinaMobilePhoneNumberRegex/blob/master/README-CN.md">ChinaMobilePhoneNumberRegex</a>
     */
    public static boolean isMobile(final String text) {
        return RegexUtil.matches(text, RegexUtil.MOBILE);
    }

    /**
     * 校验文本是否为负浮点数
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean isNegativeFloat(final String text) {
        return RegexUtil.matches(text, RegexUtil.NEGATIVE_FLOAT);
    }

    // 应用场景文本校验
    // ------------------------------------------------------------------------------

    /**
     * 校验文本是否为负整数
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean isNegativeInteger(final String text) {
        return RegexUtil.matches(text, RegexUtil.NEGATIVE_INTEGER);
    }

    /**
     * 校验全部都不是单词字符，即不能由数字、26个英文字母或者下划线组成
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean isNoneGeneralChar(final String text) {
        return RegexUtil.matches(text, RegexUtil.NONE_GENERAL_CHAR);
    }

    /**
     * 校验文本中是否完全没有数字字符
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean isNoneNumberChar(final String text) {
        return RegexUtil.matches(text, RegexUtil.NONE_NUM_CHAR);
    }

    /**
     * 校验文本是否为非正浮点数
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean isNotPositiveFloat(final String text) {
        return !isNotNegativeFloat(text);
    }

    /**
     * 校验文本是否为非负浮点数
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean isNotNegativeFloat(final String text) {
        return RegexUtil.matches(text, RegexUtil.NOT_NEGATIVE_FLOAT);
    }

    /**
     * 校验文本是否为非正整数
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean isNotPositiveInteger(final String text) {
        return !isNotNegativeInteger(text);
    }

    /**
     * 校验文本是否为非负整数
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean isNotNegativeInteger(final String text) {
        return RegexUtil.matches(text, RegexUtil.NOT_NEGATIVE_INTEGER);
    }

    /**
     * 校验文本中是否全是数字字符
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean isNumber(final String text) {
        return RegexUtil.matches(text, RegexUtil.NUMBERS);
    }

    /**
     * 校验中国固话号码（大陆地区） 描述：固话号码，必须加区号（以0开头）。 3位有效区号：010、020~029，固话位数为8位 4位有效区号：03xx开头到09xx，固话位数为7
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean isPhone(final String text) {
        return RegexUtil.matches(text, RegexUtil.PHONE);
    }

    /**
     * 校验文本是否为中国车牌号码
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean isPlateNumber(final String text) {
        return RegexUtil.matches(text, RegexUtil.PLATE_NUMBER);
    }

    /**
     * 校验文本是否为正浮点数
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean isPositiveFloat(final String text) {
        return RegexUtil.matches(text, RegexUtil.POSITIVE_FLOAT);
    }

    /**
     * 校验文本是否为正整数
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean isPositiveInteger(final String text) {
        return RegexUtil.matches(text, RegexUtil.POSITIVE_INTEGER);
    }

    /**
     * 校验文本是否为M到N位之间的数字
     *
     * @param text 被校验的文本
     * @param min  最低位数
     * @param max  最高位数
     * @return boolean
     */
    public static boolean isRangeDigitNumber(final String text, final int min, final int max) {
        String format = String.format(RegexUtil.NUMBER_DIGIT_RANGE, min, max);
        return RegexUtil.matches(text, format);
    }

    /**
     * 校验时间。
     * <p>
     * 描述：时、分、秒必须是有效数字，如果数值不是两位数，十位需要补零。
     * <p>
     * 匹配：00:00:00 | 23:59:59 | 17:06:30
     * <p>
     * 不匹配：17:6:30 | 24:16:30
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean isTime(final String text) {
        return RegexUtil.matches(text, RegexUtil.TIME);
    }

    /**
     * 校验URL。
     * <p>
     * 描述：支持http、https、ftp、ftps。 匹配：http://google.com/help/me | http://www.google.com/help/me/ |
     * https://www.google.com/help.asp | ftp://www.google.com | ftps://google.org 不匹配：http://un/www.google.com/index.asp
     *
     * @param text 被校验的文本
     * @return boolean
     * @see <a href="https://stackoverflow.com/questions/3809401/what-is-a-good-regular-expression-to-match-a-url">what-is-a-good-regular-expression-to-match-a-url</a>
     */
    public static boolean isUrl(final String text) {
        return RegexUtil.matches(text, RegexUtil.URL);
    }

    /**
     * 校验文本是否为 UUID
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean isUuid(final String text) {
        return RegexUtil.matches(text, RegexUtil.UUID);
    }

    /**
     * 校验邮政编码
     *
     * @param text 被校验的文本
     * @return boolean
     */
    public static boolean isZipCode(final String text) {
        return RegexUtil.matches(text, RegexUtil.ZIP_CODE);
    }

}
