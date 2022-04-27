package com.wiryadev.rentcar.ui

import com.wiryadev.rentcar.model.GetAllCarResponseItem

interface MainView {

    fun onLoading()
    fun onDismissLoading()
    fun onSuccess(response: List<GetAllCarResponseItem>)
    fun onFailed(message: String)

}