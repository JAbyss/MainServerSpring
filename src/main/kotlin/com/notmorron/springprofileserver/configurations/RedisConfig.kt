package com.notmorron.springprofileserver.configurations

import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

object Redis{
    val template = RedisTemplate<String, Any>()
    init {
        template.setConnectionFactory(LettuceConnectionFactory())
    }
    fun setValue(key: String, value: Any) {
        template.opsForValue().set(key, value)
    }

    fun getValue(key: String): Any? {
        return template.opsForValue().get(key)
    }

    fun deleteValue(key: String) {
        template.delete(key)
    }
}