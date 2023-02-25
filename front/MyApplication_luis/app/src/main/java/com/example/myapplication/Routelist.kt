package com.example.myapplication

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.bumptech.glide.Glide
import com.example.dto.LocationCoordinate
import com.example.dto.TimeRoute
import com.example.dto.Transportation
import com.example.dto.request.FavoriteLocationCoordinateRequest
import com.example.dto.request.TokenRefreshRequest
import com.example.dto.response.ServerResponse
import com.example.dto.response.TokenRefreshResponse
import com.example.util.prefs.App
import com.example.util.retrofit.RetrofitBuilder
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_first_page.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Serializable
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import kotlin.math.round

class Routelist : AppCompatActivity() {

    var pointx: ArrayList<String> = arrayListOf("", "")
    var pointy: ArrayList<String> = arrayListOf("", "")
    var locationStr = ArrayList<String>()
    lateinit var lc: LocationCoordinate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route_list)

        val intent = getIntent()
        val identifier = intent.getStringExtra("identify")

        if (identifier == "1") {
            val start = intent.getStringExtra("start").toString()
            val end = intent.getStringExtra("end").toString()

            locationStr = arrayListOf(start, end)

            val thread = NetworkThread()
            thread.start()
            thread.join()

            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            lc = LocationCoordinate(pointx[0], pointy[0], pointx[1], pointy[1])
        } else {
            val sx = intent.getStringExtra("sx").toString()
            val sy = intent.getStringExtra("sy").toString()
            val ex = intent.getStringExtra("ex").toString()
            val ey = intent.getStringExtra("ey").toString()

            lc = LocationCoordinate(sx, sy, ex, ey)
        }
        val addButton = findViewById<Button>(R.id.tempFavoriteAddButton)

        //경로를 받을 리스트 생성
        //var responseTimeRoutes =  ArrayList<TimeRoute>()
        val time = ArrayList<TextView>()
        val totaltime = ArrayList<TextView>()
        val layouts = ArrayList<LinearLayout>()
        val imageView : (ImageView) = findViewById(R.id.imageview)
        val loadingLayout : (LinearLayout) = findViewById(R.id.loading)

        Glide.with(this).load(R.raw.bus).into(imageView)

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
                        layouts[count].setOnClickListener {
                            val intent = Intent(this@Routelist, Detail::class.java)
                            intent.putExtra("time", timeRoute.time)
                            intent.putExtra("totaltime", timeRoute.route.totalTime)
                            intent.putExtra("transportationList", timeRoute.route.transportationArrayList)
                            startActivity(intent)
                        }
                        //responseTimeRoutes.add(timeRoute)
                        count++
                    }
                    loadingLayout.visibility = View.GONE
                    for (i: Int in 0..4) {
                        layouts[i].visibility = View.VISIBLE
                    }
                }
                else {
                    //todo : 현재 탈 수 있는 교통 수단 X
                    println(body.message)
                }
            }
            override fun onFailure(call: Call<ServerResponse<List<TimeRoute>>>, t: Throwable) {
            }
        })

        //즐겨찾기 클릭
        addButton.setOnClickListener {
            //todo: 팝업 창 등을 사용 하여 이름 입력 받기
            val name : String = "입력된 이름"

            //내가 등록할 즐겨찾기의 정보를 담은 객체(이름, 좌표...)
            val enrollRequest = FavoriteLocationCoordinateRequest.fromLocationCoordinate(name, lc);

            //local storage 에서 token 가져옮
            val accessToken = "Bearer ${App.prefs.access}"

            //POST 요청 전송
            RetrofitBuilder.api.addFavorite(accessToken, enrollRequest).enqueue(object : Callback<ServerResponse<String>> {
                override fun onResponse(
                    call: Call<ServerResponse<String>>,
                    response: Response<ServerResponse<String>>
                ) {
                    val body = response.body() ?: return
                    val message = body.message
                    if (message.equals("success")) {
                        Toast.makeText(this@Routelist, "즐겨찾기 등록", Toast.LENGTH_LONG).show()
                    }
                    else if (message.equals("You cannot create more than 5 favorites.")){
                        Toast.makeText(this@Routelist, "즐겨찾기는 5개 이상 등록할 수 없습니다.", Toast.LENGTH_LONG).show()
                    }
                    else{
                        RetrofitBuilder.api.refresh(TokenRefreshRequest(App.prefs.refresh)).enqueue(object : Callback<ServerResponse<TokenRefreshResponse>> {
                            override fun onResponse(
                                call: Call<ServerResponse<TokenRefreshResponse>>,
                                response: Response<ServerResponse<TokenRefreshResponse>>
                            ) {
                                val body = response.body() ?: return
                                val message = body.message
                                if (message.equals("success")) {
                                    //[성공] -> 현재 화면 다시 요청
                                    val newAccessToken = body.result.access
                                    App.prefs.access = newAccessToken
                                }
                                else {
                                    //[실급]실패 -> 로그인 화면
                                    Toast.makeText(this@Routelist, "세션 종료. 로그인", Toast.LENGTH_LONG).show()
                                    val intent = Intent(this@Routelist, Login::class.java)
                                    startActivity(intent)
                                }
                            }
                            override fun onFailure(
                                call: Call<ServerResponse<TokenRefreshResponse>>,
                                t: Throwable
                            ) {
                            }
                        })
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
    inner class NetworkThread: Thread(){
        override fun run() {
            for (i:Int in 0 .. 1) {
                val bufferedReader: BufferedReader
                val stringBuilder = StringBuilder()
                val query =
                    "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + URLEncoder.encode(
                        locationStr[i],
                        "UTF-8"
                    )
                val url = URL(query)
                val conn = url.openConnection() as HttpURLConnection
                if (conn != null) {
                    conn.connectTimeout = 5000
                    conn.readTimeout = 5000
                    conn.requestMethod = "GET"
                    conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", "svt7kearbg")
                    conn.setRequestProperty("X-NCP-APIGW-API-KEY", "Ao8DvzFZWokU3WaGWsYsIcjcVdgNoJJ8iAJxx5fY")
                    conn.doInput = true

                    val responseCode = conn.responseCode

                    bufferedReader = if (responseCode == 200) {
                        BufferedReader(InputStreamReader(conn.inputStream))
                    } else {
                        BufferedReader(InputStreamReader(conn.errorStream))
                    }

                    var line: String? = null
                    while (bufferedReader.readLine().also { line = it } != null) {
                        stringBuilder.append("""$line""".trimIndent())
                    }

                    var indexFirst: Int
                    var indexLast: Int

                    indexFirst = stringBuilder.indexOf("\"x\":\"")
                    indexLast = stringBuilder.indexOf("\",\"y\":")
                    val x = stringBuilder.substring(indexFirst + 5, indexLast)

                    indexFirst = stringBuilder.indexOf("\"y\":\"")
                    indexLast = stringBuilder.indexOf("\",\"distance\":")
                    val y = stringBuilder.substring(indexFirst + 5, indexLast)

                    pointx[i] = x
                    pointy[i] = y

                    bufferedReader.close()
                    conn.disconnect()
                }
            }

        }
    }
}
