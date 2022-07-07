package com.n0lik.sample

import android.app.Application
import com.n0lik.sample.common.AppComponent
import com.n0lik.sample.common.DaggerAppComponent
import com.n0lik.sample.di.DependencyActivityInjectorLifecycleCallBack

class SampleApp : Application() {

    private val appDependency: AppComponent by lazy {
        DaggerAppComponent.factory()
            .build(this.applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(DependencyActivityInjectorLifecycleCallBack(appDependency))
        appDependency.inject(this@SampleApp)
    }
}