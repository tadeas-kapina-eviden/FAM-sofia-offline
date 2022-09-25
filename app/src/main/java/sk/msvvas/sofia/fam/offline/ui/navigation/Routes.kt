package sk.msvvas.sofia.fam.offline.ui.navigation


/**
 * Enum of Routes for navigation in application
 */
enum class Routes(val value: String) {
    LOADING_SCREEN("loading_screen"),
    LOGIN_VIEW("login_view"),
    INVENTORY_LIST("inventory_list"),
    INVENTORY_DETAIL("inventory_detail"),
    PROPERTY_DETAIL("property_detail");

    /**
     * Function to add arguments when calling route
     * @param args list of arguments to add route (need to be in correct order)
     * @return string value of route with arguments
     */
    fun withArgs(vararg args: String): String {
        val route = StringBuilder(this.value)
        args.forEach {
            route.append("/$it")
        }
        return route.toString()
    }

    /**
     * Function to add parameters to route definition
     * @param params list of parameters that is added to route
     * @return string value of route with parameters
     */
    fun defineRoute(vararg params: String): String {
        val route = StringBuilder(this.value)
        params.forEach {
            route.append("/{$it}")
        }
        return route.toString()
    }

    companion object {
        /**
         * Function to add optional parameters to route definition
         * Called on result of defineRoute function
         * @param route string value of route
         * @param params list of optional parameters that is added to route
         * @return string value of route with optional parameters
         */
        fun addOptionalParametersToRoute(route: String, vararg params: String): String {
            val result = StringBuilder(route)
            params.forEachIndexed { index, it ->
                result.append("${if (index == 0) "?" else "&"}$it={$it}")
            }
            return result.toString()
        }
    }
}