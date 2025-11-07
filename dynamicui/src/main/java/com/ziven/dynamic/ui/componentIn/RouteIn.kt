package com.ziven.dynamic.ui.componentIn

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import androidx.navigation.navArgument
import com.ziven.dynamic.ui.ComponentAction
import com.ziven.dynamic.ui.ComponentList
import com.ziven.dynamic.ui.ComponentState
import com.ziven.dynamic.ui.UIComponent
import com.ziven.dynamic.ui.UIManager
import com.ziven.dynamic.ui.addExtras
import com.ziven.dynamic.ui.forEachChildComponent
import com.ziven.dynamic.ui.internal.logPrint

private class ComponentRoute(
    val route: String,
    val params: List<String>,
    val component: UIComponent,
)

private fun UIComponent.toComponentRoute(): List<ComponentRoute> {
    val componentRoutes = mutableListOf<ComponentRoute>()
    this.forEachChildComponent { origin ->
        UIManager.getComponent(origin.componentType) { target ->
            origin.routeName?.let { route ->
                val params = origin.routeParams ?: emptyList()
                componentRoutes += ComponentRoute(route, params, target)
            }
        }
    }
    logPrint("componentRoutes: $componentRoutes")
    return componentRoutes
}

@Composable
internal fun RouteComponent(
    uiComponent: UIComponent,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
    onClick: (ComponentAction) -> Unit,
) {
    val navHostController = componentState?.navHostController
    val componentRoutes = uiComponent.toComponentRoute()
    if (navHostController == null || componentRoutes.isEmpty()) {
        DispatchRenderComponent(uiComponent, Modifier, onClick, componentList, componentState)
        return
    }
    val navGroup =
        navHostController.createGraph(componentRoutes[0].route) {
            componentRoutes.forEach { route ->
                var routeName = route.route
                val routeParams = mutableListOf<NamedNavArgument>()
                route.params.forEach { param ->
                    routeName += "/{$param}"
                    routeParams += navArgument(param) { type = NavType.StringType }
                }
                logPrint("RouteComponent: routeName: $routeName, routeParams: ${route.params}")
                composable(route = routeName, arguments = routeParams) { value ->
                    val extras = mutableMapOf<String, String>()
                    value.arguments?.let { arguments ->
                        route.params.forEach { key ->
                            arguments.getString(key)?.let {
                                extras[key] = it
                            }
                        }
                        if (componentState.updateValue?.invoke(routeName, route.component, extras) != true) {
                            route.component.addExtras(extras)
                        }
                        logPrint("RouteComponent: extras: $extras")
                    }
                    DispatchRenderComponent(
                        route.component,
                        Modifier,
                        onClick,
                        componentList,
                        componentState,
                    )
                }
            }
        }
    NavHost(navController = navHostController, graph = navGroup)
}
