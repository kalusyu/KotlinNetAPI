package com.kalus.kotlinnetapi

import kotlinx.coroutines.*
import org.junit.Test
import kotlin.system.measureTimeMillis

/**
 * desc:
 *
 * @author biaowen.yu
 * @date 2020/11/19 11:04
 *
 **/
class CoroutineComposeHold {

    @Test
    fun testComposeHold() = runBlocking {
        val time = measureTimeMillis {
            val one = doSomethingUsefulOne()
            val two = doSomethingUsefulTwo()
            println("The answer is ${one + two}")
        }
        println("Completed in $time ms")

    }

    suspend fun doSomethingUsefulOne(): Int {
        delay(1000L) // 假设我们在这⾥做了⼀些有⽤的事
        return 13
    }

    suspend fun doSomethingUsefulTwo(): Int {
        delay(1000L) // 假设我们在这⾥也做了⼀些有⽤的事
        return 29
    }

    @Test
    fun testComposeAsyc() = runBlocking {
        val time = measureTimeMillis {
            val one = async { doSomethingUsefulOne() }
            val two = async { doSomethingUsefulTwo() }
            println("The answer is ${one.await() + two.await()}")
        }
        println("Completed in $time ms")

    }

    /**
     * 可选的，async 可以通过将 start 参数设置为 CoroutineStart.LAZY ⽽变为惰性的。在这个模式下，只有结果通过
    await 获取的时候协程才会启动，或者在 Job 的 start 函数调⽤的时候。
     */
    @Test
    fun testLazyAsync() = runBlocking {
        val time = measureTimeMillis {
            val one = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
            val two = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }
// 执⾏⼀些计算
            one.start() // 启动第⼀个
            two.start() // 启动第⼆个
            println("The answer is ${one.await() + two.await()}")
        }
        println("Completed in $time ms")

    }

    @Test
    fun testStructAsync() = runBlocking {
        val time = measureTimeMillis {
            println("The answer is ${concurrentSum()}")
        }
        println("Completed in $time ms")
    }

    suspend fun concurrentSum(): Int = coroutineScope {
        val one = async { doSomethingUsefulOne() }
        val two = async { doSomethingUsefulTwo() }
        one.await() + two.await()
    }

    /**
     * 如果其中⼀个⼦协程（即 two ）失败，第⼀个 async 以及等待中的⽗协程都会被取消
     */
    @Test
    fun testCancelStructAsync():Unit = runBlocking {

        try {
            failedConcurrentSum()
        } catch (e: ArithmeticException) {
            println("Computation failed with ArithmeticException")
        }

    }

    private suspend fun failedConcurrentSum(): Int = coroutineScope {
        val one = async<Int> {
            try {
                delay(Long.MAX_VALUE) // 模拟⼀个⻓时间的运算
                42
            } finally {
                println("First child was cancelled")
            }
        }
        val two = async<Int> {
            println("Second child throws an exception")
            throw ArithmeticException()
        }
        one.await() + two.await()
    }


}