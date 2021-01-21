package com.tahhan.micalculator.view

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tahhan.micalculator.R
import com.tahhan.micalculator.model.CalculatorRepositoryImpl
import com.tahhan.micalculator.toast
import com.tahhan.micalculator.viewmodel.CalculatorViewModel
import com.tahhan.micalculator.viewmodel.CalculatorViewModelFactory
import kotlinx.android.synthetic.main.fragment_calculator.*
import java.lang.Exception


private const val SHARED_PREFS = "1001"
private const val RESULT_KEY = "1002"
private const val TAG = "CalculatorFragment"


class CalculatorFragment : Fragment() {
    private var firstOperand = 0.0f
        get() = sharedPrefs().getFloat(RESULT_KEY, 0F)
        set(value) {
            sharedPrefs().edit().putFloat(RESULT_KEY, value).apply()
            field = value
        }
    private var operation = ""
    private var secondOperand = ""


    private val viewModel: CalculatorViewModel by viewModels {
        CalculatorViewModelFactory(CalculatorRepositoryImpl(firstOperand))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resultTextView.text = firstOperand.toString()
        setupListeners()
        observeFirstOperand()
        populateRV()
    }

    private fun observeOperationHistory() {
        viewModel.getOperationHistoryLiveData().observe(viewLifecycleOwner, Observer {
            // Log.d(TAG,"operation= $it")
            historyRV.adapter = OperationAdapter(it) { operationItem ->
                secondOperand = operationItem.secondOperand.toString()
                operation = operationItem.operation
                when (operation){
                    "+"-> operation = "-"
                    "-"-> operation = "+"
                    "*"-> operation = "/"
                    "/"-> operation = "*"
                    else-> {}
                }
                operate(secondOperand)
            }
        })
    }

    private fun observeFirstOperand() {
        viewModel.getFirstOperandLiveData().observe(viewLifecycleOwner, Observer {
            firstOperand = it
            resultTextView.text = firstOperand.toString()
        })
    }

    private fun setupListeners() {
        divisionButton.setOnClickListener {
            operation = "/"
            makeButtonsNonClickable(operation)
        }
        minusButton.setOnClickListener {
            operation = "-"
            makeButtonsNonClickable(operation)
        }
        plusButton.setOnClickListener {
            operation = "+"
            makeButtonsNonClickable(operation)
        }
        multiplyButton.setOnClickListener {
            operation = "*"
            makeButtonsNonClickable(operation)
        }
        equalButton.setOnClickListener {
            secondOperand = enterNumberEditText.text.toString()
            operate(secondOperand)
            makeButtonsClickable()
            populateRV()
        }
        resetButton.setOnClickListener {
            secondOperand = ""
            makeButtonsClickable()
            viewModel.reset()
        }

        deselectButton.setOnClickListener {
            operation = ""
            makeButtonsClickable()
        }
        redoButton.setOnClickListener {
            viewModel.redo()
            makeButtonsClickable()
        }
        undoButton.setOnClickListener {
            viewModel.undo()
            makeButtonsClickable()
        }
    }

    private fun operate(secondOperand: String) {
        if (operation.isNotBlank() && secondOperand.isNotBlank()) {
            val returnValue = viewModel.operate(operation, secondOperand.toInt())
            if (returnValue !is Exception) {
                firstOperand = returnValue.toString().toFloat()
                resultTextView.text = firstOperand.toString()
            }
        } else requireContext().toast("you must click an operation and enter a number")
    }


    private fun sharedPrefs() =
        requireContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

    private fun makeButtonsNonClickable(operation: String) {
        val gray = Color.GRAY
        when (operation) {
            "+" -> {
                divisionButton.isClickable = false
                minusButton.isClickable = false
                multiplyButton.isClickable = false
                multiplyButton.setBackgroundColor(gray)
                divisionButton.setBackgroundColor(gray)
                minusButton.setBackgroundColor(gray)
            }
            "-" -> {
                divisionButton.isClickable = false
                plusButton.isClickable = false
                multiplyButton.isClickable = false
                divisionButton.setBackgroundColor(gray)
                plusButton.setBackgroundColor(gray)
                multiplyButton.setBackgroundColor(gray)
            }
            "/" -> {
                minusButton.isClickable = false
                plusButton.isClickable = false
                multiplyButton.isClickable = false
                minusButton.setBackgroundColor(gray)
                plusButton.setBackgroundColor(gray)
                multiplyButton.setBackgroundColor(gray)
            }
            "*" -> {
                divisionButton.isClickable = false
                plusButton.isClickable = false
                minusButton.isClickable = false
                divisionButton.setBackgroundColor(gray)
                plusButton.setBackgroundColor(gray)
                minusButton.setBackgroundColor(gray)
            }
        }
    }

    private fun makeButtonsClickable() {
        val primaryColor = ContextCompat.getColor(requireContext(), R.color.purple_700)
        divisionButton.isClickable = true
        minusButton.isClickable = true
        multiplyButton.isClickable = true
        plusButton.isClickable = true
        multiplyButton.setBackgroundColor(primaryColor)
        divisionButton.setBackgroundColor(primaryColor)
        minusButton.setBackgroundColor(primaryColor)
        plusButton.setBackgroundColor(primaryColor)
        enterNumberEditText.text?.clear()
    }


    private fun populateRV() {
        historyRV.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL, false
        )
        observeOperationHistory()
    }

}


