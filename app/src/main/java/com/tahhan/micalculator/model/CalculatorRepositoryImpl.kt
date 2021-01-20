package com.tahhan.micalculator.model

class CalculatorRepositoryImpl() : CalculatorRepository {
    var firstOperand = 0
    override fun operate(operation: String, secondOperand: Int): Any {
        return 1.0F
    }

    override fun redo() {
        TODO("Not yet implemented")
    }

    override fun undo() {
        TODO("Not yet implemented")
    }
}