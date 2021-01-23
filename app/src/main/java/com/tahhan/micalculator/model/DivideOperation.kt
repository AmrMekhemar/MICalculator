package com.tahhan.micalculator.model

/**
 * DivideOperation class
 *
 * @constructor Creates a DivideOperation object
 */
class DivideOperation : OperationStrategy {
    /**
     * make a division operation and return the result
     *
     * @param firstOperand
     * @param secondOperand
     * @param operation
     * @return
     */
    override fun doOperation(
        firstOperand: Float, secondOperand: Int, operation: String
    ) = firstOperand / secondOperand

}