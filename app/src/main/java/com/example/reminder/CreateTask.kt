package com.example.reminder

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ListView
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.reminder.databinding.ActivityMainBinding
import java.util.Calendar


class CreateTask : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

//    DATA
    lateinit var taskData : Button
    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0
    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0

    //    CATEGORY
    lateinit var taskCategory : Button
    lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_create_task)

//        DATA
        taskData = findViewById(R.id.taskData)
        taskData.setOnClickListener{
            getDateTimeCalendar()
            DatePickerDialog(this, this, year, month, day).show()
        }

//        CATEGORY
        taskCategory = findViewById(R.id.taskCategory)
        val categoryList : ListView = findViewById(R.id.categoryList)
        taskCategory.setOnClickListener{
            if (categoryList.visibility == View.VISIBLE)
                categoryList.visibility = View.INVISIBLE
            else
                categoryList.visibility = View.VISIBLE
        }

        val checkbox : CheckBox = findViewById(R.id.checkboxEmail)
        val createTaskButton : Button = findViewById(R.id.createTaskButton)
        createNotificationChannel()
        createTaskButton.setOnClickListener{
            if (checkbox.isChecked) {
                scheduleNotification()
            }

            val title : EditText = findViewById(R.id.taskName)
            MainActivity.Global.historyDb.getDao().insertItem(
                Task(null,
                    title.text.toString(),
                    year.toString(),
                    month.toString(),
                    day.toString(),
                    hour.toString(),
                    minute.toString()
                )
            )

            val intent = Intent(this, MainActivity::class.java)
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

    //DATA
    private fun getDateTimeCalendar() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year

        getDateTimeCalendar()
        TimePickerDialog(this,this,hour,minute, true).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute

        taskData.text = "$savedHour:$savedMinute\n$savedDay/$savedMonth/$savedYear"
    }

    //CATEGORY


    //Notification
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = "Notification Channel"
        val desc = "desc of channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    @SuppressLint("ScheduleExactAlarm")
    private fun scheduleNotification() {
        val intent = Intent(applicationContext, Notification::class.java)
        val title : EditText = findViewById(R.id.taskName)
        val data : Button = findViewById(R.id.taskData)
        intent.putExtra(titleExtra, title.text.toString())
        intent.putExtra(messageExtra, data.text.toString())

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)
        val time = calendar.timeInMillis
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )

    }
}