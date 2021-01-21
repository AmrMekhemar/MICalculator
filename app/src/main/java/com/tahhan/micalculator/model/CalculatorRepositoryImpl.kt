package com.tahhan.micalculator.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import java.lang.Exception

private const val TAG = "CalculatorRepositoryImp"

class CalculatorRepositoryImpl(var firstOperand: Float) : CalculatorRepository {
    private var operationHistory = mutableListOf<Triple<String, Int, Float>>()
    var operationHistoryLiveData: MutableLiveData<List<Triple<String, Int, Float>>> =
        MutableLiveData()
    var firstOperandLiveData: MutableLiveData<Float> = MutableLiveData()
    private var undoOperationHistory = mutableListOf<Triple<String, Int, Float>>()
    var recentResults = mutableListOf<Float>()


    override fun operate(operation: String, secondOperand: Int): Any {
        return when (operation) {
            "+" -> add(secondOperand)
            "-" -> subtract(secondOperand)
            "/" -> divide(secondOperand)
            "*" -> multiply(secondOperand)
            else -> Exception("Not Defined Operation")
        }
    }


    override fun redo() {
        if (undoOperationHistory.isNotEmpty()) {
            val operationHistoryElement = undoOperationHistory.last()
            firstOperand = recentResults.last()
            firstOperandLiveData.value = firstOperand
            operationHistory.add(operationHistoryElement)
            operationHistoryLiveData.value = operationHistory
            undoOperationHistory.remove(operationHistoryElement)
            recentResults.remove(firstOperand)
        }

    }

    override fun undo() {
        if (operationHistory.isNotEmpty()) {
            val operationHistoryElement = operationHistory.last()
            recentResults.add(firstOperand)
            firstOperand = operationHistoryElement.third
            firstOperandLiveData.value = firstOperand
            operationHistory.remove(operationHistoryElement)
            operationHistoryLiveData.value = operationHistory
            undoOperationHistory.add(operationHistoryElement)
        }
    }

    override fun reset() {
        firstOperand = 0F
        firstOperandLiveData.value = firstOperand
        operationHistory = mutableListOf()
        operationHistoryLiveData.value = operationHistory
    }

    private fun divide(secondOperand: Int): Any {
        return when (secondOperand) {
            0 -> Exception("Can't Divide on Zero")
            else -> {
                firstOperand /= secondOperand
                addToOperationsHistory(Triple("/",secondOperand,firstOperand)
                )
                firstOperandLiveData.value = firstOperand

                firstOperand
            }
        }
    }

    private fun add(secondOperand: Int): Float {
        firstOperand += secondOperand
        addToOperationsHistory(Triple("+",secondOperand,firstOperand)
        )
        firstOperandLiveData.value = firstOperand

        Log.d(TAG, "operations::: ${firstOperandLiveData.value}")
        return firstOperand
    }

    private fun subtract(secondOperand: Int): Float {
        firstOperand -= secondOperand
        addToOperationsHistory(Triple("-",secondOperand,firstOperand)
        )
        firstOperandLiveData.value = firstOperand
        return firstOperand
    }

    private fun multiply(secondOperand: Int): Float {
        firstOperand *= secondOperand
        firstOperandLiveData.value = firstOperand
        addToOperationsHistory(Triple("*",secondOperand,firstOperand)
        )
        return firstOperand
    }

    private fun addToOperationsHistory(operation:Triple<String,Int,Float>){
        operationHistory.add(operation)
        operationHistoryLiveData.value = operationHistory
    }
}