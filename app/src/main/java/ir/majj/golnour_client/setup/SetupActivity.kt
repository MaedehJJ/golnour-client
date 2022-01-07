package ir.majj.golnour_client.setup

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import ir.majj.golnour_client.R
import ir.majj.golnour_client.databinding.ActivitySetupBinding
import ir.majj.golnour_client.preferences.Settings
import ir.majj.golnour_client.utils.BoundActivity
import ir.majj.golnour_client.utils.intentFor

class SetupActivity : BoundActivity<ActivitySetupBinding>() {
    override fun inflateLayout(container: ViewGroup?) = ActivitySetupBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind {
            if (Settings.phoneNumber.isNotEmpty()) {
                phone.setText(Settings.phoneNumber)
            }
            if (Settings.password.isNotEmpty()) {
                password.setText(Settings.password)
            }

            save.setOnClickListener { saveSetup() }
        }
    }

    private fun saveSetup() = bind {
        if (phone.text.isNullOrEmpty() || password.text.isNullOrEmpty()) {
            Toast.makeText(this@SetupActivity, R.string.setup_error, Toast.LENGTH_SHORT).show()
        } else {
            Settings.password = password.text.toString()
            Settings.phoneNumber = phone.text.toString()
            Settings.isSetupDone = true
        }
    }

    companion object {
        fun getOpenIntent(context: Context) = context.intentFor<SetupActivity>()
    }
}
