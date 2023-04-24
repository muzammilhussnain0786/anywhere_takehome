package com.anywhere.takehome.base

import com.anywhere.takehome.data.TopicsDataSource
import com.anywhere.takehome.data.remote.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRemoteDataSource : TopicsDataSource {

    /**
     * safe Api Call to convert a network response to
     * the generic Result specific to the Data Sources
     * native our application for error handling
     */
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> {

        return withContext(Dispatchers.IO) {
            try {
                val response: Response<T> = apiCall()

                if (response.isSuccessful) {
                    Result.Success(data = response.body()!!)
                } else {
                    Result.Error(errorMessage = response.errorBody()?.string() ?: "Something went wrong")
                }

            } catch (e: HttpException) {
                Result.Error(errorMessage = e.message ?: "Something went wrong", e)
            } catch (e: IOException) {
                Result.Error(errorMessage = "Please check your network connection", e)
            } catch (e: Exception) {
                Result.Error(errorMessage = "Something went wrong", e)
            }
        }
    }

}