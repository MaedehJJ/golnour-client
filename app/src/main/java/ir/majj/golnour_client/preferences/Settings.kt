package ir.majj.golnour_client.preferences

object Settings {
    var phoneNumber by Shared("Settings:phoneNumber", "")
    var password by Shared("password", "")
    var isSetupDone by Shared("isSetupDone", false)
}
