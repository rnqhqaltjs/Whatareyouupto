package com.project.whatareyouupto.ToDoList

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.whatareyouupto.ToDoCalendar.TodoaddActivity
import com.project.whatareyouupto.databinding.FragmentListBinding
import com.project.whatareyouupto.sqlite.Memo
import com.project.whatareyouupto.sqlite.SqliteHelper
import java.util.*


class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private val listData = ArrayList<Memo>()
    private var helper: SqliteHelper? = null

    private val startTimeCalendar = Calendar.getInstance()

    private val currentYear = startTimeCalendar.get(Calendar.YEAR)
    private val currentMonth = startTimeCalendar.get(Calendar.MONTH)
    private val currentDate = startTimeCalendar.get(Calendar.DATE)

    override fun onAttach(context: Context) {
        super.onAttach(context)

        helper = SqliteHelper(activity,"memo",null,1)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater,container,false)

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

        binding.refresh.setOnClickListener {

            progressbar()

        }

        ShowRecyclerView()

        //프로그레스바를 0.1초마다 반복실행시켜 실시간 진행도 반영
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (true) {

                    progressbar()
                    handler.postDelayed(this, 100)

                }
            }
        }, 100)

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

    }

    fun progressbar(){

            val itemcount = binding.recyclerView.adapter?.itemCount

            val Cursor = helper?.readableDatabase?.rawQuery("select * from memo WHERE year = $currentYear" +
                    " and month = $currentMonth and day = $currentDate and checkbox = 1",null)

            binding.progressBar.max = itemcount!!
            binding.progressBar.progress = Cursor!!.count

            val n1 = itemcount.toDouble()
            val n2 = Cursor.count.toDouble()

            binding.cbc.text = String.format("%.0f",(n2/n1)*100) + "%"

            if(binding.cbc.text == "NaN%"){
                binding.cbc.text = "0%"
            }

    }
}