package com.n0lik.sample.di

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.n0lik.sample.common.AppDependency
import com.n0lik.sample.common.di.Injectable

internal class DependencyActivityInjectorLifecycleCallBack(
    private val dependency: AppDependency
) : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        if (p0 is Injectable) {
            p0.inject(dependency)
        }
        if (p0 is FragmentActivity) {
            p0.supportFragmentManager.registerFragmentLifecycleCallbacks(
                dependencyFragmentInjectorLifecycleCallbacks(),
                true
            )
        }
    }

    override fun onActivityStarted(p0: Activity) = Unit

    override fun onActivityResumed(p0: Activity) = Unit

    override fun onActivityPaused(p0: Activity) = Unit

    override fun onActivityStopped(p0: Activity) = Unit

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) = Unit

    override fun onActivityDestroyed(p0: Activity) = Unit

    private fun dependencyFragmentInjectorLifecycleCallbacks(): FragmentManager.FragmentLifecycleCallbacks {
        return object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentPreAttached(
                fm: FragmentManager,
                fragment: Fragment,
                context: Context
            ) {
                if (fragment is Injectable) {
                    fragment.inject(dependency)
                }
            }
        }
    }
}