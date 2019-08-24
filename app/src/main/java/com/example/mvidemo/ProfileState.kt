package com.example.mvidemo

sealed class ProfileState {
    object LoadingState : ProfileState()
    data class DataState(val data : User) : ProfileState()
    data class ErrorState(val data : String) : ProfileState()
    object FinishState : ProfileState()
}