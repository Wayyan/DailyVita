package com.example.dailyvita.data.common.di

import com.example.dailyvita.data.common.repository.CommonRepositoryImpl
import com.example.dailyvita.domain.common.repository.CommonRepository
import org.koin.dsl.module

val DataModule = module {
    single<CommonRepository> {
        CommonRepositoryImpl(get())
    }
}