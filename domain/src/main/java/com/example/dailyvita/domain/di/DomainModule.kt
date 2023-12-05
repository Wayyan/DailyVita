package com.example.dailyvita.domain.di

import com.example.dailyvita.domain.common.useCase.GetAllAllergies
import com.example.dailyvita.domain.common.useCase.GetAllDiets
import com.example.dailyvita.domain.common.useCase.GetAllHealthConcerns
import org.koin.dsl.module

val DomainModule = module {
    single {
        GetAllAllergies(get(), get())
    }

    single {
        GetAllDiets(get(), get())
    }

    single {
        GetAllHealthConcerns(get(), get())
    }
}