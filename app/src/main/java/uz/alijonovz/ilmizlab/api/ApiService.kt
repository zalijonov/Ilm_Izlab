package uz.alijonovz.ilmizlab.api

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.alijonovz.ilmizlab.App
import uz.alijonovz.ilmizlab.utils.Constants
import uz.alijonovz.ilmizlab.utils.Constants.KEY
import uz.alijonovz.ilmizlab.utils.Constants.KEY_VALUE
import uz.alijonovz.ilmizlab.utils.Constants.TOKEN
import uz.alijonovz.ilmizlab.utils.PrefUtils

object ApiService {
    var retrofit: Retrofit? = null

    fun apiClient(): Api {
        if (retrofit == null) {
            val client = OkHttpClient.Builder()
                .addInterceptor(
                    ChuckerInterceptor.Builder(App.app)
                        .collector(ChuckerCollector(App.app))
                        .maxContentLength(250000L)
                        .redactHeaders(emptySet())
                        .alwaysReadResponseBody(false)
                        .build()
                )
                .addInterceptor {
                    val request = it.request().newBuilder()
                        .addHeader(KEY, KEY_VALUE)
                        .addHeader(TOKEN, PrefUtils.getToken())
                        .build()
                    return@addInterceptor it.proceed(request)
                }
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!.create(Api::class.java)
    }
}