package com.ing.helloing3.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ing.helloing3.R
import com.ing.helloing3.models.Subject
import com.ing.helloing3.shared.DialogBotYesNo
import com.ing.helloing3.usedatabase.UserDatabase
import kotlinx.coroutines.launch

class AddSubjectActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_subject)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val logoImageView = findViewById<ImageView>(R.id.logoImageView)
        val codeEditText = findViewById<EditText>(R.id.codeEditText)
        val libelleEditText = findViewById<EditText>(R.id.nameEditText)
        val teacherEditText = findViewById<EditText>(R.id.teacherEditText)
        val horaireEditText = findViewById<EditText>(R.id.horaireEditText)
        val coefficientEditText = findViewById<EditText>(R.id.coefficientEditText)

        val submitSubjectButton = findViewById<Button>(R.id.submitSubjectButton)
        val resetSubjectButton = findViewById<Button>(R.id.resetSubjectButton)

        Glide.with(this)
            .load(R.drawable.iai_logo)
            .apply(RequestOptions.circleCropTransform())
            .into(logoImageView)

        resetSubjectButton.setOnClickListener {
            val dialogBot = DialogBotYesNo(
                context = this,
                title = "Confirmation",
                message = "Voulez-vous annuler l'enregistrement?",
                positiveButtonAction = {
                    clearAttribut(codeEditText, libelleEditText, teacherEditText, horaireEditText, coefficientEditText)
                },
                negativeButtonAction = {
                    Toast.makeText(
                        this,
                        "Veuillez vérifier les informations de la matière!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            )
            dialogBot.showDialog()
        }

        submitSubjectButton.setOnClickListener {
            val control = validationDesChamps(codeEditText, libelleEditText, teacherEditText, horaireEditText, coefficientEditText)
            if (control) {
                val dialogBot = DialogBotYesNo(
                    context = this,
                    title = "Confirmation",
                    message = "Voulez-vous enrégistrer cette matière?",
                    positiveButtonAction = {
                        val db = UserDatabase(this).getDatabase()
                        val subject = Subject(
                            null,
                            codeEditText.text.toString(),
                            libelleEditText.text.toString(),
                            teacherEditText.text.toString(),
                            horaireEditText.text.toString().toInt(),
                            coefficientEditText.text.toString().toInt()
                        )

                        lifecycleScope.launch {
                            db.subjectDao().insertSubject(subject)
                            runOnUiThread {
                                clearAttribut(codeEditText, libelleEditText, teacherEditText, horaireEditText, coefficientEditText)
                                Toast.makeText(
                                    this@AddSubjectActivity,
                                    "Enrégistrement effectué avec succès!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    },
                    negativeButtonAction = {
                        Toast.makeText(
                            this,
                            "Veuillez vérifier les informations de la matière et réessayer!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                )
                dialogBot.showDialog()
            } else {
                Toast.makeText(
                    this,
                    "Tous les champs sont obligatoires. Veuillez compléter SVP!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun validationDesChamps(code: EditText, libelle: EditText, teacher: EditText, horaire: EditText, coef: EditText): Boolean {
        return code.text.toString().isNotEmpty() &&
                libelle.text.toString().isNotEmpty() &&
                teacher.text.toString().isNotEmpty() &&
                horaire.text.toString().isNotEmpty() &&
                coef.text.toString().isNotEmpty()
    }

    private fun clearAttribut(code: EditText, libelle: EditText, teacher: EditText, horaire: EditText, coef: EditText) {
        code.setText("")
        libelle.setText("")
        teacher.setText("")
        horaire.setText("")
        coef.setText("")
    }
}
