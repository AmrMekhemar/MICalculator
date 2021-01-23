package com.tahhan.micalculator.model

class DivideOperation():OperationStrategy{
    override fun doOperation(
        firstOperand: Float, secondOperand: Int, operation: String
    ): Float {
        return firstOperand / secondOperand
    }
}