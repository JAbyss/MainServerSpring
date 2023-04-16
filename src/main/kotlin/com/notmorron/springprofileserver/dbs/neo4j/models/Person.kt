package com.notmorron.springprofileserver.dbs.neo4j.models

data class PersonResponse(
    val idUser: Long?,
    val username: String,
    val description: String?,
    val gender: String?,
    val orientation: String?,
    val interests: List<String>?,
    val education: String?,
    val languages: List<String>?,
    val food: String?,
    val alcohol: String?,
    val smoking: String?,
    val family: String?,
    val purposeDating: String?,
    val pets: List<String>?,
    val sport: String?,
    val city: String?,
    val height: String?
)