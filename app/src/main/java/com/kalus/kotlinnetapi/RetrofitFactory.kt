package com.kalus.kotlinnetapi

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * desc:
 *
 * @author biaowen.yu
 * @date 2020/11/24 9:36
 *
 **/
object RetrofitFactory {


    fun <T> getService(clazz: Class<T>): T {
        val retrofit = makeRetrofit()
        return retrofit.create(clazz)
    }

    private fun makeRetrofit(): Retrofit {
        val builder = OkHttpClient.Builder()
        // Token
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.102:9090/mock/15/")
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}


