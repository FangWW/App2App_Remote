package com.w.service

import com.google.gson.Gson

class ApiJSONUtil {
    var gson: Gson = Gson()


    fun toJson(bean: Any): String {
        return gson.toJson(bean)
    }

    fun toObject(json: String, beanClass: Class<*>): Any {
        return gson.fromJson<Any>(json, beanClass)
    }

    companion object {

        private var apiJSONUtil: ApiJSONUtil? = null

        val instance: ApiJSONUtil
            get() {
                if (apiJSONUtil == null) {
                    apiJSONUtil = ApiJSONUtil()
                }
                return apiJSONUtil!!
            }
    }
}

