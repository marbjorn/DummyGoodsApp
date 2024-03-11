package com.marbjorn.dummygoodsapp

import android.app.Application
import com.marbjorn.dummygoodsapp.data.GoodsRepository
import com.marbjorn.dummygoodsapp.data.GoodsRepositoryImpl
import com.marbjorn.dummygoodsapp.vm.GoodsInfoViewModel
import com.marbjorn.dummygoodsapp.vm.GoodsListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.dsl.bind
import org.koin.dsl.module

class DummyGoodsApp : Application() {

    private val appModule = module {
        single<GoodsRepository> { GoodsRepositoryImpl() } bind GoodsRepository::class
        viewModelOf(::GoodsInfoViewModel)
        viewModelOf(::GoodsListViewModel)
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            fragmentFactory()
            androidContext(this@DummyGoodsApp)
            modules(appModule)
        }
    }
}
