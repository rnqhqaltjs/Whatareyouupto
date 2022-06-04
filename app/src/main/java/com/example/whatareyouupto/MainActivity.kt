package com.example.whatareyouupto

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.amitshekhar.DebugDB
import com.example.whatareyouupto.CalendarDecorator.*
import com.example.whatareyouupto.ToDo.RecyclerViewAdapter
import com.example.whatareyouupto.ToDo.TodoaddActivity
import com.example.whatareyouupto.databinding.ActivityMainBinding
import com.example.whatareyouupto.sqlite.Memo
import com.example.whatareyouupto.sqlite.SqliteHelper
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val listData = ArrayList<Memo>()
    private val helper = SqliteHelper(this,"memo",null,1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //캘린더 ui
        val startTimeCalendar = Calendar.getInstance()
        val endTimeCalendar = Calendar.getInstance()

        val currentYear = startTimeCalendar.get(Calendar.YEAR)
        val currentMonth = startTimeCalendar.get(Calendar.MONTH)
        val currentDate = startTimeCalendar.get(Calendar.DATE)

        endTimeCalendar.set(Calendar.MONTH, currentMonth+5)

        binding.calendarView.state().edit()
            .setFirstDayOfWeek(Calendar.SUNDAY)
            .setMinimumDate(CalendarDay.from(currentYear, currentMonth, 1))
            .setMaximumDate(CalendarDay.from(currentYear, currentMonth+5, endTimeCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)))
            .setCalendarDisplayMode(CalendarMode.MONTHS)
            .commit()

        val stCalendarDay = CalendarDay(currentYear, currentMonth, currentDate)
        val enCalendarDay = CalendarDay(endTimeCalendar.get(Calendar.YEAR), endTimeCalendar.get(Calendar.MONTH), endTimeCalendar.get(Calendar.DATE))

        val sundayDecorator = SundayDecorator()
        val saturdayDecorator = SaturdayDecorator()
        val minMaxDecorator = MinMaxDecorator(stCalendarDay, enCalendarDay)
        val boldDecorator = BoldDecorator(stCalendarDay, enCalendarDay)
        val todayDecorator = TodayDecorator(this)

        binding.calendarView.addDecorators(sundayDecorator, saturdayDecorator, boldDecorator, minMaxDecorator, todayDecorator)

        binding.calendarView.setOnDateChangedListener { widget, date, selected ->

            binding.fab.isVisible = true

            binding.fab.setOnClickListener {

                val intent = Intent(this, TodoaddActivity::class.java)
                intent.putExtra("date",date.toString())
                startActivity(intent)

            }
            val adapter = RecyclerViewAdapter(this,listData,helper)
            adapter.listData.addAll(helper.selectMemo())
            adapter.helper = helper

            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

            adapter.listData.clear()
            adapter.listData.addAll(helper.selectMemo())
            adapter.notifyDataSetChanged()




        }



    }

    //캘린더 하단 일정 리사이클러뷰
    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()




    }

}