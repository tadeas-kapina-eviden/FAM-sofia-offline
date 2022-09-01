package sk.msvvas.sofia.fam.offline.ui.views.navigation

enum class Routes(val value: String) {
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

}