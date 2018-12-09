package com.luckyzhangx.kotlinjarlabo.v1_3.contract

fun test(str: String?) {
    if (str.isNullOrEmpty()) {
        print("isNullOrEmpty")
        return
    }
    if (str.isNullOrBlank()) {
        print("isNullOrBlank")
        return
    }
}

fun main(args: Array<String>) {
    test("    ")
    println("hhh")

}