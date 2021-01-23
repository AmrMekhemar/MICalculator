package com.tahhan.micalculator.model

/**
 * CalculatorRepository Interface
 *
 */
interface CalculatorRepository {
     fun operate(operation:String,secondOperand:Int): Any
     fun redo():Float
     fun undo():Float
     fun reset()
}