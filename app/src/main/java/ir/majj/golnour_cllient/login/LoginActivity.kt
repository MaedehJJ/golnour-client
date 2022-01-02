package ir.majj.golnour_cllient.login

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import ir.majj.golnour_cllient.utils.intentFor

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)


    }

    companion object {
        fun getOpenIntent(context: Context) = context.intentFor<LoginActivity>()
    }
}