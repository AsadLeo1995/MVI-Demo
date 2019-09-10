package com.example.mvidemo

import org.koin.dsl.module



    val appModule = module {
        factory  {
            ProfilePresenter()
        }
    }
