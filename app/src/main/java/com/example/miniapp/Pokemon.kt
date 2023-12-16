package com.example.miniapp

data class Pokemon(
    val name: String,
    val imageUrl: String,
    val type: String,
    val ability: String,
    val size: Int,
    val weight: Int,
    val evolution: String? // 진화 정보 추가
)

