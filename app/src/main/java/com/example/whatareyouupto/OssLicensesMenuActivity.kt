package com.example.whatareyouupto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.whatareyouupto.databinding.ActivityMainBinding
import com.example.whatareyouupto.databinding.ActivityOssLicensesMenuBinding

class OssLicensesMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOssLicensesMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOssLicensesMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}