package com.example.mvidemo

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ProfilePresenter(private val webservice: Webservice) {

    private lateinit var view : ProfileView
    private val compositeDisposable = CompositeDisposable()


    fun bind(view: ProfileView){
        this.view = view
    }

    fun getUserProfile(userId: String) =
             webservice
            .getMyProfile(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnTerminate { view.render(ProfileState.FinishState) }
             .subscribe(object : Observer<User> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                    view.render(ProfileState.LoadingState)
                }

                override fun onNext(t: User) {
                    view.render(ProfileState.DataState(t))
                }

                override fun onError(e: Throwable) {
                    view.render(ProfileState.ErrorState(e.localizedMessage))
                }

            })

    fun unbind() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

}