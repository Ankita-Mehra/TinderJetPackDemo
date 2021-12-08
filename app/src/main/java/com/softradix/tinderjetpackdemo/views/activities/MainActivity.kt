package com.softradix.tinderjetpackdemo.views.activities

import android.app.Activity
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.*
import com.softradix.tinderjetpackdemo.R
import com.softradix.tinderjetpackdemo.app.Status
import com.softradix.tinderjetpackdemo.data.AuthViewModel
import com.softradix.tinderjetpackdemo.modelClass.OnBoardingViewModel
import com.softradix.tinderjetpackdemo.modelClass.onBoardItems
import com.softradix.tinderjetpackdemo.network.ConnectivityStatus
import com.softradix.tinderjetpackdemo.ui.theme.TinderJetPackDemoTheme
import com.softradix.tinderjetpackdemo.utils.PreferenceClass
import com.softradix.tinderjetpackdemo.utils.PreferenceClass.Companion.TOKEN
import com.softradix.tinderjetpackdemo.utils.ViewUtils
import com.softradix.tinderjetpackdemo.utils.callActivity
import com.softradix.tinderjetpackdemo.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.HashMap

@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalCoroutinesApi
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TinderJetPackDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Navigation()
                    ConnectivityStatus()  // showing this for network connectivity test
                }
            }
        }
    }
}

@ExperimentalPagerApi
@ExperimentalComposeUiApi
@Composable
fun Navigation() {

    val navController = rememberNavController()
    //way to get the instance of the viewModel
    val loginViewModel = hiltViewModel<AuthViewModel>()
    NavHost(
        navController = navController,
        startDestination = "onBoard_screen"
    ) {
        composable("login_screen") {
            LoginScreen(loginViewModel, navController)
        }

        composable("register_screen") {
            RegisterScreen(loginViewModel, navController)
        }

        composable("onBoard_screen") {
            OnBoardScreen(navController)
        }
    }
}


@ExperimentalPagerApi
@Composable
fun OnBoardScreen(navController: NavHostController) {
    val onBoardViewModel = hiltViewModel<OnBoardingViewModel>()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val currentPage = onBoardViewModel.currentPage.collectAsState()

    val pagerState = rememberPagerState(
        pageCount = onBoardItems.size,
        initialPage = 0,
        infiniteLoop = false,
        initialOffscreenLimit = 3
    )
    Scaffold(modifier = Modifier.fillMaxSize(), scaffoldState = scaffoldState) {
        /* scope.launch {
             pagerState.animateScrollToPage(page = currentPage.value)
         }*/
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                HorizontalPager(state = pagerState) { page ->
                    Column(
                        modifier = Modifier
                            .padding(top = 25.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Image(
                            painter = painterResource(id = onBoardItems[page].image),
                            contentDescription = "",
                            modifier = Modifier.size(270.dp)
                        )

                        Text(
                            text = onBoardItems[page].title.uppercase(Locale.getDefault()),
                            modifier = Modifier.padding(top = 20.dp),
                            color = Color.Black,
                            style = TextStyle(),
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold, fontSize = 24.sp
                        )

                        Text(
                            text = onBoardItems[page].desc,
                            modifier = Modifier.padding(top = 10.dp, start = 20.dp, end = 20.dp),
                            color = Color.Black,
                            fontFamily = FontFamily.Serif, textAlign = TextAlign.Center,
                            fontSize = 20.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.size(10.dp))
                PagerIndicator(
                    size = onBoardItems.size,
                    currentPage = pagerState.currentPage,
                    pagerState
                )

                Spacer(modifier = Modifier.size(10.dp))

                Box {
                    Column(
                        modifier = Modifier
                            .padding(bottom = 10.dp, start = 30.dp, end = 30.dp, top = 20.dp)
                            .fillMaxWidth()
                    ) {
                        OutlinedButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            onClick = {
                                navController.navigate("login_screen")
                            },
                            shape = RoundedCornerShape(45.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.app_color))
                        ) {
                            Text(
                                text = "LOG IN",
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 30.dp),
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.size(15.dp))
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "New User? ".uppercase(),
                                fontSize = 17.sp,
                                color = Color.Gray,
                                fontFamily = FontFamily.Serif
                            )
                            ClickableText(
                                text = AnnotatedString("REGISTER"), style = TextStyle(
                                    fontSize = 16.sp, fontFamily = FontFamily.Serif,
                                    color = colorResource(id = R.color.app_color)
                                ),
                                onClick = {
                                    navController.navigate("register_screen")
                                }
                            )
                        }
                    }
                }
            }
        }

    }

}


