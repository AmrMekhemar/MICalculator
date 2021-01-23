package com.tahhan.micalculator.model

interface OperationStrategy {
    fun doOperation(
        firstOperand: Float,
        secondOperand: Int,
        operation: String
    ): Float

}