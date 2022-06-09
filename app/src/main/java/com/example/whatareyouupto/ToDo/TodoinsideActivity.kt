package com.example.whatareyouupto.ToDo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.whatareyouupto.R
import com.example.whatareyouupto.databinding.ActivityMainBinding
import com.example.whatareyouupto.databinding.ActivityTodoinsideBinding
import com.example.whatareyouupto.sqlite.Memo
import com.example.whatareyouupto.sqlite.SqliteHelper

class TodoinsideActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTodoinsideBinding
    private val listData = ArrayList<Memo>()
    private val helper = SqliteHelper(this,"memo",null,1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTodoinsideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("title")
        val mintime = intent.getStringExtra("mintime")
        val maxtime = intent.getStringExtra("maxtime")

//        val date = intent.getStringExtra("date")
//            ?.replace("CalendarDay","")
//            ?.replace("{","")
//            ?.replace("}","")
//            ?.replace("-","")
//        val num = Integer.parseInt(date)



        binding.title.text = title
        binding.mintime.text = mintime
        binding.maxtime.text = maxtime
//        binding.date.text = date

        binding.deletefab.setOnClickListener {

            val cursor = intent.getLongExtra("id",-1)

            helper.deleteMemo(cursor)

            Toast.makeText(this,"삭제 완료", Toast.LENGTH_SHORT).show()
            finish()

        }

    }
}