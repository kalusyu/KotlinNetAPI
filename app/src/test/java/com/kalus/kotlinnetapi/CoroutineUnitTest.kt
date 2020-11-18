package com.kalus.kotlinnetapi

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CoroutineUnitTest {
    @Test
    fun testLaunch() {
        GlobalScope.launch {
            println("user info")
        }
        repeat(8){
            println("main thread $it")
        }
    }
}