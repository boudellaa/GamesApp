package com.example.prvaspirala

data class UserReview(
    override val username: String,
    override val timestamp: Long,
    val review: String
) : UserImpression(username, timestamp)