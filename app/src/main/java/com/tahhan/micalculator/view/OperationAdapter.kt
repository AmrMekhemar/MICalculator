package com.tahhan.micalculator.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tahhan.micalculator.R
import com.tahhan.micalculator.model.Operation

/**
 * Operation adapter class
 *
 * @property operations
 * @property listener
 * @constructor Creates an Operation adapter object
 */
class OperationAdapter(
    private var operations: List<Operation>,
    val listener: (Operation) -> Unit
) :
    RecyclerView.Adapter<OperationAdapter.ViewHolder?>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val operationsTV: TextView = itemView.findViewById(R.id.operationTV)
        val secondOperandTV: TextView = itemView.findViewById(R.id.secondOperandTV)
    }

    /**
     * Inflates the historyItem view
     *
     * @param parent
     * @param viewType
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_item, parent, false)
        return ViewHolder(v)
    }

    /**
     * binds the operation data to history item
     *
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = operations[position]
        holder.operationsTV.text = item.operation
        holder.secondOperandTV.text = item.secondOperand.toString()

        holder.itemView.setOnClickListener {
            listener(item)
        }

    }

    /**
     * returns the size of the operationsList
     *
     * @return
     */
    override fun getItemCount(): Int {
        return operations.size
    }
}