package com.tahhan.micalculator.model

/**
 * OperationStrategy Interface
 *
 */
interface OperationStrategy {
    fun doOperation(
        firstOperand: Float,
        secondOperand: Int,
        operation: String
    ): Float

}