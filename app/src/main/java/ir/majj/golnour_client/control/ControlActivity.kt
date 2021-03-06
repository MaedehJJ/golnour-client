package ir.majj.golnour_client.control

import android.Manifest.permission.SEND_SMS
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.os.CountDownTimer
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.isGone
import ir.majj.golnour_client.R
import ir.majj.golnour_client.about.AboutActivity
import ir.majj.golnour_client.databinding.ActivityControlBinding
import ir.majj.golnour_client.databinding.ViewSliderControlBinding
import ir.majj.golnour_client.preferences.Settings
import ir.majj.golnour_client.setup.SetupActivity
import ir.majj.golnour_client.utils.*
import timber.log.Timber
import kotlin.math.max
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds


class ControlActivity : BoundActivity<ActivityControlBinding>() {
    private val sliders: MutableList<ViewSliderControlBinding> = mutableListOf()
    private val towers: MutableList<ViewSliderControlBinding> = mutableListOf()

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

            newTowersOn.onClick {
                sliders.forEach { slider ->
                    slider.checkbox.isChecked = true
                }
            }
            newTowersOff.onClick {
                sliders.forEach { slider ->
                    slider.checkbox.isChecked = false
                }
            }
            sliders.forEachIndexed { index, slider ->
                setUpColumn(index, slider)
            }

            oldTowersOn.onClick {
                towers.forEach { tower ->
                    tower.checkbox.isChecked = true
                }
            }
            oldTowersOff.onClick {
                towers.forEach { tower ->
                    tower.checkbox.isChecked = false
                }
            }
            towers.forEachIndexed { index, tower ->
                setUpColumn(index, tower)
            }

            send.onClick {
                performSend()
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

    private fun performSend() {
        if (sendMessage()) {
            disableSend()
            EnableSendTimer().start()
        }
    }

    private fun sendMessage(): Boolean {
        if (!checkSmsPermission()) {
            return false
        }
        val data = collectData()
        if (data == null) {
            Timber.e("Data is null")
            return false
        }

        saveState(data)
        return SMSController.sendSms(data, this)
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

    private fun disableSend() = bind {
        send.isEnabled = false
        sendTimer.text = string(
            R.string.control_sent,
            SEND_COOL_DOWN.inWholeSeconds.toString().withPersianDigits()
        )
        sendTimer.isGone = false
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

        performSend()
    }

    inner class EnableSendTimer : CountDownTimer(
        SEND_COOL_DOWN.inWholeMilliseconds, 1.seconds.inWholeMilliseconds
    ) {
        override fun onTick(millisUntilFinished: Long) {
            val remainingSeconds = millisUntilFinished.milliseconds.inWholeSeconds.toString()
            bind {
                sendTimer.text = string(R.string.control_sent, remainingSeconds.withPersianDigits())
            }
        }

        override fun onFinish() = bind {
            sendTimer.isGone = true
            send.isEnabled = true
        }
    }

    companion object {
        private const val REQUEST_SEND_SMS_PERMISSIONS = 1
        private const val DISABLED_VALUE = 10
        private const val MIN_VALUE = 50
        private val SEND_COOL_DOWN = 15.seconds

        fun getOpenIntent(context: Context) = context.intentFor<ControlActivity>()
    }
}
