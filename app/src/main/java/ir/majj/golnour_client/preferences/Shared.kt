package ir.majj.golnour_client.preferences

import java.io.Serializable
import kotlin.reflect.KProperty

class Shared<T : Serializable>(
    private val key: String,
    private val default: T,
    private val callback: (() -> Unit)? = null
) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = Preferences.get(key, default)

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (value != getValue(thisRef, property)) {
            Preferences.put(key, value)
            callback?.invoke()
        }
    }
}
