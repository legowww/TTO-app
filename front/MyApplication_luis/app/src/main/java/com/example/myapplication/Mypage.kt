package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.dto.Favorite
import com.example.dto.request.TokenRefreshRequest
import com.example.dto.response.MyPageResponse
import com.example.dto.response.ServerResponse
import com.example.dto.response.TokenRefreshResponse
import com.example.util.prefs.App
import com.example.util.retrofit.RetrofitBuilder
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Mypage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        //val back = Intent(this,MainActivity::class.java)
        val star = Intent(this,Favorites::class.java)
        val home = Intent(this, MainActivity::class.java)
        val account = Intent(this, Mypage::class.java)
        val logoutBtn = findViewById<Button>(R.id.logout)

        val accessToken = "Bearer ${App.prefs.access}"
        RetrofitBuilder.api.getMyPage(accessToken).enqueue(object : Callback<ServerResponse<MyPageResponse>> {
            override fun onResponse(
                call: Call<ServerResponse<MyPageResponse>>,
                response: Response<ServerResponse<MyPageResponse>>
            ) {
                val body = response.body() ?: return
                val message = body.message
                if (message.equals("success")) {
                    val myName = body.result.name
                    val myId = body.result.username
                    println("=======name=$myName")
                    println("=======id=$myId")
                }
                else {
                    RetrofitBuilder.api.refresh(TokenRefreshRequest(App.prefs.refresh)).enqueue(object : Callback<ServerResponse<TokenRefreshResponse>> {
                        override fun onResponse(
                            call: Call<ServerResponse<TokenRefreshResponse>>,
                            response: Response<ServerResponse<TokenRefreshResponse>>
                        ) {
                            val body = response.body() ?: return
                            val message = body.message
                            if (message.equals("success")) {
                                val newAccessToken = body.result.access
                                App.prefs.access = newAccessToken
                                val intent = Intent(this@Mypage, Mypage::class.java)
                                startActivity(intent)
                            }
                            else {
                                Toast.makeText(this@Mypage, "세션 종료. 로그인", Toast.LENGTH_LONG).show()
                                val intent = Intent(this@Mypage, Login::class.java)
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
            override fun onFailure(call: Call<ServerResponse<MyPageResponse>>, t: Throwable) {
            }
        })










        logoutBtn.setOnClickListener {
            RetrofitBuilder.api.logout(TokenRefreshRequest(App.prefs.refresh)).enqueue(object : Callback<ServerResponse<String>> {
                override fun onResponse(
                    call: Call<ServerResponse<String>>,
                    response: Response<ServerResponse<String>>
                ) {
                    val body = response.body() ?: return
                    App.prefs.access = ""
                    App.prefs.refresh = ""
                    Toast.makeText(this@Mypage, "로그아웃", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@Mypage, Login::class.java)
                    startActivity(intent)
                }
                override fun onFailure(
                    call: Call<ServerResponse<String>>, t: Throwable) {}
            })
        }

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
                    3 -> startActivity(account)
                    4 -> startActivity(account)
                }
            }
        })
    }
}