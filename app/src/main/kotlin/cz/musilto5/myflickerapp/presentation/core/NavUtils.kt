package cz.musilto5.myflickerapp.presentation.core

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object NavUtils {
    inline fun <reified T> fromJsonString(string: String): T {
        return Json.decodeFromString<T>(string)
    }

    inline fun <reified T> toJsonString(t: T): String {
        return Json.encodeToString(t)
    }
}