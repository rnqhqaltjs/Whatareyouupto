package com.example.whatareyouupto.ToDoCalendar


import android.content.Context
import android.content.Intent
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whatareyouupto.databinding.CalendaritemmainBinding
import com.example.whatareyouupto.sqlite.Memo
import com.example.whatareyouupto.sqlite.SqliteHelper

class CalendarRVAdapter(val context: Context, val listData:ArrayList<Memo>, var helper: SqliteHelper? = null)
    : RecyclerView.Adapter<CalendarRVAdapter.Viewholder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val binding =
            CalendaritemmainBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val memo: Memo = listData[position]
        holder.setMemo(memo)

    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class Viewholder(private val binding: CalendaritemmainBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setMemo(memo: Memo) {

            binding.title.text = memo.title
            binding.mintime.text = memo.mintime
            binding.maxtime.text = memo.maxtime

            Glide.with(context)
                .load(memo.image)
                .override(80, 80)
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
        }
    }
}
