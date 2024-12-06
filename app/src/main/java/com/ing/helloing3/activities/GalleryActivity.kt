package com.ing.helloing3.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ing.helloing3.R

class GalleryActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gallery)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imageResources = listOf(
            R.drawable.souvenir1,
            R.drawable.souvenir2,
            R.drawable.souvenir3,
            R.drawable.souvenir4
        )


        val rememberImageView = findViewById<ImageView>(R.id.souvenirImageView)
        val addNewStudentButton = findViewById<Button>(R.id.addNewStudentButton)
        val listOfStudentButton = findViewById<Button>(R.id.listOfStudentButton)

        listOfStudentButton.setOnClickListener {
            val intent = Intent(this, ListGalleryActivity::class.java)
            startActivity(intent)
        }

        addNewStudentButton.setOnClickListener {
            val intent = Intent(this, AddGalleryActivity::class.java)
            startActivity(intent)
        }


        val handler = Handler(Looper.getMainLooper())
        var currentIndex = 0

        val runnable = object : Runnable {
            override fun run() {
                // Animation de translation pour faire défiler l'image
                rememberImageView.animate().translationX(-rememberImageView.width.toFloat())
                    .setDuration(500).withEndAction {
                    // Met à jour l'image après l'animation
                    rememberImageView.setImageResource(imageResources[currentIndex])
                    rememberImageView.translationX = rememberImageView.width.toFloat()
                    rememberImageView.animate().translationX(0f).setDuration(500).start()
                }

                // Met à jour l'index de l'image
                currentIndex = (currentIndex + 1) % imageResources.size

                // Planifie la prochaine exécution
                handler.postDelayed(this, 5000)
            }
        }

        // Démarre le changement d'image
        handler.post(runnable)

    }
}