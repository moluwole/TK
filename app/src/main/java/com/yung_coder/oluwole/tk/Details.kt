package com.yung_coder.oluwole.tk

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.TextUtils
import android.widget.Toast
import com.yung_coder.oluwole.tk.db.DBManager
import com.yung_coder.oluwole.tk.models.Models
import kotlinx.android.synthetic.main.activity_details.*
import java.text.DateFormat
import java.util.*

class Details : AppCompatActivity() {

    companion object {
        var topic = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        topic = intent.getStringExtra("topic")
        details_topic.text = topic.toUpperCase()
        val database = DBManager(this@Details)

        button_details.setOnClickListener{
            details_text.error = null
            details_amount.error = null

            val details = details_text.text.toString()
            val amount = details_amount.text.toString()

            when {
                TextUtils.isEmpty(details) -> {
                    details_text.error = "Provide Details to Save"
                    details_text.requestFocus()
                }
                TextUtils.isEmpty(amount) -> {
                    details_amount.error = "Provide Amount to document"
                    details_amount.requestFocus()
                }
                else -> {
                    val current_date = DateFormat.getDateTimeInstance().format(Date())
                    val detail = Models.details(topic, details, amount, current_date)
                    database.saveDetails(detail)

                    setResult(Activity.RESULT_OK)
                    finish()
                }
            }
        }
    }
}
