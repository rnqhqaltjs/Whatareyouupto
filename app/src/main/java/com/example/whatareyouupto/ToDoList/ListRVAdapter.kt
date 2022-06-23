package com.example.whatareyouupto.ToDoList

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
import com.example.whatareyouupto.R
import com.example.whatareyouupto.ToDoCalendar.TodoinsideActivity
import com.example.whatareyouupto.databinding.ListitemmainBinding
import com.example.whatareyouupto.sqlite.Memo
import com.example.whatareyouupto.sqlite.SqliteHelper

class ListRVAdapter(val context: Context, val listData:ArrayList<Memo>, var helper: SqliteHelper? = null)
    : RecyclerView.Adapter<ListRVAdapter.Viewholder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val binding = ListitemmainBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val memo: Memo = listData[position]
        holder.setMemo(memo)

    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class Viewholder(private val binding: ListitemmainBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ResourceAsColor")
        fun setMemo(memo: Memo){

            binding.title.text = memo.title
            binding.mintime.text = memo.mintime
            binding.maxtime.text = memo.maxtime

            Glide.with(context)
                .load(memo.image)
                .override(90,90)
                .into(binding.image)

            itemView.setOnClickListener {

                val intent = Intent(context, TodoinsideActivity::class.java)
                intent.putExtra("title", memo.title)
                intent.putExtra("content", memo.content)
                intent.putExtra("image", memo.image)
                intent.putExtra("mintime", memo.mintime)
                intent.putExtra("maxtime", memo.maxtime)
                intent.putExtra("id", memo.id)
                intent.putExtra("year", memo.year)
                intent.putExtra("month", memo.month)
                intent.putExtra("day", memo.day)
                context.startActivity(intent)

            }

            binding.checkbox.setOnCheckedChangeListener(null)

            binding.checkbox.setOnCheckedChangeListener { buttonView, isChecked ->

                if (isChecked) {

                    binding.item.isEnabled = false
                    binding.title.paintFlags = binding.title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    binding.mintime.paintFlags = binding.mintime.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    binding.maxtime.paintFlags = binding.maxtime.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    binding.title.setTypeface(null, Typeface.ITALIC)
                    binding.mintime.setTypeface(null, Typeface.ITALIC)
                    binding.maxtime.setTypeface(null, Typeface.ITALIC)
                    binding.title.setTextColor(Color.parseColor("#808080"))
                    binding.image.setColorFilter(Color.parseColor("#808080"))

                    helper?.updatecheckbox(memo.id!!,true)

                } else {

                    binding.item.isEnabled = true
                    binding.title.paintFlags = binding.title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    binding.mintime.paintFlags = binding.mintime.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    binding.maxtime.paintFlags = binding.maxtime.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    binding.title.setTypeface(null,Typeface.NORMAL)
                    binding.mintime.setTypeface(null,Typeface.NORMAL)
                    binding.maxtime.setTypeface(null,Typeface.NORMAL)
                    binding.title.setTextColor(Color.parseColor("#000000"))
                    binding.image.setColorFilter(Color.parseColor("#FF7F00"))

                    helper?.updatecheckbox(memo.id!!,false)

                }
            }

            binding.checkbox.isChecked = memo.checkbox == true

        }

    }
}