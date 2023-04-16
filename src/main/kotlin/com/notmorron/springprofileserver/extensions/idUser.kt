package com.notmorron.springprofileserver.extensions

import org.springframework.web.reactive.function.server.ServerRequest

val ServerRequest.idUser
    get() = this.attribute("idUser").get().toString().toLong()