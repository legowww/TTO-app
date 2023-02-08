package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.dto.request.LoginRequest
import com.example.dto.response.ServerResponse
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

        loginBtn.setOnClickListener {
            RetrofitBuilder.api.login(LoginRequest(idText.text.toString().trim(), passwordText.text.toString().trim()))
                .enqueue(object : Callback<ServerResponse<String>> {
                override fun onResponse(
                    call: Call<ServerResponse<String>>,
                    response: Response<ServerResponse<String>>
                ) {
                    val body = response.body() ?: return
                    val message = body.message
                    if (message.equals("success")) {
                        /*로그인 성공 => 토큰 최신화*/
                        val receivedToken = body.result
                        App.prefs.token = "Bearer $receivedToken"

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
                override fun onFailure(call: Call<ServerResponse<String>>, t: Throwable) {
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