package com.luckyzhangx.practice.dropdownlist.popupwindow

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.luckyzhangx.practice.dropdownlist.R
import kotlinx.android.synthetic.main.activity_multi_sel.*

class MultiSelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_sel)

        val delegateMgr = DefaultSelWrapperDelegateMgr()
        val callback: OnCollectFilterData = { delegateMgr ->
            if (delegateMgr is DefaultSelWrapperDelegateMgr) {
                tv.text = (delegateMgr.constructFilterData())
            }
        }

        view.setOnClickListener {
            DropDownMultiSelector(this).apply {
                attachDelegate(delegateMgr, callback)
                showAsDropDown(it)
            }

        }

    }
}
