package com.example.whatareyouupto.ToDoCalendar

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whatareyouupto.CalendarDecorator.*
import com.example.whatareyouupto.databinding.FragmentCalendarBinding
import com.example.whatareyouupto.sqlite.Memo
import com.example.whatareyouupto.sqlite.SqliteHelper
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class CalendarFragment : Fragment() {

    private lateinit var binding: FragmentCalendarBinding
    private val listData = ArrayList<Memo>()
    private var helper: SqliteHelper? = null
    private var memoyear = 0
    private var memomonth = 0
    private var memoday = 0
    //    private var date2 = CalendarDay(Calendar.getInstance().get(Calendar.YEAR),
//        Calendar.getInstance().get(Calendar.YEAR),
//            Calendar.getInstance().get(Calendar.YEAR))

    override fun onAttach(context: Context) {
        super.onAttach(context)

        helper = SqliteHelper(activity,"memo",null,1)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater,container,false)

//        dotdecorator()

//        binding.calendarView.selectedDate = stCalendarDay

        binding.calendarView.setOnDateChangedListener { widget, date, selected ->

            binding.fab.isVisible = true

            val year =  date.year
            val month = date.month
            val day = date.day

            memoyear = year
            memomonth = month
            memoday = day

            binding.fab.setOnClickListener {

                val intent = Intent(requireContext(), TodoaddActivity::class.java)
                intent.putExtra("year",year)
                intent.putExtra("month",month)
                intent.putExtra("day",day)
                startActivity(intent)

            }


            ShowRecyclerView()

        }


        return binding.root
    }

//    캘린더 하단 일정 리사이클러뷰
    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()

        dotdecorator()
        ShowRecyclerView()


    }

    @SuppressLint("NotifyDataSetChanged")
    fun ShowRecyclerView(){

        val adapter = activity?.let { CalendarRVAdapter(it,listData,helper) }
        helper?.let { adapter?.listData?.addAll(it.selectMemo(memoyear,memomonth,memoday)) }
        adapter?.helper = helper

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        adapter?.listData?.clear()
        helper?.let { adapter?.listData?.addAll(it.selectMemo(memoyear,memomonth,memoday)) }
        adapter?.notifyDataSetChanged()

    }

    @SuppressLint("Recycle")
    fun dotdecorator() {

        val dates = ArrayList<CalendarDay>()
        val cursor = helper?.readableDatabase?.rawQuery("select year,month,day from memo", null)

        if(cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val dot_year = cursor.getInt(cursor.getColumnIndexOrThrow("year"))
                val dot_month = cursor.getInt(cursor.getColumnIndexOrThrow("month"))
                val dot_day = cursor.getInt(cursor.getColumnIndexOrThrow("day"))
                dates.add(CalendarDay(dot_year, dot_month, dot_day))
                Log.d("dates", dates.toString())

                cursor.moveToNext()

            }

        }
        cursor.close()



        binding.calendarView.removeDecorators()
        binding.calendarView.invalidateDecorators()



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
        val enCalendarDay = CalendarDay(endTimeCalendar.get(Calendar.YEAR), endTimeCalendar.get(
            Calendar.MONTH), endTimeCalendar.get(Calendar.DATE))

        val sundayDecorator = SundayDecorator()
        val saturdayDecorator = SaturdayDecorator()
        val minMaxDecorator = MinMaxDecorator(stCalendarDay, enCalendarDay)
        val boldDecorator = BoldDecorator(stCalendarDay, enCalendarDay)
        val todayDecorator = TodayDecorator(requireContext())
        val myselectordecorator = MySelectorDecorator(requireContext())
        // 토, 일 색칠 + 오늘 날짜 표시
        binding.calendarView.addDecorators(boldDecorator, sundayDecorator, saturdayDecorator, myselectordecorator, minMaxDecorator, todayDecorator)

        if (dates.size > 0) {
            binding.calendarView.addDecorator(EventDecorator(Color.BLACK, dates))
        }



    }

}