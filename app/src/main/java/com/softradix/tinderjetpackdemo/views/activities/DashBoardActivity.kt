package com.softradix.tinderjetpackdemo.views.activities

import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.softradix.tinderjetpackdemo.R
import com.softradix.tinderjetpackdemo.network.NetworkChangeReceiver
import com.softradix.tinderjetpackdemo.utils.PreferenceClass
import com.softradix.tinderjetpackdemo.utils.PreferenceClass.Companion.TOKEN
import com.softradix.tinderjetpackdemo.utils.callActivity
import com.softradix.tinderjetpackdemo.views.activities.ui.theme.TinderJetPackDemoTheme
import kotlinx.coroutines.launch

class DashBoardActivity : ComponentActivity() {
    @ExperimentalMaterialApi

    private lateinit var mInterNetCheckReceiver: BroadcastReceiver

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mInterNetCheckReceiver =
            NetworkChangeReceiver()      // register check internet broadcast receiver
        @Suppress("DEPRECATION")
        registerReceiver(
            mInterNetCheckReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )

        setContent {
            TinderJetPackDemoTheme {
                val dataStore = PreferenceClass(this)
                val navController = rememberNavController()
                Scaffold(topBar = {
                    TopAppBar(backgroundColor = colorResource(id = R.color.app_color)) {
                        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                            val (title, logOut) = createRefs()
                            Text(
                                text = "Tundur", style = TextStyle(
                                    color = Color.White,
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center
                                ), modifier = Modifier
                                    .padding(5.dp)
                                    .constrainAs(title) {
                                        top.linkTo(parent.top)
                                        bottom.linkTo(parent.bottom)
                                        start.linkTo(parent.start)
                                    }
                            )

                            IconButton(modifier = Modifier.then(
                                Modifier
                                    .size(30.dp)
                                    .padding(5.dp)
                                    .constrainAs(logOut) {
                                        top.linkTo(parent.top)
                                        end.linkTo(parent.end)
                                        bottom.linkTo(parent.bottom)
                                    }
                            ),
                                onClick = {
                                    lifecycleScope.launch {
                                        dataStore.saveString(TOKEN, "0")
                                        // dataStore.clearDataWithKey()
                                        callActivity(this@DashBoardActivity, MainActivity())
                                    }
                                }) {
                                Icon(
                                    painterResource(id = R.drawable.ic_logout),
                                    "contentDescription",
                                    tint = Color.White
                                )
                            }
                        }
                    }

                }, bottomBar = {
                    BottomNavigationBar(
                        items = listOf(
                            BottomNavItem(
                                title = "Explore",
                                route = "home_screen",
                                icon = R.drawable.ic_explore,
                                badgeCount = 0
                            ),
                            BottomNavItem(
                                title = "Matches",
                                route = "match_screen",
                                icon = R.drawable.ic_love,
                                badgeCount = 0
                            ),
                            BottomNavItem(
                                title = "Chat",
                                route = "chat_screen",
                                icon = R.drawable.ic_chat,
                                badgeCount = 5
                            ),
                            BottomNavItem(
                                title = "Profile",
                                route = "profile_screen",
                                icon = R.drawable.ic_profile,
                                badgeCount = 0
                            ),
                        ),
                        navController = navController,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(3.dp),
                        onItemClick = {
                            navController.navigate(it.route) {
                                //to prevent back press to come to the previous screen. It will directly end the app when back pressed
                                //popUpTo(0)

                                //to move to the home screen when back pressed
                                popUpTo("home_screen")
                            }

                        }
                    )
                }) {
                    Navigation(navHostController = navController)
                }
            }
        }


    }


    @Composable
    fun Navigation(navHostController: NavHostController) {
        NavHost(navController = navHostController, startDestination = "home_screen") {
            composable("home_screen") {
                HomeScreen()
            }
            composable("match_screen") {
                MatchScreen()
            }
            composable("chat_screen") {
                ChatScreen()
            }
            composable("profile_screen") {
                ProfileScreen()
            }
        }
    }


    @ExperimentalMaterialApi
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mInterNetCheckReceiver)      // unregister check internet broadcast receiver
    }
}


@ExperimentalMaterialApi
@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {

    val backStackEntry =
        navController.currentBackStackEntryAsState() //becoz state get recompose only
    BottomNavigation(modifier = modifier, backgroundColor = Color.White, elevation = 5.dp) {

        items.forEach { item ->

            val selected =
                item.route == backStackEntry.value?.destination?.route //to get update when ever the route is changed
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = colorResource(id = R.color.app_color),
                unselectedContentColor = Color.Gray,
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (item.badgeCount > 0) {
                            BadgeBox(badgeContent = {
                                Text(text = item.badgeCount.toString())
                            }) {
                                Icon(
                                    modifier = Modifier.size(18.dp),
                                    painter = painterResource(id = item.icon),
                                    contentDescription = ""
                                )
                            }
                        } else {
                            Icon(
                                modifier = Modifier.size(18.dp),
                                painter = painterResource(id = item.icon),
                                contentDescription = ""
                            )
                        }

//                        if (selected) {
                        Text(
                            text = item.title,
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            fontFamily = FontFamily.Serif,
                            modifier = Modifier.paddingFromBaseline(2.dp)
                        )
//                        }
                    }
                })
        }
    }
}


@Composable
fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Home Screen")
    }
}

@Composable
fun MatchScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Match Screen")
    }
}

@Composable
fun ChatScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Chat Screen")
    }
}

@Composable
fun ProfileScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Profile Screen")
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    TinderJetPackDemoTheme {

    }
}

data class BottomNavItem(
    val title: String,
    val route: String,
    @DrawableRes val icon: Int,
    val badgeCount: Int = 0
)