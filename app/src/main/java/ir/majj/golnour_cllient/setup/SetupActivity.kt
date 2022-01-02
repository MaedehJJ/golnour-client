package ir.majj.golnour_cllient.setup

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import ir.majj.golnour_cllient.login.LoginActivity
import ir.majj.golnour_cllient.utils.intentFor

class SetupActivity : AppCompatActivity() {
    companion object {
        fun getOpenIntent(context: Context) = context.intentFor<LoginActivity>()
    }
}
