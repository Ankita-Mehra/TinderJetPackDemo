package com.softradix.tinderjetpackdemo.network

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.softradix.tinderjetpackdemo.R
import com.softradix.tinderjetpackdemo.network.NetworkUtils.connectivityState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun ConnectivityStatus() {
    // This will cause re-composition on every network state change
    val connection by connectivityState()

    val isConnected = connection === ConnectionState.Available

    if (isConnected) {
        // Show UI when connectivity is available
    } else {
        ShowDialog()
        // Show UI for No Internet Connectivity
    }
}

@Composable
fun ShowDialog() {
    Dialog(onDismissRequest = { /*TODO*/ }) {
        //No Internet dialog
        Surface(
            modifier = Modifier
                .padding(5.dp)
                .width(380.dp)
                .height(380.dp),
            shape = RoundedCornerShape(10.dp),
            color = colorResource(id = R.color.app_color)
        ) {

            Spacer(modifier = Modifier.size(10.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp)
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ghost),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp)
                )

                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = stringResource(id = R.string.no_internet_title),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    modifier = Modifier.fillMaxWidth(),
                    fontFamily = FontFamily.Serif,
                    style = TextStyle(color = Color.White)
                )
                Spacer(modifier = Modifier.size(15.dp))
                Text(
                    text = stringResource(id = R.string.no_internet_body),
                    textAlign = TextAlign.Center,
                    fontSize = 19.sp,
                    modifier = Modifier.fillMaxWidth(),
                    fontFamily = FontFamily.Serif,
                    style = TextStyle(color = Color.White)
                )
                Spacer(modifier = Modifier.size(10.dp))
            }
        }
    }

}
