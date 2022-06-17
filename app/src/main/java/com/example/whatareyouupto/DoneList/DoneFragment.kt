package com.example.whatareyouupto.DoneList

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whatareyouupto.R
import com.example.whatareyouupto.ToDoList.ListRVAdapter
import com.example.whatareyouupto.databinding.FragmentDoneBinding
import com.example.whatareyouupto.databinding.FragmentListBinding
import com.example.whatareyouupto.sqlite.Memo
import com.example.whatareyouupto.sqlite.SqliteHelper
import java.util.*

class DoneFragment : Fragment() {

    private lateinit var binding: FragmentDoneBinding
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
        binding = FragmentDoneBinding.inflate(inflater,container,false)

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()

        ShowRecyclerView()

    }

    fun ShowRecyclerView(){

        val startTimeCalendar = Calendar.getInstance()

        val currentYear = startTimeCalendar.get(Calendar.YEAR)
        val currentMonth = startTimeCalendar.get(Calendar.MONTH)
        val currentDate = startTimeCalendar.get(Calendar.DATE)


        val adapter = activity?.let { ListRVAdapter(it,listData,helper) }
        helper?.let { adapter?.listData?.addAll(it.DoneMemo()) }
        adapter?.helper = helper

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        adapter?.listData?.clear()
        helper?.let { adapter?.listData?.addAll(it.DoneMemo()) }
        adapter?.notifyDataSetChanged()

    }

}