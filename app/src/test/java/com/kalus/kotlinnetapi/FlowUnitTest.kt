package com.kalus.kotlinnetapi

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.junit.Test

/**
 * desc:
 *
 * @author biaowen.yu
 * @date 2020/11/19 15:06
 *
 **/
class FlowUnitTest {

    @Test
    fun testFlow() = runBlocking<Unit> {
        launch {
            for (k in 1..3) {
                println("I'm not blocked $k")
                delay(100)
            }
        }

        simple().collect { value ->
            println(value)
        }

    }

    private fun simple(): Flow<Int> = flow {
        for (i in 1..3) {
            delay(100)
            println("Emit $i")
            emit(i)
        }
    }

    //超时取消Flow
    @Test
    fun testCancelFlow() = runBlocking {
        withTimeoutOrNull(250L) {
            simple().collect {
                println(it)
            }
        }
        println("Done")
    }

    // 流构建器
    @Test
    fun testFlowConstruct() = runBlocking {
        (1..3).asFlow().collect {
            println("flow construct $it")
        }
    }


    private suspend fun performRequest(request: Int): String {
        delay(1000)
        return "response :$request"
    }

    /**
     * 过渡流操作符
     */
    @Test
    fun testFlowFlow() = runBlocking {
        // map用来重构和包装数据结构
        (1..3).asFlow().map { request ->
            performRequest(request)
        }.collect { response ->
            println(response)
        }

        //使⽤ transform 操作符，我们可以 发射 任意值任意次。
        (1..3).asFlow().transform { request ->
            emit("Making request $request")
            emit(performRequest(request = request))
        }.collect {
            println(it)
        }
    }

    fun numbers(): Flow<Int> = flow {
        try {
            emit(1)
            emit(2)
            println("This line will not execute")
            emit(3)
        } finally {
            println("Finally in numbers")
        }
    }

    @Test
    fun testNumbersFlow() = runBlocking {
        numbers().take(2).collect {
            println(it)
        }
    }

    /**
     * 末端流操作符 collect是其中一种
     */
    @Test
    fun testEndFlow() = runBlocking {
        val sum = (1..5).asFlow().map { it * it }
            .reduce { a, b -> a + b }
        println(sum)
    }

    /**
     * 流是连续的
     */
    @Test
    fun testFlowContinuity() = runBlocking {
        (1..5).asFlow().filter {
            println("filter $it")
            it % 2 == 0
        }.map {
            println("Map $it")
            "string $it"
        }.collect {
            println("Collect $it")
        }
    }

    /**
     * withContext 发出错误
     */
    @Test
    fun testFlowContext() = runBlocking {
        simpleContext().collect {
            println(it)
        }

    }

    private fun simpleContext(): Flow<Int> = flow {
        withContext(Dispatchers.Default) {

            for (i in (1..3)) {
                Thread.sleep(100)
                log("Emitting ")
                emit(i)
            }
        }
    }

    /**
     * flowOn 操作符
     */
    @Test
    fun testFlowContext2() = runBlocking {
        simpleContext2().collect {
            log("Collect $it")
        }

    }

    private fun simpleContext2(): Flow<Int> = flow {
            for (i in (1..3)) {
                Thread.sleep(100)
                log("Emitting ")
                emit(i)
            }
    }.flowOn(Dispatchers.Default)

    fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")


    // ==============================================



}