/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package opa

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.option
import opa.http.HttpMethod

class Opa: CliktCommand() {
    val url by argument()

    val method by option(help = "HTTP Method").choice("get", "post")
    override fun run() {
        echo("Hello World!")
    }
}

fun main(args: Array<String>) = Opa().main(args)