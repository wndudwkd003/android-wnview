package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zyn.wnview.views.ImageSlider

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageSlider = findViewById<ImageSlider>(R.id.imageSlider)
        val items = listOf(
            R.drawable.ic_launcher_background,
            R.drawable.ic_android_black_24dp,
            R.drawable.ic_launcher_foreground
        )

        imageSlider.imageItemList = items
        imageSlider.invalidate()

    }
}