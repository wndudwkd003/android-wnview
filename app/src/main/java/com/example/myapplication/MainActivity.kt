package com.example.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.zyn.wnview.views.ImageSlider

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageSlider = findViewById<ImageSlider>(R.id.imageSlider)
        val items = listOf(
            R.drawable.test_image_1,
            R.drawable.test_image_2,
            R.drawable.test_image_3,
            R.drawable.test_image_4
        )

        imageSlider.imageItemList = items
        imageSlider.setOnImageClickListener(object : ImageSlider.OnImageClickListener {
            override fun onImageClick(position: Int, context: Context) {
                Toast.makeText(context, position.toString() + "Click", Toast.LENGTH_SHORT).show()
            }
        })
        imageSlider.invalidate()

    }
}