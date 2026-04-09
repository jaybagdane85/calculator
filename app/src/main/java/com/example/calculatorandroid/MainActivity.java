package com.example.calculatorandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvDisplay;
    private String currentNumber = "";
    private String operator = "";
    private double firstNumber = 0;
    private boolean isOperatorPressed = false;
    private boolean isEqualsPressed = false;
    private DecimalFormat decimalFormat = new DecimalFormat("#.########");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize display
        tvDisplay = findViewById(R.id.tvDisplay);

        // Set up all buttons with onClickListener
        int[] buttonIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide,
                R.id.btnEquals, R.id.btnClear, R.id.btnDecimal,
                R.id.btnPlusMinus, R.id.btnPercent
        };

        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Button button = (Button) view;
        String buttonText = button.getText().toString();

        // Number buttons
        if (id == R.id.btn0 || id == R.id.btn1 || id == R.id.btn2 || id == R.id.btn3 ||
                id == R.id.btn4 || id == R.id.btn5 || id == R.id.btn6 || id == R.id.btn7 ||
                id == R.id.btn8 || id == R.id.btn9) {
            handleNumberInput(buttonText);
        }

        // Operator buttons
        else if (id == R.id.btnAdd) {
            handleOperatorInput("+");
        } else if (id == R.id.btnSubtract) {
            handleOperatorInput("-");
        } else if (id == R.id.btnMultiply) {
            handleOperatorInput("×");
        } else if (id == R.id.btnDivide) {
            handleOperatorInput("÷");
        }

        // Function buttons
        else if (id == R.id.btnEquals) {
            handleEquals();
        } else if (id == R.id.btnClear) {
            handleClear();
        } else if (id == R.id.btnDecimal) {
            handleDecimal();
        } else if (id == R.id.btnPlusMinus) {
            handlePlusMinus();
        } else if (id == R.id.btnPercent) {
            handlePercent();
        }
    }

    private void handleNumberInput(String number) {
        if (isEqualsPressed) {
            currentNumber = "";
            isEqualsPressed = false;
        }

        if (isOperatorPressed) {
            currentNumber = "";
            isOperatorPressed = false;
        }

        if (currentNumber.equals("0") && !number.equals("0")) {
            currentNumber = number;
        } else if (!currentNumber.equals("0")) {
            currentNumber += number;
        }

        updateDisplay(currentNumber);
    }

    private void handleOperatorInput(String op) {
        if (!currentNumber.isEmpty()) {
            if (!operator.isEmpty() && !isOperatorPressed) {
                handleEquals();
            }
            firstNumber = Double.parseDouble(currentNumber);
        }
        operator = op;
        isOperatorPressed = true;
        isEqualsPressed = false;
    }

    private void handleEquals() {
        if (!operator.isEmpty() && !currentNumber.isEmpty() && !isOperatorPressed) {
            double secondNumber = Double.parseDouble(currentNumber);
            double result = 0;

            switch (operator) {
                case "+":
                    result = firstNumber + secondNumber;
                    break;
                case "-":
                    result = firstNumber - secondNumber;
                    break;
                case "×":
                    result = firstNumber * secondNumber;
                    break;
                case "÷":
                    if (secondNumber != 0) {
                        result = firstNumber / secondNumber;
                    } else {
                        updateDisplay("Error");
                        return;
                    }
                    break;
            }

            currentNumber = formatResult(result);
            updateDisplay(currentNumber);
            operator = "";
            isEqualsPressed = true;
        }
    }

    private void handleClear() {
        currentNumber = "";
        operator = "";
        firstNumber = 0;
        isOperatorPressed = false;
        isEqualsPressed = false;
        updateDisplay("0");
    }

    private void handleDecimal() {
        if (isEqualsPressed || isOperatorPressed) {
            currentNumber = "0";
            isEqualsPressed = false;
            isOperatorPressed = false;
        }

        if (currentNumber.isEmpty()) {
            currentNumber = "0";
        }

        if (!currentNumber.contains(".")) {
            currentNumber += ".";
            updateDisplay(currentNumber);
        }
    }

    private void handlePlusMinus() {
        if (!currentNumber.isEmpty() && !currentNumber.equals("0")) {
            if (currentNumber.startsWith("-")) {
                currentNumber = currentNumber.substring(1);
            } else {
                currentNumber = "-" + currentNumber;
            }
            updateDisplay(currentNumber);
        }
    }

    private void handlePercent() {
        if (!currentNumber.isEmpty()) {
            double number = Double.parseDouble(currentNumber);
            number = number / 100;
            currentNumber = formatResult(number);
            updateDisplay(currentNumber);
        }
    }

    private void updateDisplay(String text) {
        tvDisplay.setText(text);
    }

    private String formatResult(double result) {
        if (result == (long) result) {
            return String.valueOf((long) result);
        } else {
            return decimalFormat.format(result);
        }
    }
}
