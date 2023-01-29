package example.spring.core.aop;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSONUtil;
import io.github.dunwu.tool.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link MethodLog} 注解的处理器
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-09
 */
@Slf4j
@Aspect
@Component
public class MethodLogAspect {

    private ThreadLocal<Long> beginTime = new ThreadLocal<>();
    private final LogRecordDao logRecordDao;

    public MethodLogAspect(LogRecordDao logRecordDao) {
        this.logRecordDao = logRecordDao;
    }

    /**
     * AOP 切点
     */
    @Pointcut("@annotation(example.spring.core.aop.MethodLog)")
    public void pointcut() { }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     * @param joinPoint join point for advice
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        beginTime.set(System.nanoTime());
        saveLogInfo(joinPoint, null);
        return joinPoint.proceed();
    }

    @AfterThrowing(pointcut = "pointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        saveLogInfo(joinPoint, e);
        beginTime.remove();
    }

    @Async
    public void saveLogInfo(JoinPoint joinPoint, Throwable e) {
        LogRecord logRecord;
        long time = System.nanoTime() - beginTime.get();
        beginTime.remove();
        Duration duration = Duration.ofNanos(time);
        String formatTime = DateUtil.formatDurationChineseString(duration);
        if (e == null) {
            logRecord = new LogRecord("INFO");
        } else {
            logRecord = new LogRecord("ERROR");
            logRecord.setException(e.getMessage());
        }

        // 从 joinPoint 中获取信息，并填入 log 实体
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        // 方法名
        Method method = signature.getMethod();
        String methodName = joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()";

        // 参数 Value
        Object[] argValues = joinPoint.getArgs();
        // 参数 Key
        String[] argKeys = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Map<String, String> map = new HashMap<>(argValues.length);
        if (ArrayUtil.isNotEmpty(argValues)) {
            for (int i = 0; i < argValues.length; i++) {
                map.put(argKeys[i], argValues[i].toString());
            }
        }
        // 方法参数
        String params = JSONUtil.toJsonStr(map);

        // 方法的 Logger 注解
        MethodLog logger = method.getAnnotation(MethodLog.class);

        logRecord.setDescription(logger.value())
            .setMethod(methodName)
            .setParams(params)
            .setRequestTime(formatTime);

        logRecordDao.insert(logRecord);
    }

}
