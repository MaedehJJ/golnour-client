package ir.majj.golnour_client.control

import android.Manifest.permission.SEND_SMS
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import ir.majj.golnour_client.R
import ir.majj.golnour_client.about.AboutActivity
import ir.majj.golnour_client.databinding.ActivityControlBinding
import ir.majj.golnour_client.databinding.ViewSliderControlBinding
import ir.majj.golnour_client.preferences.Settings
import ir.majj.golnour_client.setup.SetupActivity
import ir.majj.golnour_client.utils.*
import timber.log.Timber
import kotlin.math.max


class ControlActivity : BoundActivity<ActivityControlBinding>() {
    private val sliders: MutableList<ViewSliderControlBinding> = mutableListOf()
    private val towers: MutableList<ViewSliderControlBinding> = mutableListOf()

    override fun inflateLayout(container: ViewGroup?) =
        ActivityControlBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind {
            sliders.addAll(listOf(new1, new2, new3, new4, new5, new6, new7, new8))
            towers.addAll(
                listOf(old1, old2, old3, old4, old5, old6, old7, old8, old9, old10, old11)
            )

            sliders.forEachIndexed { index, slider ->
                setUpColumn(index, slider)
            }
            towers.forEachIndexed { index, tower ->
                setUpColumn(index, tower)
            }

            send.onClick {
                sendMessage()
            }
            setup.onClick {
                SetupActivity.getOpenIntent(this@ControlActivity)
                    .startActivity(this@ControlActivity)
                finish()
            }
            about.onClick {
                AboutActivity.getOpenIntent(this@ControlActivity)
                    .startActivity(this@ControlActivity)
            }

            loadInitials()
        }
    }

    private fun setUpColumn(index: Int, view: ViewSliderControlBinding) {
        view.label.text = string(R.string.control_tower, index + 1)
        view.checkbox.setOnCheckedChangeListener { _, isChecked ->
            view.slider.circleFillColor = color(
                if (isChecked) {
                    R.color.primaryDark
                } else {
                    R.color.disable
                }
            )
            view.sliderOverlay.isClickable = !isChecked
        }
    }

    private fun loadInitials() {
        if (Settings.slidersState.isEmpty() || Settings.towersState.isEmpty()) {
            collectData()?.let { saveState(it) }
            return
        }

        sliders.forEachIndexed { index, slider ->
            val state = Settings.slidersState.getOrNull(index)
            slider.checkbox.isChecked = state != DISABLED_VALUE
            slider.slider.currentValue = max(state ?: MIN_VALUE, MIN_VALUE)
        }

        towers.forEachIndexed { index, tower ->
            val state = Settings.towersState.getOrNull(index)
            tower.checkbox.isChecked = state != DISABLED_VALUE
            tower.slider.currentValue = max(state ?: MIN_VALUE, MIN_VALUE)
        }
    }

    private fun saveState(towerData: SMSController.TowerData) {
        Settings.slidersState = towerData.firstSet.toIntArray()
        Settings.towersState = towerData.secondSet.toIntArray()
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

        saveState(data)
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
            if (it.checkbox.isChecked) {
                it.slider.currentValue
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
        private const val MIN_VALUE = 50

        fun getOpenIntent(context: Context) = context.intentFor<ControlActivity>()
    }
}
