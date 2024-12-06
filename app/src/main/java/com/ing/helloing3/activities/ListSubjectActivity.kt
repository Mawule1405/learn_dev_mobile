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
import com.ing.helloing3.adapters.SubjectAdapter
import com.ing.helloing3.usedatabase.AppDatabase
import com.ing.helloing3.usedatabase.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ListSubjectActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    private val ADD_SUBJECT_REQUEST_CODE = 1

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_subject)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val addSubject = findViewById<FloatingActionButton>(R.id.addNewSubjectFloatingactionbutton)
        val recyclerView: RecyclerView = findViewById(R.id.subjectRecyclerView)
        val db = UserDatabase(this).getDatabase()

        recyclerView.layoutManager = LinearLayoutManager(this)

        loadSubjects(db, recyclerView)

        addSubject.setOnClickListener {
            val intent = Intent(this, AddSubjectActivity::class.java)
            startActivityForResult(intent, ADD_SUBJECT_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_SUBJECT_REQUEST_CODE && resultCode == RESULT_OK) {
            val db = UserDatabase(this).getDatabase()
            val recyclerView: RecyclerView = findViewById(R.id.subjectRecyclerView)
            loadSubjects(db, recyclerView)
        }
    }

    override fun onResume() {
        super.onResume()
        val db = UserDatabase(this).getDatabase()
        val recyclerView: RecyclerView = findViewById(R.id.subjectRecyclerView)
        loadSubjects(db, recyclerView)
    }

    private fun loadSubjects(db: AppDatabase, recyclerView: RecyclerView) {
        lifecycleScope.launch {
            val subjects = db.subjectDao().getAllSubjects()
            runOnUiThread {
                val subjectAdapter = SubjectAdapter(subjects)
                recyclerView.adapter = subjectAdapter
            }
        }
    }
}
