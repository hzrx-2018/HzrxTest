package com.hhf.forum.aop;


import com.hhf.forum.annotation.ReadOnly;
import com.hhf.forum.config.DbContectHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @version 0.1
 * @ahthor haifeng
 * @date 2021/6/8 22:05
 */
@Aspect
@Component
public class DbAop implements Ordered {

    @Around("@annotation(readOnly)")
    public Object setRead(ProceedingJoinPoint joinPoint, ReadOnly readOnly) throws Throwable{
        try{
            DbContectHolder.setDbType(DbContectHolder.READ);
            return joinPoint.proceed();
        }finally {
            DbContectHolder.clearDbType();
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
