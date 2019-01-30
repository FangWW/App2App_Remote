[TOC]

### 项目结构

```
app 服务端 可远端调用
app_client 客户端
assistantlib 拥有调用service app 能力的library

```
##### service
```
初始化
        PieAssistant.initService { request, iRequestCallBack ->
        }
```
##### client
```
初始化
        PieAssistant.initClient(this, "com.w.service")//服务端的appid
数据请求类似jsonrpc
        var request = Request()
        //协议版本
        request.version = 1
        //请求方法
        request.method = "getLoacl"
        //请求参数
        request.params["lat"] = "39.7071866568"
        request.params["long"] = "116.7626953125"
        //公用参数 拓展信息
        request.exts["appId"] = BuildConfig.APPLICATION_ID
        request.exts["key"] = "dataSign"
拉取数据
        PieAssistant.request(request, object : AssistantCallback {
                override fun onResponse(response: String?) {
                    toast(response ?: "")
                }
        
                override fun onFailure(e: Throwable?) {
                    Log.e("ww", e.toString())
                }
            })
response
        {
            "entry": {
            },
            "message": "",
            "responseCode": "1",
            "status": true //操作成功
        }
onDestroy()
        关闭连接    
```
##### api
```
method：getLoacl
   获取当前定位
params：无        
```
```
method：getLoaclInfo
   经纬度获取详细信息
params：
       request.params["lat"] = "39.7071866568"
       request.params["long"] = "116.7626953125"        
```
```
method：getWeather
   获取天气
params：   
       request.params["city"] = "深圳"     
```
```
method：更多扩展方法      
```
