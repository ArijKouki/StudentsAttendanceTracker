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
    var selectedMatiere:String ="All"
    var selectedPresence:String="All"



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

        fun applyFilters() {

            val filteredStudents = students.filter { student ->
                (selectedMatiere == "All" || student.matiere.toString() == selectedMatiere) &&
                        (selectedPresence == "All" || student.etat.toString() == selectedPresence)
            }
            adapter.updateData(ArrayList(filteredStudents))
        }


        spinnerMatiere.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedMatiere = spinnerMatiere.selectedItem as String

                applyFilters()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                // Handle the case when nothing is selected
            }
        }

        spinnerPresence.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedPresence = spinnerPresence.selectedItem as String
                applyFilters()
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


}