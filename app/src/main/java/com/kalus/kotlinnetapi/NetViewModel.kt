package com.kalus.kotlinnetapi

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * desc:
 *
 * @author biaowen.yu
 * @date 2020/11/24 9:28
 *
 **/
class NetViewModel : ViewModel() {
    val user = MutableLiveData<User>()

    fun getName() {
        // 启动一个新的协程处理任务
        viewModelScope.launch {
            try {
                // withContext的意思是在协程中使用指定的上下文 如IO线程，MAIN线程
                // Dispatchers有四个 IO MAIN DEFAULT UNCONFINED
                val data = withContext(Dispatchers.IO) {
                    val service = RetrofitFactory.getService(ApiService::class.java)
                    service.getName()
                }
                user.value = data
            } catch (e: Exception) {
                Log.e("TAG", "E ： $e")
            }
        }
        // 错误写法
        /*
        val data = withContext(Dispatchers.IO) {
                    val service = RetrofitFactory.getService(ApiService::class.java)
                    // 在IO线程使用报错，必须在MAIN线程，你问我为什么？请看setValue 与 PostValue的区别
                    user.value = service.getName()
                }
        * */
    }
}