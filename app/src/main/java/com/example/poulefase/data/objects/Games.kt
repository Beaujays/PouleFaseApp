package com.example.poulefase.data.objects

data class Games (
    val GameID: String,
    val PoolID: String,
    val HomeName: String,
    val AwayName: String,
    val GoalsHomeTeam: String,
    val GoalsAwayTeam: String,
    val PointsHomeTeam: String,
    val PointsAwayTeam: String,
    val FatigueHomeTeam: String,
    val FatigueAwayTeam: String
)