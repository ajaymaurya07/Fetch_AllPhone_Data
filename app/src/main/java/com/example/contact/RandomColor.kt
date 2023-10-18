package com.example.contact

import android.graphics.Color
import kotlin.random.Random


class RandomColors {

    fun randomColors():Int{
        val random = Random.Default
        val red = random.nextInt(256)
        val green = random.nextInt(256)
        val blue = random.nextInt(256)

        return Color.rgb(red,green, blue)
    }
}