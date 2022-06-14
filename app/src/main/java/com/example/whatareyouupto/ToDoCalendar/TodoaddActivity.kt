package com.example.whatareyouupto.ToDoCalendar

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.Button
import android.widget.CompoundButton
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
    private val helper = SqliteHelper(this,"memo",null,1)
    private var mintime = ""
    private var maxtime = ""
    private var image = 0

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTodoaddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        //툴바 뒤로가기 UI
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val year = intent.getIntExtra("year",-1)
        val month = intent.getIntExtra("month",-1)
        val day = intent.getIntExtra("day",-1)

        binding.title.addTextChangedListener(object : TextWatcher {


            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                binding.textwatcher.text = "0 / 12"

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                val input = binding.title.text.toString()
                binding.textwatcher.text = input.length.toString() + " / 12"

            }

            override fun afterTextChanged(s: Editable?) {

                val input = binding.title.text.toString()
                binding.textwatcher.text = input.length.toString() + " / 12"

            }
        })

        binding.content.addTextChangedListener(object : TextWatcher {


            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                binding.textwatcher2.text = "0 / 50"

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                val input = binding.content.text.toString()
                binding.textwatcher2.text = input.length.toString() + " / 50"

            }

            override fun afterTextChanged(s: Editable?) {

                val input = binding.content.text.toString()
                binding.textwatcher2.text = input.length.toString() + " / 50"

            }
        })


        binding.checkbox.setImageResource(R.drawable.checkboxpick)
        image = R.drawable.checkboxpick


        binding.checkbox.setOnClickListener {

            binding.checkbox.setImageResource(R.drawable.checkboxpick)
            binding.cake.setImageResource(R.drawable.cake)
            binding.bookmark.setImageResource(R.drawable.bookmark)
            binding.star.setImageResource(R.drawable.star)

            image = R.drawable.checkboxpick
        }

        binding.cake.setOnClickListener {

            binding.cake.setImageResource(R.drawable.cakepick)
            binding.checkbox.setImageResource(R.drawable.checkbox)
            binding.bookmark.setImageResource(R.drawable.bookmark)
            binding.star.setImageResource(R.drawable.star)

            image = R.drawable.cakepick
        }

        binding.bookmark.setOnClickListener {

            binding.bookmark.setImageResource(R.drawable.bookmarkpick)
            binding.checkbox.setImageResource(R.drawable.checkbox)
            binding.cake.setImageResource(R.drawable.cake)
            binding.star.setImageResource(R.drawable.star)

            image = R.drawable.bookmarkpick
        }

        binding.star.setOnClickListener {

            binding.star.setImageResource(R.drawable.starpick)
            binding.checkbox.setImageResource(R.drawable.checkbox)
            binding.cake.setImageResource(R.drawable.cake)
            binding.bookmark.setImageResource(R.drawable.bookmark)

            image = R.drawable.starpick
        }

        //  스위치를 클릭했을때
        binding.allday.setOnCheckedChangeListener{
                CompoundButton, onSwitch ->

            //  스위치가 켜지면
            if (onSwitch){
                binding.minimumtime.text = "12:00 오전"
                binding.maximumtime.text = "12:00 오전 (+1일)"

                binding.minimumtime.isEnabled = false
                binding.maximumtime.isEnabled = false
            }

            //  스위치가 꺼지면
            else{
                binding.minimumtime.text = SimpleDateFormat("hh:mm a",Locale.KOREA).format(System.currentTimeMillis())
                binding.maximumtime.text = SimpleDateFormat("hh:mm a",Locale.KOREA).format(System.currentTimeMillis()+ 3600000)

                binding.minimumtime.isEnabled = true
                binding.maximumtime.isEnabled = true
            }
        }

        binding.minimumtime.text = SimpleDateFormat("hh:mm a",Locale.KOREA).format(System.currentTimeMillis())
        binding.maximumtime.text = SimpleDateFormat("hh:mm a",Locale.KOREA).format(System.currentTimeMillis()+ 3600000)

        binding.minimumtime.setOnClickListener {

            getMinimumTime(this)


        }

        binding.maximumtime.setOnClickListener {

            getMaximumTime(this)


        }

        binding.fab.setOnClickListener {

            val title  = binding.title.text.toString()
            val content = binding.content.text.toString()

            if(title.isEmpty()){

                Toast.makeText(this,"일정 이름을 입력하세요",Toast.LENGTH_SHORT).show()

            } else {

                mintime = binding.minimumtime.text.toString()
                maxtime = binding.maximumtime.text.toString()

                val memo = Memo(null,title,content,image,mintime,maxtime,year,month,day)
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