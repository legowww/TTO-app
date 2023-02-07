package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.dto.request.JoinRequest
import com.example.dto.response.ServerResponse
import com.example.util.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class Signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val joinUsername = findViewById<EditText>(R.id.joinUsername)
        val joinId = findViewById<EditText>(R.id.joinId)
        val joinPassword = findViewById<EditText>(R.id.joinPassword)
        val passwordConfirm = findViewById<EditText>(R.id.password_confirm)
        val joinBtn = findViewById<Button>(R.id.joinButton)
        val loginPageBtn = findViewById<Button>(R.id.loginPageButton)

        loginPageBtn.setOnClickListener {
            val intent = Intent(this@Signup, Login::class.java)
            startActivity(intent)
        }
        joinBtn.setOnClickListener{
            //비밀번호 재확인
            if (joinPassword.text.toString().trim() != passwordConfirm.text.toString().trim()) {
                Toast.makeText(this@Signup, "비밀번호 재확인", Toast.LENGTH_LONG).show()
                joinPassword.text.clear()
                passwordConfirm.text.clear()
            }
            //정상적 입력 여부 확인
            else {
                val joinRequest = JoinRequest(joinUsername.text.toString().trim(), joinId.text.toString().trim(), joinPassword.text.toString().trim())
                RetrofitBuilder.api.join(joinRequest).enqueue(object : Callback<ServerResponse<String>> {
                    override fun onResponse(
                        call: Call<ServerResponse<String>>,
                        response: Response<ServerResponse<String>>
                    ) {
                        val body = response.body() ?: return
                        val message = body.message
                        if (message.equals("success")) {
                            Toast.makeText(this@Signup, "회원가입에 성공하였습니다.", Toast.LENGTH_LONG).show()
                            val intent = Intent(this@Signup, Login::class.java)
                            startActivity(intent)
                        }
                        else {
                            var errorMessage = "다시 입력하십시오."

                            if (message.equals("This id already exists")) {
                                errorMessage = "이미 존재하는 아이디입니다."
                                joinId.text.clear()
                            }
                            else if (message.equals("Please enter a valid name")) {
                                errorMessage = "유효한 이름을 입력하십시오."
                                joinUsername.text.clear()
                            }
                            else if (message.equals("Please enter a valid id")) {
                                errorMessage = "유효한 아이디를 입력하십시오."
                                joinId.text.clear()
                            }
                            else if (message.equals("Please enter a valid password")) {
                                errorMessage = "유효한 비밀번호를 입력하십시오."
                                joinPassword.text.clear()
                            }
                            Toast.makeText(this@Signup, errorMessage, Toast.LENGTH_LONG).show()
                        }
                    }
                    override fun onFailure(call: Call<ServerResponse<String>>, t: Throwable) {
                    }
                })
            }
        }
    }
}
