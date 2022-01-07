package ir.majj.golnour_client.setup

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import ir.majj.golnour_client.R
import ir.majj.golnour_client.databinding.ActivitySetupBinding
import ir.majj.golnour_client.launcher.LauncherActivity
import ir.majj.golnour_client.preferences.Settings
import ir.majj.golnour_client.utils.BoundActivity
import ir.majj.golnour_client.utils.intentFor
import ir.majj.golnour_client.utils.startActivity

class SetupActivity : BoundActivity<ActivitySetupBinding>() {
    override fun inflateLayout(container: ViewGroup?) = ActivitySetupBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind {
            if (Settings.phoneNumber.isNotEmpty()) {
                phone.setText(Settings.phoneNumber)
            }
            if (Settings.password.isNotEmpty()) {
                passcode.setText(Settings.password)
            }

            save.setOnClickListener { saveSetup() }
        }
    }

    private fun saveSetup() = bind {
        if (phone.text.isNullOrEmpty() || passcode.text.isNullOrEmpty()) {
            Toast.makeText(this@SetupActivity, R.string.setup_error, Toast.LENGTH_SHORT).show()
        } else {
            Settings.password = passcode.text.toString()
            Settings.phoneNumber = phone.text.toString()
            Settings.isSetupDone = true

            LauncherActivity.getOpenIntent(this@SetupActivity).startActivity(this@SetupActivity)
            finish()
        }
    }

    companion object {
        fun getOpenIntent(context: Context) = context.intentFor<SetupActivity>()
    }
}
