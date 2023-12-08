package com.bluedigi.microservicesgatewayserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MicroservicesGatewayServerApplication

fun main(args: Array<String>) {
    runApplication<MicroservicesGatewayServerApplication>(*args)
}
