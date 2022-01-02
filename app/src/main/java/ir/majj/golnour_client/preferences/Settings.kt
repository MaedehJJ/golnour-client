package ir.majj.golnour_client.preferences

object Settings {
    var phoneNumber by Shared("Settings:phoneNumber", "")
    var password by Shared("Settings:password", "")
    var isSetupDone by Shared("Settings:isSetUpDone", false)
}
