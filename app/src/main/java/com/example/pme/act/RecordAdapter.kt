package com.example.pme.act

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pme.R
import com.example.pme.add.DatabaseHelper
import com.example.pme.add.Record

class RecordAdapter(private val db:DatabaseHelper, private val num:Int? = null) : RecyclerView.Adapter<RecordAdapter.ViewHolder>(){
    private var records = fillList()

    private fun fillList():List<Record>{
        return if (num == null)
            db.getAllRecords()
        else
            db.getRecords(num)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val sys: TextView = view.findViewById(R.id.recSystolicText)
        val dia: TextView = view.findViewById(R.id.recDiastolicText)
        val dataTime: TextView = view.findViewById(R.id.recDateTimeText)
        val pulse: TextView = view.findViewById(R.id.recPulseText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.record_design, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return records.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.sys.text = records[position].systolic.toString()
        holder.dia.text = records[position].diastolic.toString()
        holder.dataTime.text = records[position].dateTime
        holder.pulse.text = records[position].pulse.toString()

        holder.itemView.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setMessage("Are you sure you want to delete this record?")
                .setPositiveButton("Yes") { dialog, which ->
                    records[position].id?.let { it1 -> db.delete(it1) }
                    records = fillList()
                    Toast.makeText(holder.itemView.context, "Record deleted", Toast.LENGTH_SHORT).show()
                    notifyDataSetChanged()
                }
                .setNegativeButton("No", null)
                .show()
        }
    }


}