package com.example.whatareyouupto.ToDo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.whatareyouupto.R
import com.example.whatareyouupto.databinding.ActivityMainBinding
import com.example.whatareyouupto.databinding.ActivityTodoaddBinding

class TodoaddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTodoaddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todoadd)

        binding = ActivityTodoaddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.todoComplete.setOnClickListener {
            var todoTitle  = binding.todoTitle.text

        }
    }
}