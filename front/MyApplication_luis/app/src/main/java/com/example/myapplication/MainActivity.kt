package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View
import android.widget.*
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.tabs.TabLayout
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL


class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private val LOCATION_PERMISSTION_REQUEST_CODE: Int = 1000
    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap
    private val marker = Marker()
    var startAddr = ArrayList<String>()
    var endAddr = ArrayList<String>()
    lateinit var start: CharSequence
    lateinit var end: CharSequence

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mapView = findViewById(R.id.fragment_container_view)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        locationSource = FusedLocationSource(this, LOCATION_PERMISSTION_REQUEST_CODE)
        val eText1 = findViewById<EditText>(R.id.start)
        val eText2 = findViewById<EditText>(R.id.end)
        val list1 = findViewById<ScrollView>(R.id.listview1)
        val list2 = findViewById<ScrollView>(R.id.listview2)
        val search1 = findViewById<AppCompatButton>(R.id.searchbtn1)
        val search2 = findViewById<AppCompatButton>(R.id.searchbtn2)
        val text1 = ArrayList<TextView>()
        val text2 = ArrayList<TextView>()
        val cancelAlarm : (Button) = findViewById(R.id.cancelAlarm)

        cancelAlarm.setOnClickListener{
            stopService(Intent(this, AlarmSoundService::class.java))
        }

        for (i: Int in 0..4) {
            startAddr.add("")
            endAddr.add("")
        }
        for (i: Int in 0 .. 4) {
            val textId : (String) = "text" + i
            val resId1 = resources.getIdentifier(textId, "id", packageName)
            val tmp : (TextView) = findViewById(resId1)
            text1.add(tmp)
        }
        for (i: Int in 5 .. 9) {
            val textId : (String) = "text" + i
            val resId = resources.getIdentifier(textId, "id", packageName)
            val tmp : (TextView) = findViewById(resId)
            text2.add(tmp)
        }

        // if edittext is focus visibility change? -> idea
        search1.setOnClickListener{
            for (i:Int in 0 .. 4) {
                text1[i].text = startAddr[i]
            }
            list1.visibility = View.VISIBLE
        }

        search2.setOnClickListener{
            for (i:Int in 0 .. 4) {
                text2[i].text = endAddr[i]
            }
            list2.visibility = View.VISIBLE
        }

        for (i: Int in 0 .. 4) {
            text1[i].setOnClickListener{
                start = text1[i].text
                list1.visibility = View.GONE
                eText1.setBackgroundResource(R.drawable.selected_input)
            }
        }
        for (i: Int in 0 .. 4) {
            text2[i].setOnClickListener{
                end = text2[i].text
                list2.visibility = View.GONE
                eText2.setBackgroundResource(R.drawable.selected_input)
            }
        }

        eText1.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun afterTextChanged(p0: Editable?) {
                val thread = NetworkThread1()
                thread.start()
                thread.join()
            }
        })

        eText2.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                val thread = NetworkThread2()
                thread.start()
                thread.join()
            }
        })

        eText2.setOnKeyListener { view, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER) {
                val subIntent = Intent(this@MainActivity, Routelist::class.java)
                subIntent.putExtra("identify", "1")
                subIntent.putExtra("start", start)
                subIntent.putExtra("end", end)
                startActivity(subIntent)
            }
            true
        }

        //val back = Intent(this,MainActivity::class.java)
        val star = Intent(this,Favorites::class.java)
        val home = Intent(this, MainActivity::class.java)
        val reload = Intent(this, MainActivity::class.java)
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

//        val tabs : (TabLayout) = findViewById(R.id.tabs)
//        tabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//            }
//
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                when (tab!!.position) {
//                    1 -> supportFragmentManager.beginTransaction().replace(R.id.viewPager, TwoFragment()).commit()
//                    2 -> supportFragmentManager.beginTransaction().replace(R.id.viewPager, OneFragment()).commit()
//                    4 -> supportFragmentManager.beginTransaction().replace(R.id.viewPager, ThreeFragment()).commit()
//                }
//            }
//        })

    }

    override fun onMapReady(@NonNull naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        marker.position = LatLng(37.6281, 127.0905)
        marker.map = naverMap
        marker.icon = MarkerIcons.BLACK
        marker.iconTintColor = Color.RED // 현재위치 마커 빨간색으로
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    inner class NetworkThread1: Thread(){
        override fun run() {
            val eText1 = findViewById<TextView>(R.id.start)
            val site = "https://business.juso.go.kr/addrlink/addrLinkApi.do?currentPage=1&countPerPage=5&keyword="+eText1.text+
                    "&confmKey=devU01TX0FVVEgyMDIzMDIxMDIwMzgzNDExMzUwMTU=&resultType=json"
            val url = URL(site)
            val conn = url.openConnection()
            val input = conn.getInputStream()
            val isr = InputStreamReader(input)
            val br = BufferedReader(isr)

            try {
                var str: String? = null
                val buf = StringBuffer()

                do{
                    str = br.readLine()

                    if(str!=null){
                        buf.append(str)
                    }
                }while (str!=null)

                val root = JSONObject(buf.toString())
                val tmp = root.getJSONObject("results").get("juso")
                if (eText1.length() <= 2 || tmp.equals(null)) {
                    return
                } else {
                    val response = root.getJSONObject("results").getJSONArray("juso")
                    for (i in 0 until response.length()) {
                        startAddr[i] = response.getJSONObject(i).get("jibunAddr").toString()
                    }
                }
            } catch (e: java.lang.NullPointerException) {
                return
            }
        }
    }
    inner class NetworkThread2: Thread(){
        override fun run() {
            val eText2 = findViewById<EditText>(R.id.end)
            val site = "https://business.juso.go.kr/addrlink/addrLinkApi.do?currentPage=1&countPerPage=5&keyword="+eText2.text+
                    "&confmKey=devU01TX0FVVEgyMDIzMDIxMDIwMzgzNDExMzUwMTU=&resultType=json"
            val url = URL(site)
            val conn = url.openConnection()
            val input = conn.getInputStream()
            val isr = InputStreamReader(input)
            val br = BufferedReader(isr)

            try {
                var str: String? = null
                val buf = StringBuffer()

                do{
                    str = br.readLine()

                    if(str!=null){
                        buf.append(str)
                    }
                }while (str!=null)

                val root = JSONObject(buf.toString())
                val tmp = root.getJSONObject("results").get("juso")
                if (eText2.length() <= 2 ||tmp.equals(null)) {
                    return
                } else {
                    val response = root.getJSONObject("results").getJSONArray("juso")
                    for (i in 0 until response.length()) {
                        endAddr[i] = response.getJSONObject(i).get("jibunAddr").toString()
                    }
                }
            } catch (e: java.lang.NullPointerException) {
                return
            }
        }
    }
}
