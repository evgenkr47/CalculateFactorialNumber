package com.example.factorialapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.factorialapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        observeViewModel()
        binding.btCalculate.setOnClickListener {
            viewModel.calculate(binding.tvEditNumber.text.toString())
        }
    }

    private fun observeViewModel() {

        viewModel.state.observe(this) {
            binding.progressBarLoading.visibility = View.GONE
            binding.btCalculate.isEnabled = true

            when (it) {
                is Error -> {
                    Toast.makeText(
                        this,
                        "You did not entered value",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is Progress -> {
                    binding.progressBarLoading.visibility = View.VISIBLE
                    binding.btCalculate.isEnabled = false
                }

                is Factorial -> {
                    binding.textViewFactorial.text = it.factorial
                }
            }
        }
    }

}