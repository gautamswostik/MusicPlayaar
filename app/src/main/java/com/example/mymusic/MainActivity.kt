package com.example.mymusic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        musicView.setOnClickListener {
            val intent = Intent(this , MusicActivity::class.java)
            Toast.makeText(this , "Songs" , Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }

    }
}
