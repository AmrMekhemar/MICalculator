package com.tahhan.micalculator.model

class SubtractOperation : OperationStrategy {
    override fun doOperation(
        firstOperand: Float, secondOperand: Int, operation: String
    ): Float {
        return firstOperand - secondOperand
    }
}