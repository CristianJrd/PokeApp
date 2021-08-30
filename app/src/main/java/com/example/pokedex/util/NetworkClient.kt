package com.example.pokedex.util

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


object NetworkClient {
    fun buildRetrofitClient(): Retrofit {
        val interceptor = TokenInterceptor()
        return Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttpClient(interceptor))
            .baseUrl(
                "https://pokeapi.co/api/v2/" +
                        ""
            )
            .build()
    }

    private fun buildOkHttpLoggerInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            //if (BuildConfig.DEBUG) {
            level = HttpLoggingInterceptor.Level.HEADERS
            level = HttpLoggingInterceptor.Level.BODY
            //}
        }
    }


    private fun okhttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
            addInterceptor(buildOkHttpLoggerInterceptor())
        }.build()
    }

}

class TokenInterceptor : Interceptor {
    private val token =
        "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwMDIzOWRjZGMwODc0NmI3MTI4MDAzOTZjYjI4ZWVmNyIsInN1YiI6IjYwZjZlNjAzYzQzOWMwMDA1YzI5NTIyMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.MLfWvoAVljf3yBXVsm7Zp5vbbZf1d6Kko0nxDQR1uS0"

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        //rewrite the request to add bearer token
        val newRequest: Request = chain.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(newRequest)
    }
}