package com.example.app.inject

import com.example.app.data.pojo.Photo
import com.example.app.data.repository.ItemList
import com.example.app.data.store.StoreCore
import com.example.app.data.store.core.MemoryStoreCore
import com.example.app.features.main.album.AlbumViewModel
import com.example.app.features.main.album.store.AlbumRepository
import com.example.app.features.main.album.store.AlbumStore
import com.example.app.features.main.details.DetailsViewModel
import com.example.app.features.main.details.store.DetailsStore
import com.example.app.network.NetworkConfig
import com.example.app.network.PhotoApi
import com.example.app.network.result.ResultCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
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
        get<Retrofit>().create(PhotoApi::class.java)
    }

    single {
        Picasso.Builder(get())
            .downloader(OkHttp3Downloader(get<OkHttpClient>()))
            .build()
    }

    // Index core is used for storing lists of items
    single<StoreCore<String, ItemList<String, Int>>>(named("indexCore")) { MemoryStoreCore() }
    single<StoreCore<Int, Photo>> { MemoryStoreCore() }

    factory { AlbumStore(get(named("indexCore")), get()) }
    factory { AlbumRepository(get(), get()) }
    factory { DetailsStore(get()) }

    factory { }

    viewModel { AlbumViewModel(get()) }
    viewModel { (id: Int) -> DetailsViewModel(id, get()) }
}
