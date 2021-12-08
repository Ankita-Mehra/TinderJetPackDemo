package com.softradix.tinderjetpackdemo.modelClass

import com.softradix.tinderjetpackdemo.R

data class OnBoardModel(val image: Int, val title: String, val desc: String)

val onBoardItems = listOf(
    OnBoardModel(
        R.drawable.ic_welcome1,
        "Tundur",
        "Discover new and interesting people  and have fun with them"
    ),
    OnBoardModel(
        R.drawable.ic_welcome2,
        "Update your Profile",
        "Please update your profile to a perfect match. Discover new and interesting people and have fun with them"
    ),
    OnBoardModel(
        R.drawable.ic_welcome3,
        "Fall in love with",
        "Meet new people and have fun together"
    ),
    OnBoardModel(
        R.drawable.ic_welcome4,
        "find your perfect match",
        "You will find your dream partner with the right matches"
    )
)