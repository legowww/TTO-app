package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import com.example.dto.Favorite
import com.example.dto.response.ServerResponse
import com.example.util.prefs.App
import com.example.util.retrofit.RetrofitBuilder
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        //local storage 에서 token 가져옮
        val token = App.prefs.token


        /*
            1. GET: 즐겨찾기 조회(https://github.com/legowww/time-to-out/issues/44#issuecomment-1409048102)
         */
        RetrofitBuilder.api.getFavorites(token).enqueue(object :
            Callback<ServerResponse<List<Favorite>>> {
            override fun onResponse(
                call: Call<ServerResponse<List<Favorite>>>,
                response: Response<ServerResponse<List<Favorite>>>
            ) {
                val body = response.body() ?: return
                val message = body.message
                if (message.equals("success")) {
                    val myEnrollFavoriteCount = body.result.size //내가 등록한 즐겨찾기 개수

                    //내가 등록한 즐겨찾기가 하나도 없는 경우
                    if (myEnrollFavoriteCount == 0) {
                        println("[info] 내가 등록한 즐겨찾기가 존재하지 않음")
                    }
                    else {
                        val favorites : List<Favorite> = body.result
                        println("내 즐겨찾기 개수=$myEnrollFavoriteCount")
                        //출력 확인
                        for (favorite in favorites) {
                            println("[info] ====== id=${favorite.id} name=${favorite.name}, lc=${favorite.lc} ======")
                        }
                    }
                }
            }
            override fun onFailure(call: Call<ServerResponse<List<Favorite>>>, t: Throwable) {
            }
        })

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