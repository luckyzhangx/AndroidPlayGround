package com.luckyzhangx.corejavalibs.trie

import java.util.*

// Created by luckyzhangx on 2019-09-09.
class Trie<T> {
    private val topNode = Node<T>(' ')

    fun put(key: String, value: T) {
        put(key, 0, topNode, value)
    }

    /**
     * [parentNode] index - 1 匹配的 node
     */

    private fun put(key: String, index: Int, parentNode: Node<T>, value: T) {
        if (key.isBlank()) return
        val char = key[index]

        var node = parentNode.subChar[char]
        if (node == null) {
            node = Node(char, if (index == key.lastIndex) key else key.substring(0, index + 1))
            parentNode.subChar[char] = node
        }

        if (index == key.lastIndex) {
            node.value = value
        } else {
            put(key, index + 1, node, value)
        }
    }

    fun get(key: String): T? {
        return get(key, 0, topNode)
    }

    fun allNodes(): List<Node<T>> {
        val list = mutableListOf<Node<T>>()
        allNodes("", topNode, list)
        return list
    }

    private fun allNodes(key: String, node: Node<T>, list: MutableList<Node<T>>) {
        if (node.value != null) list.add(node)

        node.subChar.forEach { char, node ->
            allNodes(key + char, node, list)
        }
    }

    /**
     * [parentNode] index - 1 匹配的 node
     */

    private fun get(key: String, index: Int, parentNode: Node<T>): T? {

        val char = key[index]

        val node = parentNode.subChar[char] ?: return null

        if (index == key.lastIndex) {
            return node.value
        }

        return get(key, index + 1, node)
    }

    fun remove(key: String) {
        removeNode(key, 0, topNode)
    }

    private fun removeNode(key: String, index: Int, parentNode: Node<T>) {
        val node = parentNode.subChar[key[index]] ?: return
        if (index == key.lastIndex) {
            node.value = null
        } else {
            removeNode(key, index + 1, node)
        }

        if (node.subChar.isEmpty()) parentNode.subChar.remove(node.char)
    }

}

class Node<T>(
        val char: Char = ' ',
        val key: String = ""
) {
    //    val subChar = mutableMapOf<Char, Node<T>>()
    val subChar = TreeMap<Char, Node<T>>()
    var value: T? = null
}

fun main() {
    val trie = Trie<String>()
    val current = System.currentTimeMillis()
    insertTrie(trie)
    val now = System.currentTimeMillis()
    println("insert duration: ${now - current}")

    trie.remove("aab")
    trie.remove("aa")

    println("---------------------------------")

    val list = trie.allNodes()
    list.forEach {
        println("node: ${it.key}: ${it.value}")
    }

}

private fun insertTrie(trie: Trie<String>) {
    insertNew(trie, "", 0, 3)
}

private fun insertNew(trie: Trie<String>, preFix: String, current: Int, limit: Int) {

    if (current == limit) return

    for (char in 'a'..'z') {
        val key = preFix + char
        trie.put(key, key)
//        println("insert: $key")
        insertNew(trie, key, current + 1, limit)
    }
}