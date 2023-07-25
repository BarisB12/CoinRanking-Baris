package com.example.coinranking_baris.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coinranking_baris.databinding.ActivityMainBinding
import com.example.coinranking_baris.sharedprefs.SharedPrefs

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        SharedPrefs.init(this)
    }
}