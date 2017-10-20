package com.yung_coder.oluwole.tk.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.yung_coder.oluwole.tk.DetailsList
import com.yung_coder.oluwole.tk.R
import com.yung_coder.oluwole.tk.db.DBManager
import com.yung_coder.oluwole.tk.models.Models

/**
 * Created by yung on 10/19/17.
 */
class TopicAdapter constructor(data_list: ArrayList<Models.topics>): RecyclerView.Adapter<TopicAdapter.ViewAdapter>() {

    private var data_list: ArrayList<Models.topics>? = null
    private var generator = ColorGenerator.MATERIAL
    private var context: Context? = null

    init {
        this.data_list = data_list
    }

    override fun getItemCount(): Int {
        return data_list?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewAdapter?, position: Int) {
        val data = data_list?.get(position)
        holder?.topic_text?.text = data?.title?.toUpperCase()
        val drawable = TextDrawable.builder().buildRound(data?.title?.get(0).toString(), generator.randomColor)
        holder?.topic_image?.setImageDrawable(drawable)

        holder?.delete_image?.setOnClickListener {
            val database = DBManager(context!!)
            val result_topic = database.deleteTopic(data?.title!!)
            val result_details = database.deleteDetails(data.title)

            val message = if (result_topic) {
                data_list?.removeAt(position)
                notifyDataSetChanged()
                "Project has been deleted Successfully"
            } else {
                notifyDataSetChanged()
                "Error when deleting project. Please try again"
            }
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }

        holder?.topic_text?.setOnClickListener {
            val intent = Intent(context, DetailsList::class.java)
            intent.putExtra("topic", data?.title)
            context?.startActivity(intent)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewAdapter {
        val rootView = LayoutInflater.from(parent?.context).inflate(R.layout.topic_view, parent, false)
        context = rootView.context
        return ViewAdapter(rootView)
    }

    class ViewAdapter(layoutView: View): RecyclerView.ViewHolder(layoutView){
        val topic_image: ImageView = layoutView.findViewById(R.id.topic_image)
        val topic_text: TextView = layoutView.findViewById(R.id.text_title)
        val delete_image: ImageView = layoutView.findViewById(R.id.topic_delete)
        val topic_container: RelativeLayout = layoutView.findViewById(R.id.topic_container)
    }
}