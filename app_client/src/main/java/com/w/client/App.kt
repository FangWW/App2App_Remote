package com.w.client

import android.app.Application
import com.w.assistantlib.PieAssistant

/**
 * @Author: FangJW
 * @Date: 2018/10/8 11:49
 */

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        PieAssistant.initClient(this, "com.w.service")//服务端的appid
    }
}