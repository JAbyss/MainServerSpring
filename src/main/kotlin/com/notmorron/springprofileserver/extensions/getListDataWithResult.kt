package com.notmorron.springprofileserver.extensions

import org.neo4j.driver.Value

fun <T> org.neo4j.driver.Result.getListDataWithResult(
    body: (Map<String, Any>, Value) -> T
): Result<List<T>> = runCatching {
    return Result.success(this.list().map {
        val rawNode = it.fields().single().value()
        rawNode.asMap().let {
            body(it, rawNode)
        }
    })
}