package com.tahhan.micalculator.model

import androidx.lifecycle.MutableLiveData
import java.lang.Exception

/**
 * CalculatorRepositoryImpl class
 *
 * @property firstOperand
 * @constructor Creates empty CalculatorRepositoryImpl Object
 */
class CalculatorRepositoryImpl(var firstOperand: Float) : CalculatorRepository {
    var operationHistoryLiveData: MutableLiveData<List<Operation>> =
        MutableLiveData()
    var firstOperandLiveData: MutableLiveData<Float> = MutableLiveData()
    private var operationHistory = mutableListOf<Operation>()
    private var undoOperationHistory = mutableListOf<Operation>()

    /**
     * operates the desired calculation
     *
     * @param operation
     * @param secondOperand
     * @return
     */
    override fun operate(operation: String, secondOperand: Int): Any {
        return when (operation) {
            "+" -> doOperation(AddOperation(), secondOperand, operation)

            "-" -> doOperation(SubtractOperation(), secondOperand, operation)

            "/" -> {
                return when (secondOperand) {
                    0 -> Exception("Can't Divide on Zero")
                    else -> {
                        doOperation(DivideOperation(), secondOperand, operation)
                    }
                }
            }
            "*" -> {
                doOperation(MultiplyOperation(), secondOperand, operation)
            }
            else -> Exception("Not Defined Operation")
        }
    }

    /**
     * Do a specific calculation operation
     *
     * @param operationStrategy
     * @param secondOperand
     * @param operation
     * @return
     */
    private fun doOperation(
        operationStrategy: OperationStrategy,
        secondOperand: Int,
        operation: String
    ): Float {
        val tempFirstOperand = firstOperand
        firstOperand = operationStrategy.doOperation(firstOperand, secondOperand, operation)
        operationHistory.add(Operation(operation, tempFirstOperand, secondOperand, firstOperand))
        updateLiveDataObjects()
        return firstOperand
    }

    /**
     * Redo the most recent undo operation
     * @return
     */
    override fun redo(): Float {
        if (undoOperationHistory.isNotEmpty()) {
            val operationHistoryElement = undoOperationHistory.last()
            firstOperand = operationHistoryElement.result
            operationHistory.add(operationHistoryElement)
            updateLiveDataObjects()
            undoOperationHistory.remove(operationHistoryElement)
        }
        return firstOperand
    }

    /**
     * Undo the most recent operation
     * @return
     */
    override fun undo(): Float {
        if (operationHistory.isNotEmpty()) {
            val operationHistoryElement = operationHistory.last()
            operationHistory.remove(operationHistoryElement)
            firstOperand = operationHistoryElement.firstOperand
            updateLiveDataObjects()
            undoOperationHistory.add(operationHistoryElement)
        }
        return firstOperand
    }

    /**
     * resets the first operand and operation history
     */
    override fun reset() {
        firstOperand = 0.0F
        operationHistory = mutableListOf()
        updateLiveDataObjects()
    }

    /**
     * updates the firstOperandLiveData
     * and the operationHistoryLiveData with the their new values
     */
    private fun updateLiveDataObjects() {
        firstOperandLiveData.value = firstOperand
        operationHistoryLiveData.value = operationHistory
    }

}