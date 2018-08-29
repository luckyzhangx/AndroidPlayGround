package com.luckyzhangx.practice.dropdownlist.popupwindow

// Created by luckyzhangx on 2018/8/29.

/**
 * ui:
 * 实现[BaseSelWrapperDelegate]：自定义数据绑定逻辑[BaseSelWrapperDelegate.bindHolderData]，
 * 实现[SelWrapperDelegateMgr]：返回
 *
 * 数据:
 * [BaseSelWrapperDelegate.getSels] 根据sel 拼接自己需要的数据。
 * 通过自定义的[SelWrapperDelegateMgr]获取所有自定义的[BaseSelWrapperDelegate]，拼接所有自己需要的数据。
 *
 */

class DefaultSelWrapperDelegateMgr : SelWrapperDelegateMgr {

    val delegates = listOf<DefaultSelWrapperDelegate>(
            DefaultSelWrapperDelegate("筛选1"),
            DefaultSelWrapperDelegate("筛选2"),
            DefaultSelWrapperDelegate("筛选3"),
            DefaultSelWrapperDelegate("筛选4"),
            DefaultSelWrapperDelegate("筛选5"),
            DefaultSelWrapperDelegate("筛选6"),
            DefaultSelWrapperDelegate("筛选7"),
            DefaultSelWrapperDelegate("筛选8"),
            DefaultSelWrapperDelegate("筛选9")
    )

    override fun getCount(): Int {
        return delegates.size
    }

    override fun getDelegate(pos: Int): SelWrapperDelegate<*, *> {
        return delegates[pos]
    }

    fun constructFilterData(): String {
        val sb = StringBuilder()
        for (i in 0 until getCount() - 1) {
            val delegate = getDelegate(i)
            sb.append(delegate.getTitle() + ": ")
            if (delegate is DefaultSelWrapperDelegate) {
                sb.append(delegate.constructFilterString())
            }
            sb.append("\n")
        }
        return sb.toString()
    }
}

class DefaultSelWrapperDelegate(val titleStr: String) : BaseSelWrapperDelegate() {

    override fun bindHolderData(holder: SelViewHolder, position: Int) {

    }

    override fun getTitle(): String {
        return titleStr
    }

    override fun getCount(): Int {
        return 10
    }

    fun constructFilterString(): String {
        return getSels().joinToString(",")
    }
}