package com.ing.helloing3.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ing.helloing3.R
import com.ing.helloing3.adapters.StudentAdapter
import com.ing.helloing3.models.Student
import com.ing.helloing3.usedatabase.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ListGalleryActivity : AppCompatActivity(), CoroutineScope by MainScope() {



    @SuppressLint("MissingInflatedId")
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_gallery)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialisation du RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.listOfStudentRecyclerView)


        // Définition du layout manager
        recyclerView.layoutManager = LinearLayoutManager(this)

        val db = UserDatabase(this).getDatabase()

        lifecycleScope.launch {
            val students = db.studentDao().getAllStudents()
            // Initialisation de l'adaptateur sur le thread principal
            runOnUiThread {
                val studentAdapter = StudentAdapter(students)
                // Assurez-vous de définir l'adaptateur sur votre RecyclerView
                recyclerView.adapter = studentAdapter
            }
        }


    }
}
