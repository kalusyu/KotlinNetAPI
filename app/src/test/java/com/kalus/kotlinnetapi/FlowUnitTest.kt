package com.kalus.kotlinnetapi

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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

    fun simple(): Flow<Int> = flow {
        for (i in 1..3){
            delay(100)
            emit(i)
        }
    }
}