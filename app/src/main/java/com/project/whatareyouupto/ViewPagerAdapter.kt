package com.project.whatareyouupto

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.project.whatareyouupto.DoneList.DoneFragment
import com.project.whatareyouupto.ToDoCalendar.CalendarFragment
import com.project.whatareyouupto.ToDoList.ListFragment


class ViewPagerAdapter (fragment : FragmentActivity) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ListFragment()
            1 -> CalendarFragment()
            2 -> DoneFragment()
            else -> DoneFragment()
        }
    }
}