package ir.majj.golnour_client.control

import android.Manifest.permission.SEND_SMS
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import ir.majj.golnour_client.R
import ir.majj.golnour_client.databinding.ActivityControlBinding
import ir.majj.golnour_client.databinding.ViewTowerControlBinding
import ir.majj.golnour_client.utils.BoundActivity
import ir.majj.golnour_client.utils.intentFor
import ir.majj.golnour_client.utils.onClick
import ir.majj.golnour_client.utils.string
import timber.log.Timber


class ControlActivity : BoundActivity<ActivityControlBinding>() {
    private val towers: MutableList<ViewTowerControlBinding> = mutableListOf()

    override fun inflateLayout(container: ViewGroup?) =
        ActivityControlBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind {
            towers.addAll(listOf(tower1, tower2, tower3, tower4, tower5, tower6, tower7, tower8))
            towers.forEachIndexed { index, tower ->
                tower.checkbox.setOnCheckedChangeListener { _, isChecked ->
                    towers[index].slider.isEnabled = isChecked
                }
            }

            send.onClick {
                sendMessage()
            }
        }
    }

    private fun sendMessage() {
        if (!checkSmsPermission()) {
            return
        }
        val data = collectData()
        if (data == null) {
            Timber.e("Data is null")
            return
        }

        SMSController.sendSms(data)
    }

    private fun checkSmsPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(this, SEND_SMS) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                listOf(SEND_SMS).toTypedArray(),
                REQUEST_SEND_SMS_PERMISSIONS,
            )
            return false
        }
        return true
    }

    private fun collectData(): SMSController.TowerData? = bindAndReturn {
        val firstSet = towers.map {
            if (it.checkbox.isChecked) {
                it.slider.currentValue
            } else {
                DISABLED_VALUE
            }
        }
        val secondSet = towers.map {
            if (it.toggle.isChecked) {
                ENABLED_VALUE
            } else {
                DISABLED_VALUE
            }
        }
        SMSController.TowerData(firstSet, secondSet)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != REQUEST_SEND_SMS_PERMISSIONS) {
            return
        }

        if (grantResults.isEmpty() || grantResults[0] != PERMISSION_GRANTED) {
            Toast.makeText(
                this,
                string(R.string.control_sms_permission_error), Toast.LENGTH_LONG
            ).show()
            return
        }

        sendMessage()
    }

    companion object {
        private const val REQUEST_SEND_SMS_PERMISSIONS = 1
        private const val DISABLED_VALUE = 10
        private const val ENABLED_VALUE = 100

        fun getOpenIntent(context: Context) = context.intentFor<ControlActivity>()
    }
}
