package com.bluedigi.microservicesgatewayserver.filters.factory

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class FilterOneGatewayFilterFactory: GatewayFilterFactory<FilterOneGatewayFilterFactory.Configuracion> {

    private val logger: Logger = LoggerFactory.getLogger(FilterOneGatewayFilterFactory::class.java)

    override fun apply(config: Configuracion): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            logger.info("ejecutando pre gateway filter factory: ${config.mensaje}")
            chain.filter(exchange).then(Mono.fromRunnable {
                config.cookieValor?.let { cookie ->
                    exchange.response.addCookie(ResponseCookie.from(config.cookieNombre.toString(), cookie).build())
                }

                logger.info("ejecutando post gateway filter factory: ${config.mensaje}")
            })
        }
    }

    override fun name(): String {
        return "EjemploCookie"
    }

    override fun shortcutFieldOrder(): List<String> {
        return listOf("mensaje", "cookieNombre", "cookieValor")
    }

    class Configuracion {
        var mensaje: String? = null
        var cookieValor: String? = null
        var cookieNombre: String? = null
    }
}


