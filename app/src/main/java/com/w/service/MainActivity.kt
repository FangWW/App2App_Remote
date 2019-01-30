package com.w.service

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.w.assistantlib.PieAssistant
import com.w.service.bean.Rep
import com.w.service.bean.Weather
import com.w.service.bean.WeatherList
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PieAssistant.initService { request, iRequestCallBack ->
            request?.let {
                Log.e("ww", "appid:${request.exts["appId"]} --- method:${request.method} --- ThreadName:${Thread.currentThread().name} ")
                if (request.version == 1) {
                    //后续抽离代码 到各个action
                    when {
                        request.method == "getLoacl" -> {
                            //模拟高德定位
                            dealData(1000) {
                                Log.e("ww", "1秒到了返回地址")
                                iRequestCallBack?.onResponse(ApiJSONUtil.instance.toJson(Rep(true, "", "1", "当前定位中国北京市动物园")))
                            }
                        }
                        request.method == "getLoaclInfo" -> {
                            val lat = request.params["lat"]
                            val long = request.params["long"]
                            Log.e("ww", "$lat $long 请求地址中。。。 3秒返回")
                            //模拟高德反查询地位
                            dealData(3000) {
                                Log.e("ww", "3秒到了返回地址")
                                iRequestCallBack?.onResponse(ApiJSONUtil.instance.toJson(Rep(true, "", "1", "中国北京市动物园")))
                            }
                        }
                        request.method == "getWeather" -> {
                            //模拟高德定位
                            dealData(1000) {
                                if (Random().nextInt(4) % 2 == 0) {
                                    Log.e("ww", "1秒到了返回天气复杂对象")
                                    val weathers = arrayListOf<Weather>()
                                    weathers.add(Weather("22号 多云", "http//xxx.com/img.png"))
                                    weathers.add(Weather("23号 晴天", "http//xxx.com/img.png"))
                                    weathers.add(Weather("24号 大太阳", "http//xxx.com/img.png"))
                                    val weatherList = WeatherList("${request.params["city"]} 今天天气很好", weatherList = weathers)
                                    iRequestCallBack?.onResponse(ApiJSONUtil.instance.toJson(Rep(true, "", "1", weatherList)))
                                } else {
                                    Log.e("ww", "1秒到了返回错误天气")
                                    iRequestCallBack?.onResponse(ApiJSONUtil.instance.toJson(Rep(false, "服务异常", "0", null)))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun dealData(millis: Long, handler: () -> Unit) {
        Thread(Runnable {
            Thread.sleep(millis)
            handler()
        }).start()
    }
}
