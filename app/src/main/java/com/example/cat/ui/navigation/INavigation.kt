package com.example.cat.ui.navigation

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