package uz.alijonovz.ilmizlab.screen.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.alijonovz.ilmizlab.api.ApiService
import uz.alijonovz.ilmizlab.databinding.ActivityLoginBinding
import uz.alijonovz.ilmizlab.model.BaseResponse
import uz.alijonovz.ilmizlab.model.login.*
import uz.alijonovz.ilmizlab.screen.main.MainActivity
import uz.alijonovz.ilmizlab.utils.PrefUtils

enum class LoginState {
    CHECK_PHONE,
    LOGIN,
    REGISTRATION,
    CONFIRM
}

class LoginActivity : AppCompatActivity() {
    var state = LoginState.CHECK_PHONE
    lateinit var binding: ActivityLoginBinding
    var phone: String = ""
    var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ed1.setOnKeyListener(GenericKeyEvent(binding.ed1, null))
        binding.ed2.setOnKeyListener(GenericKeyEvent(binding.ed2, binding.ed1))
        binding.ed3.setOnKeyListener(GenericKeyEvent(binding.ed3, binding.ed2))
        binding.ed4.setOnKeyListener(GenericKeyEvent(binding.ed4, binding.ed3))
        //-------------------------------------------------------
        binding.ed1.addTextChangedListener(GenericTextWatcher(binding.ed1, binding.ed2))
        binding.ed2.addTextChangedListener(GenericTextWatcher(binding.ed2, binding.ed3))
        binding.ed3.addTextChangedListener(GenericTextWatcher(binding.ed3, binding.ed4))
        binding.ed4.addTextChangedListener(GenericTextWatcher(binding.ed4, null))

