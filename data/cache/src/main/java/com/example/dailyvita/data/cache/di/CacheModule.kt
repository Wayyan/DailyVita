package com.example.dailyvita.data.cache.di

import com.example.dailyvita.data.cache.source.CommonCacheSourceImpl
import com.example.dailyvita.data.common.repository.CommonCacheSource
import org.koin.dsl.module

val CacheModule = module {
    single<CommonCacheSource> {
        CommonCacheSourceImpl(get())
    }
}