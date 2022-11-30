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

        val times = listOf<TextView>(time1, time2, time3)
        val starts = listOf<TextView>(start1, start2, start3)
        val ends = listOf<TextView>(end1, end2, end3)
        var messages = mutableListOf<String>(String(), String(), String())


        val call = RetrofitBuilder.api.getTimeRoute(
            LocationCoordinate("126.6486573", "37.3908814", "126.63652", "37.37499041")
        )

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
                if (timeRoutes != null) {
                    for(i in 0 until 3) {
                        val timeRoute = timeRoutes[i]
                        val start = "송도더샵퍼스트월드"
                        val end = timeRoute.route.lastEndStation
                        times[i].text = timeRoute.time
                        starts[i].text = start
                        ends[i].text = end

                        //detail 임시 출력물
                        var str = StringBuilder()
                        str.append("나갈시간:${timeRoute.time}\n총 소요시간:${timeRoute.route.totalTime}분\n\n")
                        str.append("출발지: $start\n목적지: $end\n\n")
                        val tr = timeRoute.route.transportationList
                        tr.forEachIndexed { i, t ->
                            val trafficType = t.trafficType
                            if (trafficType.equals("WALK")) {
                                str.append("[도보] ${t.time}분\n\n")
                            }
                            else if (trafficType.equals("BUS")) {
                                str.append("[버스] ${t.time}분 (${t.busNum}번 ${t.startName} -> ${t.endName})\n\n")
                            }
                        }
                        str.append("\n")
                        messages[i] = str.toString()
                    }
                }
            }

            override fun onFailure(call: Call<List<TimeRoute>>, t: Throwable) {
                fail.text = t.message
            }
        })

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
            intent.putExtra("info", messages[0])
            startActivity(intent)
        }
        route2.setOnClickListener {
            val intent = Intent(this, detail::class.java)
            intent.putExtra("info", messages[1])
            startActivity(intent)
        }
        route3.setOnClickListener {
            val intent = Intent(this, detail::class.java)
            intent.putExtra("info", messages[2])
            startActivity(intent)
        }

    }
}