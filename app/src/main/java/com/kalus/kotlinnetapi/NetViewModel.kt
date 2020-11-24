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
        viewModelScope.launch {
            try {
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
                    user.value = service.getName()
                }
        * */
    }
}