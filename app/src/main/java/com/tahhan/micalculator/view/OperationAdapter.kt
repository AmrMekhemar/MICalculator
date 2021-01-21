package com.tahhan.micalculator.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tahhan.micalculator.R


class OperationAdapter(private var operations: List<Pair<String,Int>>, val listener: (Pair<String,Int>) -> Unit) :
    RecyclerView.Adapter<OperationAdapter.ViewHolder?>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val operationsTV: TextView = itemView.findViewById(R.id.operationTV)
        val secondOperandTV: TextView = itemView.findViewById(R.id.secondOperandTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         val item =operations[position]
        holder.operationsTV.text = item.first
        holder.secondOperandTV.text = item.second.toString()

        holder.itemView.setOnClickListener {
            listener(item)
        }

    }
    override fun getItemCount(): Int {
        return operations.size
    }
}