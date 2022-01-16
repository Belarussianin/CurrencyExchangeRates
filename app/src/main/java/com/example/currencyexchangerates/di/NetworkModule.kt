package com.example.currencyexchangerates.di

import com.example.currencyexchangerates.data.remote.ExchangeRatesAPI
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    single {
        OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    single<ExchangeRatesAPI> {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        Retrofit.Builder()
            .client(get())
            .baseUrl("https://www.nbrb.by/api/exrates/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ExchangeRatesAPI::class.java)
    }
}