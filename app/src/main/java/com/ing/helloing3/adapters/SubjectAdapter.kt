package com.ing.helloing3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.ing.helloing3.R
import com.ing.helloing3.models.Subject

class SubjectAdapter(private val subjectList: List<Subject>) :
    RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_subject, parent, false)
        return SubjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val subject = subjectList[position]
        holder.subjectNameTextView.text = subject.name
        holder.subjectCodeTextView.text = subject.code
        holder.detailButton.setOnClickListener {
            val dialogView = LayoutInflater.from(holder.itemView.context).inflate(R.layout.dialog_custom, null)
            val dialogBuilder = AlertDialog.Builder(holder.itemView.context) .setView(dialogView) .create()
            val dialogTitle = dialogView.findViewById<TextView>(R.id.dialogTitle)
            val dialogMessage = dialogView.findViewById<TextView>(R.id.dialogMessage)
            val dialogButton = dialogView.findViewById<Button>(R.id.dialogButton)
        // Définir les valeurs des vues de la boîte de dialogue
            dialogTitle.text = subject.name
            dialogMessage.text = subject.toPresentation()
            dialogButton.setOnClickListener { dialogBuilder.dismiss() }
            dialogBuilder.show()
        }
    }

    override fun getItemCount(): Int {
        return subjectList.size
    }

    class SubjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val subjectNameTextView: TextView = itemView.findViewById(R.id.subjectNameTextView)
        val subjectCodeTextView: TextView = itemView.findViewById(R.id.codeTextView)
        val detailButton: Button = itemView.findViewById(R.id.detailButton)
    }
}
