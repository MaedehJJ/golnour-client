package ir.majj.golnour_client.utils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BoundActivity<T : ViewBinding> : BoundView<T>, AppCompatActivity() {
    override var binding: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = initializeBinding()
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyBinding()
    }
}
