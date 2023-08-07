package opa

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.choice
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request

class Opa : CliktCommand() {
    val url by argument()
    val method by option(help = "HTTP Method").choice("GET", "POST").required()

    // TODO: Create option for this
    val numOfRequests: Int = 100

    val client = OkHttpClient()

    override fun run() = runBlocking {
        println("opa: URL: [$url], Method: [$method]")

        val requests = mutableListOf<Deferred<Int>>()

        (1..numOfRequests).forEach { requests.add(async { makeRequest(url, method) }) }

        val statusCodes = requests.awaitAll()

        val countByCode: Map<Int, Int> = statusCodes.groupingBy { it }.eachCount()

        println("Results:")
        countByCode.entries.forEach { (code, count) -> println("$code: $count") }
    }

    suspend fun makeRequest(url: String, method: String): Int {
        val request = Request.Builder().url(url).method(method, null).build()
        val response = client.newCall(request).execute()
        response.body?.close()
        return response.code
    }
}

fun main(args: Array<String>) = Opa().main(args)
