package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.tabs.TabLayout

class Favorites : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        val texts = ArrayList<TextView>()
        val layouts = ArrayList<LinearLayout>()

        for (i: Int in 1..5) {
            val textId : (String) = "text" + i
            val layoutId : (String) = "layout" + i
            val resId1 = resources.getIdentifier(textId, "id", packageName)
            val resId2 = resources.getIdentifier(layoutId, "id", packageName)
            val tmp1 : (TextView) = findViewById(resId1)
            val tmp2 : (LinearLayout) = findViewById(resId2)
            texts.add(tmp1)
            layouts.add(tmp2)
        }

        //val back = Intent(this,Routelist::class.java)
        val star = Intent(this,Favorites::class.java)
        val home = Intent(this, MainActivity::class.java)
        //val reload = Intent(this, Detail::class.java)
        val account = Intent(this, Mypage::class.java)

        val tabs : (TabLayout) = findViewById(R.id.tabs)
        tabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> startActivity(home)
                    1 -> startActivity(star)
                    2 -> startActivity(home)
                    3 -> startActivity(star)
                    4 -> startActivity(account)
                }
            }
        })
    }
}