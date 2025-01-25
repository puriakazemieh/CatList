package com.example.cat.core.ui.navigation

import kotlin.collections.forEach
import kotlin.text.replace
import kotlin.toString

interface INavigation {
    val route: String

    fun withArgs(vararg args: Any): String {
        return buildString {
            append(route)
            args.forEach {
                append("?$it")
            }
        }
    }


    fun routeWithArgs(vararg params: Pair<String, Any?>): String {
        var newRoute = route
        params.forEach {
            newRoute = newRoute.replace("{" + it.first + "}", it.second.toString())
        }
        return newRoute
    }
}