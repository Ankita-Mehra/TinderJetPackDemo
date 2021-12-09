package com.softradix.tinderjetpackdemo.views.activities

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.softradix.tinderjetpackdemo.R

@Composable
fun OtpTextFields(
    length: Int,
    onFilled: (code: String) -> Unit
) {
    var code: List<Char> by remember { mutableStateOf(listOf()) }
    val focusRequesters: List<FocusRequester> = remember {
        val temp = mutableListOf<FocusRequester>()
        repeat(length) {
            temp.add(FocusRequester())
        }
        temp
    }

        Row(
            modifier = Modifier
                .height(50.dp)
                .padding(start = 10.dp, end = 10.dp)
        ) {
            (0 until length).forEach { index ->
                TextField(
                    modifier = Modifier
                        .width(40.dp)
                        .height(50.dp)
                        .focusOrder(focusRequester = focusRequesters[index]) {
                            focusRequesters[index + 1].requestFocus()
                        },
                    textStyle = MaterialTheme.typography.body2.copy(
                        textAlign = TextAlign.Center, color = Color.Black
                    ),
                    singleLine = true,
                    value = code.getOrNull(index = index)?.takeIf {
                        it.isDigit()
                    }?.toString() ?: "",
                    onValueChange = { value: String ->
                        if (focusRequesters[index].freeFocus()) {
                            val temp = code.toMutableList()
                            if (value == "") {
                                if (temp.size > index) {
                                    temp.removeAt(index = index)
                                    code = temp
                                    focusRequesters.getOrNull(index - 1)?.requestFocus()
                                }
                            } else {
                                if (code.size > index) {
                                    temp[index] = value.getOrNull(0) ?: ' '
                                } else {
                                    temp.add(value.getOrNull(0) ?: ' ')
                                    code = temp
                                    focusRequesters.getOrNull(index + 1)?.requestFocus()
                                        ?: onFilled(
                                            code.joinToString(separator = "")
                                        )
                                }
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ), colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = colorResource(id = R.color.app_color),
                        backgroundColor = Color.White
                    )
                    /* ,
                     visualTransformation = PasswordVisualTransformation()*/
                )

                Spacer(modifier = Modifier.width(15.dp))
            }
        }

}
