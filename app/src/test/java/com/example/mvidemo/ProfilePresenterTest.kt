package com.example.mvidemo

import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.doReturn
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class ProfilePresenterTest {

    //https://medium.com/mindorks/small-things-when-unit-testing-rxjava-in-android-7f7c336ccabd

    @Mock
    lateinit var webservice: Webservice
    @Mock
    lateinit var profileView: ProfileView
    lateinit var presenter: ProfilePresenter
    lateinit var schedulerProvider: TrampolineSchedulerProvider

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        schedulerProvider = TrampolineSchedulerProvider()
        presenter = ProfilePresenter(schedulerProvider,webservice)
        presenter.bind(profileView)
    }


    @Test
    fun getUserProfile() {

        val user = User(1,"","TestUser")
        doReturn(Observable.just(user)).`when`(webservice).getMyProfile(anyString())
        presenter.getUserProfile(anyString())
        verify(profileView).render(ProfileState.DataState(user))

    }

    @After
    fun tearDown() {
        presenter.unbind()
    }

}