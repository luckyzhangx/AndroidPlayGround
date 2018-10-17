package com.luckyzhangx.models.models;


import com.luckyzhangx.styleannos.StyleFamily;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
@StyleFamily
public @interface PoiStyleFamily {
}
