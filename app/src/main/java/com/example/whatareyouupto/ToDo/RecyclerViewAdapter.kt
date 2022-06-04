package com.example.whatareyouupto.ToDo


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whatareyouupto.databinding.TodoitemMainBinding
import com.example.whatareyouupto.sqlite.Memo
import com.example.whatareyouupto.sqlite.SqliteHelper

class RecyclerViewAdapter(val context: Context,  val listData:ArrayList<Memo>, var helper: SqliteHelper? = null)
    : RecyclerView.Adapter<RecyclerViewAdapter.Viewholder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val binding = TodoitemMainBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val memo: Memo = listData[position]
        holder.setMemo(memo)

    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class Viewholder(private val binding: TodoitemMainBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setMemo(memo: Memo){
            binding.id.text = memo.id.toString()
            binding.title.text = memo.title
            binding.date.text = memo.date

        }

    }
    }