package com.kalus.kotlinnetapi

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
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
            for (k in 1..3){
                println("I'm not blocked $k")
                delay(100)
            }
        }

        simple().collect { value ->
            println(value)
        }

    }

    private fun simple(): Flow<Int> = flow {
        for (i in 1..3){
            delay(100)
            println("Emit $i")
            emit(i)
        }
    }

    //超时取消Flow
    @Test
    fun testCancelFlow() = runBlocking {
        withTimeoutOrNull(250L){
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

}