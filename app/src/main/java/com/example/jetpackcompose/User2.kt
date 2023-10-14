package com.example.jetpackcompose

import android.os.Parcelable
import java.time.LocalDateTime


data class User2(
    val name: String,
    val id: String,
    val created: LocalDateTime
)
