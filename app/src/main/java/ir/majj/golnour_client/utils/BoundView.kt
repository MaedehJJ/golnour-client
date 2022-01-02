package ir.majj.golnour_client.utils

import android.util.Log
import android.view.ViewGroup
import androidx.viewbinding.BuildConfig
import androidx.viewbinding.ViewBinding

interface BoundView<T : ViewBinding> {
    var binding: T?

    fun inflateLayout(container: ViewGroup? = null): T

    fun initializeBinding(container: ViewGroup? = null): T {
        val binding = inflateLayout(container)
        this.binding = binding
        return binding
    }

    fun destroyBinding() {
        binding = null
    }

    fun bind(block: T.() -> Unit) {
        if (binding == null) {
            Log.e("BoundView", "Trying to access binding while the backing field is null")
            if (BuildConfig.DEBUG) {
                throw IllegalStateException(
                    "Trying to access binding while the backing field is null"
                )
            }
        }
        binding?.block()
    }

    fun <R> bindAndReturn(block: T.() -> R?): R? {
        if (binding == null) {
            Log.e("BoundView", "Trying to access binding while the backing field is null")
            if (BuildConfig.DEBUG) {
                throw IllegalStateException(
                    "Trying to access binding while the backing field is null"
                )
            }
        }
        return binding?.block()
    }
}
