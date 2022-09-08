package sk.msvvas.sofia.fam.offline.ui.navigation

enum class Routes(val value: String) {
    LOADING_SCREEN("loading_screen"),
    LOGIN_VIEW("login_view"),
    INVENTORY_LIST("inventory_list"),
    INVENTORY_DETAIL("inventory_detail"),
    PROPERTY_DETAIL("property_detail");


    fun withArgs(vararg args: String): String {
        val route = StringBuilder(this.value)
        args.forEach {
            route.append("/$it")
        }
        return route.toString()
    }

    fun defineRoute(vararg params: String): String {
        val route = StringBuilder(this.value)
        params.forEach {
            route.append("/{$it}")
        }
        return route.toString()
    }

    companion object {
        fun addOptionalArgumentsToRoute(route: String, vararg params: String): String {
            val result = StringBuilder(route)
            params.forEach {
                result.append("?$it={$it}")
            }
            return result.toString()
        }
    }
}