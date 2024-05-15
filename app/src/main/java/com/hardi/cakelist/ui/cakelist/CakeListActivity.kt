package com.hardi.cakelist.ui.cakelist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hardi.cakelist.databinding.ActivityCakeListBinding

class CakeListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCakeListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCakeListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}