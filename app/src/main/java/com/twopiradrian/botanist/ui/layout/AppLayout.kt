package com.twopiradrian.botanist.ui.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.twopiradrian.botanist.ui.app.NavigationType
import com.twopiradrian.botanist.ui.components.organisms.AppNavigationBar
import com.twopiradrian.botanist.ui.components.organisms.AppNavigationRail
import com.twopiradrian.botanist.ui.components.organisms.AppPermanentNavigation

@Composable
fun AppLayout(
    modifier: Modifier = Modifier,
    navController: NavController,
    navigationType: NavigationType,
    withNavigationBar: Boolean = true,
    content: @Composable () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (withNavigationBar && navigationType == NavigationType.BOTTOM_NAVIGATION) {
                AppNavigationBar(navController = navController)
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (navigationType == NavigationType.BOTTOM_NAVIGATION) {
                ContentInColumn(
                    modifier = modifier,
                    innerPadding = innerPadding,
                    content = content
                )
            } else {
                ContentInRow(
                    modifier = modifier,
                    navigationType = navigationType,
                    navController = navController,
                    withNavigationBar = withNavigationBar,
                    content = content
                )
            }
        }
    }
}

@Composable
fun ContentInColumn(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .padding(bottom = innerPadding.calculateBottomPadding())
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        content()
    }
}

@Composable
fun ContentInRow(
    modifier: Modifier = Modifier,
    navigationType: NavigationType,
    content: @Composable () -> Unit,
    navController: NavController,
    withNavigationBar: Boolean,
){
    Row(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(withNavigationBar && navigationType == NavigationType.NAVIGATION_RAIL){
            AppNavigationRail(navController = navController)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 42.dp, vertical = 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                content()
            }
        }
        else if(withNavigationBar && navigationType == NavigationType.PERMANENT_NAVIGATION_DRAWER){
            Column(
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 0.dp),
            ) {
                AppPermanentNavigation(
                    navController = navController, content = content
                )
            }
        }
        else{
            Column(
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                content()
            }
        }
    }
}