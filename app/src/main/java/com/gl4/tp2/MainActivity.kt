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
import com.gl4.tp2.Genre


class MainActivity : AppCompatActivity() {

    val spinner : Spinner by lazy { findViewById(R.id.spinner) }
    val recyclerView: RecyclerView by lazy{ findViewById(R.id.recyclerView)}
    val searchBar: EditText by lazy {findViewById(R.id.searchBar)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var matieres = listOf<String>("Cours","TP")
        spinner.adapter = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,matieres)



        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = spinner.selectedItem as String
                Toast.makeText(this@MainActivity, "$selectedItem selected", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        }



        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val students = ArrayList<Student>()

        // Add some students to the list
        students.add(Student("Kouki", "Arij", Genre.FEMININ,Matiere.COURS))
        students.add(Student("Jabloun", "Omar", Genre.MASCULIN,Matiere.COURS))
        students.add(Student("Lassoued", "Mohammed", Genre.MASCULIN,Matiere.COURS))
        students.add(Student("Ouerfelli", "Oumayama", Genre.FEMININ,Matiere.TP))


        val adapter = StudentAdapter(students)
        recyclerView.adapter = adapter

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