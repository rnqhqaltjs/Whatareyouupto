package com.example.whatareyouupto.ToDo

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.whatareyouupto.MainActivity
import com.example.whatareyouupto.R
import com.example.whatareyouupto.databinding.ActivityMainBinding
import com.example.whatareyouupto.databinding.ActivityTodoaddBinding
import com.example.whatareyouupto.sqlite.Memo
import com.example.whatareyouupto.sqlite.SqliteHelper
import java.text.SimpleDateFormat
import java.util.*

class TodoaddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTodoaddBinding
    private val listData = ArrayList<Memo>()
    private val helper = SqliteHelper(this,"memo",null,1)
    private var mintime = ""
    private var maxtime = ""

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todoadd)

        binding = ActivityTodoaddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val todoDate = intent.getStringExtra("date")

        binding.todoDate.text = todoDate

        binding.minimumtime.text = SimpleDateFormat("hh:mm a",Locale.KOREA).format(System.currentTimeMillis())
        binding.maximumtime.text = SimpleDateFormat("hh:mm a",Locale.KOREA).format(System.currentTimeMillis()+ 3600000)

        binding.minimumtime.setOnClickListener {

            getMinimumTime(this)


        }

        binding.maximumtime.setOnClickListener {

            getMaximumTime(this)


        }

        binding.todoComplete.setOnClickListener {

            val todoTitle  = binding.todoTitle.text.toString()

            if(todoTitle.isEmpty()){

                Toast.makeText(this,"제목을 입력하세요",Toast.LENGTH_SHORT).show()

            } else {

                mintime = binding.minimumtime.text.toString()
                maxtime = binding.maximumtime.text.toString()

                val memo = Memo(null,todoTitle,mintime,maxtime,todoDate!!)
                helper.insertMemo(memo)

                finish()


            }

        }

    }

    fun getMinimumTime(context: Context){

        val cal = Calendar.getInstance()

        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)

            binding.minimumtime.text = SimpleDateFormat("hh:mm a", Locale.KOREA).format(cal.time)
        }

        TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show()

    }

    fun getMaximumTime(context: Context){

        val cal = Calendar.getInstance()

        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)

            binding.maximumtime.text = SimpleDateFormat("hh:mm a", Locale.KOREA).format(cal.time)
        }

        TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY)+1, cal.get(Calendar.MINUTE), false).show()

    }
}