        binding.btnSign.setOnClickListener {
            binding.lyProgress.visibility = View.VISIBLE
            binding.progress.progress = ProgressBar.VISIBLE
            when (state) {
                LoginState.CHECK_PHONE -> {
                    phone = binding.edPhone.text.toString().replace(" ", "").replace("+", "")
                    checkPhone(phone)
                    initViews()
                }

                LoginState.LOGIN -> {
                    phone = binding.edPhone.text.toString().replace(" ", "").replace("+", "")
                    password = binding.edPassword.text.toString()
                    login(phone, password)
                }

                LoginState.REGISTRATION -> {
                    phone = binding.edPhone.text.toString()
                    sendCode(phone)
                    val fullname = binding.edName.text.toString()
                    val password = binding.edPassword.text.toString()
                    val repassword = binding.edConfirmPassword.text.toString()
                    val edCode: String =
                        (binding.ed1.text.toString() + binding.ed2.text.toString() + binding.ed3.text.toString() + binding.ed4.text.toString())
                    val smsCode: String = edCode
                    if (fullname.isNullOrEmpty()) {
                        Toast.makeText(this, "Iltimos ismingizni kiriting", Toast.LENGTH_SHORT)
                            .show()
                        return@setOnClickListener
                    }
                    if (password.isNullOrEmpty()) {
                        Toast.makeText(this, "Iltimos parolni kiriting", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    if (password != repassword) {
                        Toast.makeText(this, "Qayta kiritilgan parol xato", Toast.LENGTH_SHORT)
                            .show()
                        return@setOnClickListener
                    }
                    if (smsCode.isNullOrEmpty()) {
                        Toast.makeText(this, "SMS kodni kiriting", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    registration(fullname, phone, smsCode)
                    initViews()
                }
                LoginState.CONFIRM -> {
                    phone = binding.edPhone.text.toString().replace(" ", "")
                    checkPhone(phone)
                    sendCode(phone)
                    val password = binding.edPassword.text.toString()
                    val edCode: String =
                        (binding.ed1.text.toString() + binding.ed2.text.toString() + binding.ed3.text.toString() + binding.ed4.text.toString())
                    confirmUser(phone, password, edCode)
                    initViews()
                }
            }
        }
        initViews()
    }

    private fun initViews() {
        binding.tvName.visibility = View.GONE
        binding.edName.visibility = View.GONE
        binding.tvRePass.visibility = View.GONE
        binding.edPassword.visibility = View.GONE
        binding.tvPass.visibility = View.GONE
        binding.edConfirmPassword.visibility = View.GONE
        binding.lySMSCode.visibility = View.GONE
        binding.tvSmsCode.visibility = View.GONE

        when (state) {
            LoginState.CHECK_PHONE -> {
                binding.btnSign.text = "KIRISH"
                binding.tvTitle.text = "Tizimga kirish"
            }

            LoginState.LOGIN -> {
                binding.btnSign.text = "KIRISH"
                binding.tvTitle.text = "Tizimga kirish"
                binding.edPassword.visibility = View.VISIBLE
                binding.tvPass.visibility = View.VISIBLE
            }

            LoginState.REGISTRATION -> {
                binding.tvTitle.text = "Tizimga kirish"
                binding.edName.visibility = View.VISIBLE
                binding.tvName.visibility = View.VISIBLE
                binding.edPassword.visibility = View.VISIBLE
                binding.tvPass.visibility = View.VISIBLE
                binding.edConfirmPassword.visibility = View.VISIBLE
                binding.tvRePass.visibility = View.VISIBLE
                binding.tvSmsCode.visibility = View.VISIBLE
                binding.lySMSCode.visibility = View.VISIBLE
                binding.btnSign.text = "Ro'yxatdan o'tish"
            }

            LoginState.CONFIRM -> {
                binding.tvTitle.text = "Tizimga kirish"
                binding.edPassword.visibility = View.VISIBLE
                binding.tvPass.visibility = View.VISIBLE
                binding.tvSmsCode.visibility = View.VISIBLE
                binding.lySMSCode.visibility = View.VISIBLE
                binding.btnSign.text = "KIRISH"
            }
        }
    }

    private fun checkPhone(phone: String) {
        ApiService.apiClient().checkPhone(CheckPhoneRequest(phone))
            .enqueue(object : Callback<BaseResponse<CheckResult>> {
                override fun onResponse(
                    call: Call<BaseResponse<CheckResult>>,
                    response: Response<BaseResponse<CheckResult>>
                ) {
                    binding.lyProgress.visibility = View.GONE
                    binding.progress.progress = ProgressBar.GONE
                    if(response.isSuccessful) {
                        if (response.body()!!.success) {
                            state = if (response.body()!!.data.isReg) {
                                LoginState.LOGIN
                            } else {
                                LoginState.REGISTRATION
                            }
                            initViews()
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                response.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }else{
                        Toast.makeText(this@LoginActivity, response.message(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<BaseResponse<CheckResult>>, t: Throwable) {
                    binding.lyProgress.visibility = View.GONE
                    binding.progress.progress = ProgressBar.GONE
                    Toast.makeText(this@LoginActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                        .show()
                }

            })
    }

    fun login(phone: String, password: String) {
        ApiService.apiClient().login(LoginModel(phone, password))
            .enqueue(object : Callback<BaseResponse<GetTokenModel>> {
                override fun onResponse(

                    call: Call<BaseResponse<GetTokenModel>>,
                    response: Response<BaseResponse<GetTokenModel>>
                ) {
                    binding.lyProgress.visibility = View.GONE
                    binding.progress.progress = ProgressBar.GONE
                    if (response.body()!!.success) {
                        PrefUtils.setToken(response.body()?.data?.token ?: "")
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<BaseResponse<GetTokenModel>>, t: Throwable) {
                    binding.lyProgress.visibility = View.GONE
                    binding.progress.progress = ProgressBar.GONE
                    Toast.makeText(this@LoginActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                        .show()
                }

            })
    }

    fun registration(fullname: String, phone: String, smsCode: String) {
        ApiService.apiClient().registration(RegistrationRequest(fullname, phone, smsCode))
            .enqueue(object : Callback<BaseResponse<GetTokenModel>> {
                override fun onResponse(
                    call: Call<BaseResponse<GetTokenModel>>,
                    response: Response<BaseResponse<GetTokenModel>>
                ) {
                    binding.lyProgress.visibility = View.GONE
                    binding.progress.progress = ProgressBar.GONE
                    if (response.body()!!.success) {
                        state = LoginState.CONFIRM
                        initViews()
                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse<GetTokenModel>>,
                    t: Throwable
                ) {
                    binding.lyProgress.visibility = View.GONE
                    binding.progress.progress = ProgressBar.GONE
                    Toast.makeText(this@LoginActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
                }

            })
    }

    fun sendCode(smsCode: String){
        ApiService.apiClient().sendConfirmCode(ConfirmRequest(smsCode)).enqueue(object: Callback<BaseResponse<CheckResult>>{
            override fun onResponse(
                call: Call<BaseResponse<CheckResult>>,
                response: Response<BaseResponse<CheckResult>>
            ) {
                binding.lyProgress.visibility = View.GONE
                binding.progress.progress = ProgressBar.GONE
                if(response.body()!!.success){
                    if(response.body()!!.data.isReg){
                        LoginState.LOGIN
                    } else{
                        LoginState.REGISTRATION
                    }
                    initViews()
                } else {
                    Toast.makeText(this@LoginActivity, response.body()!!.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<BaseResponse<CheckResult>>, t: Throwable) {
                binding.lyProgress.visibility = View.GONE
                binding.progress.progress = ProgressBar.GONE
                Toast.makeText(this@LoginActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun confirmUser(phone: String, password: String, sms_code: String){
        ApiService.apiClient().confirm(ConfirmUser(phone, password, sms_code)).enqueue(object: Callback<BaseResponse<GetTokenModel>>{
            override fun onResponse(
                call: Call<BaseResponse<GetTokenModel>>,
                response: Response<BaseResponse<GetTokenModel>>
            ) {
                binding.lyProgress.visibility = View.GONE
                binding.progress.progress = ProgressBar.GONE
                if(response.body()!!.success){
                    PrefUtils.setToken(response.body()?.data?.token ?: "")
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }
            }

            override fun onFailure(call: Call<BaseResponse<GetTokenModel>>, t: Throwable) {
                Toast.makeText(this@LoginActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }
}