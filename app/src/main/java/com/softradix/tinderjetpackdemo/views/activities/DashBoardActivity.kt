package com.softradix.tinderjetpackdemo.views.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.softradix.tinderjetpackdemo.R
import com.softradix.tinderjetpackdemo.network.ConnectivityStatus
import com.softradix.tinderjetpackdemo.utils.PreferenceClass
import com.softradix.tinderjetpackdemo.utils.PreferenceClass.Companion.TOKEN
import com.softradix.tinderjetpackdemo.utils.callActivity
import com.softradix.tinderjetpackdemo.views.activities.ui.theme.TinderJetPackDemoTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class DashBoardActivity : ComponentActivity() {
    @ExperimentalPagerApi
    @ExperimentalCoroutinesApi
    @ExperimentalMaterialApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                //internet check
                ConnectivityStatus()
            }
        }
    }


    @ExperimentalPagerApi
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

@ExperimentalPagerApi
@Composable
fun MatchScreen() {
    TabPage.values().size
    val pagerState = rememberPagerState()
    var tabPage by remember { mutableStateOf(TabPage.Likes) }
    val scope = rememberCoroutineScope()
    Scaffold(topBar = {
        TabHome(
            // if you use no pagerState
            selectedTabIndex = tabPage.ordinal, onSelectedTabPage = { tabPage = it },
        )
        /*TabHome(
            selectedTabIndex = pagerState.currentPage,
            onSelectedTabPage = {
                scope.launch {
                    pagerState.animateScrollToPage(it.ordinal)
                }
            },
        )*/
    }) {
        Column() {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = tabPage.name)
            }
        }

        //for horizontal scrolling of tabs
        /*  HorizontalPager(count = TabPage.values().size, state = pagerState) { index ->
              Column(Modifier.fillMaxSize()) {
                  Text(text = TabPage.values()[index].name)

                  when (index) {
                      0 -> MatchScreen()
                      1 -> LikeScreen()
                  }
              }

          }*/
    }
}


@Composable
fun LikeScreen() {
    Text(text = "Hellloooooo")
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


// bottom navigation item class
data class BottomNavItem(
    val title: String,
    val route: String,
    @DrawableRes val icon: Int,
    val badgeCount: Int = 0
)


//***************** Tab Layout *******************
enum class TabPage(@DrawableRes val icon: Int) {
    Likes(R.drawable.ic_love),
    TopLikes(R.drawable.ic_love)
}

// for tab inicater
@Composable
fun tabIndicator(tabPosition: List<TabPosition>, index: Int) {
    val width = tabPosition[index].width
    val offsetx = tabPosition[index].left
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomStart)
            .offset()
            .width(width = width)
            .padding(4.dp)
            .fillMaxSize()
    )
}

// function for setting the basic requirement for tabs
@Composable
fun TabHome(selectedTabIndex: Int, onSelectedTabPage: (TabPage) -> Unit) {
    TabRow(
        backgroundColor = Color.White, selectedTabIndex = selectedTabIndex
        /* , indicator = {
         tabIndicator(
             tabPosition = it,
             index = selectedTabIndex
         )
     }*/
    ) {
        TabPage.values().forEachIndexed { index, tabPage ->
            Tab(
                selected = index == selectedTabIndex, onClick = { onSelectedTabPage(tabPage) },
                text = { Text(text = tabPage.name) },
                /*   icon = {
                       Icon(
                           painter = painterResource(id = tabPage.icon),
                           contentDescription = ""
                       )
                   },*/
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Gray
            )
        }
    }
}
