package com.example.pagerheaderdemo

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.pager_container.*
import kotlinx.android.synthetic.main.pager_layout.*

// Created by luckyzhangx on 2018/12/17.
class PagerVH(parent: ViewGroup)
    : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.pager_container, parent, false)), LayoutContainer {
    override val containerView: View?
        get() = itemView

    init {
        pager.apply {
            adapter = com.example.pagerheaderdemo.PagerAdapter((context as AppCompatActivity).supportFragmentManager).apply {
                repeat(10) {
                    list.add(Any())
                }
            }
        }
    }
}

class Page

class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    val list = mutableListOf<Any>()

    override fun getItem(position: Int): Fragment {
        return PagerFragment()
    }

    override fun getCount(): Int {
        return list.size
    }
}

class PagerFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.pager_layout, container, false).apply {
            setOnClickListener {
                Toast.makeText(inflater.context, "click", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pagerRV.apply {
            adapter = ContentAdapter().apply {
                repeat(100) { list.add(Simple()) }
            }
        }
    }
}