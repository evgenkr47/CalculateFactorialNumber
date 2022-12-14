package com.example.factorialapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.factorialapp.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

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
        binding.buttonCalculate.setOnClickListener {
            viewModel.calculate(binding.editTextNumber.text.toString())
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.onEach {
                binding.progressBarLoading.visibility = View.GONE
                binding.buttonCalculate.isEnabled = true

                when (it) {
                    is Error -> {
                        Toast.makeText(
                            this@MainActivity,
                            "You did not entered value",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is Progress -> {
                        binding.progressBarLoading.visibility = View.VISIBLE
                        binding.buttonCalculate.isEnabled = false
                    }

                    is Factorial -> {
                        binding.textViewFactorial.text = it.factorial
                    }
                }
            }.collect()
        }



    }

}