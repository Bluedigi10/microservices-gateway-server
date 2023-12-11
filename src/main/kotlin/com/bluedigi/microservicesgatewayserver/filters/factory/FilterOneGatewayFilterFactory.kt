package com.bluedigi.microservicesgatewayserver.filters.factory

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.Optional

@Component
class FilterOneGatewayFilterFactory: AbstractGatewayFilterFactory<FilterOneGatewayFilterFactory.Config>(Config::class.java) {

    private val logger: Logger = LoggerFactory.getLogger(FilterOneGatewayFilterFactory::class.java)

    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            logger.info("ejecutando pre gateway filter factory: ${config.mensaje}")
            chain.filter(exchange).then(Mono.fromRunnable {
                config.cookieValor.let { cookie ->
                    exchange.response.addCookie(ResponseCookie.from(config.cookieNombre, cookie).build())
                }
                logger.info("ejecutando post gateway filter factory: ${config.mensaje}")
            })
        }
    }

    class Config {
        var mensaje: String = ""
        var cookieValor: String = ""
        var cookieNombre: String = ""
    }
}


