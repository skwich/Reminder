package com.example.reminder

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    object Global {
        lateinit var db: MainDb
        lateinit var historyDb: HistoryDb
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Global.db = MainDb.getDb(this)
        Global.historyDb = HistoryDb.getDb(this)

        val addTaskButton: ImageButton = findViewById(R.id.addTaskButton)
        addTaskButton.setOnClickListener{
            val intent = Intent(this, CreateTask::class.java)
            startActivity(intent)
        }

        //        NAVIGATION BUTTONS
        val taskButton: FrameLayout = findViewById(R.id.taskButton)
        taskButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val categoryButton: FrameLayout = findViewById(R.id.categoryButton)
        categoryButton.setOnClickListener{
            val intent = Intent(this, Category::class.java)
            startActivity(intent)
        }

        val historyButton: FrameLayout = findViewById(R.id.historyButton)
        historyButton.setOnClickListener{
            val intent = Intent(this, Category::class.java)
            startActivity(intent)
        }
    }
}