package com.example.desafiocontacorrente

import android.app.Activity
import android.app.Instrumentation
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.desafiocontacorrente.login.LoginView
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import com.example.desafiocontacorrente.transactional.MainActivity
import org.hamcrest.Matchers.not

/**
 * Instrumented test for Login Activity
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule val mActivityRule = IntentsTestRule(LoginView::class.java, false, true)

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

        onView(withId(R.id.btnLogin)).perform(ViewActions.click())

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

        onView(withId(R.id.btnLogin)).perform(ViewActions.click())

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

        onView(withId(R.id.btnLogin)).perform(ViewActions.click())


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

        onView(withId(R.id.btnLogin)).perform(ViewActions.click())

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

        onView(withId(R.id.btnLogin)).perform(ViewActions.click())

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
        val result = Instrumentation.ActivityResult(Activity.RESULT_OK, null)
        // On MainActivity  intent , respond with Result_OK instead of starting it
        intending(matcher).respondWith(result)

        onView(withId(R.id.btnLogin)).perform(ViewActions.click())


        Intents.intended(matcher)
    }
}
