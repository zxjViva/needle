package com.zxj.needle_runtime;

import com.zxj.needle_annotation.NeedleAnnotation;
import com.zxj.needle_annotation.NeedleParamsAnnotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;
import java.util.Map;

@Aspect
public class NeedleAspect {
    @Pointcut("execution(@com.zxj.needle_annotation.NeedleAnnotation * *(..)) && @annotation(anno)")
    public void needleAnnotation(NeedleAnnotation anno) {
    }

    ;

    @Before("needleAnnotation(anno)")
    public void track(JoinPoint joinPoint, NeedleAnnotation anno) {
        Map<String, String> trackMap = new LinkedHashMap<>();
        trackMap.put("eventId",anno.eventId());
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Annotation[][] parameterAnnotations = signature.getMethod().getParameterAnnotations();
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            Annotation[] parameterAnnotation = parameterAnnotations[i];
            for (Annotation annotation : parameterAnnotation) {
                //拿到参数注解上对应的key 和 value
                if (annotation instanceof NeedleParamsAnnotation) {
                    String value = args[i] == null ? "" : args[i].toString();
                    String key = ((NeedleParamsAnnotation) annotation).key();
                    //如果key 相同 value 就拿“+”拼接
                    if (trackMap.containsKey(key)) {
                        value = trackMap.get(key) + "+" + value;
                    }
                    trackMap.put(key, value);
                }
            }
        }
        Tracker.track(trackMap);
    }
}
