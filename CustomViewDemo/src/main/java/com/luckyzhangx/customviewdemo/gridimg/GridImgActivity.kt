package com.luckyzhangx.customviewdemo.gridimg

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.luckyzhangx.coreandroidlibs.gridphotoview.PoiCommentGridImgView
import com.luckyzhangx.customviewdemo.R
import kotlinx.android.synthetic.main.activity_grid_img.*

class GridImgActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid_img)

        val adapter = object : PoiCommentGridImgView.DataMgr.Adapter<String> {
            override fun getUrl(t: String): String {
                return ""
            }

            override fun getWidth(t: String): Int {
                return 0
            }

            override fun getHeight(t: String): Int {
                return 0
            }
        }

        val dataMgr = PoiCommentGridImgView.DataMgr(gridView, adapter)
        val list = listOf(
                "h", "h", "H"
        )
        dataMgr.showImgs(list)
    }
}
