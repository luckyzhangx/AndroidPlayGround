package com.luckyzhangx.loadmore.demo

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.luckyzhangx.loadmore.R
import com.luckyzhangx.loadmore.core.ListViewModel
import com.luckyzhangx.loadmore.demo.list.StringAdapter
import kotlinx.android.synthetic.main.activity_load_more_demo.*

class LoadMoreDemoActivity : AppCompatActivity(), SimpleListStateViewImpl<String> {

    override val stateViewProvider: StateViewProvider by lazy {
        object : StateViewProvider {
            override val context: Context
                get() = this@LoadMoreDemoActivity
        }
    }

    private val adapter = StringAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_more_demo)
        initView()
    }

    private fun initView() {
        list.apply {
            adapter = this@LoadMoreDemoActivity.adapter
        }

        val vm = ViewModelProviders.of(this).get(DemoViewModel::class.java)
        vm.attachView(this)
        val presenter = ListPresenter().apply { attachVM(vm) }

        refresh.setOnClickListener {
            presenter.refresh()
        }

        append.setOnClickListener {
            presenter.loadMore()
        }
    }


    override fun refreshData(list: List<String>) {
        adapter.postList(list)
    }

    override fun updateData(list: List<String>) {
        adapter.postList(list)
    }
}


class DemoViewModel : ListViewModel<String>()

