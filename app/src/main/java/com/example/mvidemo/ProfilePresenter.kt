package com.example.mvidemo

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ProfilePresenter {

    private lateinit var view : ProfileView
    private val compositeDisposable = CompositeDisposable()
    private val userId = "8a533d76f625c3f8103057f12fb4bace"

    fun bind(view: ProfileView){
        this.view = view
    }

    fun getUserProfile(userId: String) =
        WebServiceFactory.getInstance()
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