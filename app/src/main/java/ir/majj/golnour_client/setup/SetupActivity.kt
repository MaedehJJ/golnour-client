package ir.majj.golnour_client.setup

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.majj.golnour_client.login.LoginActivity
import ir.majj.golnour_client.utils.intentFor

class SetupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        fun getOpenIntent(context: Context) = context.intentFor<LoginActivity>()
    }
}
