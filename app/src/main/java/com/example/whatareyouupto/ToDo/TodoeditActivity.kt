package com.example.whatareyouupto.ToDo

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.whatareyouupto.MainActivity
import com.example.whatareyouupto.R
import com.example.whatareyouupto.databinding.ActivityTodoaddBinding
import com.example.whatareyouupto.databinding.ActivityTodoeditBinding
import com.example.whatareyouupto.sqlite.Memo
import com.example.whatareyouupto.sqlite.SqliteHelper
import java.text.SimpleDateFormat
import java.util.*

class TodoeditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTodoeditBinding
    private val helper = SqliteHelper(this,"memo",null,1)
    private var mintime = ""
    private var maxtime = ""

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todoadd)

        binding = ActivityTodoeditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("title")
        val mintime = intent.getStringExtra("mintime")
        val maxtime = intent.getStringExtra("maxtime")
        val cursor = intent.getLongExtra("id",-1)

        binding.minimumtime.text = mintime
        binding.maximumtime.text = maxtime
        binding.todoTitle.setText(title)



        binding.minimumtime.setOnClickListener {

            getMinimumTime(this)


        }

        binding.maximumtime.setOnClickListener {

            getMaximumTime(this)


        }

        binding.todoComplete.setOnClickListener {

            val edittitle = binding.todoTitle.text.toString()
            val editmintime = binding.minimumtime.text.toString()
            val editmaxtime = binding.maximumtime.text.toString()

            if(binding.todoTitle.text.isEmpty()){

                Toast.makeText(this,"제목을 입력하세요", Toast.LENGTH_SHORT).show()

            } else {


                helper.updateMemo(cursor,edittitle,editmintime,editmaxtime)

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