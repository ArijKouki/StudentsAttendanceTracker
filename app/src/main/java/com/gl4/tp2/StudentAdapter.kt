package com.gl4.tp2

import android.util.Log
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

class StudentAdapter(private val students: ArrayList<Student>) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>(),Filterable {

    var dataFilterList = ArrayList<Student>()
    private var studentStates = mutableMapOf<Int, Boolean>()
    init {
        dataFilterList=  students
        for ((index, student) in students.withIndex()) {
            studentStates[index] = student.etat == Etat.PRESENT
        }
    }


    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val studentImage: ImageView = itemView.findViewById(R.id.imageView)
        val studentName: TextView = itemView.findViewById(R.id.textView)
        val studentState: CheckBox = itemView.findViewById(R.id.checkBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.student_item, parent, false)
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

        holder.studentState.isChecked = student.etat == Etat.PRESENT

        holder.studentState.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                student.etat = Etat.PRESENT
            } else {
                student.etat = Etat.ABSENT
            }
        }

    }

    override fun getItemCount(): Int {
        return dataFilterList.size
    }

    fun updateData(newData: ArrayList<Student>) {
        dataFilterList = newData
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    dataFilterList = students
                } else {
                    val resultList = ArrayList<Student>()
                    for (student in students) {
                        if (student.nom.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList.add(student)
                        }
                    }
                    dataFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = dataFilterList
                Log.d("Filtered: ", dataFilterList.toString())
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                dataFilterList = results?.values as ArrayList<Student>
                notifyDataSetChanged()
            }

        }
    }

}
