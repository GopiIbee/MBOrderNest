package com.ibee.mbordernest

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ibee.mbordernest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        setContentView(view)

    }
}
