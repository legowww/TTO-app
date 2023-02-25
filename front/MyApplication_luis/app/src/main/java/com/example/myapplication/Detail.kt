package com.example.myapplication

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dto.Transportation
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_detail.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.Calendar

class Detail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val intent = getIntent()
        val time = intent.getStringExtra("time")
        val totaltime = intent.getStringExtra("totaltime")
        val transportationList = intent.getSerializableExtra("transportationList") as ArrayList<Transportation>
        for (transportation in transportationList) {
            /*
            출력 예시:
            I/System.out: Transportation{time=5, transportationType='WALK', busNum='null', startName='null', endName='null'}
            I/System.out: Transportation{time=11, transportationType='BUS', busNum='99', startName='동춘역', endName='테크노파크역'}
             */
            println("$transportation")
        }
        val detailData : (TextView) = findViewById(R.id.detaildata)
        val hour : (String)
        val min : (String)
        val alarmBtn : (Button) = findViewById(R.id.alarm)

        detailData.text = time

        if (time != null) {
            hour = time.substring(0 until 2)
            min = time.substring(3 until 5)

            val calendar : Calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, hour.toInt())
            calendar.set(Calendar.MINUTE, min.toInt())
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            val mAlarmIntent = Intent(this, AlarmReceiver::class.java)
            val mPendingIntent = PendingIntent.getBroadcast(this, 0, mAlarmIntent, PendingIntent.FLAG_IMMUTABLE)
            val mAlarmManager : AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

            alarmBtn.setOnClickListener{
                mAlarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, mPendingIntent)
                Toast.makeText(this, "알람이 설정되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        val back = Intent(this,Routelist::class.java)
        val star = Intent(this,Favorites::class.java)
        val home = Intent(this, MainActivity::class.java)
        val reload = Intent(this, Detail::class.java)
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
                    1 -> startActivity(star)
                    2 -> startActivity(home)
                    3 -> startActivity(reload)
                    4 -> startActivity(account)
                }
            }
        })
    }
}