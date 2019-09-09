package com.luckyzhangx.corejavalibs.trie

import java.io.BufferedReader
import java.io.FileReader

// Created by luckyzhangx on 2019-09-09.

fun main() {

    val trie = Trie<String>()

    val path = "/Users/mfw/Desktop/words"
    val reader = FileReader(path)

    val br = BufferedReader(reader)

    var line: String = ""
    line = br.readLine()
    while (line.isNotBlank()) {
        // 一次读入一行数据
        val pair = line.split(":")
//        println("len:${pair.size}")
        trie.put(pair[0], pair[1])
//        println(line);
        line = br.readLine() ?: ""
    }


//    val words = words.split('|')
//    words.forEach {
//        val pair = it.split(":")
//        trie.put(pair[0], pair[1])
//    }

    val nodes = trie.allNodes()
    nodes.forEach {
        println("${it.key}:${it.value}")
    }
}