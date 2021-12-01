package com.softradix.tinderjetpackdemo.data

import com.softradix.tinderjetpackdemo.app.ApiState
import com.softradix.tinderjetpackdemo.modelClass.SignUpResponse
import com.softradix.tinderjetpackdemo.retrofit.ApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class UserRepository @Inject constructor(private val apiInterface: ApiInterface) {

    suspend fun userLogin(hashMap: HashMap<String, String>): Flow<ApiState<SignUpResponse>> {
        return flow {

            // get the comment Data from the api
            val response = apiInterface.login(hashMap)

            // Emit this data wrapped in
            // the helper class [ApiState]
            emit(ApiState.success(response))
        }.flowOn(Dispatchers.IO)
    }
}