package com.example.poulefase.data.objects

data class PoolTeams(
    val PoolTeamsID: String = "Placeholder",
    val GamesPlayed: String,
    var SequenceNb: String,
    val PoolID: String,
    var TeamID: String,
    val TeamName: String,
    var PointsFor: Long,
    var PointsAgainst: Long,
    var GoalsFor: Long,
    var GoalsAgainst: Long,
    val FatiguePercentage: String,
    val Strength: String
)