package ir.majj.golnour_client.login

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import ir.majj.golnour_client.R
import ir.majj.golnour_client.control.ControlActivity
import ir.majj.golnour_client.databinding.ActivityLoginBinding
import ir.majj.golnour_client.preferences.Settings
import ir.majj.golnour_client.utils.*

class LoginActivity : BoundActivity<ActivityLoginBinding>() {
    override fun inflateLayout(container: ViewGroup?) = ActivityLoginBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind {
            login.onClick { login() }
        }
    }

    private fun login() = bind {
        val pass = passcode.text.toString()
        if (pass != Settings.password) {
            passcodeContainer.error = string(R.string.login_wrongPasscode)
        } else {
            ControlActivity.getOpenIntent(this@LoginActivity).startActivity(this@LoginActivity)
        }
    }

    companion object {
        fun getOpenIntent(context: Context) = context.intentFor<LoginActivity>()
    }
}
