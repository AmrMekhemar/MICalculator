package com.tahhan.micalculator.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tahhan.micalculator.model.CalculatorRepository
import com.tahhan.micalculator.model.CalculatorRepositoryImpl
import com.tahhan.micalculator.model.Operation
import com.tahhan.micalculator.model.SharedPrefsManager
import java.lang.Exception

/**
 * Calculator viewModel class
 *
 * @property repository
 * @property sharedPrefsManager
 * @constructor Creates a Calculator viewModel object
 */
class CalculatorViewModel(
    private val repository: CalculatorRepositoryImpl,
    private val sharedPrefsManager: SharedPrefsManager
) : ViewModel(), CalculatorRepository {
    /**
     * delegates the operation to the repository and return the result value
     *
     * @param operation
     * @param secondOperand
     * @return
     */
    override fun operate(operation: String, secondOperand: Int): Any {
        val returnValue = repository.operate(operation, secondOperand)
        if (returnValue !is Exception)
            setFirstOperand(returnValue as Float)
        return returnValue
    }

    /**
     * delegates the redo operation to the repository and return the result value
     * @return
     */
    override fun redo():Float {
        val returnValue =  repository.redo()
        setFirstOperand(returnValue)
        return returnValue
    }

    /**
     * delegates the undo operation to the repository and return the result value
     * @return
     */
    override fun undo():Float {
        val returnValue = repository.undo()
        setFirstOperand(returnValue)
        return returnValue
    }

    /**
     * delegates the undo operation to the repository and return the result value
     */
    override fun reset() {
        repository.reset()
        setFirstOperand(0F)
    }

    /**
     * retrieves operationHistoryLiveData from the repository
     *
     * @return
     */
    fun getOperationHistoryLiveData(): MutableLiveData<List<Operation>> =
        repository.operationHistoryLiveData

    /**
     * retrieves firstOperandLiveData from the repository
     *
     * @return
     */
    fun getFirstOperandLiveData(): MutableLiveData<Float> = repository.firstOperandLiveData

    private fun setFirstOperand(value: Float) {
        sharedPrefsManager.firstOperand = value
    }


}