package com.yung_coder.oluwole.tk

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.yung_coder.oluwole.tk.adapter.TopicAdapter
import com.yung_coder.oluwole.tk.db.DBManager
import com.yung_coder.oluwole.tk.models.Models
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        val REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        supportActionBar?.setDisplayShowHomeEnabled(true)
//        supportActionBar?.setLogo(R.drawable.logo)
//        supportActionBar?.setDisplayUseLogoEnabled(true)

        fab_add.setOnClickListener{
            startActivityForResult(Intent(this, New::class.java), REQUEST_CODE)
        }

        loadTopics()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val database = DBManager(this@MainActivity)
            val topic = Models.topics(data?.getStringExtra("topic").toString())
            database.saveTopic(topic)
            Snackbar.make(recycler_topic, "Project has been saved", Snackbar.LENGTH_LONG).show()

            loadTopics()
        }
    }

    fun loadTopics(){
        val database = DBManager(this@MainActivity)
        val topics = database.loadTitle()
        val layout_manager = LinearLayoutManager(this@MainActivity)
        val decorator_item = DividerItemDecoration(this@MainActivity, layout_manager.orientation)
        recycler_topic.layoutManager = layout_manager
        recycler_topic.addItemDecoration(decorator_item)

        if(topics.size > 0) {
            recycler_topic.adapter = TopicAdapter(topics)
            image_nodata.visibility = View.GONE
            text_nodata.visibility = View.GONE
            recycler_topic.visibility = View.VISIBLE
        }
        else{
            image_nodata.visibility = View.VISIBLE
            text_nodata.visibility = View.VISIBLE
            recycler_topic.visibility = View.GONE
        }
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
