package com.example.pigadmin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pigadmin.databinding.ActivityMainBinding

class MainActivityLogin : AppCompatActivity() {


    private  lateinit var  binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
    }
}