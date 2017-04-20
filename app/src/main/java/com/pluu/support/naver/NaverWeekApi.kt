package com.pluu.support.naver

import android.content.Context
import com.pluu.support.impl.AbstractWeekApi
import com.pluu.support.impl.NAV_ITEM
import com.pluu.support.impl.NetworkSupportApi
import com.pluu.webtoon.common.Const
import com.pluu.webtoon.item.Status
import com.pluu.webtoon.item.WebToonInfo
import org.jsoup.Jsoup
import java.util.*

/**
 * 네이버 웹툰 Week API
 * Created by pluu on 2017-04-20.
 */
class NaverWeekApi(context: Context) : AbstractWeekApi(context, NaverWeekApi.TITLE) {
    private val URL_VALUE = arrayOf("mon", "tue", "wed", "thu", "fri", "sat", "sun", "fin")

    private var currentPos: Int = 0

    override val naviItem: NAV_ITEM
        get() = NAV_ITEM.NAVER

    override fun parseMain(position: Int): List<WebToonInfo> {
        currentPos = position

        val list = ArrayList<WebToonInfo>()

        val response: String
        try {
            response = requestApi()
        } catch (e: Exception) {
            e.printStackTrace()
            return list
        }

        val doc = Jsoup.parse(response)
        val pattern = "(?<=titleId=)\\d+".toRegex()
        for (a in doc.select("#pageList a")) {
            pattern.find(a.attr("href"))?.apply {
                val item = WebToonInfo(groupValues[0]).apply {
                    title = a.select(".toon_name").text()
                    image = a.select("img").first().attr("src")

                    if (!a.select(".aside_info .ico_up").isEmpty()) {
                        // 최근 업데이트
                        status = Status.UPDATE
                    } else if (!a.select(".aside_info .ico_break").isEmpty()) {
                        // 휴재
                        status = Status.BREAK
                    }
                    setIsAdult(!a.select(".ico_adult2").isEmpty())
                    writer = a.select(".sub_info").text()
                    rate = Const.getRateNameByRate(a.select(".txt_score").text())
                    updateDate = a.select("span[class=if1]").text()
                }
                list.add(item)
            }
        }

        return list
    }

    override val method: String
        get() = NetworkSupportApi.GET

    override val url: String
        get() = URL

    override val params: Map<String, String>
        get() {
            return HashMap<String, String>().apply {
                put("week", URL_VALUE[currentPos])
            }
        }

    companion object {

        private val TITLE = arrayOf("월", "화", "수", "목", "금", "토", "일", "완결")

        private val URL = "http://m.comic.naver.com/webtoon/weekday.nhn"
    }
}
