package kmp.project.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import kmp.project.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var firstNum = ""
    private var currentNum = ""
    private var currentOperator = ""
    private var result = ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)


        //NoLimitScreen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        //initViews
        binding.apply {
            // get all buttons
            binding.layoutMain.children.filterIsInstance<Button>().forEach { button ->
                //buttons click listener
                button.setOnClickListener {
                    //get clicked button text
                    val buttonText = button.text.toString()
                    when {
                        buttonText.matches(Regex("[0-9]")) -> {
                            if (currentOperator.isEmpty()) {
                                firstNum += buttonText
                                resultUI.text = firstNum
                            } else {
                                currentNum += buttonText
                                resultUI.text = currentNum
                            }
                        }

                        buttonText.matches(Regex("[+\\-*/]")) -> {
                            currentNum = ""
                            if (resultUI.text.toString().isNotEmpty()) {
                                currentOperator = buttonText
                                resultUI.text = "0"
                            }
                        }

                        buttonText == "=" -> {
                            if (currentNum.isNotEmpty() && currentOperator.isNotEmpty()) {
                                formula.text = "$firstNum$currentOperator$currentNum"
                                result = evaluateExpression(firstNum, currentNum, currentOperator)
                                firstNum = result
                                resultUI.text = result
                            }
                        }

                        buttonText == "." -> {
                            if (currentOperator.isEmpty()) {
                                if (!firstNum.contains(".")) {
                                    if (firstNum.isEmpty()) firstNum += "0$buttonText"
                                    else firstNum += buttonText
                                    resultUI.text = firstNum
                                }
                            } else {
                                if (!currentNum.contains(".")) {
                                    if (currentNum.isEmpty()) currentNum += "0$buttonText"
                                    else currentNum += buttonText
                                    resultUI.text = currentNum
                                }
                            }
                        }

                        buttonText == "C" -> {
                            currentNum = ""
                            firstNum = ""
                            currentOperator = ""
                            resultUI.text = "0"
                            formula.text = ""
                        }
                    }
                }
            }


        }
    }

    //functions
    private fun evaluateExpression(
        firstNumber: String,
        secondNumber: String,
        operator: String
    ): String {
        val num1 = firstNumber.toDouble()
        val num2 = secondNumber.toDouble()
        return when (operator) {
            "+" -> (num1 + num2).toString()
            "-" -> (num1 - num2).toString()
            "*" -> (num1 * num2).toString()
            "/" -> (num1 / num2).toString()
            else -> ""
        }
    }
}