package com.example.desafiocontacorrente

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.desafiocontacorrente.login.LoginView
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import com.example.desafiocontacorrente.mocks.Mocks
import com.example.desafiocontacorrente.service.AccountServiceApi
import com.example.desafiocontacorrente.service.AccountServiceImpl
import com.example.desafiocontacorrente.service.RetrofitConfig
import com.example.desafiocontacorrente.service.RetrofitEndpoint
import com.example.desafiocontacorrente.transactional.MainActivity
import com.google.gson.GsonBuilder
import net.vidageek.mirror.dsl.Mirror
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.*

/**
 * Instrumented test for Login Activity
 */
@RunWith(AndroidJUnit4::class)
class LoginTest {
    @get:Rule
    val mActivityRule = IntentsTestRule(LoginView::class.java, false, true)

    private lateinit var server: MockWebServer

    @Before
    fun setup(){
        server = MockWebServer()
        server.start()
        setupServerUrl()
    }

    private fun setupServerUrl(){
        val url = server.url("/").toString()

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val accountApi = AccountServiceImpl().mRetrofit

        val api: RetrofitEndpoint = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(client)
            .build()
            .create()

        setField(accountApi, "api", api)
    }

    private fun setField(target: Any, fieldName: String, value: Any) {
        Mirror()
            .on(target)
            .set()
            .field(fieldName)
            .withValue(value)
    }

    /**
     * When trying to login with both fields (email and password) unfilled,
     * check if a "invalid fields" message is shown by toast.
     */
    @Test
    fun loginEmptyEmailAndPassword(){
        onView(withId(R.id.etEmailLogin))
            .perform(typeText(""))
        closeSoftKeyboard()
        onView(withId(R.id.etPassword))
            .perform(typeText(""))
        closeSoftKeyboard()

        onView(withId(R.id.btnLogin)).perform(click())

        onView(withText(R.string.error_invalid_field))
            .inRoot(withDecorView(not(mActivityRule.activity.window.decorView)))
            .check(matches(isDisplayed()))
    }

    /**
     * When trying to login with the email field unfilled,
     * check if a "invalid fields" message is shown by toast.
     */
    @Test
    fun loginEmptyEmail(){
        onView(withId(R.id.etEmailLogin))
            .perform(typeText(""))
        closeSoftKeyboard()
        onView(withId(R.id.etPassword))
            .perform(typeText("123"))
        closeSoftKeyboard()

        onView(withId(R.id.btnLogin)).perform(click())

        onView(withText(R.string.error_invalid_field))
            .inRoot(withDecorView(not(mActivityRule.activity.window.decorView)))
            .check(matches(isDisplayed()))
    }

    /**
     * When trying to login with the password field unfilled,
     * check if a "invalid fields" message is shown by toast.
     */
    @Test
    fun loginEmptyPassword(){
        onView(withId(R.id.etEmailLogin))
            .perform(typeText("test@test.com"))
        closeSoftKeyboard()
        onView(withId(R.id.etPassword))
            .perform(typeText(""))
        closeSoftKeyboard()

        onView(withId(R.id.btnLogin)).perform(click())


        onView(withText(R.string.error_invalid_field))
            .inRoot(withDecorView(not(mActivityRule.activity.window.decorView)))
            .check(matches(isDisplayed()))
    }

    /**
     * When trying to login with wrong email,
     * check if a "user not found" message is shown by toast.
     */
    @Test
    fun loginWrongEmail(){
        onView(withId(R.id.etEmailLogin))
            .perform(typeText("test@test.com"))
        closeSoftKeyboard()
        onView(withId(R.id.etPassword))
            .perform(typeText("123456"))
        closeSoftKeyboard()

        onView(withId(R.id.btnLogin)).perform(click())

        onView(withText(R.string.error_user_not_found))
            .inRoot(withDecorView(not(mActivityRule.activity.window.decorView)))
            .check(matches(isDisplayed()))
    }

    /**
     * When trying to login with wrong password,
     * check if a "user not found" message is shown by toast.
     */
    @Test
    fun loginWrongPassword(){
        onView(withId(R.id.etEmailLogin))
            .perform(typeText("lamarques.oliveira@evosystems.com.br"))
        closeSoftKeyboard()
        onView(withId(R.id.etPassword))
            .perform(typeText("123"))
        closeSoftKeyboard()

        onView(withId(R.id.btnLogin)).perform(click())

        onView(withText(R.string.error_user_not_found))
            .inRoot(withDecorView(not(mActivityRule.activity.window.decorView)))
            .check(matches(isDisplayed()))
    }

    /**
     * Test a successful login
     * When both fields are correct and button is pressed,
     * check if is dispatched an intent to MainActivity
     */
    @Test
    fun loginSuccess(){
        //Insert user's email and password on edit texts and hide keyboard after each one
        onView(withId(R.id.etEmailLogin))
            .perform(typeText("lamarques.oliveira@evosystems.com.br"))
        closeSoftKeyboard()
        onView(withId(R.id.etPassword))
            .perform(typeText("123456"))
        closeSoftKeyboard()
        // Verify if the test will launch MainActivity
        val matcher = hasComponent(MainActivity::class.java.name)
        // Simulate the intent's result

        val resultData = Intent()
        val email = "lamarques.oliveira@evosystems.com.br"
        resultData.putExtra("email", email)
        val result = Instrumentation.ActivityResult(Activity.RESULT_OK, resultData)

        // On MainActivity intent , respond with Result_OK instead of starting it
        intending(matcher).respondWith(result)

        server.enqueue(MockResponse().setResponseCode(200).setBody(Mocks().LOGIN_SUCCESS))
        onView(withId(R.id.btnLogin)).perform(click())

        intended(matcher)
    }

    @After
    fun tearDown(){
        server.shutdown()
    }
}
