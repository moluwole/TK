package com.yung_coder.oluwole.tk

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_new.*

class New : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)

        button_verify.setOnClickListener{
            hideKeyboard()
            val topic = text_code.text.toString()
            if(TextUtils.isEmpty(topic)){
                Toast.makeText(this@New, "Enter a Project Title to proceed", Toast.LENGTH_LONG).show()
            }
            else{
                val intent = Intent()
                intent.putExtra("topic", topic)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun hideKeyboard() {
        val inputManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}