@ExperimentalPagerApi
@Composable
fun PagerIndicator(size: Int, currentPage: Int, pagerState: PagerState) {
    /* Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(20.dp)) {
         repeat(size) {
             IndicatorIcon(isSelected = it == currentPage)
         }
     }*/

    HorizontalPagerIndicator(
        pagerState = pagerState,
        activeColor = colorResource(id = R.color.app_color),
        inactiveColor = Color.Gray
    )
}

@Composable
fun IndicatorIcon(isSelected: Boolean) {
    val width = animateDpAsState(targetValue = if (isSelected) 10.dp else 10.dp)

    Box(
        modifier = Modifier
            .padding(2.dp)
            .height(10.dp)
            .width(width = width.value)
            .clip(CircleShape)
            .background(
                if (isSelected) {
                    colorResource(id = R.color.app_color)
                } else {
                    Color.Gray.copy(alpha = 0.5f)
                }
            )
    )
}

@ExperimentalComposeUiApi
@Composable
fun RegisterScreen(
    loginViewModel: AuthViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        var passwordTextValue by remember {
            mutableStateOf("")
        }
        var confirmPasswordTextValue by remember {
            mutableStateOf("")
        }
        var emailTextValue by remember {
            mutableStateOf("")
        }

        Spacer(modifier = Modifier.size(20.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_tundur_logo),
            contentDescription = "Logo",
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
        )

        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = 3.dp, modifier = Modifier
                .padding(20.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = "SIGN UP",
                    fontSize = 25.sp,
                    modifier = Modifier
                        .padding(5.dp)
                        .offset(x = 5.dp),
                    fontFamily = FontFamily.Serif,
                    style = TextStyle(color = colorResource(id = R.color.black)),
                )

                Text(
                    text = "Please fill information to further continue",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(5.dp)
                        .offset(x = 5.dp),
                    fontFamily = FontFamily.Serif,
                    style = TextStyle(color = colorResource(id = R.color.black))
                )


                TextField(value = emailTextValue,
                    onValueChange = { newText ->
                        emailTextValue = newText
                    },
                    label = { // giving hint label
                        Text(
                            text = "Email Address", style = TextStyle(
                                fontFamily = FontFamily.Serif
                            )
                        )
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusRequester.requestFocus()
                        }
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = colorResource(id = R.color.app_color),
                        backgroundColor = Color.White
                    ), modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .focusRequester(focusRequester)
                )

                Spacer(modifier = Modifier.size(5.dp))

                var passwordVisibility by remember { mutableStateOf(false) }
                var confirmPasswordVisibility by remember { mutableStateOf(false) }

                TextField(value = passwordTextValue,
                    onValueChange = { newText ->
                        passwordTextValue = newText
                    }, visualTransformation = if (passwordVisibility) VisualTransformation.None
                    else PasswordVisualTransformation(),

                    label = { // giving hint label
                        Text(
                            text = "Password",
                            fontFamily = FontFamily.Serif
                        )
                    }, trailingIcon = {
                        val image = if (passwordVisibility)

                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        IconButton(onClick = {
                            passwordVisibility = !passwordVisibility
                        }) {
                            Icon(imageVector = image, "")
                        }
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            //click listener for ime action
                            focusRequester.requestFocus()
                        }
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = colorResource(id = R.color.app_color),
                        backgroundColor = Color.White
                    ), modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .focusRequester(focusRequester)
                )

                Spacer(modifier = Modifier.size(5.dp))

                TextField(value = confirmPasswordTextValue,
                    onValueChange = { newText ->
                        confirmPasswordTextValue = newText
                    },
                    visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None
                    else PasswordVisualTransformation(),

                    label = { // giving hint label
                        Text(
                            text = "Confirm Password",
                            fontFamily = FontFamily.Serif
                        )
                    },
                    trailingIcon = {
                        val image = if (confirmPasswordVisibility)

                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        IconButton(onClick = {
                            confirmPasswordVisibility = !confirmPasswordVisibility
                        }) {
                            Icon(imageVector = image, "")
                        }
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            //click listener for ime action
                            keyboardController?.hide()
                        }
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = colorResource(id = R.color.app_color),
                        backgroundColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .focusRequester(focusRequester)
                )

                Spacer(modifier = Modifier.size(15.dp))
            }
        }//end of card


        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .height(50.dp),
            onClick = { //sending normal string to register screen
                when {
                    emailTextValue.isEmpty() -> {
                        Toast.makeText(
                            context,
                            "Please enter your email.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    passwordTextValue.isEmpty() -> {
                        Toast.makeText(
                            context,
                            "Please enter your password.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    confirmPasswordTextValue.isEmpty() -> {
                        Toast.makeText(
                            context,
                            "Please enter your confirm password.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {
                    }
                }

            },
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.app_color)),
            shape = RoundedCornerShape(30.dp)
        ) {
            Text(
                text = "SIGN UP",
                fontSize = 17.sp,
                color = Color.White,
                fontFamily = FontFamily.Serif
            )
        }

        Spacer(modifier = Modifier.size(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Already have an account? ",
                fontSize = 17.sp,
                color = Color.Gray,
                fontFamily = FontFamily.Serif
            )


            ClickableText(
                text = AnnotatedString("LOG IN"), style = TextStyle(
                    fontSize = 16.sp, fontFamily = FontFamily.Serif,
                    color = colorResource(id = R.color.app_color)
                ),
                onClick = {
                    navController.navigate("login_screen")
                }
            )
        }
    }
}


