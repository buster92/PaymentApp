package com.andresgarrido.paymentapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.andresgarrido.paymentapp.R
import com.andresgarrido.paymentapp.model.PayerCost

class PayerCostAdapter(
    var clientArrayList: List<PayerCost>
) : BaseAdapter() {

    fun setData(data: List<PayerCost>) {
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
            LayoutInflater.from(parent.context).inflate(R.layout.spinner_text_item, parent, false)
        } else
            convertView
        val detail = clientArrayList[position]
        val name = row.findViewById<TextView>(R.id.item_name)
        name.text = detail.recommended_message

        return row
    }
}