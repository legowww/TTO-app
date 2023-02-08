package com.example.myapplication

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.dto.LocationCoordinate
import com.example.dto.TimeRoute
import com.example.dto.request.FavoriteLocationCoordinateRequest
import com.example.dto.response.ServerResponse
import com.example.util.prefs.App
import com.example.util.retrofit.RetrofitBuilder
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_first_page.*
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.round

class Routelist : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route_list)

        val intent = getIntent()
        val start = intent.getStringExtra("start").toString()
        val end = intent.getStringExtra("end").toString()

        val geocoder = Geocoder(this)
        val startpoint = geocoder.getFromLocationName(start, 1)
        val destination = geocoder.getFromLocationName(end, 1)

        // 출발지, 목적지 위도 경도값
        val lc = LocationCoordinate((round(startpoint[0].longitude * 1000000) / 1000000).toString(), (round(startpoint[0].latitude * 1000000) / 1000000).toString(), (round(destination[0].longitude * 1000000) / 1000000).toString(), (round(destination[0].latitude * 1000000) / 1000000).toString())

        println(round(startpoint[0].latitude * 1000000) / 1000000)
        println(round(startpoint[0].longitude * 1000000) / 1000000)
        println(round(destination[0].latitude * 1000000) / 1000000)
        println(round(destination[0].longitude * 1000000) / 1000000)

        //경로를 받을 리스트 생성
        var responseTimeRoutes =  ArrayList<TimeRoute>()
        val time = ArrayList<TextView>()
        val totaltime = ArrayList<TextView>()
        val layouts = ArrayList<LinearLayout>()
        val imageView : (ImageView) = findViewById(R.id.imageview)
        val loadingLayout : (LinearLayout) = findViewById(R.id.loading)

        Glide.with(this).load(R.raw.walk).into(imageView)

        for (i: Int in 1..5) {
            val timeId : (String) = "time" + i
            val totalId : (String) = "totaltime" + i
            val layoutId : (String) = "layout" + i
            val resId1 = resources.getIdentifier(timeId, "id", packageName)
            val resId2 = resources.getIdentifier(totalId, "id", packageName)
            val resId3 = resources.getIdentifier(layoutId, "id", packageName)
            val tmp1 : (TextView) = findViewById(resId1)
            val tmp2 : (TextView) = findViewById(resId2)
            val tmp3 : (LinearLayout) = findViewById(resId3)
            time.add(tmp1)
            totaltime.add(tmp2)
            layouts.add(tmp3)
        }

        val call = RetrofitBuilder.api.getTimeRoute(lc)
        call.enqueue(object : Callback<ServerResponse<List<TimeRoute>>> {
            override fun onResponse(
                call: Call<ServerResponse<List<TimeRoute>>>,
                response: Response<ServerResponse<List<TimeRoute>>>
            ) {
                val body = response.body() ?: return
                if (body.message.equals("success")) {
                    val timeRoutes : List<TimeRoute> = body.result
                    var count : (Int) = 0 // 받아온 데이터 개수 카운터
                    for (timeRoute in timeRoutes) {
                        //경로 추가(최대 5개 까지 담길 수 있음)
                        //println("${timeRoute.time}")
                        time[count].text = timeRoute.time
                        totaltime[count].text = timeRoute.route.totalTime.toString() + "분"
                        responseTimeRoutes.add(timeRoute)
                        count++
                    }
                    loadingLayout.visibility = View.GONE
                    for (i: Int in 0..4) {
                        layouts[i].visibility = View.VISIBLE
                    }
                }
                else {
                    //todo: 조회된 대중교통이 없다. 경고 메시지(버튼)
                }
            }
            override fun onFailure(call: Call<ServerResponse<List<TimeRoute>>>, t: Throwable) {
            }
        })


        /*
            2. POST: 즐겨찾기 등록(https://github.com/legowww/time-to-out/issues/44#issuecomment-1409071435)
         */
        //즐겨찾기 등록 버튼(예시 용도)
        val addButton = findViewById<Button>(R.id.tempFavoriteAddButton)

        //추가 버튼 클릭
        addButton.setOnClickListener {
            //local storage 에서 token 가져옮
            val token = App.prefs.token

            //todo: 팝업 창 등을 사용 하여 이름 입력 받기
            val name : String = "입력된 이름"

            //내가 등록할 즐겨찾기의 정보를 담은 객체(이름, 좌표...)
            val enrollRequest = FavoriteLocationCoordinateRequest.fromLocationCoordinate(name, lc);

            //POST 요청 전송
            RetrofitBuilder.api.addFavorite(token, enrollRequest).enqueue(object : Callback<ServerResponse<String>> {
                override fun onResponse(
                    call: Call<ServerResponse<String>>,
                    response: Response<ServerResponse<String>>
                ) {
                    val body = response.body() ?: return
                    val message = body.message
                    if (message.equals("success")) {
                        Toast.makeText(this@Routelist, "즐겨찾기 등록", Toast.LENGTH_LONG).show()
                    }
                    else {
                        Toast.makeText(this@Routelist, "즐겨찾기는 5개 이상 등록할 수 없습니다.", Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<ServerResponse<String>>, t: Throwable) {
                }
            })
        }

        //val back = Intent(this,MainActivity::class.java)
        val star = Intent(this,Favorites::class.java)
        val home = Intent(this, MainActivity::class.java)
        val reload = Intent(this, Routelist::class.java)
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
                    3 -> startActivity(reload)
                    4 -> startActivity(account)
                }
            }
        })
    }
}