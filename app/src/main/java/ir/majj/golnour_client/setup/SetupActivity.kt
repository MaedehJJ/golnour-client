package ir.majj.golnour_client.setup

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import dev.samstevens.totp.code.DefaultCodeGenerator
import dev.samstevens.totp.code.DefaultCodeVerifier
import dev.samstevens.totp.time.SystemTimeProvider
import ir.majj.golnour_client.R
import ir.majj.golnour_client.databinding.ActivitySetupBinding
import ir.majj.golnour_client.login.LoginActivity
import ir.majj.golnour_client.preferences.Settings
import ir.majj.golnour_client.utils.*

class SetupActivity : BoundActivity<ActivitySetupBinding>() {
    override fun inflateLayout(container: ViewGroup?) = ActivitySetupBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind {
            if (Settings.isVerified) {
                otpContainer.gone()
                settingContainer.visible()
            } else {
                otpContainer.visible()
                settingContainer.gone()
            }

            otpConfirm.onClick { confirmOTP() }

            if (Settings.phoneNumber.isNotEmpty()) {
                phone.setText(Settings.phoneNumber)
            }
            if (Settings.password.isNotEmpty()) {
                passcode.setText(Settings.password)
            }

            save.setOnClickListener { saveSetup() }
        }
    }

    private fun confirmOTP() {
        bind {
            val code = otp.text
            if (verifyOtp(code.toString())) {
                Settings.isVerified = true
                otpContainer.gone()
                settingContainer.visible()
            } else {
                Toast.makeText(
                    this@SetupActivity,
                    string(R.string.setup_otpWrongPasscode),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun verifyOtp(code: String): Boolean {
        val timeProvider = SystemTimeProvider()
        val codeGenerator = DefaultCodeGenerator()
        val verifier = DefaultCodeVerifier(codeGenerator, timeProvider)
        return verifier.isValidCode(SECRET, code)
    }

    private fun saveSetup() = bind {
        if (phone.text.isNullOrEmpty() || passcode.text.isNullOrEmpty()) {
            Toast.makeText(this@SetupActivity, R.string.setup_error, Toast.LENGTH_SHORT).show()
        } else {
            Settings.password = passcode.text.toString()
            Settings.phoneNumber = phone.text.toString()
            Settings.isSetupDone = true

            LoginActivity.getOpenIntent(this@SetupActivity).startActivity(this@SetupActivity)
            finish()
        }
    }

    companion object {
        private const val SECRET = "WOLK2ZD45UYN2RGIVYT76O4Z6SZD56XU"
        fun getOpenIntent(context: Context) = context.intentFor<SetupActivity>()
    }
}
