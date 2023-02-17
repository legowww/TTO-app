package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.dto.request.LoginRequest
import com.example.dto.request.TokenRefreshRequest
import com.example.dto.response.LoginSuccessTokenResponse
import com.example.dto.response.ServerResponse
import com.example.dto.response.TokenRefreshResponse
import com.example.util.prefs.App
import com.example.util.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val idText = findViewById<EditText>(R.id.loginId)
        val passwordText = findViewById<EditText>(R.id.loginPassword)
        val loginBtn = findViewById<Button>(R.id.loginButton)
        //자동 로그인(액세스 토큰 유효할 경우)
        if (App.prefs.access != null && !App.prefs.access.equals("")) {
            val accessToken = "Bearer ${App.prefs.access}"
            RetrofitBuilder.api.autoLogin(accessToken).enqueue(object : Callback<ServerResponse<String>> {
                override fun onResponse(
                    call: Call<ServerResponse<String>>,
                    response: Response<ServerResponse<String>>
                ) {
                    val body = response.body() ?: return
                    val message = body.message
                    if (message.equals("success")) {
                        Toast.makeText(this@Login, "로그인", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@Login, MainActivity::class.java)
                        startActivity(intent)
                    }
                    else {
                        println("$message + ${App.prefs.access}")
                    }
                }
                override fun onFailure(call: Call<ServerResponse<String>>, t: Throwable) {
                }
            })
        }

        loginBtn.setOnClickListener {
            RetrofitBuilder.api.login(LoginRequest(idText.text.toString().trim(), passwordText.text.toString().trim()))
                .enqueue(object : Callback<ServerResponse<LoginSuccessTokenResponse>> {
                override fun onResponse(
                    call: Call<ServerResponse<LoginSuccessTokenResponse>>,
                    response: Response<ServerResponse<LoginSuccessTokenResponse>>
                ) {
                    val body = response.body() ?: return
                    val message = body.message
                    if (message.equals("success")) {
                        val tokens = body.result
                        App.prefs.access = tokens.access
                        App.prefs.refresh = tokens.refresh

                        Toast.makeText(this@Login, "로그인", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@Login, MainActivity::class.java)
                        startActivity(intent)
                    }
                    else {
                        var errorMessage = "다시 입력하십시오."
                        if (message.equals("unfounded username")) {
                            errorMessage = "존재하지 않는 아이디입니다."
                        }
                        else if (message.equals("wrong password")) {
                            errorMessage = "잘못된 비밀번호입니다."
                        }
                        Toast.makeText(this@Login, errorMessage, Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<ServerResponse<LoginSuccessTokenResponse>>, t: Throwable) {
                }
            })
        }

        val signup : Button = findViewById(R.id.signupButton)
        signup.setOnClickListener {
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }
    }
}