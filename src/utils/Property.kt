package ca.etsmtl.applets.notre_dame.utils

import java.util.*

object Property {

    val props = Properties().apply {
        Property::class.java.classLoader.getResourceAsStream("application.properties").use {
            load(it)
        }
    }

    operator fun get(key: String): String = props[key].toString()
}