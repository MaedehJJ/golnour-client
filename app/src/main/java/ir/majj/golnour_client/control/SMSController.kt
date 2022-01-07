package ir.majj.golnour_client.control

import android.telephony.SmsManager
import ir.majj.golnour_client.preferences.Settings
import timber.log.Timber

object SMSController {
    fun sendSms() {
        val smsManager = SmsManager.getDefault()
        try {
            smsManager.sendTextMessage(Settings.phoneNumber, null, "", null, null)
        } catch (e: IllegalArgumentException) {
            Timber.e(e, "${Settings.phoneNumber} is not in its appropriate format")
        }
    }
}
