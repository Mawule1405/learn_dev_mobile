package com.ing.helloing3.usedatabase

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ing.helloing3.models.Student
import com.ing.helloing3.models.Subject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDatabase(context: Context) {

    private val db: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java, "ing3db"
    ).addCallback(object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            // Insérer des données initiales
            CoroutineScope(Dispatchers.IO).launch {
                insertInitialData()
            }
        }
    }).build()

    fun getDatabase() : AppDatabase{
        return db
    }

    private suspend fun insertInitialData() {
        val studentDao = db.studentDao()
        val subjectDao = db.subjectDao()

        // Insérer des étudiants initiaux
        val studentList = listOf(
            Student(1, "emmanuel", "APEDO", "Kossi Emmanuel", "Ingénieur en Informatique", "123-456-7890"),
            Student(2, "sally", "NZOGHE ASSENGONE", "Yolande Saly", "Data Scientist", "234-567-8901"),
            Student(3, "maurice", "DZIDJINYO", "Komlan Maurice Yann", "Développeur Mobile", "345-678-9012"),
            Student(4, "theodore", "BAWANA", "Theodore", "Analyste de Données", "456-789-0123"),
            Student(5, "larry", "BELIGONE", "Lary", "Ingénieur Réseau", "567-890-1234"),
            Student(6, "ted", "MAYOMBO MOUBAROU", "Ted Orly", "Architecte Logiciel", "678-901-2345"),
            Student(7, "eric", "REMADJI", "Eric", "Consultant en Sécurité", "789-012-3456"),
            Student(8, "marina", "BEBANE MOUKOUMBI", "Marina", "Chef de Projet", "890-123-4567"),
            Student(9, "doguy", "ZENKOUEREY", "Doguy", "Ingénieur Système", "901-234-5678"),
            Student(10, "fifi", "NKILI OBELE", "Fifi Karen", "Spécialiste en IA", "012-345-6789"),
            Student(11, "bienfait", "MBOYI", "Bienfait", "Développeur Full Stack", "123-456-7890")
        )

        val subjectList = listOf(
            Subject(1, "AD", "Analyse des données", "M. MATY Maman", 40, 4),
            Subject(2, "BDA", "Base de données Avancées", "", 50, 5),
            Subject(3, "CPI", "Conduite Projet Informatique", "", 30, 3),
            Subject(4, "IDI", "Informatique Décisionnelle Introduction", "", 40, 4),
            Subject(5, "IAR", "Installation et Administration des Réseaux", "M. OSENNE", 70, 6),
            Subject(6, "IRHD", "Interconnexion et Réseaux Haut Débit + MSR", "M. OSENNE", 60, 5),
            Subject(7, "MFC", "Méthodes Formelles de Conception", "M. NOUSSI", 40, 4),
            Subject(8, "PM", "Programmation Mobile", "M. TAKOUMBO", 40, 4),
            Subject(9, "PS", "Programmation Système", "Dr MOUKELI", 40, 4),
            Subject(10, "QL", "Qualité Logiciel", "", 30, 3),
            Subject(11, "IoT", "Objets connectés, Internet des Objets", "", 30, 2),
            Subject(12, "SIGT", "Systèmes d'Informations Géographiques, Télédétection", "M. MASSALA", 50, 5),
            Subject(13, "TC", "Techniques Contratuelles", "", 30, 2),
            Subject(14, "SM", "Stage et Mémoire", "", 24 * 30 * 5, 20)
        )

        studentList.forEach { studentDao.insertStudent(it) }
        subjectList.forEach { subjectDao.insertSubject(it) }
    }
}
