package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.domain.RetrofitBuilder
import com.example.myapplication.domain.LocationCoordinate

//import com.example.domain.*
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, route_list::class.java)
        val eText1 : EditText = findViewById(R.id.editText)
        val eText2 : EditText = findViewById(R.id.editText2)
        eText2.setOnKeyListener { view, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER) {
//                intent.putExtra("start", eText1.text.toString())
//                intent.putExtra("end", eText2.text.toString())
                startActivity(intent)
            }
            true
        }
    }
}