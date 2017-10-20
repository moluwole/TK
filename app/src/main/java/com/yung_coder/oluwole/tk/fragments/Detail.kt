package com.yung_coder.oluwole.tk.fragments


import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.yung_coder.oluwole.tk.DetailsList

import com.yung_coder.oluwole.tk.R
import com.yung_coder.oluwole.tk.db.DBManager
import org.jetbrains.anko.find
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class Detail : BottomSheetDialogFragment() {

    companion object {
        var topic = ""
        var detail = ""
        var amount = ""
        var date = ""
    }
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.navigation_delete -> {

                val dialog = AlertDialog.Builder(this.context)
                dialog.setMessage("Would you like to delete this Entry?")
                dialog.setIcon(android.R.drawable.ic_dialog_alert)
                dialog.setTitle("Delete?")
                dialog.setPositiveButton("YES") { p0, p1 ->
                    val database = DBManager(this@Detail.context)
                    val result = database.deleteSingle(detail)
                    if(result){
                        Toast.makeText(context, "Delete Successful", Toast.LENGTH_LONG).show()
                        val activity = context as DetailsList
                        activity.loadDetails()
                        getActivity().onBackPressed()
                    } else{
                        Toast.makeText(context, "An Error Occurred during Deletion. Try Again", Toast.LENGTH_LONG).show()
                    }
                }
                dialog.setNegativeButton("NO"){ _, _ ->

                }
                dialog.show()
                return@OnNavigationItemSelectedListener  true
            }
            R.id.navigation_share -> {
                val intent = Intent(android.content.Intent.ACTION_SEND)
                intent.type = "text/plain"
                val text = "Project Name: $topic\nDetails: $detail\nAmount: $amount\nDate: $date\n"
                intent.putExtra(android.content.Intent.EXTRA_TEXT, text)
                startActivity(Intent.createChooser(intent, "Share Options"))
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private var generator = ColorGenerator.MATERIAL

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater!!.inflate(R.layout.fragment_detail, container, false)

        val details_title: TextView = rootView.findViewById(R.id.modal_name)
        val details_text: TextView = rootView.findViewById(R.id.modal_details)
        val details_amount: TextView = rootView.findViewById(R.id.modal_amount)
        val details_date: TextView = rootView.findViewById(R.id.modal_date)

        val details_logo: ImageView = rootView.findViewById(R.id.modal_image)
        val details_back: ImageView = rootView.findViewById(R.id.modal_background)

        val navigation: BottomNavigationView = rootView.findViewById(R.id.modal_nav)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val args = arguments
        details_amount.text = args.getString("amount")
        details_title.text = args.getString("title")
        details_text.text = args.getString("details")
        details_date.text = args.getString("date")

        topic = args.getString("title")
        detail = args.getString("details")
        amount = args.getString("amount")
        date = args.getString("date")

        val drawable = TextDrawable.builder().buildRound(args.getString("title")[0].toString(), generator.randomColor)
        details_logo.setImageDrawable(drawable)

        val random = Random()
        val next = random.nextInt(7)
        when(next){
            0 ->{
                details_back.setImageResource(R.drawable.back1)
            }
            1 ->{
                details_back.setImageResource(R.drawable.back2)
            }
            2 ->{
                details_back.setImageResource(R.drawable.back3)
            }
            3 -> {
                details_back.setImageResource(R.drawable.back4)
            }
            4 -> {
                details_back.setImageResource(R.drawable.back5)
            }
            5 -> {
                details_back.setImageResource(R.drawable.back6)
            }
            6 -> {
                details_back.setImageResource(R.drawable.back7)
            }
        }

        return rootView
    }

}// Required empty public constructor
