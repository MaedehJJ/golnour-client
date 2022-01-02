package ir.majj.golnour_client.preferences

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import androidx.preference.PreferenceManager
import java.io.*

object Preferences {
    private lateinit var sharedPreferences: SharedPreferences

    fun setup(context: Context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun <T> put(key: String, value: T) {
        when (value) {
            is Boolean -> edit { putBoolean(key, value) }
            is Int -> edit { putInt(key, value) }
            is Long -> edit { putLong(key, value) }
            is Float -> edit { putFloat(key, value) }
            is String -> edit { putString(key, value) }
            is Serializable -> edit { putString(key, toString(value)) }
            else -> throw IllegalArgumentException("Cannot store value $value in key $key due to illegal type.")
        }
    }

    @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
    fun <T> get(key: String, default: T): T {
        return when (default) {
            is Boolean -> get { getBoolean(key, default) }
            is Int -> get { getInt(key, default) }
            is Long -> get { getLong(key, default) }
            is Float -> get { getFloat(key, default) }
            is String -> get { getString(key, default) }
            is Serializable -> get {
                val objString = getString(key, null)
                objString?.let { fromString<Serializable>(it) } ?: default
            }
            else -> throw IllegalArgumentException("Cannot query key $key with default value $default due to illegal type.")
        } as T
    }

    fun remove(key: String) = edit { remove(key) }

    fun getAll(): Map<String, *> = get { all }

    @SuppressLint("ApplySharedPref")
    fun clear() {
        sharedPreferences.edit().clear().commit()
    }

    private fun <T> toString(obj: T): String? {
        try {
            val byteOutput = ByteArrayOutputStream()
            val objectOutput = ObjectOutputStream(byteOutput)
            objectOutput.writeObject(obj)
            objectOutput.flush()
            return Base64.encodeToString(byteOutput.toByteArray(), Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    private inline fun <reified T> fromString(s: String): T? {
        return try {
            val bytes = Base64.decode(s.toByteArray(), Base64.DEFAULT)
            val byteInput = ByteArrayInputStream(bytes)
            val objectInput = ObjectInputStream(byteInput)
            val obj = objectInput.readObject()
            obj as T
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun edit(block: SharedPreferences.Editor.() -> Unit) {
        val editor = sharedPreferences.edit()
        editor.block()
        editor.apply()
    }

    private fun <T> get(block: SharedPreferences.() -> T): T = sharedPreferences.block()
}
