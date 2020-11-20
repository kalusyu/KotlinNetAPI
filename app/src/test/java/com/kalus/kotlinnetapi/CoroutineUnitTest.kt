package com.kalus.kotlinnetapi

import kotlinx.coroutines.*
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
            delay(2000)
            println("，world")
        }

//        Thread{
//            delay(3000)
//        }.start()

        print("hello")
//        Thread.sleep(3000)  等于 runBlocking
        runBlocking {
            delay(3000)
        }
    }

    @Test
    fun testMySuspendingFunction() = runBlocking<Unit>{
        println("welcome")
    }

    // join必须在协程内运行
    @Test
    fun testJobCoroutine() = runBlocking{
        val job = GlobalScope.launch {
            delay(1000)
            println("World!!")
        }

        print("Hello,")
        job.join()
    }

    /**
     * 除了由不同的构建器提供协程作⽤域之外，还可以使⽤ coroutineScope 构建器声明⾃⼰的作⽤域。它会创建⼀个协 程作⽤域并且在所有已启动⼦协程执⾏完毕之前不会结束。
     */
    @Test
    fun effectScope() = runBlocking {
        launch {
            delay(200)
            println("Task from runBlocking")
        }

        // 非阻塞的
        coroutineScope {
            launch {
                delay(500L)
                println("Task from nested launch")
            }

            delay(100L)
            println("Task from coroutineScope")
        }

        println("CoroutineScope is over!")
    }

    @Test
    fun testHookCoroutine() = runBlocking {
        launch {
            doWorld()
        }
        println("Hello,")
    }

    private suspend fun doWorld() {
        delay(1000)
        println("World!")
    }

    /**
     * 大量创建协程与创建线程对比
     */
    @Test
    fun testMuchCoroutine() = runBlocking {
        repeat(100_000){
            launch {
//                delay(5000)
                print(".")
            }
        }
    }

    @Test
    fun testMuchThread() {
        repeat(100_000){
            Thread{
                print("-")
            }.start()
        }
    }



}