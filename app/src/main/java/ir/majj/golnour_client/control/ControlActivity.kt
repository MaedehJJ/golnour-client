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
import ir.majj.golnour_client.databinding.ViewSliderControlBinding
import ir.majj.golnour_client.databinding.ViewTowerControlBinding
import ir.majj.golnour_client.setup.SetupActivity
import ir.majj.golnour_client.utils.*
import timber.log.Timber


class ControlActivity : BoundActivity<ActivityControlBinding>() {
    private val sliders: MutableList<ViewSliderControlBinding> = mutableListOf()
    private val towers: MutableList<ViewTowerControlBinding> = mutableListOf()

    override fun inflateLayout(container: ViewGroup?) =
        ActivityControlBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind {
            sliders.addAll(
                listOf(slider1, slider2, slider3, slider4, slider5, slider6, slider7, slider8)
            )
            towers.addAll(
                listOf(
                    tower1, tower2, tower3, tower4, tower5, tower6, tower7, tower8, tower9,
                    tower10, tower11
                )
            )
            sliders.forEachIndexed { index, slider ->
                slider.checkbox.setOnCheckedChangeListener { _, isChecked ->
                    sliders[index].slider.circleFillColor = color(
                        if (isChecked) {
                            R.color.primaryDark
                        } else {
                            R.color.disable
                        }
                    )
                    sliders[index].sliderOverlay.isClickable = !isChecked
                }
            }
            towers.forEachIndexed { index, tower ->
                tower.label.text = string(R.string.control_tower, index + 1)
            }

            send.onClick {
                sendMessage()
            }
            setup.onClick {
                SetupActivity.getOpenIntent(this@ControlActivity)
                    .startActivity(this@ControlActivity)
                finish()
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

        SMSController.sendSms(data, this)
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
        val firstSet = sliders.map {
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
