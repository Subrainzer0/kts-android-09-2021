package ru.shanin.madarareddit.data

import net.openid.appauth.ResponseTypeValues

object AuthConfig {

    const val AUTH_URI = "https://www.reddit.com/api/v1/authorize"
    const val TOKEN_URI = "https://www.reddit.com/api/v1/access_token"
    const val RESPONSE_TYPE = ResponseTypeValues.CODE
    const val SCOPE = "read,vote"

    const val CLIENT_ID = "_tn2enQSGBAXPXyUTdWM5A"
    const val CLIENT_SECRET = "mx8_1fAM7Cyt9wxrGKmtAHM85UWoaA"
    const val CALLBACK_URL = "school://kts.studio/callback"
}
