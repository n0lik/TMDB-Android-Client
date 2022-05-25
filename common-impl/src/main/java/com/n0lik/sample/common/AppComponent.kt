package com.n0lik.sample.common

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        RetrofitNetworkModule::class,
        KtorNetworkModule::class
    ]
)
interface AppComponent : AppDependency {

    fun inject(app: Application)

    @Component.Factory
    interface Factory {

        fun build(@BindsInstance app: Application): AppComponent
    }
}