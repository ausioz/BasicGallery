package com.example.basicgallery.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.basicgallery.databinding.ActivityImageDetailBinding

class ImageDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imagePath = intent.getStringExtra("path")
        val imageName = intent.getStringExtra("name")

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = imageName

        Glide.with(this)
            .load(imagePath)
            .into(binding.imageView)
    }
}