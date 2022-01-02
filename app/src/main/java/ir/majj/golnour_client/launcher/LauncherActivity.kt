package ir.majj.golnour_client.launcher

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.majj.golnour_client.login.LoginActivity
import ir.majj.golnour_client.preferences.Settings
import ir.majj.golnour_client.setup.SetupActivity
import ir.majj.golnour_client.utils.startActivity

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
