package com.test.assigntest

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("posts")
    fun getdata(): Call<List<DataModel>>

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }
}
