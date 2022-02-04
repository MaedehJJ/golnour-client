package ir.majj.golnour_client.control

import android.content.Context
import android.telephony.SmsManager
import android.widget.Toast
import ir.majj.golnour_client.R
import ir.majj.golnour_client.preferences.Settings
import timber.log.Timber

object SMSController {
    fun sendSms(data: TowerData, context: Context) {
        val content = "m,${data.firstSet.joinToString(",")}\nn,${data.secondSet.joinToString(",")}"
        val smsManager = SmsManager.getDefault()
        try {
            Toast.makeText(context, R.string.sms_sending, Toast.LENGTH_LONG).show()
            smsManager.sendTextMessage(Settings.phoneNumber, null, content, null, null)
        } catch (e: IllegalArgumentException) {
            Toast.makeText(context, R.string.sms_sendingFailed, Toast.LENGTH_LONG).show()
            Timber.e(e, "${Settings.phoneNumber} is not in its appropriate format")
        }
    }

    data class TowerData(val firstSet: List<Int>, val secondSet: List<Int>)
}
