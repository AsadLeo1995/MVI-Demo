package com.example.mvidemo

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class ProfilePresenter(private val schedulerProvider: BaseSchedulerProvider, private val webservice: Webservice) {


    private lateinit var view : ProfileView
    private val compositeDisposable = CompositeDisposable()


    fun bind(view: ProfileView){
        this.view = view
    }

    fun getUserProfile(userId: String) =
        webservice
            .getMyProfile(userId)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .doOnSubscribe { d ->
                compositeDisposable.add(d)
                view.render(ProfileState.LoadingState)
            }
            .doOnTerminate { view.render(ProfileState.FinishState) }
            .subscribeBy(
                onNext = { user -> view.render(ProfileState.DataState(user)) },
                onError = { e -> view.render(ProfileState.ErrorState(e.localizedMessage)) }
            )

    fun unbind() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

}