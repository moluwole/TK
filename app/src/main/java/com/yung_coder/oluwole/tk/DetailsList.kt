package com.yung_coder.oluwole.tk

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.yung_coder.oluwole.tk.adapter.DetailsAdapter
import com.yung_coder.oluwole.tk.db.DBManager
import com.yung_coder.oluwole.tk.fragments.Detail
import kotlinx.android.synthetic.main.activity_details_list.*

class DetailsList : AppCompatActivity() {

    companion object {
        val REQUEST_CODE = 2
        var topic = ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_list)

        topic = intent.getStringExtra("topic")

//        Toast.makeText(this, topic, Toast.LENGTH_LONG).show()

        fab_add_details.setOnClickListener {
            val intent = Intent(this@DetailsList, Details::class.java)
            intent.putExtra("topic", topic)
            startActivityForResult(intent, REQUEST_CODE)
        }

        loadDetails()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Snackbar.make(recycler_details, "Project Detail for the day has been saved", Snackbar.LENGTH_LONG).show()
            loadDetails()
        }
    }

    fun loadDetails(){
        val database = DBManager(this@DetailsList)
        val details = database.loadDetails(topic)

        val layout_manager = LinearLayoutManager(this)
        val decorator_item = DividerItemDecoration(this, layout_manager.orientation)

        recycler_details.layoutManager = layout_manager
        recycler_details.addItemDecoration(decorator_item)

        if(details.size > 0){
            image_details_nodata.visibility = View.GONE
            text_details_nodata.visibility = View.GONE
            recycler_details.adapter = DetailsAdapter(details)
            recycler_details.visibility = View.VISIBLE
            var total = 0
            for (i in 0 until details.size){
                total += details[i].amount.toInt()
            }

            details_total.text = "₦ $total.00 K"
        }
        else{
            image_details_nodata.visibility = View.VISIBLE
            text_details_nodata.visibility = View.VISIBLE
            recycler_details.visibility = View.GONE

            details_total.text = "₦ 0.00 K"
        }
    }

    fun switchContent(bundle: Bundle){
        val fragment = Detail()
        fragment.arguments = bundle
        fragment.show(supportFragmentManager, "details Modal")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_about, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        when(id){
            R.id.action_about -> {
                startActivity(Intent(this, About::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
