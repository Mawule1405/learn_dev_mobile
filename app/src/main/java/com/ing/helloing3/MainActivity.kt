package com.ing.helloing3

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ing.helloing3.activities.GalleryActivity
import com.ing.helloing3.activities.ListSubjectActivity
import com.ing.helloing3.usedatabase.UserDatabase

class MainActivity : AppCompatActivity(){
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        //============================ Begin of code ==============================================
        val subjectButton = findViewById<Button>(R.id.subjectButton)
        val galleryButton = findViewById<Button>(R.id.galleryButton)

        val sendEmailButton = findViewById<Button>(R.id.sendMailButton)

        galleryButton.setOnClickListener {
            val intent = Intent(this, GalleryActivity::class.java) // Remplacez TargetActivity par le nom de votre activité cible
            startActivity(intent)
        }

        subjectButton.setOnClickListener{
            val intent = Intent(this, ListSubjectActivity::class.java)
            startActivity(intent)
        }

        sendEmailButton.setOnClickListener{
            sendEmail("helkmawule@gmail.com", "How are you?", "Salutation")
        }


    }


    fun sendEmail(emailAdresses : String, emailContain : String, subject : String){
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_EMAIL,arrayOf("apedokossiemmanuel@gmail.com", "helkmawule@gmail.com") )
        intent.putExtra(Intent.EXTRA_TEXT, emailContain)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)

        if(intent.resolveActivity(packageManager) != null){
            startActivity(intent)
        }
    }




}