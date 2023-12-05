package com.example.dailyvita.base.di

import com.example.dailyvita.base.DefaultDispatcherProvider
import com.example.dailyvita.domain.DispatcherProvider
import org.koin.dsl.module

val BaseAppModule = module {
    single<DispatcherProvider> {
        DefaultDispatcherProvider()
    }
}