package com.luckyzhangx.corejavalibs

// Created by luckyzhangx on 2019-09-09.
class Trie<Value> {
    private var root: Node? = null

    private inner class Node {

        // root of trie

        internal var c: Char = ' '

        internal var left: Node? = null
        internal var mid: Node? = null
        internal var right: Node? = null

        // character // left, middle, and right subtries // value associated with string

        internal var `val`: Value? = null
    }

    operator fun get(key: String): Value? {

        val x = get(root, key, 0) ?: return null

        return x.`val`
    }

    private operator fun get(x: Node?, key: String, d: Int): Node? {

        if (x == null) return null
        val c = key[d]
        return if (c < x.c)
            get(x.left, key, d)
        else if (c > x.c)
            get(x.right, key, d)
        else if (d < key.length - 1)
            get(x.mid, key, d + 1)
        else
            x

    }

    fun put(key: String, `val`: Value) {
        root = put(root, key, `val`, 0)
    }

    private fun put(x: Node?, key: String, `val`: Value, d: Int): Node {
        var x = x

        val c = key[d]
        if (x == null) {
            x = Node()
            x.c = c
        }
        if (c < x.c)
            x.left = put(x.left, key, `val`, d)
        else if (c > x.c)
            x.right = put(x.right, key, `val`, d)
        else if (d < key.length - 1)
            x.mid = put(x.mid, key, `val`, d + 1)
        else
            x.`val` = `val`
        return x

    }

}
