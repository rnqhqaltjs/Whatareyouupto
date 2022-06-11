package com.example.whatareyouupto.ToDo

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.whatareyouupto.R
import com.example.whatareyouupto.databinding.ActivityTodoeditBinding
import com.example.whatareyouupto.sqlite.SqliteHelper
import java.text.SimpleDateFormat
import java.util.*

class TodoeditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTodoeditBinding
    private val helper = SqliteHelper(this,"memo",null,1)

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
        binding.title.setText(title)

        binding.minimumtime.setOnClickListener {

            getMinimumTime(this)


        }

        binding.maximumtime.setOnClickListener {

            getMaximumTime(this)


        }

        binding.fab.setOnClickListener {

            val edittitle = binding.title.text.toString()
            val editmintime = binding.minimumtime.text.toString()
            val editmaxtime = binding.maximumtime.text.toString()

            if(binding.title.text.isEmpty()){

                Toast.makeText(this,"제목을 입력하세요", Toast.LENGTH_SHORT).show()

            } else {


                helper.updateMemo(cursor,edittitle,editmintime,editmaxtime)
                Toast.makeText(this,"수정 완료", Toast.LENGTH_SHORT).show()
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