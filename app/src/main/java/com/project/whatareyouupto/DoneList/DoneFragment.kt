package com.project.whatareyouupto.DoneList

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.whatareyouupto.databinding.FragmentDoneBinding
import com.project.whatareyouupto.sqlite.Memo
import com.project.whatareyouupto.sqlite.SqliteHelper


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
    ): View {
        binding = FragmentDoneBinding.inflate(inflater,container,false)

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()

        ShowRecyclerView()

    }

    fun ShowRecyclerView(){

        val adapter = activity?.let {
            DoneRVAdapter(
                it,
                listData,
                helper
            )
        }
        helper?.let { adapter?.listData?.addAll(it.DoneMemo()) }
        adapter?.helper = helper

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        adapter?.listData?.clear()
        helper?.let { adapter?.listData?.addAll(it.DoneMemo()) }
        adapter?.notifyDataSetChanged()

    }

}