package org.zp.notes.spring.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

// =================== cron表达式 ===================
// 字段 允许值 允许的特殊字符
// 秒 0-59 , - * /
// 分 0-59 , - * /
// 小时 0-23 , - * /
// 日期 1-31 , - * ? / L W C
// 月份 1-12 或者 JAN-DEC , - * /
// 星期 1-7 或者 SUN-SAT , - * ? / L C #
// 年（可选） 留空, 1970-2099 , - * /
// 表达式意义
// "0 0 12 * * ?" 每天中午12点触发
// "0 15 10 ? * *" 每天上午10:15触发
// "0 15 10 * * ?" 每天上午10:15触发
// "0 15 10 * * ? *" 每天上午10:15触发
// "0 15 10 * * ? 2005" 2005年的每天上午10:15触发
// "0 * 14 * * ?" 在每天下午2点到下午2:59期间的每1分钟触发
// "0 0/5 14 * * ?" 在每天下午2点到下午2:55期间的每5分钟触发
// "0 0/5 14,18 * * ?" 在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发
// "0 0-5 14 * * ?" 在每天下午2点到下午2:05期间的每1分钟触发
// "0 10,44 14 ? 3 WED" 每年三月的星期三的下午2:10和2:44触发
// "0 15 10 ? * MON-FRI" 周一至周五的上午10:15触发
// "0 15 10 15 * ?" 每月15日上午10:15触发
// "0 15 10 L * ?" 每月最后一日的上午10:15触发
// "0 15 10 ? * 6L" 每月的最后一个星期五上午10:15触发
// "0 15 10 ? * 6L 2002-2005" 2002年至2005年的每月的最后一个星期五上午10:15触发
// "0 15 10 ? * 6#3" 每月的第三个星期五上午10:15触发
// 每天早上6点
// 0 6 * * *
// 每两个小时
// 0 */2 * * *
// 晚上11点到早上8点之间每两个小时，早上八点
// 0 23-7/2，8 * * *
// 每个月的4号和每个礼拜的礼拜一到礼拜三的早上11点
// 0 11 4 * 1-3
// 1月1日早上4点
// 0 4 1 1 *

@Component
public class ScheduledTasks {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Scheduled(cron = "0/5 * * * * ?")
    public void reportCurrentTime() {
        System.out.println("The time is now " + dateFormat.format(new Date()));
    }
}
