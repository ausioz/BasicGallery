package com.example.basicgallery.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.basicgallery.databinding.ActivityMainBinding
import com.example.basicgallery.di.ViewModelFactory
import com.example.basicgallery.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel =
            ViewModelProvider(this, ViewModelFactory(application))[MainViewModel::class.java]


        binding.layoutRV.layoutManager = GridLayoutManager(this, 3)
        binding.layoutRV.setHasFixedSize(true)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_MEDIA_IMAGES
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                101
            )
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.allImage.observe(this, Observer {
            binding.layoutRV.adapter = ImageAdapter(viewModel.getAllImage())
        })
    }

}