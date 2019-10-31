package com.luckyzhangx.customviewdemo.recyclerview.sticky

import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.luckyzhangx.coreandroidlibs.recyclerview.sticky.StickyMgr
import com.luckyzhangx.coreandroidlibs.recyclerview.sticky.test.NoStickRenderer
import com.luckyzhangx.coreandroidlibs.recyclerview.sticky.test.StickRenderer
import com.luckyzhangx.coreandroidlibs.recyclerview.sticky.test.StickRenderer2
import com.luckyzhangx.coreandroidlibs.renderer.renderadapter.DiffViewRendererAdapter
import com.luckyzhangx.corejavalibs.core.configure
import com.luckyzhangx.customviewdemo.R
import kotlinx.android.synthetic.main.activity_sticky_test.*
import kotlinx.coroutines.*

class StickyTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sticky_test)

        for (i in 0..100) {
            print(i)
        }
        print("\n")

        configure(list) list@{
            layoutManager = LinearLayoutManager(this@StickyTestActivity)

            adapter = DiffViewRendererAdapter().apply {
                postList(mutableListOf<Any>().apply {
                    repeat(20) {
                        add(NoStickRenderer())
                    }

                    repeat(5) {
                        add(StickRenderer())
                        repeat(30) {
                            add(NoStickRenderer())
                        }
                        add(StickRenderer2())
                        repeat(30) {
                            add(NoStickRenderer())
                        }
                    }

                })
            }

            StickyMgr(sticky, false).apply {
                registerType(StickRenderer::class.java.hashCode())
                registerType(StickRenderer2::class.java.hashCode())
                attach(this@list)
            }
        }

        scroll.setOnClickListener {


            GlobalScope.launch {
                download()
                println("hhh")

            }
            Thread.sleep(1000)

//            val sp = SpannableStringBuilder()
//            sp.append("<span style=background:#ff0> hello</span>, it's over")
//            sp.setSpan(BackgroundColorSpan(Color.BLUE), 39, 42, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            scroll.text = Html.fromHtml("<span style=background:#ff0> hello</span>")
//            scroll.text = Html.fromHtml("sp")
//            list.scrollToPositionWithOffset(44, 49)
        }

    }
}

suspend fun download() {
    supervisorScope {
        try {
            launch(Dispatchers.Default) {
                throw IllegalStateException()
            }

        } catch (e: Exception) {

        }
        Log.e("coroutine", "download end")
    }
}



