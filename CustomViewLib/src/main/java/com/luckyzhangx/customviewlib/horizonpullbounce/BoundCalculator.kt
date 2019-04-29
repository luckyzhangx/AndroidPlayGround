package com.luckyzhangx.customviewlib.horizonpullbounce

import kotlin.math.abs

// Created by luckyzhangx on 2019-04-26.
class BoundCalculator {
    var range = 0
        set(value) {
            field = value
            dragFactor = field * field
        }
    private var dragFactor = range * range

    private var _scaledDistance = 0
    var scaledDistance
        get() = _scaledDistance
        set(value) {
            _scaledDistance = value
            _distance = invertScale(value)
        }

    private var _distance = 0
    var distance
        get() = _distance
        set(value) {
            _distance = value
            _scaledDistance = scale(value)
        }

    private fun checkRange(): Boolean {
        return scaledDistance == ((-dragFactor / (abs(distance) + range)) + range)
    }

    fun eatDistance(dx: Int) {
        distance += dx
    }

    private fun scale(x: Int): Int {
        return (-dragFactor / (abs(x) + range)) + range
    }

    private fun invertScale(scaledX: Int): Int {
        return scaledX * range / (range - scaledX)

    }
}
