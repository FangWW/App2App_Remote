package com.w.client

import android.content.ComponentName
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.w.assistantlib.PieAssistant
import com.w.assistantlib.bean.Request
import com.w.assistantlib.remote.AssistantCallback
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.newTask
import org.jetbrains.anko.toast
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loacl.setOnClickListener {
            //可以自己再次封装一次
            Log.e("ww", "询问地址")
            var request = Request()
            request.version = 1
            request.method = "getLoacl"
            request.exts["appId"] = BuildConfig.APPLICATION_ID
            request.exts["key"] = "dataSign"
            PieAssistant.request(request, object : AssistantCallback {
                override fun onResponse(response: String?) {
                    Log.e("ww", response)
                    toast(response ?: "")
                }

                override fun onFailure(e: Throwable?) {
                    Log.e("ww", e.toString())
                }
            })
        }


        loaclInfo.setOnClickListener {
            Log.e("ww", "询问地址")
            //可以自己再次封装一次
            var request = Request()
            request.version = 1
            request.method = "getLoaclInfo"
            request.params["lat"] = "39.7071866568"
            request.params["long"] = "116.7626953125"
            request.exts["appId"] = BuildConfig.APPLICATION_ID
            request.exts["key"] = "dataSign"
            PieAssistant.request(request, object : AssistantCallback {
                override fun onResponse(response: String?) {
                    Log.e("ww", response)
                    toast(response ?: "")
                }

                override fun onFailure(e: Throwable?) {
                    Log.e("ww", e.toString())
                }
            })
        }
        weather.setOnClickListener {
            Log.e("ww", "查询天气")
            //可以自己再次封装一次
            var request = Request()
            request.version = 1
            request.method = "getWeather"
            request.params["city"] = "深圳市"
            request.exts["appId"] = BuildConfig.APPLICATION_ID
            request.exts["key"] = "dataSign"
            PieAssistant.request(request, object : AssistantCallback {
                override fun onResponse(response: String?) {
                    Log.e("ww", response)
                    toast(response ?: "")
                }

                override fun onFailure(e: Throwable?) {
                    Log.e("ww", e.toString())
                }
            })
        }
    }
}
