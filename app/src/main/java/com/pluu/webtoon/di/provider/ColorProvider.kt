package com.pluu.webtoon.di.provider

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.pluu.webtoon.model.NAV_ITEM
import com.pluu.webtoon.model.toUiType
import javax.inject.Inject

/** Color Resource Provider */
class ColorProvider(
    private val context: Context
) {
    fun getColor(
        @ColorRes resId: Int
    ): Int = ContextCompat.getColor(context, resId)
}

/** Navigation Color Provider */
class NaviColorProvider @Inject constructor(
    private val provider: ColorProvider,
    naviItem: NAV_ITEM
) {
    private val uiNaviItem = naviItem.toUiType()

    fun getTitleColor(): Int = provider.getColor(uiNaviItem.color)

    fun getTitleColorVariant(): Int = provider.getColor(uiNaviItem.bgColor)
}
