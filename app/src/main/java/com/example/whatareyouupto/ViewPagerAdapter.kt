package com.example.whatareyouupto

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.whatareyouupto.ToDoCalendar.CalendarFragment
import com.example.whatareyouupto.ToDoList.ListFragment

class ViewPagerAdapter (fragment : FragmentActivity) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ListFragment()
            1 -> CalendarFragment()
            else -> CalendarFragment()
        }
    }
}