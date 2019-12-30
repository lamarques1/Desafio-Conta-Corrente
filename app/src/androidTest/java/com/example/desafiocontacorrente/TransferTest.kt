package com.example.desafiocontacorrente

import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.desafiocontacorrente.transactional.MainActivity
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TransferTest {
    @get:Rule
    val mActivityRule = IntentsTestRule(MainActivity::class.java, false, true)

    private lateinit var server: MockWebServer

    @Before
    fun setup(){
        val server = MockWebServer()
        server.start()

    }
    @After
    fun tearDown(){
        server.shutdown()
    }

    @Test
    fun openTransferView(){

    }

    @Test
    fun transferEmptyEmailAndValue(){

    }

    @Test
    fun transferEmptyEmail(){

    }

    @Test
    fun transferEmptyValue(){

    }

    @Test
    fun transferWrongEmail(){

    }

    @Test
    fun transferInsufficientBalance(){

    }

    @Test
    fun transferSuccess(){

    }
}