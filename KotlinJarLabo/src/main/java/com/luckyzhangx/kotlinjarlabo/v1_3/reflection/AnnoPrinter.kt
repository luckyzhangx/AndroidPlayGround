package com.luckyzhangx.kotlinjarlabo.v1_3.reflection

import kotlin.reflect.full.declaredMemberProperties

// Created by luckyzhangx on 2018/12/13.

open class AnnoPrinter {
    @JAnno
    @KAnno
    val annoProp = "hello"

    fun recursiveProperty(clazz: Class<*>) {
        clazz.declaredFields.forEach { prop ->
            println("kproperty: $prop")
            prop.annotations.forEach {
                println("   kanno: ${prop.name} :$it")
            }
        }
    }

    fun print() {
        this::class.declaredMemberProperties
        var clazz: Class<*>? = this::class.java
        do {
            recursiveProperty(clazz!!)
            clazz = clazz?.superclass
        } while (clazz != null)

        println()
    }
}

class SubAnnoPrinter : AnnoPrinter() {
    @JAnno
    @KAnno
    val subAnnoProp = "hello"
}

fun main(args: Array<String>) {
    SubAnnoPrinter().print()
}