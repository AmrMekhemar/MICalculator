package com.tahhan.micalculator.model

import androidx.lifecycle.MutableLiveData
import java.lang.Exception

private const val TAG = "CalculatorRepositoryImp"

class CalculatorRepositoryImpl(var firstOperand: Float) : CalculatorRepository {
    var operationHistoryLiveData: MutableLiveData<List<Operation>> =
        MutableLiveData()
    var firstOperandLiveData: MutableLiveData<Float> = MutableLiveData()
    private var operationHistory = mutableListOf<Operation>()
    private var undoOperationHistory = mutableListOf<Operation>()


    override fun operate(operation: String, secondOperand: Int): Any {
        return when (operation) {
            "+" -> add(secondOperand)
            "-" -> subtract(secondOperand)
            "/" -> divide(secondOperand)
            "*" -> multiply(secondOperand)
            else -> Exception("Not Defined Operation")
        }
    }


    override fun redo(): Float {
        if (undoOperationHistory.isNotEmpty()) {
            val operationHistoryElement = undoOperationHistory.last()
            firstOperand = operationHistoryElement.result
            firstOperandLiveData.value = firstOperand
            operationHistory.add(operationHistoryElement)
            operationHistoryLiveData.value = operationHistory
            undoOperationHistory.remove(operationHistoryElement)
        }
        return firstOperand
    }

    override fun undo(): Float {
        if (operationHistory.isNotEmpty()) {
            val operationHistoryElement = operationHistory.last()
//            Log.d(TAG,"first operand before: $firstOperand")
            operationHistory.remove(operationHistoryElement)
            firstOperand = operationHistoryElement.firstOperand
//            Log.d(TAG,"first operand after: $firstOperand")
            firstOperandLiveData.value = firstOperand
//            Log.d(TAG,"first operand livedata after: $firstOperand")
            operationHistoryLiveData.value = operationHistory
            undoOperationHistory.add(operationHistoryElement)
        }
        return firstOperand

    }

    override fun reset() {
        firstOperand = 0.0F
        firstOperandLiveData.value = firstOperand
        operationHistory = mutableListOf()
        operationHistoryLiveData.value = operationHistory
    }

    private fun divide(secondOperand: Int): Any {
        return when (secondOperand) {
            0 -> Exception("Can't Divide on Zero")
            else -> {
                val temporaryFirstOperand = firstOperand
                firstOperand /= secondOperand
                addToOperationsHistory(
                    Operation("/", temporaryFirstOperand, secondOperand, firstOperand)
                )
                firstOperand
            }
        }
    }

    private fun add(secondOperand: Int): Float {
        val temporaryFirstOperand = firstOperand
        firstOperand += secondOperand
        addToOperationsHistory(
            Operation("+", temporaryFirstOperand, secondOperand, firstOperand)
        )
        return firstOperand
    }

    private fun subtract(secondOperand: Int): Float {
        val temporaryFirstOperand = firstOperand
        firstOperand -= secondOperand
        addToOperationsHistory(
            Operation("-", temporaryFirstOperand, secondOperand, firstOperand)
        )
        return firstOperand
    }

    private fun multiply(secondOperand: Int): Float {
        val temporaryFirstOperand = firstOperand
        firstOperand *= secondOperand
        addToOperationsHistory(
            Operation("*", temporaryFirstOperand, secondOperand, firstOperand)
        )
        return firstOperand
    }

    private fun addToOperationsHistory(operation: Operation) {
        operationHistory.add(operation)
        operationHistoryLiveData.value = operationHistory
        firstOperandLiveData.value = firstOperand
    }
}