package com.project.whatareyouupto.ToDoCalendar

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.project.whatareyouupto.R
import com.project.whatareyouupto.databinding.ActivityTodoeditBinding
import com.project.whatareyouupto.sqlite.SqliteHelper
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
        setSupportActionBar(binding.toolbar)

        //툴바 뒤로가기 UI
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        var image = intent.getIntExtra("image",-1)
        val mintime = intent.getStringExtra("mintime")
        val maxtime = intent.getStringExtra("maxtime")
        val cursor = intent.getLongExtra("id",-1)

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

        image = if(image == R.drawable.checkboxpick){

            binding.checkbox.setImageResource(R.drawable.checkboxpick)
            R.drawable.checkboxpick

        } else if(image == R.drawable.cakepick){

            binding.cake.setImageResource(R.drawable.cakepick)
            R.drawable.cakepick

        } else if(image == R.drawable.bookmarkpick){

            binding.bookmark.setImageResource(R.drawable.bookmarkpick)
            R.drawable.bookmarkpick

        } else {

            binding.star.setImageResource(R.drawable.starpick)
            R.drawable.starpick

        }


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

        binding.minimumtime.text = mintime
        binding.maximumtime.text = maxtime
        binding.title.setText(title)
        binding.content.setText(content)

        if(binding.maximumtime.text == "12:00 오전 (+1일)"){
            binding.allday.isChecked = true

            binding.minimumtime.isEnabled = false
            binding.maximumtime.isEnabled = false
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


        binding.minimumtime.setOnClickListener {

            getMinimumTime(this)


        }

        binding.maximumtime.setOnClickListener {

            getMaximumTime(this)


        }

        binding.fab.setOnClickListener {

            val edittitle = binding.title.text.toString()
            val editcontent = binding.content.text.toString()
            val editimage = image
            val editmintime = binding.minimumtime.text.toString()
            val editmaxtime = binding.maximumtime.text.toString()

            if(binding.title.text.isEmpty()){

                Toast.makeText(this,"일정 이름을 입력하세요", Toast.LENGTH_SHORT).show()

            } else {


                helper.updateMemo(cursor,edittitle,editcontent,editimage,editmintime,editmaxtime)
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