package core.ui.navigation

import android.net.Uri
import androidx.navigation.NavHostController
import androidx.navigation.navOptions

/**
 * Enumeration representing various navigation strategies used in navigating between destinations.
 */
enum class NavigationStrategy {
    /**
     * Strategy to navigate back to the previous screen.
     */
    Back {
        override fun doProceed(route: String?, uri: Uri, controller: NavHostController) {
            if (controller.previousBackStackEntry != null) {
                controller.popBackStack()
            }
        }
    },

    /**
     * Strategy to create a new instance of the destination and navigate to it.
     */
    NewInstance {
        override fun doProceed(route: String?, uri: Uri, controller: NavHostController) {
            controller.navigate(uri)
        }
    },

    /**
     * Strategy to use a single instance of the destination and navigate to it.
     */
    SingleInstance {
        override fun doProceed(route: String?, uri: Uri, controller: NavHostController) {
            controller.navigate(uri, navOptions {
                if (route != null) {
                    popUpTo(route) {
                        inclusive = true
                    }
                }
                launchSingleTop = true
                restoreState = false
            })
        }
    },

    /**
     * Strategy to replace the previous destination with a new one and navigate to it.
     */
    ReplacePrevious {
        override fun doProceed(route: String?, uri: Uri, controller: NavHostController) {
            val prev = controller.currentDestination?.route ?: route
            controller.navigate(uri, navOptions {
                if (prev != null) {
                    popUpTo(prev) {
                        inclusive = true
                    }
                }
                launchSingleTop = true
                restoreState = false
            })
        }
    },

    /**
     * Strategy to clear the navigation history and navigate to the specified destination.
     */
    ClearHistory {
        override fun doProceed(route: String?, uri: Uri, controller: NavHostController) {
            controller.navigate(uri, navOptions {
                popUpTo(controller.graph.id) {
                    inclusive = true
                }
            })
        }
    }

    ;

    /**
     * Method to execute the navigation strategy.
     *
     * @param route The route of the destination.
     * @param uri The URI of the destination.
     * @param controller The NavController used for navigation.
     */
    internal fun proceed(route: String?, uri: Uri, controller: NavHostController) {
        val currentRoute = controller.currentDestination?.route
        if (!uri.query.isNullOrEmpty() || currentRoute != route) {
            doProceed(route, uri, controller)
        }
    }

    protected open fun doProceed(route: String?, uri: Uri, controller: NavHostController) = Unit
}