package com.notmorron.springprofileserver.configurations

import com.notmorron.springprofileserver.configurations.filters.authFilter
import com.notmorron.springprofileserver.routes.feed.feedRoute
import com.notmorron.springprofileserver.routes.feed.handler.FeedHandler
import com.notmorron.springprofileserver.routes.profile.handler.ProfileHandler
import com.notmorron.springprofileserver.routes.profile.profileRoute
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class ProfileRouteConfiguration(
    private val profileHandler: ProfileHandler,
    private val feedHandler: FeedHandler
) {

    @Bean
    fun apiRouter() = coRouter {

        authFilter
        profileRoute(profileHandler)
        feedRoute(feedHandler)
    }
}

