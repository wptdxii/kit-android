package com.olaroc.core.binding

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <T : ViewBinding> viewBinding(bindingFactory: (View) -> T) =
    ViewHolderViewBinding(bindingFactory)

fun <T : ViewDataBinding> dataBinding() = ViewHolderDataBinding<T>()


class ViewHolderViewBinding<T : ViewBinding>(
    private val bindingFactory: (View) -> T
) : ReadOnlyProperty<RecyclerView.ViewHolder, T> {

    private lateinit var binding: T

    override fun getValue(thisRef: RecyclerView.ViewHolder, property: KProperty<*>): T {
        if (this::binding.isInitialized) {
            return binding
        }

        return bindingFactory(thisRef.itemView)
            .also { binding = it }
    }
}

class ViewHolderDataBinding<T : ViewDataBinding> : ReadOnlyProperty<RecyclerView.ViewHolder, T> {

    private lateinit var binding: T

    override fun getValue(thisRef: RecyclerView.ViewHolder, property: KProperty<*>): T {
        if (this::binding.isInitialized) {
            return binding
        }

        DataBindingUtil.bind<T>(thisRef.itemView)?.let {
            binding = it
        }
        return binding
    }

}
