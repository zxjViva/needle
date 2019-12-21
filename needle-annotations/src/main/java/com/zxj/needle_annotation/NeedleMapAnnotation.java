package com.zxj.needle_annotation;

import com.zxj.needle_annotation.map.AbsTrackDataMapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedleMapAnnotation {
    String eventId();
    Class<? extends AbsTrackDataMapping>[] classez();
}
