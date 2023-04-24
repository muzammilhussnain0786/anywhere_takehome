package com.anywhere.takehome.network

import com.anywhere.takehome.data.remote.DuckDuckGoService
import com.anywhere.takehome.data.remote.RemoteTopicsDataSource
import com.anywhere.takehome.data.remote.Result
import com.anywhere.takehome.data.remote.models.DuckDuckGoResponse
import com.anywhere.takehome.domain.TopicsRepositoryImpl
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import java.net.HttpURLConnection

class NetworkTest {

    private lateinit var network: MockNetworkModule
    private lateinit var json: Json
    private lateinit var retrofit: Retrofit
    private lateinit var service: DuckDuckGoService
    private val mockWebServer: MockWebServer = MockWebServer()
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher()
    val jsonResponse = ClassLoader.getSystemClassLoader().getResource("api_response.json").readText()
    private lateinit var repository: TopicsRepositoryImpl

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockWebServer.start()
        network = MockNetworkModule(mockWebServer)
        json = network.provideKotlinSerializationJson()
        retrofit = network.provideRetrofitClient(json)
        service = network.provideDuckDuckGoService(retrofit)
        repository = TopicsRepositoryImpl(RemoteTopicsDataSource(service), mockk())
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return if (request.requestUrl?.queryParameter("q") == "404"){
                    MockResponse().setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
                } else {
                    MockResponse().setResponseCode(HttpURLConnection.HTTP_OK).setBody(jsonResponse)
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown(){
        mockWebServer.shutdown()
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test DuckDuckGo Api Success and Parsing`() = runTest(testDispatcher) {
        val result = repository.getRelatedTopics("")

        assert(result is Result.Success)

        val decodedResponse = json.decodeFromString<DuckDuckGoResponse>(jsonResponse)
        assertEquals(decodedResponse.relatedTopics.size, (result as Result.Success).data.size)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test 404 case`() = runTest(testDispatcher) {
        val result = repository.getRelatedTopics("404")

        assert(result is Result.Error)
    }
}