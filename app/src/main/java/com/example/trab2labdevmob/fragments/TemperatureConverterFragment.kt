package com.example.trab2labdevmob.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.trab2labdevmob.databinding.FragmentTemperatureConverterBinding
import android.text.InputType

class TemperatureConverterFragment : Fragment() {
    private var _binding: FragmentTemperatureConverterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTemperatureConverterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editCelsius.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

        binding.buttonConverter.setOnClickListener {
            convertTemperature()
        }
    }

    private fun convertTemperature() {
        val celsiusText = binding.editCelsius.text.toString()
        if (celsiusText.isNotEmpty()) {
            val celsius = celsiusText.toDoubleOrNull()
            if (celsius != null) {
                val fahrenheit = (celsius * 9 / 5) + 32
                binding.textResultado.text = String.format("%.1f°F", fahrenheit)
            } else {
                binding.textResultado.text = "Digite um número válido"
            }
        } else {
            binding.textResultado.text = "Digite uma temperatura"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}