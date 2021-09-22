package com.example.poulefase.data.objects

data class PoolTeams(
    val PoolTeamsID: String = "Placeholder",
    val GamesPlayed: String,
    var SequenceNb: String,
    val PoolID: String,
    var TeamID: String,
    val TeamName: String,
    val PointsFor: String,
    val PointsAgainst: String,
    val GoalsFor: String,
    val GoalsAgainst: String,
    val FatiguePercentage: String,
    val Strength: String
)