package com.norbertneudert.openmygarage.apiservice

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.norbertneudert.openmygarage.database.EntryLog
import com.norbertneudert.openmygarage.database.StoredPlate
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST


private const val BASE_URL = "https://openmygarageapi.azurewebsites.net/api/values/"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface OMGApiService {
    @GET("entrylogs")
    fun getEntryLogs() : Deferred<List<EntryLog>>

    @GET("storedplates")
    fun getStoredPlates() : Deferred<List<StoredPlate>>

    @POST("storedplates")
    fun postStoredPlate(@Body storedPlate: StoredPlate)

    @DELETE("storedplates")
    fun deleteStoredPlate(@Body storedPlate: StoredPlate)
}

object OMGApi {
    val retrofitService: OMGApiService by lazy { retrofit.create(OMGApiService::class.java) }
}