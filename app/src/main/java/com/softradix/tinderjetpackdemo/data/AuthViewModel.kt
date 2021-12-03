package com.softradix.tinderjetpackdemo.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softradix.tinderjetpackdemo.app.ApiState
import com.softradix.tinderjetpackdemo.app.Status
import com.softradix.tinderjetpackdemo.modelClass.SignUpResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel // to use viewModel with hilt -use this annotation
class AuthViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    private var _isLoading = MutableStateFlow( // function to show the loader
        ApiState(
            Status.LOADING,
            SignUpResponse(), ""
        )
    )
    val isLoading: StateFlow<ApiState<SignUpResponse>> = _isLoading

    fun userLogin(hashMap: HashMap<String, String>) {
        // Since Network Calls takes time,Set the initial value to loading state

        _isLoading.value = ApiState.loading()

        // ApiCalls takes some time, So it has to be run on background thread. Using viewModelScope
        // to call the api
        viewModelScope.launch {

            // Collecting the data emitted by the function in repository
            userRepository.userLogin(hashMap)
                // If any errors occurs like 404 not found or invalid query, set the state to error
                // State to show some info on screen
                .catch {
                    _isLoading.value =
                        ApiState.error(it.message.toString())
                }

                //If Api call is succeeded, set the State to Success and set the response data to data received from api
                .collect {
                    _isLoading.value = ApiState.success(it.data)
                }
        }

    }

}