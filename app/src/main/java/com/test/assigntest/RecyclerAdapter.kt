package com.test.assigntest

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView


class RecyclerAdapter(
    var context: Context,
    var arrayList: ArrayList<DataModel>,
    var activity: MainActivity
) :
    RecyclerView.Adapter<RecyclerAdapter.viewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.row_recyclerview, parent, false)
        return viewholder(view)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val obj = arrayList[position]
        holder.tv_title.setText(obj.title)
        holder.cv_main.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("body", obj.body)
            val fragmentManager = activity.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val fragobj = BodyFragment()
            fragobj.arguments = bundle
            fragmentTransaction.replace(R.id.fragment_container, fragobj)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
            // Toast.makeText(context, ""+obj.getBody(), Toast.LENGTH_SHORT).show();
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_title: TextView
        var cv_main: CardView

        init {
            cv_main = itemView.findViewById(R.id.cv_main)
            tv_title = itemView.findViewById(R.id.tv_title)
        }
    }
}
