package com.ing.helloing3.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ing.helloing3.R
import com.ing.helloing3.shared.DialogBotYesNo
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AddGalleryActivity : AppCompatActivity() {

    private val PICK_PHOTO_REQUEST_CODE = 1
    private val REQUEST_IMAGE_CAPTURE = 2
    private lateinit var photoURI: Uri
    private lateinit var currentPhotoPath: String

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_gallery)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val studentImageView = findViewById<ImageView>(R.id.studentImageView)
        val choosePhotoButton = findViewById<Button>(R.id.choosePhotoButton)
        val takePhotoButton = findViewById<Button>(R.id.takePhotoButton)
        val firstNameEditText = findViewById<EditText>(R.id.firstNameEditText)
        val lastNameEditText = findViewById<EditText>(R.id.lastNameEditText)
        val telephoneEditText = findViewById<EditText>(R.id.phoneNumberEditText)
        val specilisationEditText = findViewById<EditText>(R.id.specializationEditText)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val cancelButton = findViewById<Button>(R.id.cancelButton)

        // Changer la forme de la photo
        Glide.with(this)
            .load(R.drawable.defaut_student_image)
            .apply(RequestOptions.circleCropTransform())
            .into(studentImageView)

        cancelButton.setOnClickListener {
            val dialogBot = DialogBotYesNo(
                context = this,
                title = "Confirmation",
                message = "Voulez-vous annuler l'enregistrement?",
                positiveButtonAction = {
                    Glide.with(this)
                        .load(R.drawable.defaut_student_image)
                        .apply(RequestOptions.circleCropTransform())
                        .into(studentImageView)

                    firstNameEditText.setText("")
                    lastNameEditText.setText("")
                    telephoneEditText.setText("")
                    specilisationEditText.setText("")
                },
                negativeButtonAction = {
                    Toast.makeText(
                        this,
                        "Veuillez vérifier les informations de l'étudiant!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            )
            dialogBot.showDialog()
        }

        choosePhotoButton.setOnClickListener {
            // Créez une intention pour choisir une image
            val intent = Intent(Intent.ACTION_PICK).apply {
                type = "image/*" // Filtrer pour les fichiers d'image uniquement
            }
            startActivityForResult(intent, PICK_PHOTO_REQUEST_CODE)
        }

        takePhotoButton.setOnClickListener {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_PHOTO_REQUEST_CODE && resultCode == RESULT_OK) {
            val selectedImageUri = data?.data
            if (selectedImageUri != null) {
                // Chargez et affichez l'image avec Glide
                Glide.with(this)
                    .load(selectedImageUri)
                    .apply(RequestOptions.circleCropTransform())
                    .into(findViewById(R.id.studentImageView))
            } else {
                Toast.makeText(this, "Aucune image sélectionnée", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Affichez l'image capturée avec Glide
            Glide.with(this)
                .load(photoURI)
                .apply(RequestOptions.circleCropTransform())
                .into(findViewById(R.id.studentImageView))
        }
    }






}
