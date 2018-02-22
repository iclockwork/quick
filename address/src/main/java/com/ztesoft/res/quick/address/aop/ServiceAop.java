package com.ztesoft.res.quick.address.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * ServiceAop
 *
 * @author: fengwang
 * @date: 2018/2/6 14:10
 * @version: 1.0
 * @since: JDK 1.8
 */
@Aspect
@Component
public class ServiceAop {
    private static Logger logger = LoggerFactory.getLogger(ServiceAop.class);

    private ThreadLocal<Long> tLocal = new ThreadLocal<Long>();

    @Pointcut("execution(public * com.ztesoft.res.quick.address.service.*.*(..))")
    public void webRequestLog() {
    }

    @Before("webRequestLog()")
    public void doBefore(JoinPoint joinPoint) {
        long beginTime = System.currentTimeMillis();
        tLocal.set(beginTime);
    }

    @AfterReturning(returning = "result", pointcut = "webRequestLog()")
    public void doAfterReturning(Object result) {
        logger.info("Consumption time of : " + (System.currentTimeMillis() - tLocal.get()) + " milliseconds");
    }

    @Around("webRequestLog()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object o = pjp.proceed();
        return o;
    }
}
