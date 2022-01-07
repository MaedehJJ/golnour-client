package ir.majj.golnour_client.launcher

import android.app.Activity
import android.content.Context
import android.os.Bundle
import ir.majj.golnour_client.login.LoginActivity
import ir.majj.golnour_client.preferences.Settings
import ir.majj.golnour_client.setup.SetupActivity
import ir.majj.golnour_client.utils.intentFor
import ir.majj.golnour_client.utils.startActivity

class LauncherActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Settings.isSetupDone) {
            LoginActivity.getOpenIntent(this).startActivity(this)
        } else {
            SetupActivity.getOpenIntent(this).startActivity(this)
        }
    }

    companion object {
        fun getOpenIntent(context: Context) = context.intentFor<LauncherActivity>()
    }
}
