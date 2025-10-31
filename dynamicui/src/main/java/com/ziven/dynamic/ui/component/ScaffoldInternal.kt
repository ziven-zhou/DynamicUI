package com.ziven.dynamic.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ziven.dynamic.ui.ComponentAction
import com.ziven.dynamic.ui.ComponentList
import com.ziven.dynamic.ui.ComponentState
import com.ziven.dynamic.ui.ForEachChildComponent
import com.ziven.dynamic.ui.UIComponent
import com.ziven.dynamic.ui.findChildComponentWithName
import com.ziven.dynamic.ui.internal.componentLayout
import com.ziven.dynamic.ui.internal.componentUI
import com.ziven.dynamic.ui.internal.toContainerColor
import com.ziven.dynamic.ui.internal.toContentColor
import com.ziven.dynamic.ui.internal.toFabPosition
import com.ziven.dynamic.ui.internal.toFontColor
import com.ziven.dynamic.ui.internal.toFontFamily
import com.ziven.dynamic.ui.internal.toFontSize
import com.ziven.dynamic.ui.internal.toFontStyle
import com.ziven.dynamic.ui.internal.toFontWeight
import com.ziven.dynamic.ui.internal.toMaxLines
import com.ziven.dynamic.ui.internal.toMinLines
import com.ziven.dynamic.ui.internal.toOverflow
import com.ziven.dynamic.ui.internal.toShape
import com.ziven.dynamic.ui.internal.toText
import com.ziven.dynamic.ui.logPrint

@Composable
internal fun ScaffoldComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onClick: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
) {
    Scaffold(
        modifier = modifier.componentLayout(uiComponent.layout),
        topBar = {
            uiComponent.findChildComponentWithName("TopBar")?.let {
                DispatchRenderComponent(it, Modifier, onClick, componentList, componentState)
            }
        },
        bottomBar = {
            uiComponent.findChildComponentWithName("BottomBar")?.let {
                DispatchRenderComponent(it, Modifier, onClick, componentList, componentState)
            }
        },
        snackbarHost = {
            uiComponent.findChildComponentWithName("SnackBar")?.let {
                DispatchRenderComponent(it, Modifier, onClick, componentList, componentState)
            }
        },
        floatingActionButton = {
            uiComponent.findChildComponentWithName("FloatingActionButton")?.let {
                DispatchRenderComponent(it, Modifier, onClick, componentList, componentState)
            }
        },
        floatingActionButtonPosition = uiComponent.run { style.toFabPosition() },
        containerColor = uiComponent.style.toContainerColor(MaterialTheme.colorScheme.background),
        contentColor = uiComponent.style.toContentColor(MaterialTheme.colorScheme.background),
    ) { padding ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding),
        ) {
            uiComponent.ForEachChildComponent { child ->
                when (child.componentName) {
                    "TopBar" -> return@ForEachChildComponent
                    "BottomBar" -> return@ForEachChildComponent
                    "SnackBar" -> return@ForEachChildComponent
                    "FloatingActionButton" -> return@ForEachChildComponent
                    else ->
                        DispatchRenderComponent(
                            child,
                            Modifier,
                            onClick,
                            componentList,
                            componentState,
                        )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TopBarComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onClick: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
) {
    TopAppBar(
        modifier = modifier.componentUI(uiComponent),
        title = {
            Text(
                text = uiComponent.value.toText(),
                fontSize = uiComponent.style.toFontSize(),
                color = uiComponent.style.toFontColor(),
                fontWeight = uiComponent.style.toFontWeight(),
                fontStyle = uiComponent.style.toFontStyle(),
                fontFamily = uiComponent.style.toFontFamily(),
                overflow = uiComponent.style.toOverflow(),
                maxLines = uiComponent.style.toMaxLines(),
                minLines = uiComponent.style.toMinLines(),
            )
        },
        navigationIcon = {
            uiComponent.ForEachChildComponent {
                DispatchRenderComponent(it, Modifier, onClick, componentList, componentState)
            }
        },
    )
}

@Composable
internal fun BottomBarComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onClick: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
) {
    BottomAppBar(
        modifier = modifier.componentLayout(uiComponent.layout),
        containerColor = uiComponent.style.toContainerColor(BottomAppBarDefaults.containerColor),
        contentColor = uiComponent.style.toContentColor(BottomAppBarDefaults.containerColor),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            uiComponent.ForEachChildComponent {
                DispatchRenderComponent(
                    it,
                    Modifier.align(Alignment.CenterVertically),
                    onClick,
                    componentList,
                    componentState,
                )
            }
        }
    }
}

@Composable
internal fun SnackBarComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onClick: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
) {
    logPrint("SnackBarComponent: $componentState")
    componentState?.snackBarHostState ?: return
    SnackbarHost(hostState = componentState.snackBarHostState)
}

@Composable
internal fun FloatingActionButtonComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onClick: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
) {
    FloatingActionButton(
        onClick = click(uiComponent, onClick, componentList, componentState),
        modifier = modifier.componentLayout(uiComponent.layout),
        shape = uiComponent.style.toShape(FloatingActionButtonDefaults.shape),
        containerColor = uiComponent.style.toContainerColor(FloatingActionButtonDefaults.containerColor),
        contentColor = uiComponent.style.toContentColor(FloatingActionButtonDefaults.containerColor),
    ) {
        uiComponent.ForEachChildComponent {
            DispatchRenderComponent(
                it,
                Modifier,
                onClick,
                componentList,
                componentState,
            )
        }
    }
}
