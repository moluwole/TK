package com.yung_coder.oluwole.tk.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.yung_coder.oluwole.tk.DetailsList
import com.yung_coder.oluwole.tk.R
import com.yung_coder.oluwole.tk.models.Models
import com.yung_coder.oluwole.tk.MainActivity



/**
 * Created by yung on 10/19/17.
 */
class DetailsAdapter constructor(data_list: ArrayList<Models.details>): RecyclerView.Adapter<DetailsAdapter.ViewAdapter>() {

    private var data_list: ArrayList<Models.details>? = null
    private var generator = ColorGenerator.MATERIAL
    private var context: Context? = null

    init {
        this.data_list = data_list
    }

    override fun getItemCount(): Int {
        return data_list?.size ?: 0
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewAdapter?, position: Int) {
        val data = data_list?.get(position)
        val drawable = TextDrawable.builder().buildRound(data?.title?.get(0).toString(), generator.randomColor)
        holder?.details_image?.setImageDrawable(drawable)

        holder?.details_date?.text = data?.date
        holder?.details_title?.text = data?.details_text?.substring(0, data.details_text.length - 5) + "...."

        holder?.details_title?.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("title", data?.title)
            bundle.putString("details", data?.details_text)
            bundle.putString("amount", data?.amount)
            bundle.putString("date", data?.date)

            loadModal(bundle)
        }
    }

    private fun loadModal(bundle: Bundle){
        val detail_activity = context as DetailsList
        detail_activity.switchContent(bundle)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewAdapter {
        val rootView = LayoutInflater.from(parent?.context).inflate(R.layout.details_view, parent, false)
        context = rootView.context
        return ViewAdapter(rootView)
    }

    class ViewAdapter(layoutView: View): RecyclerView.ViewHolder(layoutView){
        val details_image: ImageView = layoutView.findViewById(R.id.detail_image)
        val details_title: TextView = layoutView.findViewById(R.id.text_title)
        val details_date: TextView = layoutView.findViewById(R.id.details_date)
        val details_container: RelativeLayout = layoutView.findViewById(R.id.details_container)
    }
}