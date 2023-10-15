package com.gl4.tp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    val spinnerMatiere : Spinner by lazy { findViewById(R.id.spinner) }
    val spinnerPresence : Spinner by lazy { findViewById(R.id.spinner2) }
    val recyclerView: RecyclerView by lazy{ findViewById(R.id.recyclerView)}
    val searchBar: EditText by lazy {findViewById(R.id.searchBar)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var matieres = listOf<String>("All","Cours","TP")

        var etats = listOf<String>("All","Absent" , "Present")
        spinnerMatiere.adapter = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,matieres)
        spinnerPresence.adapter = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,etats)

        spinnerMatiere.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = spinnerMatiere.selectedItem as String
                Toast.makeText(this@MainActivity, "$selectedItem selected", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        }



        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val students = ArrayList<Student>()

        // Add some students to the list
        students.add(Student("Kouki", "Arij", Genre.FEMININ,Matiere.COURS, Etat.PRESENT))
        students.add(Student("Jabloun", "Omar", Genre.MASCULIN,Matiere.COURS,Etat.ABSENT))
        students.add(Student("Lassoued", "Mohammed", Genre.MASCULIN,Matiere.COURS,Etat.PRESENT))
        students.add(Student("Ouerfelli", "Oumayama", Genre.FEMININ,Matiere.TP,Etat.ABSENT))


        val adapter = StudentAdapter(students)
        recyclerView.adapter = adapter

        val originalStudents = ArrayList<Student>(students) // Store the original list of students

        spinnerMatiere.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = spinnerMatiere.selectedItem as String
                val filteredStudents = when (selectedItem) {
                    "Cours" -> ArrayList(originalStudents.filter { it.matiere == Matiere.COURS })
                    "TP" -> ArrayList(originalStudents.filter { it.matiere == Matiere.TP })
                    else -> ArrayList(originalStudents) // Show all students when nothing is selected
                }
                applyPresenceFilter(filteredStudents,adapter)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                // Handle the case when nothing is selected
            }
        }

        spinnerPresence.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = spinnerPresence.selectedItem as String
                val filteredStudents = when (selectedItem) {
                    "Present" -> ArrayList(originalStudents.filter { it.etat == Etat.PRESENT })
                    "Absent" -> ArrayList(originalStudents.filter { it.etat == Etat.ABSENT })
                    else -> ArrayList(originalStudents) // Show all students when nothing is selected
                }
                applyMatiereFilter(filteredStudents,adapter)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                // Handle the case when nothing is selected
            }
        }




        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed for this example
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Filter the data in the adapter when text changes
                //Toast.makeText(this@MainActivity, s, Toast.LENGTH_SHORT).show()
                adapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable?) {
                // Not needed for this example
            }
        })


    }
    private fun applyMatiereFilter(filteredStudents: ArrayList<Student>, adapter : StudentAdapter) {
        val selectedItem = spinnerPresence.selectedItem as String
        val presenceFilteredStudents = when (selectedItem) {
            "Present" -> ArrayList(filteredStudents.filter { it.etat == Etat.PRESENT })
            "Absent" -> ArrayList(filteredStudents.filter { it.etat == Etat.ABSENT })
            else -> ArrayList(filteredStudents) // Show all students when nothing is selected
        }
        adapter.updateData(presenceFilteredStudents)
    }

    private fun applyPresenceFilter(filteredStudents: ArrayList<Student>, adapter : StudentAdapter) {
        val selectedItem = spinnerMatiere.selectedItem as String
        val matiereFilteredStudents = when (selectedItem) {
            "Cours" -> ArrayList(filteredStudents.filter { it.matiere == Matiere.COURS })
            "TP" -> ArrayList(filteredStudents.filter { it.matiere == Matiere.TP })
            else -> ArrayList(filteredStudents) // Show all students when nothing is selected
        }
        adapter.updateData(matiereFilteredStudents)
    }
}