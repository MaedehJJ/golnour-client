package ir.majj.golnour_client.login

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import ir.majj.golnour_client.databinding.ActivityLoginBinding
import ir.majj.golnour_client.utils.BoundActivity
import ir.majj.golnour_client.utils.intentFor
import ir.majj.golnour_client.utils.onClick

class LoginActivity : BoundActivity<ActivityLoginBinding>() {
    override fun inflateLayout(container: ViewGroup?) = ActivityLoginBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind {
            login.onClick {  }
        }
    }

    companion object {
        fun getOpenIntent(context: Context) = context.intentFor<LoginActivity>()
    }
}