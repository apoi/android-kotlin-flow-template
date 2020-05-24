package com.example.app.inject

import com.example.app.data.pojo.ItemList
import com.example.app.data.pojo.Photo
import com.example.app.data.store.StoreCore
import com.example.app.data.store.core.MemoryStoreCore
import com.example.app.features.main.list.ListRepository
import com.example.app.features.main.list.ListStore
import com.example.app.features.main.list.ListViewModel
import com.example.app.network.NetworkConfig
import com.example.app.network.PhotoApi
import com.example.app.network.result.ResultCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val TEN_MEGABYTES: Long = 10 * 1024 * 1024

val appModule = module {

    single {
        OkHttpClient.Builder()
            .cache(Cache(androidContext().cacheDir, TEN_MEGABYTES))
            .build()
    }

    single {
        Moshi.Builder()
            .build()
    }

    single {
        Retrofit.Builder()
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .baseUrl(NetworkConfig.API_ENDPOINT)
            .client(get())
            .build()
    }

    single {
        get<Retrofit>()
            .create(PhotoApi::class.java)
    }

    single {
        Picasso.Builder(get())
            .downloader(OkHttp3Downloader(get<OkHttpClient>()))
            .build()
    }

    factory { ListStore() }
    factory { ListRepository(get(), get()) }

    factory { Dispatchers.IO }

    viewModel { ListViewModel(get()) }
}
