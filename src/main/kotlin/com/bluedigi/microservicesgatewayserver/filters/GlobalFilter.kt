package com.bluedigi.microservicesgatewayserver.filters

import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import org.slf4j.*
import org.springframework.core.Ordered
import org.springframework.http.MediaType
import org.springframework.http.ResponseCookie
import java.util.Optional

@Component
class GlobalFilter: GlobalFilter, Ordered {

    val logger: Logger = LoggerFactory.getLogger(GlobalFilter::class.java)

    override fun filter(exchange: ServerWebExchange?, chain: GatewayFilterChain?): Mono<Void> {
        logger.info("ejecutando filtro pre")
        exchange!!.request.mutate().headers{h -> h.add("token", "123")}
        return chain!!.filter(exchange).then(
            Mono.fromRunnable{
                logger.info("ejecutando filtro post")
                Optional.ofNullable(exchange.request.headers.getFirst("token")).ifPresent {
                    exchange.response.headers.add("token",it)
                }
                exchange.response.cookies.add("color",ResponseCookie.from("color","rojo").build())
                exchange.response.headers.contentType = MediaType.APPLICATION_JSON
            })
    }

    override fun getOrder(): Int {
        return 1
    }
}