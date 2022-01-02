package ir.majj.golnour_cllient.launcher

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.majj.golnour_cllient.login.LoginActivity
import ir.majj.golnour_cllient.preferences.Settings
import ir.majj.golnour_cllient.setup.SetupActivity
import ir.majj.golnour_cllient.utils.startActivity

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Settings.isSetupDone) {
            LoginActivity.getOpenIntent(this).startActivity(this)
        } else {
            SetupActivity.getOpenIntent(this).startActivity(this)
        }
    }
}
