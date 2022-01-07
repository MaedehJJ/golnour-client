package ir.majj.golnour_client.control

import android.telephony.SmsManager
import ir.majj.golnour_client.preferences.Settings
import timber.log.Timber

object SMSController {
    fun sendSms(data: TowerData) {
        val content = "M_${data.firstSet.joinToString(",")}\nN_${data.secondSet.joinToString(",")}"
        val smsManager = SmsManager.getDefault()
        try {
            smsManager.sendTextMessage(Settings.phoneNumber, null, content, null, null)
        } catch (e: IllegalArgumentException) {
            Timber.e(e, "${Settings.phoneNumber} is not in its appropriate format")
        }
    }

    data class TowerData(val firstSet: List<Int>, val secondSet: List<Int>)
}
