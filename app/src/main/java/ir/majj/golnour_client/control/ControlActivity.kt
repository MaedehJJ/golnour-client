package ir.majj.golnour_client.control

import android.Manifest.permission.SEND_SMS
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import ir.majj.golnour_client.databinding.ActivityControlBinding
import ir.majj.golnour_client.utils.BoundActivity
import ir.majj.golnour_client.utils.intentFor
import ir.majj.golnour_client.utils.onClick
import android.widget.Toast
import ir.majj.golnour_client.R
import ir.majj.golnour_client.utils.string


class ControlActivity : BoundActivity<ActivityControlBinding>() {
    override fun inflateLayout(container: ViewGroup?) =
        ActivityControlBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind {
            send.onClick {
                checkSmsPermission()
            }
        }
    }

    private fun checkSmsPermission() {
        if (ActivityCompat.checkSelfPermission(
                this@ControlActivity,
                SEND_SMS
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@ControlActivity,
                listOf(SEND_SMS).toTypedArray(), REQUEST_SEND_SMS_PERMISSIONS
            )
        } else {
            SMSController.sendSms()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isEmpty()) {
            return
        }

        when (requestCode) {
            REQUEST_SEND_SMS_PERMISSIONS -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    SMSController.sendSms()
                } else {
                    Toast.makeText(
                        this,
                        string(R.string.control_sms_permission_error), Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    companion object {
        private const val REQUEST_SEND_SMS_PERMISSIONS = 1
        fun getOpenIntent(context: Context) = context.intentFor<ControlActivity>()
    }
}
