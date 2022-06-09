package com.example.whatareyouupto

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whatareyouupto.CalendarDecorator.*
import com.example.whatareyouupto.ToDo.RecyclerViewAdapter
import com.example.whatareyouupto.ToDo.TodoaddActivity
import com.example.whatareyouupto.databinding.ActivityMainBinding
import com.example.whatareyouupto.sqlite.Memo
import com.example.whatareyouupto.sqlite.SqliteHelper
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle
    private val listData = ArrayList<Memo>()
    private val helper = SqliteHelper(this,"memo",null,1)
    private var tododate = ""
//    private var date2 = CalendarDay(Calendar.getInstance().get(Calendar.YEAR),
//        Calendar.getInstance().get(Calendar.YEAR),
//            Calendar.getInstance().get(Calendar.YEAR))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        toggle = ActionBarDrawerToggle(this,binding.drawer,
            R.string.drawer_opened,
            R.string.drawer_closed
        )
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toggle.syncState()


//        binding.calendarView.addDecorator(EventDecorator(Color.GRAY, Collections.singleton(date2)))

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
        val myselectordecorator = MySelectorDecorator(this)

        binding.calendarView.selectedDate = stCalendarDay

        binding.calendarView.addDecorators(boldDecorator, sundayDecorator, saturdayDecorator, myselectordecorator, minMaxDecorator, todayDecorator)

        binding.calendarView.setOnDateChangedListener { widget, date, selected ->

            binding.fab.isVisible = true

            tododate = date.toString()

            binding.fab.setOnClickListener {

                val intent = Intent(this, TodoaddActivity::class.java)
                intent.putExtra("date",date.toString())
                startActivity(intent)

            }

            ShowRecyclerView()

        }

    }

    //캘린더 하단 일정 리사이클러뷰
    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()

        ShowRecyclerView()

    }

    fun ShowRecyclerView(){

        val adapter = RecyclerViewAdapter(this,listData,helper)
        adapter.listData.addAll(helper.selectMemo(tododate))
        adapter.helper = helper

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter.listData.clear()
        adapter.listData.addAll(helper.selectMemo(tododate))
        adapter.notifyDataSetChanged()

        val itemcount = binding.recyclerView.adapter?.itemCount

        if (itemcount!! >=3) {

            binding.fab.isVisible = false

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){

            return true
        }

        return super.onOptionsItemSelected(item)
    }

}
