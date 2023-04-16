package com.notmorron.springprofileserver.routes.feed

import com.notmorron.springprofileserver.routes.feed.handler.FeedHandler
import org.springframework.web.reactive.function.server.CoRouterFunctionDsl

fun CoRouterFunctionDsl.feedRoute(feedHandler: FeedHandler) = run {

    "/feed".nest {
        GET("/profiles", feedHandler::getFeed)
        POST("/profiles", feedHandler::likePerson)
    }
}
