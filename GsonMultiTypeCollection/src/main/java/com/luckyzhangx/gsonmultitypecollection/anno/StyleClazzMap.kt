package com.luckyzhangx.gsonmultitypecollection.anno

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class StyleClazzMap(vararg val styleClazz: StyleClazz)

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class StyleClazz(val style: String, val clazz: KClass<*>)