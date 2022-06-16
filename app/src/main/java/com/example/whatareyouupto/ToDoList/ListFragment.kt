package com.example.whatareyouupto.ToDoList

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whatareyouupto.R
import com.example.whatareyouupto.ToDoCalendar.CalendarRVAdapter
import com.example.whatareyouupto.ToDoCalendar.TodoaddActivity
import com.example.whatareyouupto.databinding.FragmentCalendarBinding
import com.example.whatareyouupto.databinding.FragmentListBinding
import com.example.whatareyouupto.sqlite.Memo
import com.example.whatareyouupto.sqlite.SqliteHelper
import java.util.*

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private val listData = ArrayList<Memo>()
    private var helper: SqliteHelper? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        helper = SqliteHelper(activity,"memo",null,1)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater,container,false)

        val startTimeCalendar = Calendar.getInstance()

        val currentYear = startTimeCalendar.get(Calendar.YEAR)
        val currentMonth = startTimeCalendar.get(Calendar.MONTH)
        val currentDate = startTimeCalendar.get(Calendar.DATE)

        binding.day.text = currentDate.toString() + "일 "
        binding.month.text = (currentMonth+1).toString() + "월 "
        binding.year.text = currentYear.toString() + "년 "

        binding.fab.setOnClickListener {

            val intent = Intent(requireContext(), TodoaddActivity::class.java)
            intent.putExtra("year",currentYear)
            intent.putExtra("month",currentMonth)
            intent.putExtra("day",currentDate)
            startActivity(intent)

        }

        val itemcount = binding.recyclerView.adapter?.itemCount

        val Cursor = helper?.readableDatabase?.rawQuery("select * from memo WHERE year = $currentYear" +
                " and month = $currentMonth and day = $currentDate and checkbox = 1",null)

        binding.textView.text = "전체 작업 " + itemcount.toString() + "중 완료된 항목 " + Cursor!!.count.toString()






        ShowRecyclerView()

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()

        ShowRecyclerView()

    }

    fun ShowRecyclerView(){

        binding.fab.isVisible = true

        val startTimeCalendar = Calendar.getInstance()

        val currentYear = startTimeCalendar.get(Calendar.YEAR)
        val currentMonth = startTimeCalendar.get(Calendar.MONTH)
        val currentDate = startTimeCalendar.get(Calendar.DATE)

        val adapter = activity?.let { ListRVAdapter(it,listData,helper) }
        helper?.let { adapter?.listData?.addAll(it.selectMemo(currentYear,currentMonth,currentDate)) }
        adapter?.helper = helper

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        adapter?.listData?.clear()
        helper?.let { adapter?.listData?.addAll(it.selectMemo(currentYear,currentMonth,currentDate)) }
        adapter?.notifyDataSetChanged()

        val itemcount = binding.recyclerView.adapter?.itemCount

        if (itemcount!! >=3) {

            binding.fab.isVisible = false

        }

    }
}