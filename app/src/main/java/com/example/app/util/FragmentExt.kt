package com.example.app.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Binds a value to View lifecycle. Value is created with the initialization
 * function at View's onCreate, and destroyed at onDestroy.
 */
fun <T> Fragment.viewLifecycle(create: () -> T): ReadOnlyProperty<Fragment, T> =

    object : ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {

        private var value: T? = null

        init {
            // Observe the view lifecycle of the Fragment
            this@viewLifecycle
                .viewLifecycleOwnerLiveData
                .observe(
                    this@viewLifecycle,
                    Observer { owner ->
                        viewLifecycleOwner.lifecycle.removeObserver(this)
                        owner.lifecycle.addObserver(this)
                    }
                )
        }

        override fun onCreate(owner: LifecycleOwner) {
            super.onCreate(owner)
            value = create()
        }

        override fun onDestroy(owner: LifecycleOwner) {
            super.onDestroy(owner)
            value = null
        }

        override fun getValue(
            thisRef: Fragment,
            property: KProperty<*>
        ): T {
            return value ?: create().also {
                value = it
            }
        }
    }
