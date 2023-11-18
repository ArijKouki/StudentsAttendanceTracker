package com.gl4.tp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val spinnerMatiere: Spinner by lazy { findViewById(R.id.spinner) }
    private val spinnerPresence: Spinner by lazy { findViewById(R.id.spinner2) }
    private val recyclerView: RecyclerView by lazy { findViewById(R.id.recyclerView) }
    private val searchBar: SearchView by lazy { findViewById(R.id.search) }
    private lateinit var adapter: StudentAdapter
    private val originalStudents = ArrayList<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val matieres = listOf("All", "Cours", "TP")
        val etats = listOf("All", "Absent", "Present")

        spinnerMatiere.adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, matieres)
        spinnerPresence.adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, etats)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // Add some students to the list
        originalStudents.add(Student("Kouki", "Arij", Genre.FEMININ, Matiere.COURS, Etat.PRESENT))
        originalStudents.add(Student("Jabloun", "Omar", Genre.MASCULIN, Matiere.COURS, Etat.ABSENT))
        originalStudents.add(Student("Lassoued", "Mohammed", Genre.MASCULIN, Matiere.COURS, Etat.PRESENT))
        originalStudents.add(Student("Ouerfelli", "Oumayama", Genre.FEMININ, Matiere.TP, Etat.ABSENT))

        adapter = StudentAdapter(originalStudents)
        recyclerView.adapter = adapter

        setSpinnerListeners()
        setSearchViewListener()
    }

    private fun applyFilters(
        matiereFilteredStudents: List<Student>,
        presenceFilteredStudents: List<Student>,
        query: String
    ) {
        val finalFilteredStudents = ArrayList(originalStudents)

        // Apply matiere filter
        val selectedItemMatiere = spinnerMatiere.selectedItem as String
        if (selectedItemMatiere != "All") {
            finalFilteredStudents.retainAll(matiereFilteredStudents)
        }

        // Apply presence filter
        val selectedItemPresence = spinnerPresence.selectedItem as String
        if (selectedItemPresence != "All") {
            finalFilteredStudents.retainAll(presenceFilteredStudents)
        }

        // Apply search filter
        if (query.isNotEmpty()) {
            finalFilteredStudents.retainAll(originalStudents.filter { it.nom.toLowerCase().contains(query.toLowerCase()) })
        }

        adapter.updateData(finalFilteredStudents)
    }

    private fun setSpinnerListeners() {
        spinnerMatiere.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = spinnerMatiere.selectedItem as String
                val filteredStudents = when (selectedItem) {
                    "Cours" -> originalStudents.filter { it.matiere == Matiere.COURS }
                    "TP" -> originalStudents.filter { it.matiere == Matiere.TP }
                    else -> originalStudents
                }
                applyFilters(filteredStudents, originalStudents, searchBar.query.toString())
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        }

        spinnerPresence.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = spinnerPresence.selectedItem as String
                val filteredStudents = when (selectedItem) {
                    "Present" -> originalStudents.filter { it.etat == Etat.PRESENT }
                    "Absent" -> originalStudents.filter { it.etat == Etat.ABSENT }
                    else -> originalStudents
                }
                applyFilters(originalStudents, filteredStudents, searchBar.query.toString())
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        }
    }

    private fun setSearchViewListener() {
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                applyFilters(originalStudents, originalStudents, query.orEmpty())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                applyFilters(originalStudents, originalStudents, newText.orEmpty())
                return true
            }
        })
    }
}
