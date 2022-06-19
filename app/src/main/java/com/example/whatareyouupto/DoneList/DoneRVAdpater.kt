package com.example.whatareyouupto.DoneList

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
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
            binding.day.text = memo.day.toString() + "일"
            binding.month.text = memo.month.toString() + "월"
            binding.year.text = memo.year.toString() + "년"

            Glide.with(context)
                .load(memo.image)
                .override(90,90)
                .into(binding.image)

            binding.checkbox.setOnCheckedChangeListener(null)

            binding.checkbox.setOnCheckedChangeListener { buttonView, isChecked ->

                if (isChecked) {

                    binding.item.isEnabled = false
                    binding.checkbox.isEnabled = false
                    binding.title.paintFlags = binding.title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    binding.mintime.paintFlags = binding.mintime.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    binding.maxtime.paintFlags = binding.maxtime.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    binding.title.setTypeface(null, Typeface.ITALIC)
                    binding.mintime.setTypeface(null, Typeface.ITALIC)
                    binding.maxtime.setTypeface(null, Typeface.ITALIC)
                    binding.title.setTextColor(Color.parseColor("#808080"))
                    binding.image.setColorFilter(Color.parseColor("#808080"))

                }
            }

            binding.checkbox.isChecked = memo.checkbox == true

        }

    }
}