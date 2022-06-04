package com.example.whatareyouupto.ToDo

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.whatareyouupto.MainActivity
import com.example.whatareyouupto.R
import com.example.whatareyouupto.databinding.ActivityMainBinding
import com.example.whatareyouupto.databinding.ActivityTodoaddBinding
import com.example.whatareyouupto.sqlite.Memo
import com.example.whatareyouupto.sqlite.SqliteHelper
import java.util.ArrayList

class TodoaddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTodoaddBinding
    private val listData = ArrayList<Memo>()
    private val helper = SqliteHelper(this,"memo",null,1)

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todoadd)

        binding = ActivityTodoaddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val todoDate = intent.getStringExtra("date")

        binding.todoDate.text = todoDate

        binding.todoComplete.setOnClickListener {

            val todoTitle  = binding.todoTitle.text.toString()

            if(todoTitle.isEmpty()){

                Toast.makeText(this,"제목을 입력하세요",Toast.LENGTH_SHORT).show()

            } else{

                val memo = Memo(null,todoTitle,todoDate)
                helper.insertMemo(memo)

                finish()


            }

        }

    }
}