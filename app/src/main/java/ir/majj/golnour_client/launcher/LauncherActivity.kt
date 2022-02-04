package ir.majj.golnour_client.launcher

import android.os.Bundle
import android.os.CountDownTimer
import android.view.ViewGroup
import ir.majj.golnour_client.databinding.ActivityLauncherBinding
import ir.majj.golnour_client.login.LoginActivity
import ir.majj.golnour_client.preferences.Settings
import ir.majj.golnour_client.setup.SetupActivity
import ir.majj.golnour_client.utils.BoundActivity
import ir.majj.golnour_client.utils.startActivity

class LauncherActivity : BoundActivity<ActivityLauncherBinding>() {
    override fun inflateLayout(container: ViewGroup?) =
        ActivityLauncherBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LaunchTimer().start()
    }

    private fun navigate() {
        if (Settings.isSetupDone) {
            LoginActivity.getOpenIntent(this).startActivity(this)
        } else {
            SetupActivity.getOpenIntent(this).startActivity(this)
        }
        finish()
    }

    private inner class LaunchTimer : CountDownTimer(2000L, 2000L) {
        override fun onTick(millisUntilFinished: Long) {}

        override fun onFinish() {
            navigate()
        }
    }
}
