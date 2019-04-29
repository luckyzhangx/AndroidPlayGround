package com.luckyzhangx.customviewdemo

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.luckyzhangx.corejavalibs.core.use
import com.luckyzhangx.customviewdemo.textview.PullHolder
import kotlinx.android.synthetic.main.activity_horizontal_pull_demo.*

class HorizontalPullDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horizontal_pull_demo)

        use(container) {
            layoutManager = LinearLayoutManager(this@HorizontalPullDemoActivity)
            adapter = PullAdapter()
        }

    }
}

private class PullAdapter : RecyclerView.Adapter<PullHolder>() {
    private val list = mutableListOf<String>()

    init {
        repeat(40) {
            list.add("hhh")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullHolder {
        return PullHolder(parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PullHolder, position: Int) {
    }
}
