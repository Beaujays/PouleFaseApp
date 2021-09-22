@file:Suppress("UNCHECKED_CAST")

package com.example.poulefase.data.repositories

import com.example.poulefase.data.objects.Games
import com.example.poulefase.data.objects.PoolTeams
import com.example.poulefase.data.objects.Pools
import com.example.poulefase.data.objects.Teams
import com.google.firebase.firestore.DocumentSnapshot

// Convert object to Firebase object
fun DocumentSnapshot.toGames() = Games (
    GameID = this.id,
    PoolID = this["poolID"] as String,
    HomeName = this["homeName"] as String,
    AwayName = this["awayName"] as String,
    GoalsHomeTeam = this["goalsHomeTeam"] as String,
    GoalsAwayTeam = this["goalsAwayTeam"] as String,
    PointsHomeTeam = this["pointsHomeTeam"] as String,
    PointsAwayTeam = this["pointsAwayTeam"] as String,
    FatigueHomeTeam = this["fatigueHomeTeam"] as String,
    FatigueAwayTeam = this["fatigueAwayTeam"] as String
)

fun DocumentSnapshot.toPools() = Pools (
    ID = this.id,
    PoolID = this["poolID"] as String,
    PoolName = this["poolName"] as String
)
fun DocumentSnapshot.toTeams() = Teams(
    TeamID = this.id,
    TeamName = this["teamName"] as String,
    FatiguePercentage = this["fatiguePercentage"] as String,
    Strength = this["strength"] as String
)
fun DocumentSnapshot.toPoolTeams() = PoolTeams(
        PoolTeamsID = this.id,
        GamesPlayed = this["gamesPlayed"] as String,
        SequenceNb = this["sequenceNb"] as String,
        PoolID = this["poolID"] as String,
        TeamID = this["teamID"] as String,
        TeamName = this["teamName"] as String,
        PointsFor = this["pointsFor"] as String,
        PointsAgainst = this["pointsAgainst"] as String,
        GoalsFor = this["goalsFor"] as String,
        GoalsAgainst = this["goalsAgainst"] as String,
        FatiguePercentage = this["fatiguePercentage"] as String,
        Strength = this["strength"] as String
)

// Convert object to FireBase object
fun Games.toGamesData() = mapOf (
    "poolID" to this.PoolID,
    "homeName" to this.HomeName,
    "awayName" to this.AwayName,
    "goalsHomeTeam" to this.GoalsHomeTeam,
    "goalsAwayTeam" to this.GoalsAwayTeam,
    "pointsHomeTeam" to this.PointsHomeTeam,
    "pointsAwayTeam" to this.PointsAwayTeam,
    "fatigueHomeTeam" to this.FatigueHomeTeam,
    "fatigueAwayTeam" to this.PointsAwayTeam
)

fun Pools.toPoolsData() = mapOf (
        "poolID" to this.PoolID,
        "poolName" to this.PoolName
)
fun Teams.toTeamsData() = mapOf(
    "teamName" to this.TeamName,
    "fatiguePercentage" to this.FatiguePercentage,
    "strength" to this.Strength
)

fun PoolTeams.toPoolTeamsData() = mapOf(
        "poolID" to this.PoolID,
        "gamesPlayed" to this.GamesPlayed,
        "sequenceNb" to this.SequenceNb,
        "teamID" to this.TeamID,
        "teamName" to this.TeamName,
        "pointsFor" to this.PointsFor,
        "pointsAgainst" to this.PointsAgainst,
        "goalsFor" to this.PointsFor,
        "goalsAgainst" to this.PointsAgainst,
        "fatiguePercentage" to this.FatiguePercentage,
        "strength" to this.Strength
)