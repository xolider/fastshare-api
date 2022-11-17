package dev.vicart.fastshareapi.utils

import kotlin.random.Random
import kotlin.random.nextInt

object CodeUtil {

    fun generateCode(): Int {
        var number = Random.nextInt(0, 9999)
        while(number.toString().length < 4) {
            number = "0$number".toInt()
        }
        return number
    }
}