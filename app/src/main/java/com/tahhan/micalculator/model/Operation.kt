package com.tahhan.micalculator.model

/**
 * Operation is a model class holds a specific operation data
 *
 * @property operation
 * @property firstOperand
 * @property secondOperand
 * @property result
 * @constructor Creates an Operation Object
 */
data class Operation(
    val operation: String,
    val firstOperand: Float,
    val secondOperand: Int,
    val result: Float
)