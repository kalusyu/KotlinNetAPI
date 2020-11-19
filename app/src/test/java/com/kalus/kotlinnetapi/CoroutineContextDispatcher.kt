package com.kalus.kotlinnetapi

import kotlinx.coroutines.*
import org.junit.Test

/**
 * desc:
 *
 * @author biaowen.yu
 * @date 2020/11/19 11:46
 *
 **/
class CoroutineContextDispatcher {

    /**
     * 所有的协程构建器诸如 launch 和 async 接收⼀个可选的 CoroutineContext 参数，它可以被⽤来显式的为⼀个新协 程或其它上下⽂元素指定⼀个调度器。
     * launch(Dispatchers.Default) { …… } 与 GlobalScope.launch { …… } 使⽤相同的调 度器。
     */
    @Test
    fun testDispatcher(): Unit = runBlocking {
        launch {
            println("main runBlocking : I'm working in thread ${Thread.currentThread().name}")
        }

        launch(Dispatchers.Unconfined) {
            println("Unconfined : I'm working in thread ${Thread.currentThread().name}")
        }

        launch(Dispatchers.Default) {
            println("Default : I'm working in thread ${Thread.currentThread().name}")
        }

        launch(newSingleThreadContext("MyOwnThread")) {
            println("newSingleThreadContext : I'm working in thread ${Thread.currentThread().name}")
        }
    }

    /**
     *  Module with the Main dispatcher had failed to initialize. For tests Dispatchers.setMain from kotlinx-coroutines-test module can be used
     *  在普通环境是无法正常运行的，需要在Activity环境
     */
    @Test
    fun testScope(): Unit = runBlocking {
        val activity = Activity()
        activity.doSomething()
        println("Launched Coroutines")
        delay(500L)
        println("Destroying activity!")
        activity.destroy()
        delay(1000)
    }

    inner class Activity {
        private val mainScope: CoroutineScope = MainScope()

        fun destroy() {
            mainScope.cancel()
        }

        fun doSomething() {
            repeat(10) { i ->
                mainScope.launch {
                    delay((i + 1) * 200L)
                    println("Coroutine $i is done")
                }
            }
        }
    }
}