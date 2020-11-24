package com.kalus.kotlinnetapi

import retrofit2.http.GET

/**
 * desc:
 *
 * @author biaowen.yu
 * @date 2020/11/23 16:46
 *
 **/
interface ApiService {

    @GET("api/getName")
    suspend fun getName():User
}