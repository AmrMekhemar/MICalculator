package com.tahhan.micalculator.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tahhan.micalculator.model.CalculatorRepository
import com.tahhan.micalculator.model.CalculatorRepositoryImpl
import com.tahhan.micalculator.model.Operation
import java.lang.Exception

class CalculatorViewModel(
    private val repository: CalculatorRepositoryImpl,
    private val sharedPrefsManager: SharedPrefsManager
) : ViewModel(), CalculatorRepository {
    override fun operate(operation: String, secondOperand: Int): Any {
        val returnValue = repository.operate(operation, secondOperand)
        if (returnValue !is Exception)
            setFirstOperand(returnValue as Float)
        return returnValue
    }


    override fun redo():Float {
        val returnValue =  repository.redo()
        setFirstOperand(returnValue)
        return returnValue
    }

    override fun undo():Float {
        val returnValue = repository.undo()
        setFirstOperand(returnValue)
        return returnValue
    }

    override fun reset() {
        repository.reset()
        setFirstOperand(0F)
    }

    fun getOperationHistoryLiveData(): MutableLiveData<List<Operation>> =
        repository.operationHistoryLiveData


    fun getFirstOperandLiveData(): MutableLiveData<Float> = repository.firstOperandLiveData

    fun setFirstOperand(value: Float) {
        sharedPrefsManager.firstOperand = value
    }


}