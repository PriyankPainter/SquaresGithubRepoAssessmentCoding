package com.priyank.squaresgithubrepoassessmentcoding.retrofit

import com.priyank.squaresgithubrepoassessmentcoding.utility.AppConfig
import com.priyank.squaresgithubrepoassessmentcoding.R
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient {

    companion object {

        fun getBaseURL(): String {
            return AppConfig.mContext!!.getString(R.string.SERVER_URL)
        }

        fun getApiInterface(): ApiInterface {
            return retrofitClient.build().create(ApiInterface::class.java)
        }

        val retrofitClient: Retrofit.Builder by lazy {

            //val levelType: Level
            val levelType: Level = Level.BODY

            val logging = HttpLoggingInterceptor()
            logging.setLevel(levelType)

            val okhttpClient = OkHttpClient.Builder()
            okhttpClient.addInterceptor(logging)

            val gson = GsonBuilder()
                .setLenient()
                .create()

            Retrofit.Builder()
                .baseUrl(getBaseURL())
                .client(okhttpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
        }
    }
}