//@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalComposeUiApi
@Composable
/*
for getting the viewModel object inside composable, we are using hiltViewModel() method to get the instance.
basic syntax=loginViewModel: AuthViewModel = hiltViewModel()
 */
fun LoginScreen(loginViewModel: AuthViewModel = hiltViewModel(), navController: NavHostController) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val activity = LocalContext.current as Activity
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        var passwordTextValue by remember {
            mutableStateOf("")
        }
        var emailTextValue by remember {
            mutableStateOf("")
        }

        Spacer(modifier = Modifier.size(20.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_tundur_logo),
            contentDescription = "Logo",
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
        )

        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = 3.dp, modifier = Modifier
                .padding(20.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = "LOG IN",
                    fontSize = 25.sp,
                    modifier = Modifier
                        .padding(5.dp)
                        .offset(x = 5.dp),
                    fontFamily = FontFamily.Serif,
                    style = TextStyle(color = colorResource(id = R.color.black)),
                )

                Text(
                    text = "Welcome Back!",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(5.dp)
                        .offset(x = 5.dp),
                    fontFamily = FontFamily.Serif,
                    style = TextStyle(color = colorResource(id = R.color.black))
                )


                TextField(value = emailTextValue,
                    onValueChange = { newText ->
                        emailTextValue = newText
                    },
                    label = { // giving hint label
                        Text(
                            text = "Email Address", style = TextStyle(
                                fontFamily = FontFamily.Serif
                            )
                        )
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusRequester.requestFocus()
                        }
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = colorResource(id = R.color.app_color),
                        backgroundColor = Color.White
                    ), modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .focusRequester(focusRequester)
                )

                Spacer(modifier = Modifier.size(5.dp))

                var passwordVisibility by remember { mutableStateOf(false) }

                TextField(value = passwordTextValue,
                    onValueChange = { newText ->
                        passwordTextValue = newText
                    }, visualTransformation = if (passwordVisibility) VisualTransformation.None
                    else PasswordVisualTransformation(),

                    label = { // giving hint label
                        Text(
                            text = "Password",
                            fontFamily = FontFamily.Serif
                        )
                    }, trailingIcon = {
                        val image = if (passwordVisibility)

                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        IconButton(onClick = {
                            passwordVisibility = !passwordVisibility
                        }) {
                            Icon(imageVector = image, "")
                        }
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            //click listener for ime action
                            keyboardController?.hide()
                        }
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = colorResource(id = R.color.app_color),
                        backgroundColor = Color.White
                    ), modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .focusRequester(focusRequester)
                )

                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "Forget Password?",
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(start = 0.dp, top = 5.dp, bottom = 15.dp, end = 5.dp),
                    style = TextStyle(
                        color = colorResource(id = R.color.app_color),
                        textDecoration = TextDecoration.Underline
                    )
                )
            }
        }//end of card


        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .height(50.dp),
            onClick = { //sending normal string to register screen
                when {
                    emailTextValue.isEmpty() -> {
                        Toast.makeText(
                            context,
                            "Please enter your email.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    passwordTextValue.isEmpty() -> {
                        Toast.makeText(
                            context,
                            "Please enter your password.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {
                        //login api using flow
                        val hashMap = HashMap<String, String>()
                        hashMap["email"] = emailTextValue
                        hashMap["password"] = passwordTextValue
                        hashMap["device_token"] = "1234"
                        hashMap["device_type"] = "1"

                        loginViewModel.userLogin(hashMap = hashMap) // api hit

                        val dataStore = PreferenceClass(context) //get dataStore instance

                        /*
                        if you want to show loader on some event click then add it here. otherwise you need to add this scope observer outside this method.
                         */
                        // Since flow run asynchronously,
                        // start listening on background thread
                        scope.launch {
                            loginViewModel.isLoading
                                .collect {
                                    // When state to check the state of received data
                                    when (it.status) {
                                        // If its loading state then  show the progress bar
                                        Status.LOADING -> {
                                            ViewUtils.showProgress(activity)
                                        }
                                        // If api call was a success , Update the Ui with  data and make progress bar invisible
                                        Status.SUCCESS -> {
                                            ViewUtils.hideProgress()
                                            // Received data can be null, put a check to prevent
                                            // null pointer exception
                                            it.data?.let { response ->
                                                if (response.success == true) {
                                                    (context as Activity).toast(response.message)
                                                    activity.callActivity(
                                                        context as ComponentActivity,
                                                        DashBoardActivity()
                                                    )

                                                    dataStore.saveString(
                                                        TOKEN,
                                                        "1"
                                                    ) //save data in datastore
                                                } else {
                                                    (context as Activity).toast(response.message)
                                                }
                                            }
                                        }
                                        // In case of error, show some data to user
                                        else -> {
                                            ViewUtils.hideProgress()
                                            Toast.makeText(
                                                context,
                                                "${it.message}",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                }
                        }//end of scope
                    }
                }

            },
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.app_color)),
            shape = RoundedCornerShape(30.dp)
        ) {
            Text(
                text = "LOG IN",
                fontSize = 17.sp,
                color = Color.White,
                fontFamily = FontFamily.Serif
            )
        }

        Spacer(modifier = Modifier.size(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "New User? ",
                fontSize = 17.sp,
                color = Color.Gray,
                fontFamily = FontFamily.Serif
            )



            ClickableText(
                text = AnnotatedString("REGISTER"), style = TextStyle(
                    fontSize = 16.sp, fontFamily = FontFamily.Serif,
                    color = colorResource(id = R.color.app_color)
                ),
                onClick = {
                    navController.navigate("register_screen")
                }
            )
        }
    }
}

/*
Custom animated Splash screen
 */

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    // AnimationEffect
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
        )
        delay(3000L)
        navController.navigate("main_screen")
    }

    // Image
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.app_color))
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_app_white_icon),
            contentDescription = "Logo",
            modifier = Modifier
                .height(200.dp)
                .width(200.dp)
        )
    }
}




