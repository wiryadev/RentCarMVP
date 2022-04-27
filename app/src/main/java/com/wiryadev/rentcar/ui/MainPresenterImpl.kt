package com.wiryadev.rentcar.ui

import com.wiryadev.rentcar.service.ApiClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainPresenterImpl(private val view: MainView) : MainPresenter {

    private val compositeDisposable = CompositeDisposable()

    override fun getList() {
        val disposable = ApiClient.instance.getAllCar()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    view.onDismissLoading()
                    if (response.isSuccessful) {
                        response.body()?.let { view.onSuccess(it) }
                    } else {
                        view.onFailed("Error from server")
                    }
                },
                { throwable ->
                    view.onDismissLoading()
                    throwable.message?.let { view.onFailed(it) }
                }
            )

        compositeDisposable.add(disposable)
    }

    override fun onUiDestroyed() {
        compositeDisposable.dispose()
    }
}