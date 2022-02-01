package fr.epsi.epsiproject_v2

import okhttp3.*
import java.io.IOException

class ApiCaller {
    companion object{
        fun sendGet(url:String): String {
            var textParse: String? = null
            val okHttpClient = OkHttpClient.Builder().build()
            val request = Request.Builder()
                .url(url)
                .get()
                .cacheControl(CacheControl.FORCE_NETWORK)
                .build()
            okHttpClient.newCall(request).enqueue(object: Callback {
                override fun onResponse(call: Call, response: Response) {
                    val respons = response.body?.string()
                    if (respons != null){
                        textParse = respons
                    }
                }
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }
            })
            return url //textParse
        }
    }
}