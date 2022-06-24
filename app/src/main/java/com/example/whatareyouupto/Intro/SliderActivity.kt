package com.example.whatareyouupto.Intro

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.whatareyouupto.MainActivity
import com.example.whatareyouupto.databinding.ActivitySliderBinding

class SliderActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySliderBinding
    private val fragment1 = SliderFragment()
    private val fragment2 = SliderFragment()
    private val fragment3 = SliderFragment()
    lateinit var adapter : myPagerAdapter

    lateinit var preference : SharedPreferences
    val pref_show_intro = "Intro"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySliderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preference = getSharedPreferences("IntroSlider" , Context.MODE_PRIVATE)

        if(!preference.getBoolean(pref_show_intro,true)){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        fragment1.setTitle("Welcome")
        fragment2.setTitle("To CodeAndroid")
        fragment3.setTitle("YouTube Channel")

        adapter = myPagerAdapter(supportFragmentManager)
        adapter.list.add(fragment1)
        adapter.list.add(fragment2)
        adapter.list.add(fragment3)

        binding.viewPager.adapter = adapter
        binding.btnNext.setOnClickListener {
            binding.viewPager.currentItem++
        }

        binding.btnSkip.setOnClickListener { goToMainboard() }

        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                if(position == adapter.list.size-1){
                    //마지막 페이지
                    binding.btnNext.text = "시작하기!"

                    binding.btnNext.setOnClickListener {
                        goToMainboard()
                    }

                }else{
                    //다음
                    binding.btnNext.text = "다음"

                    binding.btnNext.setOnClickListener {
                        binding.viewPager.currentItem++
                    }

                }

                when(binding.viewPager.currentItem){
                    0->{
                        binding.indicator1.setTextColor(Color.BLACK)
                        binding.indicator2.setTextColor(Color.GRAY)
                        binding.indicator3.setTextColor(Color.GRAY)
                    }
                    1->{
                        binding.indicator1.setTextColor(Color.GRAY)
                        binding.indicator2.setTextColor(Color.BLACK)
                        binding.indicator3.setTextColor(Color.GRAY)
                    }
                    2->{
                        binding.indicator1.setTextColor(Color.GRAY)
                        binding.indicator2.setTextColor(Color.GRAY)
                        binding.indicator3.setTextColor(Color.BLACK)
                    }
                }

            }

        })

    }

    fun goToMainboard(){
        startActivity(Intent(this,MainActivity::class.java))
        finish()
        val editor = preference.edit()
        editor.putBoolean(pref_show_intro,false)
        editor.apply()
    }

    class myPagerAdapter(manager : FragmentManager) : FragmentPagerAdapter(manager){

        val list : MutableList<Fragment> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return list[position]
        }

        override fun getCount(): Int {
            return list.size
        }

    }

}