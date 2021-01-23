package com.tahhan.micalculator.model

/**
 * MultiplyOperation class
 *
 * @constructor Creates a MultiplyOperation object
 */
class MultiplyOperation : OperationStrategy {
    /**
     * make a multiplication operation and return the result
     *
     * @param firstOperand
     * @param secondOperand
     * @param operation
     * @return
     */
    override fun doOperation(
        firstOperand: Float, secondOperand: Int, operation: String
    ): Float {
        return firstOperand * secondOperand
    }
}
