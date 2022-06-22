package com.example.whatareyouupto

import android.app.AlertDialog
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.whatareyouupto.databinding.ActivityMainBinding
import com.example.whatareyouupto.sqlite.SqliteHelper
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle
    private val helper = SqliteHelper(this,"memo",null,1)
//    private val tabTitleArray = arrayOf("오늘 뭐해?", "캘린더")
    private val tabIconArray = arrayOf(R.drawable.listicon, R.drawable.calendaricon,R.drawable.donelist)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        toggle = ActionBarDrawerToggle(this,binding.drawer,
            R.string.drawer_opened,
            R.string.drawer_closed
        )
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toggle.syncState()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // 탭이 선택 되었을 때
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // 탭이 선택되지 않은 상태로 변경 되었을 때
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // 이미 선택된 탭이 다시 선택 되었을 때
            }
        })

        // 뷰페이저에 어댑터 연결
        binding.pager.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.pager) {tab, position ->
//            tab.text = tabTitleArray[position]
            tab.icon = getDrawable(tabIconArray[position])
        }.attach()

        //툴바
        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.item_deleteall -> {

                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("주의")
                    builder.setMessage("데이터를 초기화 하시겠습니까?")

                    builder.setNegativeButton("아니오") { dialog, which ->

                    }

                    builder.setPositiveButton("네") { dialog, which ->

                        helper.deleteAllMemo()

                        Toast.makeText(this,"데이터 초기화 완료", Toast.LENGTH_SHORT).show()
                        finish();//인텐트 종료
                        overridePendingTransition(0, 0);//인텐트 효과 없애기
                        val intent = intent; //인텐트
                        startActivity(intent); //액티비티 열기
                        overridePendingTransition(0, 0)

                    }

                    builder.show()

                }

                R.id.item_exit -> {

                    moveTaskToBack(true)
                    finishAndRemoveTask()
                    exitProcess(0)

                }

            }
            true
        }

    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){

            return true
        }

        return super.onOptionsItemSelected(item)
    }



}
