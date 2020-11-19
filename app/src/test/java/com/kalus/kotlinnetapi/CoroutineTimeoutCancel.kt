package com.kalus.kotlinnetapi

import kotlinx.coroutines.*
import org.junit.Test

/**
 * desc:
 *
 * @author biaowen.yu
 * @date 2020/11/19 10:23
 *
 **/
class CoroutineTimeoutCancel {

    @Test
    fun testCancel() = runBlocking {
        val job = launch {
            repeat(1000) { i ->
                println("job: I'm sleeping $i")
                delay(500L)
            }
        }
        delay(1300L)
        println("main: I'm tired of waiting!")
        // 不调用，内部的协程会一直输出，直到达到指定次数为止。
        job.cancel()
        // 外部必须是协程，否则无法正常调用API，该API为挂起函数
        job.join()
        println("main:Now I can quit.")
    }


    /**
     * 如果协程正在执⾏计算任务，并且没有
    检查取消的话，那么它是不能被取消的
    job: I'm sleeping 0 ...
    job: I'm sleeping 1 ...
    job: I'm sleeping 2 ...
    main: I'm tired of waiting!
    job: I'm sleeping 3 ...
    job: I'm sleeping 4 ...
    main: Now I can quit.
     */
    @Test
    fun testCancel2() = runBlocking {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 5) { // ⼀个执⾏计算的循环，只是为了占⽤ CPU
// 每秒打印消息两次
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("job: I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            }
        }
        delay(1300L) // 等待⼀段时间
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // 取消⼀个作业并且等待它结束
        println("main: Now I can quit.")
    }

    /**
     * isActive 是⼀个可以被使⽤在 CoroutineScope 中的扩展属性。
    job: I'm sleeping 0 ...
    job: I'm sleeping 1 ...
    job: I'm sleeping 2 ...
    main: I'm tired of waiting!
    main: Now I can quit.
     */
    @Test
    fun testCancel3() = runBlocking {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (isActive) { // 可以被取消的计算循环
// 每秒打印消息两次
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("job: I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            }
        }
        delay(1300L) // 等待⼀段时间
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // 取消该作业并等待它结束
        println("main: Now I can quit.")

    }

    @Test
    fun testCancelRelease() = runBlocking {
        val job = launch {
            try {
                repeat(1000) { i ->
                    println("job: I'm sleeping $i ...")
                    delay(500L)
                }
            } finally {
                println("job: I'm running finally")
            }
        }
        delay(1300L) // 延迟⼀段时间
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // 取消该作业并且等待它结束
        println("main: Now I can quit.")
    }

    /**
     * 当你需要挂起⼀个被取消的
    协程，你可以将相应的代码包装在 withContext(NonCancellable) {……} 中，并使⽤ withContext 函数以及
    NonCancellable 上下⽂，
     */
    @Test
    fun testNoCancel() = runBlocking {
        val job = launch {
            try {
                repeat(1000) { i ->
                    println("job: I'm sleeping $i ...")
                    delay(500L)
                }
            } finally {
                withContext(NonCancellable) {
                    println("job: I'm running finally")
                    delay(1000L) // 挂起被取消的协程，这里的作用是挂起，如果没有withContext 则无法挂起被取消的线程，直接结束
                    println("job: And I've just delayed for 1 sec because I'm non-cancellable")
                }
            }
        }
        delay(1300L) // 延迟⼀段时间
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // 取消该作业并等待它结束
        println("main: Now I can quit.")
    }

    // ================================================================

    /**
     * 会抛出TimeoutCancellationException异常，使用 try...catch
     */
    @Test
    fun testTimeout() = runBlocking {
        try {
            val result = withTimeout(1300L) {
                repeat(1000) { i ->
                    println("I'm sleeping $i ...")
                    delay(500L)

                }
            }
            println("Result is $result")
        } catch (e: Exception) {
            println("e = $e")
        }
    }

    /**
     * withTimeoutOrNull
     */
    @Test
    fun testTimeoutNoException() = runBlocking {
        val result = withTimeoutOrNull(1300L) {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
            "Done" // 在它运⾏得到结果之前取消它
        }
        println("Result is $result")

    }


}