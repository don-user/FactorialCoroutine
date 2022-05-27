package ru.yundon.factorialcoroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import ru.yundon.factorialcoroutine.databinding.ActivityMainBinding

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

        binding.button.setOnClickListener {
            viewModel.calculate(binding.editText.text.toString())
        }
    }

    private fun observeViewModel() = with(viewModel){
        progress.observe(this@MainActivity){
            if (it){
                binding.progressBar.visibility = View.VISIBLE
                binding.button.isEnabled = false
            } else{
                binding.progressBar.visibility = View.GONE
                binding.button.isEnabled = true
            }
        }
        error.observe(this@MainActivity){
            if (it) {
                Toast.makeText(
                    this@MainActivity,
                    "You did not entered value",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        factorial.observe(this@MainActivity){
            binding.textView.text = it
        }
    }
}