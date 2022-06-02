package com.example.whatareyouupto

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.whatareyouupto.CalendarDecorator.*
import com.example.whatareyouupto.ToDo.TodoaddActivity
import com.example.whatareyouupto.databinding.ActivityMainBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter
import java.util.*
import com.prolificinteractive.materialcalendarview.format.TitleFormatter




class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                Toast.makeText(this@MainActivity, "$date", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, TodoaddActivity::class.java)
                startActivity(intent)
            }
        }
    }
}