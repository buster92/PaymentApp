package com.andresgarrido.paymentapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.andresgarrido.paymentapp.R
import com.andresgarrido.paymentapp.model.CardIssuer
import com.bumptech.glide.Glide

class CardIssuerAdapter(
    var clientArrayList: List<CardIssuer>
) : BaseAdapter() {

    fun setData(data: List<CardIssuer>) {
        clientArrayList = data
        notifyDataSetChanged()
    }
    override fun getCount(): Int {
        return clientArrayList.size
    }

    override fun getItem(position: Int): Any {
        return clientArrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val row = if (convertView == null) {
            LayoutInflater.from(parent.context).inflate(R.layout.spinner_image_text_item, parent, false)
        } else
            convertView
        val detail = clientArrayList[position]
        val imageView = row!!.findViewById<ImageView>(R.id.item_image)
        val name = row.findViewById<TextView>(R.id.item_name)
        name.text = detail.name
        Glide.with(row)
            .load(detail.secure_thumbnail)
            .into(imageView)
        return row
    }
}