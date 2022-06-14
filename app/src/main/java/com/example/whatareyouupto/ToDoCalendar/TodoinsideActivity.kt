package com.example.whatareyouupto.ToDoCalendar

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.whatareyouupto.databinding.ActivityTodoinsideBinding
import com.example.whatareyouupto.sqlite.SqliteHelper

class TodoinsideActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTodoinsideBinding
    private val helper = SqliteHelper(this,"memo",null,1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTodoinsideBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val image = intent.getIntExtra("image",-1)
        val mintime = intent.getStringExtra("mintime")
        val maxtime = intent.getStringExtra("maxtime")
        val year = intent.getIntExtra("year",-1)
        val month = intent.getIntExtra("month",-1)+1
        val day = intent.getIntExtra("day",-1)
        val id = intent.getLongExtra("id",-1)

        binding.title.text = title
        binding.content.text = content
        binding.mintime.text = mintime
        binding.maxtime.text = maxtime
        binding.year.text = year.toString()+"년"
        binding.month.text = month.toString()+"월"
        binding.day.text = day.toString()+"일"

        Glide.with(this)
            .load(image)
            .override(150,150)
            .into(binding.image)

        binding.deletefab.setOnClickListener {

            helper.deleteMemo(id)
            Toast.makeText(this,"삭제 완료", Toast.LENGTH_SHORT).show()
            finish()

        }

        binding.editfab.setOnClickListener {

            val intent = Intent(this,TodoeditActivity::class.java)
            intent.putExtra("title",binding.title.text)
            intent.putExtra("content",binding.content.text)
            intent.putExtra("image",image)
            intent.putExtra("mintime",binding.mintime.text)
            intent.putExtra("maxtime",binding.maxtime.text)
            intent.putExtra("id",id)

            startActivity(intent)
            finish()

        }

    }

    //툴바 뒤로가기 버튼
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}