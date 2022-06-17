package com.example.whatareyouupto.DoneList

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whatareyouupto.ToDoCalendar.TodoinsideActivity
import com.example.whatareyouupto.databinding.DoneitemmainBinding
import com.example.whatareyouupto.databinding.ListitemmainBinding
import com.example.whatareyouupto.sqlite.Memo
import com.example.whatareyouupto.sqlite.SqliteHelper

class DoneRVAdapter(val context: Context, val listData:ArrayList<Memo>, var helper: SqliteHelper? = null)
    : RecyclerView.Adapter<DoneRVAdapter.Viewholder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val binding = DoneitemmainBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val memo: Memo = listData[position]
        holder.setMemo(memo)

    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class Viewholder(private val binding: DoneitemmainBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ResourceAsColor")
        fun setMemo(memo: Memo){

            binding.title.text = memo.title
            binding.mintime.text = memo.mintime
            binding.maxtime.text = memo.maxtime

            Glide.with(context)
                .load(memo.image)
                .override(90,90)
                .into(binding.image)

        }

    }
}