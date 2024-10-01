package com.example.calculadorabasica_carlos

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val op1: EditText = findViewById(R.id.operando1)
        op1.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

        val r = findViewById<TextView>(R.id.resultado)
        val plusButton = findViewById<ImageButton>(R.id.plusButton)
        val minusButton = findViewById<ImageButton>(R.id.minusButton)
        val multButton = findViewById<ImageButton>(R.id.multiplyButton)
        val divideButton = findViewById<ImageButton>(R.id.divisionButton)
        val result = findViewById<ImageButton>(R.id.result)

        val blueColorFilter = PorterDuffColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN)

        val operacion: MutableList<String> = mutableListOf()

        // Añade dos elementos a la lista de operacion, el simbolo y el operando.
        fun addOperand(operand: Double, sign: String) {

            if(sign == "+" || sign == "-")
                operacion.add(sign)
            else if(sign == "*" || sign == "/")
                operacion.add(sign)
            operacion.add("$operand")
        }

        // Muestra la operacion de forma estetica.
        fun showOperacion(operacion: MutableList<String>): StringBuilder {
            val newOperacion: MutableList<String> = operacion.toMutableList()
            val operationResult = StringBuilder()
            newOperacion.removeFirst()

            for (op in newOperacion) {
                operationResult.append("$op ")
            }
            return operationResult
        }

        //
        fun operate(operacion: MutableList<String>): Double {
            var resultadoActual: Double = 0.0
            var indexActual = 0
            var currentLevel = 2 // Prioridad actual (2 para multiplicación/división, 1 para suma/resta)

            // Si la lista contiene elementos, elimina el primero (asumiendo que no se trata de un numero)
            if (operacion.size > 0) operacion.removeFirst()

            // Si solo queda un número en la lista, ese es el resultado
            if (operacion.size == 1) resultadoActual = operacion[0].toDouble()

            // Si no hay operaciones de multiplicación o división, baja la prioridad a nivel de suma/resta
            if (!operacion.contains("*") && !operacion.contains("/")) currentLevel = 1

            while (indexActual < operacion.size) {
                val op = operacion[indexActual]  // Operador actual

                // Si llegamos al final de la lista, pero aun quedan elementos por recorrer, baja el nivel de prioridad y empieza de nuevo
                if (indexActual >= operacion.size - 1 && currentLevel != 1){
                    indexActual = 0
                    currentLevel--
                    continue
                }

                // Evaluación de multiplicación o división (nivel de prioridad 2)
                if ((op == "*" || op == "/") && currentLevel == 2 && indexActual < operacion.size - 1) {
                    resultadoActual = 0.0

                    val nextValue = operacion[indexActual + 1].toDouble()

                    if (op == "*") {
                        resultadoActual = operacion[indexActual - 1].toDouble() * nextValue
                    } else {
                        resultadoActual = operacion[indexActual - 1].toDouble() / nextValue
                    }

                    // Elimina los elementos que rodean el operador
                    operacion.removeAt(indexActual + 1)
                    operacion.removeAt(indexActual - 1)

                    operacion[indexActual - 1] = resultadoActual.toString()

                    indexActual--
                }
                // Evaluación de suma o resta (nivel de prioridad 1)
                else if ((op == "+" || op == "-") && currentLevel == 1 && indexActual < operacion.size - 1) {
                    resultadoActual = 0.0

                    val nextValue = operacion[indexActual + 1].toDouble()

                    if (op == "+") {
                        resultadoActual = operacion[indexActual - 1].toDouble() + nextValue
                    } else {
                        resultadoActual = operacion[indexActual - 1].toDouble() - nextValue
                    }

                    // Elimina los elementos que rodean el operador
                    operacion.removeAt(indexActual + 1)
                    operacion.removeAt(indexActual - 1)

                    operacion[indexActual - 1] = resultadoActual.toString()

                    indexActual--
                }
                else {
                    indexActual++
                }
            }
            // Limpia la lista de operaciones después de finalizar
            operacion.removeAll(operacion)

            return resultadoActual
        }


        result.setOnClickListener(){
            r.text = operate(operacion).toString()
        }

        plusButton.setOnTouchListener(View.OnTouchListener { view: View, event: MotionEvent ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                plusButton.drawable.colorFilter = blueColorFilter
                val num = op1.text.toString().toDoubleOrNull() ?: 0.0
                addOperand(num, "+")
                op1.text = null

                r.text = showOperacion(operacion)
            }
            if (event.action == MotionEvent.ACTION_UP)
                plusButton.drawable.clearColorFilter()
            return@OnTouchListener true
        })

        minusButton.setOnTouchListener(View.OnTouchListener { view: View, event: MotionEvent ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                minusButton.drawable.colorFilter = blueColorFilter
                val num = op1.text.toString().toDoubleOrNull() ?: 0.0
                op1.text = null
                addOperand(num, "-")

                r.text = showOperacion(operacion)
            }
            if (event.action == MotionEvent.ACTION_UP)
                minusButton.drawable.clearColorFilter()
            return@OnTouchListener true
        })

        multButton.setOnTouchListener(View.OnTouchListener { view: View, event: MotionEvent ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                multButton.drawable.colorFilter = blueColorFilter
                val num = op1.text.toString().toDoubleOrNull() ?: 0.0
                op1.text = null
                addOperand(num, "*")

                r.text = showOperacion(operacion)
            }
            if (event.action == MotionEvent.ACTION_UP)
                multButton.drawable.clearColorFilter()
            return@OnTouchListener true
        })

        divideButton.setOnTouchListener(View.OnTouchListener { view: View, event: MotionEvent ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                divideButton.drawable.colorFilter = blueColorFilter
                val num = op1.text.toString().toDoubleOrNull() ?: 0.0
                op1.text = null
                addOperand(num, "/")

                r.text = showOperacion(operacion)
            }
            if (event.action == MotionEvent.ACTION_UP)
                divideButton.drawable.clearColorFilter()
            return@OnTouchListener true
        })

        /*divideButton.setOnClickListener(){
            divideButton.drawable.colorFilter = blueColorFilter
            minusButton.drawable.clearColorFilter()
            multButton.drawable.clearColorFilter()
            plusButton.drawable.clearColorFilter()

            val num1 = op1.text.toString().toDoubleOrNull() ?: 0.0
            val num2 = op2.text.toString().toDoubleOrNull() ?: 0.0

            val div:Double = (num1 / num2)

            r.text = "$div"
        }*/


    }
}