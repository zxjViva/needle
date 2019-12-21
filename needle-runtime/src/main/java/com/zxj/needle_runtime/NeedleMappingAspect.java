package com.zxj.needle_runtime;

import com.zxj.needle_annotation.NeedleMapAnnotation;
import com.zxj.needle_annotation.NeedleParamsAnnotation;
import com.zxj.needle_annotation.map.AbsTrackDataMapping;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Aspect
public class NeedleMappingAspect {
    @Pointcut("execution(@com.zxj.needle_annotation.NeedleMapAnnotation * *(..)) && @annotation(anno)")
    public void needleMapAnnotation(NeedleMapAnnotation anno) {
    }

    ;

    @Before("needleMapAnnotation(anno)")
    public void mapTrack(JoinPoint joinPoint, NeedleMapAnnotation anno) {
        Map<String, String> trackMap = new LinkedHashMap<>();
        trackMap.put("eventId",anno.eventId());
        Class<? extends AbsTrackDataMapping>[] classez = anno.classez();
        Map<String, AbsTrackDataMapping> translateMap = getTranslateMap(classez);
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Annotation[][] parameterAnnotations = signature.getMethod().getParameterAnnotations();
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            Annotation[] parameterAnnotation = parameterAnnotations[i];
            for (Annotation annotation : parameterAnnotation) {
                //拿到参数注解上对应的key 和 value
                if (annotation instanceof NeedleParamsAnnotation) {
                    String key = ((NeedleParamsAnnotation) annotation).key();
                    //映射获取最终值
                    String realValue = translate(translateMap,key,args[i]);
                    //如果key 相同 value 就拿“+”拼接
                    if (trackMap.containsKey(key)) {
                        realValue = trackMap.get(key) + "+" + realValue;
                    }
                    trackMap.put(key, realValue);
                }
            }
        }
        Tracker.track(trackMap);
    }

    //有些打点的值是要映射的比如 1 上传的时候就可能是 mine
    private Map<String, AbsTrackDataMapping> getTranslateMap(Class<? extends AbsTrackDataMapping>[] classez) {
        Map<String, AbsTrackDataMapping> map = new HashMap<>();
        for (Class<? extends AbsTrackDataMapping> aClass : classez) {
            try {
                AbsTrackDataMapping absDataMapping = aClass.newInstance();
                map.put(absDataMapping.getName(),absDataMapping);
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    private String translate(Map<String, AbsTrackDataMapping> translateMap, String key, Object value) {
        AbsTrackDataMapping absTrackDataMapping = translateMap.get(key);
        if (absTrackDataMapping != null){
            value = absTrackDataMapping.getRealData(value);
        }
        return (value == null ? "" : value.toString());
    }
}
