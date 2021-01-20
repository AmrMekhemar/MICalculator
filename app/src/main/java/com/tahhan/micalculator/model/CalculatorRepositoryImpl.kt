package com.tahhan.micalculator.model
import java.lang.Exception
class CalculatorRepositoryImpl(var firstOperand: Float) : CalculatorRepository {

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
        TODO("Not yet implemented")
    }

    override fun undo() {
        TODO("Not yet implemented")
    }

    private fun divide(secondOperand: Int): Any {
        return when (secondOperand) {
            0 -> Exception("Can't Divide on Zero")
            else ->{
                firstOperand /= secondOperand
                firstOperand
            }
        }
    }

    private fun add(secondOperand: Int):Float{
        firstOperand += secondOperand
        return firstOperand
    }

    private fun subtract(secondOperand: Int):Float{
        firstOperand -= secondOperand
        return firstOperand
    }

    private fun multiply(secondOperand: Int):Float{
        firstOperand *= secondOperand
        return firstOperand
    }
}