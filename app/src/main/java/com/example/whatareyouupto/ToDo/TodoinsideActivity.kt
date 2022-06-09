package com.example.whatareyouupto.ToDo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            val cursor = intent.getIntExtra("cursor",1)

            //강제로 null을 허용하기 위해 !! 사용
            helper.deleteMemo(listData[cursor])
            listData.remove(listData[cursor])
            Toast.makeText(this,"삭제 완료", Toast.LENGTH_SHORT).show()
            finish()

        }

    }
}