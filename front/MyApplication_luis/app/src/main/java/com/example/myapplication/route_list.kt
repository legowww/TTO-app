package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.domain.RetrofitBuilder
import com.example.myapplication.domain.LocationCoordinate
import com.example.myapplication.domain.TimeRoute
import kotlinx.android.synthetic.main.activity_first_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class route_list : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route_list)

        val time1 : TextView = findViewById(R.id.time1)
        val time2 : TextView = findViewById(R.id.time2)
        val time3 : TextView = findViewById(R.id.time3)
        val start1 : TextView = findViewById(R.id.start1)
        val end1 : TextView = findViewById(R.id.end1)
        val start2 : TextView = findViewById(R.id.start2)
        val end2 : TextView = findViewById(R.id.end2)
        val start3 : TextView = findViewById(R.id.start3)
        val end3 : TextView = findViewById(R.id.end3)
        val fail : TextView = findViewById(R.id.fail)

        /**
         * 통신 예제
         */
        val call = RetrofitBuilder.api.getTimeRoute(
            LocationCoordinate("126.6709157", "37.4056074", "126.63652", "37.37499041")
        )
        var i = 0
        call.enqueue(object : Callback<List<TimeRoute>> {
            override fun onResponse(
                call: Call<List<TimeRoute>>,
                response: Response<List<TimeRoute>>
            ) {
                if (!response.isSuccessful) {
                    fail.text = "Code: " + response.code() + "???"
                    return
                }

                val timeRoutes = response.body()
                if (timeRoutes != null && i < 1) {
                    for (timeRoute in timeRoutes) {
                        val start = "동춘한양2차아파트"
                        val end = timeRoute.route.lastEndStation

                        time1.setText(timeRoute.time)
                        start1.setText(start)
                        end1.setText(end)
                        i += 1
                    }
                }
            }

            override fun onFailure(call: Call<List<TimeRoute>>, t: Throwable) {
                fail.text = t.message
            }
        })

//        call.enqueue(object : Callback<List<TimeRoute>> {
//            override fun onResponse(
//                call: Call<List<TimeRoute>>,
//                response: Response<List<TimeRoute>>
//            ) {
//                if (!response.isSuccessful) {
//                    println("===================================================!")
//                    return
//                }
//
//                val timeRoutes = response.body()
//                if (timeRoutes != null) {
//                    for (timeRoute in timeRoutes) {
//                        var start = "현재위치"
//                        var end = timeRoute.route.lastEndStation
//
//                        val time = timeRoute.time
//                        println("통신========================================================================== ${time}")
//                    }
//                }
//            }
//            override fun onFailure(call: Call<List<TimeRoute>>, t: Throwable) {
//                println("===================================================실패")
//            }
//        })





//        val intent = intent
//        val str1 = intent.getStringExtra("start")
//        val str2 = intent.getStringExtra("end")
//
//        start1.text = str1
//        end1.text = str2
//        start2.text = str1
//        end2.text = str2
//        start3.text = str1
//        end3.text = str2

        val back : Button = findViewById(R.id.back)

        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val reload : Button = findViewById(R.id.reload)

        reload.setOnClickListener {
            val intent = Intent(this, route_list::class.java)
            startActivity(intent)
        }

        val route1 : LinearLayout = findViewById(R.id.route1)
        val route2 : LinearLayout = findViewById(R.id.route2)
        val route3 : LinearLayout = findViewById(R.id.route3)

        route1.setOnClickListener {
            val intent = Intent(this, detail::class.java)
            startActivity(intent)
        }
        route2.setOnClickListener {
            val intent = Intent(this, detail::class.java)
            startActivity(intent)
        }
        route3.setOnClickListener {
            val intent = Intent(this, detail::class.java)
            startActivity(intent)
        }

    }
}