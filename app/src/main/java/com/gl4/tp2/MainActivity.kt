package com.gl4.tp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gl4.tp2.Genre


class MainActivity : AppCompatActivity() {

    val spinner : Spinner by lazy { findViewById(R.id.spinner) }
    val recyclerView: RecyclerView by lazy{ findViewById(R.id.recyclerView)}


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

        val students = mutableListOf<Student>()

        // Add some students to the list
        students.add(Student("Kouki", "Arij", Genre.FEMININ,Matiere.COURS))
        students.add(Student("Jabloun", "Omar", Genre.MASCULIN,Matiere.COURS))
        students.add(Student("Lassoued", "Mohammed", Genre.MASCULIN,Matiere.COURS))
        students.add(Student("Ouerfelli", "Oumayama", Genre.FEMININ,Matiere.TP))


        val adapter = StudentAdapter(students)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(NoSpaceItemDecoration())



    }
}