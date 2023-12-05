package com.example.dailyvita.di

import com.example.dailyvita.feature.MainViewModel
import org.koin.dsl.module

val AppModule = module {
    single {
        MainViewModel(get(), get(), get())
    }
}