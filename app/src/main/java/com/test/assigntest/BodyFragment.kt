package com.test.assigntest

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class BodyFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_body, container, false)
        val strtext = requireArguments().getString("body")
        @SuppressLint("MissingInflatedId", "LocalSuppress") val tv_text =
            view.findViewById<TextView>(R.id.tv_text)
        tv_text.text = strtext
        return view
    }
}