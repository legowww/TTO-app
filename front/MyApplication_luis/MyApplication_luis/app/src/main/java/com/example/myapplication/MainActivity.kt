package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val eText1 = findViewById<EditText>(R.id.startEditText)
        val eText2 = findViewById<EditText>(R.id.endEditText)
        eText2.setOnKeyListener { view, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER) {
                val subIntent = Intent(this@MainActivity, Routelist::class.java)
                subIntent.putExtra("start", eText1.text.toString())
                subIntent.putExtra("end", eText2.text.toString())
                startActivity(subIntent)
            }
            true
        }

        val back = Intent(this,Login::class.java)
        //val star = Intent(this,Login::class.java)
        val home = Intent(this, MainActivity::class.java)
        val account = Intent(this, Mypage::class.java)

        val tabs : (TabLayout) = findViewById(R.id.tabs)
        tabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> startActivity(back)
                    2 -> startActivity(home)
                    3 -> startActivity(home)
                    4 -> startActivity(account)
                }
            }
        })

    }
}
