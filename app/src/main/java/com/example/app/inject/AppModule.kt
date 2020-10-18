package com.example.app.inject

import androidx.room.Room
import com.example.app.data.room.DATABASE_NAME
import com.example.app.data.room.Database
import com.example.app.data.store.StoreCore
import com.example.app.data.store.core.CachingStoreCore
import com.example.app.domain.idlist.IdList
import com.example.app.domain.idlist.store.IdListRoomCore
import com.example.app.domain.photo.Photo
import com.example.app.domain.photo.repository.PhotoListRepository
import com.example.app.domain.photo.store.PhotoListStore
import com.example.app.domain.photo.store.PhotoRoomCore
import com.example.app.domain.photo.store.PhotoStore
import com.example.app.feature.album.lazy.LazyAlbumViewModel
import com.example.app.feature.album.simple.AlbumViewModel
import com.example.app.feature.details.DetailsViewModel
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

    single {
        Room.databaseBuilder(get(), Database::class.java, DATABASE_NAME)
            .build()
    }

    // IdList core is used for storing lists of items
    single<StoreCore<String, IdList>>(named<IdList>()) {
        CachingStoreCore(IdListRoomCore(get()), IdList::key)
    }

    // Photo stores
    single<StoreCore<Int, Photo>>(named<Photo>()) {
        CachingStoreCore(PhotoRoomCore(get()), Photo::id, Photo.merger)
    }
    factory { PhotoStore(get(named<Photo>())) }

    // Photo list stores
    factory { PhotoListStore(get(named<IdList>()), get(named<Photo>())) }
    factory { PhotoListRepository(get(), get()) }

    viewModel { AlbumViewModel(get()) }
    viewModel { LazyAlbumViewModel(get(), get()) }
    viewModel { (id: Int) -> DetailsViewModel(id, get()) }
}
