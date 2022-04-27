package com.wiryadev.rentcar.service

import com.wiryadev.rentcar.model.GetAllCarResponseItem
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("admin/car")
    fun getAllCar(): Observable<Response<List<GetAllCarResponseItem>>>

}