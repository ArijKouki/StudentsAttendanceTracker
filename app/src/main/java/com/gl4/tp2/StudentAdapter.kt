package com.gl4.tp2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class StudentAdapter(private var students: List<Student>) :
    RecyclerView.Adapter<StudentAdapter.StudentViewHolder>(), Filterable {

    private var dataFilterList: List<Student> = students

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val studentImage: ImageView = itemView.findViewById(R.id.imageView)
        val studentName: TextView = itemView.findViewById(R.id.textView)
        val studentState: CheckBox = itemView.findViewById(R.id.checkBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.student_item, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = dataFilterList[position]
        holder.studentName.text = "${student.nom} ${student.prenom}"

        if (student.genre == Genre.MASCULIN) {
            holder.studentImage.setImageResource(R.drawable.man)
        } else {
            holder.studentImage.setImageResource(R.drawable.woman)
        }

        holder.studentState.setOnCheckedChangeListener(null)
        holder.studentState.isChecked = student.etat == Etat.PRESENT

        holder.studentState.setOnCheckedChangeListener { _, isChecked ->
            student.etat = if (isChecked) Etat.PRESENT else Etat.ABSENT
        }
    }

    override fun getItemCount(): Int {
        return dataFilterList.size
    }

    fun updateData(newData: List<Student>) {
        dataFilterList = newData
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString().toLowerCase(Locale.ROOT)
                val filteredList = if (charSearch.isEmpty()) {
                    students
                } else {
                    students.filter {
                        it.nom.toLowerCase(Locale.ROOT).contains(charSearch)
                    }
                }

                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                updateData(results?.values as List<Student>)
            }
        }
    }
}
