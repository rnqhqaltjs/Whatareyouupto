package com.project.whatareyouupto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.whatareyouupto.databinding.ActivityOssLicensesMenuBinding

class OssLicensesMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOssLicensesMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOssLicensesMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}