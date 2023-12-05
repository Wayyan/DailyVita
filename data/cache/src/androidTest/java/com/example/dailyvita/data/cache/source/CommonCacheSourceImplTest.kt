package com.example.dailyvita.data.cache.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.dailyvita.data.common.repository.CommonCacheSource
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CommonCacheSourceImplTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var source: CommonCacheSource

    @Before
    fun setUp() {
        source = CommonCacheSourceImpl(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun getAllergies_dataGot() {
        runBlocking {
            val data = source.getAllergies()
            Truth.assertThat(data).isNotEmpty()
        }
    }

    @Test
    fun getDiets_dataGot() {
        runBlocking {
            val data = source.getAllDiets()
            Truth.assertThat(data).isNotEmpty()
        }
    }

    fun getHealthCon_dataGot() {
        runBlocking {
            val data = source.getAllHealthConcerns()
            Truth.assertThat(data).isNotEmpty()
        }
    }
}