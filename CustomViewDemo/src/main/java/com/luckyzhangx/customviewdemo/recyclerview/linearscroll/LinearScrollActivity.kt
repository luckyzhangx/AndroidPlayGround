package com.luckyzhangx.customviewdemo.recyclerview.linearscroll

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.luckyzhangx.coreandroidlibs.recyclerview.sticky.test.NoStickRenderer
import com.luckyzhangx.coreandroidlibs.renderer.renderadapter.DiffViewRendererAdapter
import com.luckyzhangx.corejavalibs.core.configure
import com.luckyzhangx.customviewdemo.R
import kotlinx.android.synthetic.main.activity_linear_scroll.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LinearScrollActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linear_scroll)

        fun configure(rv: RecyclerView) {
            configure(rv) {
                layoutManager = LinearLayoutManager(this@LinearScrollActivity)
                adapter = DiffViewRendererAdapter().apply {
                    postList(mutableListOf<Any>().apply {
                        repeat(50) {
                            add(NoStickRenderer())
                        }
                    })
                }

                GlobalScope.launch(Dispatchers.Main) {
                    //                    rv.scrollBy(0, 5000)
                }
            }

        }

        configure(list1)
        configure(list2)

        content.scrollTo(0, 300)

    }
}
