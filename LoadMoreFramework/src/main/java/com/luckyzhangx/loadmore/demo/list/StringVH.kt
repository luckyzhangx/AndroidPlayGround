package com.luckyzhangx.loadmore.demo.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.luckyzhangx.loadmore.R

class StringVH(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.vh, parent, false)
) {
    val view = itemView.findViewById<TextView>(R.id.content)
    fun bindString(content: String) {
        view.text = content
    }
}