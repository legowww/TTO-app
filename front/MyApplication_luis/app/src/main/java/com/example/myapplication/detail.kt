package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class detail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val back : Button = findViewById(R.id.back)

        back.setOnClickListener {
            val intent = Intent(this, route_list::class.java)
            startActivity(intent)
        }

        val reload : Button = findViewById(R.id.reload)

        reload.setOnClickListener {
            val intent = Intent(this, detail::class.java)
            startActivity(intent)
        }
    }
}