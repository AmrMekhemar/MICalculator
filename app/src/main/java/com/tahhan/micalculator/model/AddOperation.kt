package com.tahhan.micalculator.model

/**
 * AddOperation class
 *
 * @constructor Creates  AddOperation object
 */
class AddOperation : OperationStrategy {
    /**
     * make a addition operation and return the result
     *
     * @param firstOperand
     * @param secondOperand
     * @param operation
     */
    override fun doOperation(
        firstOperand: Float, secondOperand: Int, operation: String
    ) = firstOperand + secondOperand

}