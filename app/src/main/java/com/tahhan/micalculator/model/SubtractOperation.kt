package com.tahhan.micalculator.model

/**
 * Subtract operation
 *
 * @constructor Creates a SubtractOperation object
 */
class SubtractOperation : OperationStrategy {
    /**
     * make a subtraction operation and return the result
     *
     * @param firstOperand
     * @param secondOperand
     * @param operation
     * @return result
     */
    override fun doOperation(
        firstOperand: Float, secondOperand: Int, operation: String
    ) = firstOperand - secondOperand

}