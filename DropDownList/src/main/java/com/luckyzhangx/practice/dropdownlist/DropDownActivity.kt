package com.luckyzhangx.practice.dropdownlist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.luckyzhangx.practice.dropdownlist.popupwindow.DropDownPopup2LevelWindow
import com.luckyzhangx.practice.dropdownlist.popupwindow.MainTestAdapterDelegate
import kotlinx.android.synthetic.main.activity_drop_down.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class DropDownActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drop_down)
        anchor.onClick {
            DropDownPopup2LevelWindow(this@DropDownActivity).apply {
                attachDelegate(MainTestAdapterDelegate())
            }.showAsDropDown(anchor)
        }
    }
}
