package com.anywhere.takehome.network

import com.anywhere.takehome.data.remote.di.NetworkModule
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockWebServer


class MockNetworkModule(private val mockWebServer: MockWebServer) : NetworkModule() {

    override fun baseUrl(): HttpUrl = mockWebServer.url("/")

}