package com.w.service.bean

data class Rep<T>(private val status: Boolean,
                  val message: String,
                  val responseCode: String,
                  val entry: T?) {

    fun isSuccessful() = status
}













