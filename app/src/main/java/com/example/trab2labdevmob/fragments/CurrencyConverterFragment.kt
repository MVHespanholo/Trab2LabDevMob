package com.example.trab2labdevmob.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.text.InputType
import androidx.fragment.app.Fragment
import com.example.trab2labdevmob.databinding.FragmentCurrencyConverterBinding

class CurrencyConverterFragment : Fragment() {
    private var _binding: FragmentCurrencyConverterBinding? = null
    private val binding get() = _binding!!

    private val taxaEuroParaDolar = 1.09 // Taxa de conversão EUR para USD
    private val taxaEuroParaReal = 5.37 // Taxa de conversão EUR para BRL

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrencyConverterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar o EditText para aceitar números decimais
        binding.editEuro.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

        // Configurar o botão
        binding.buttonConverter.setOnClickListener {
            converterMoeda()
        }
    }

    private fun converterMoeda() {
        val euroText = binding.editEuro.text.toString()

        if (euroText.isNotEmpty()) {
            val euros = euroText.toDoubleOrNull()

            if (euros != null) {
                // Calcular as conversões
                val dolares = euros * taxaEuroParaDolar
                val reais = euros * taxaEuroParaReal

                // Formatar e exibir os resultados
                binding.textDolares.text = """
                    €${String.format("%.2f", euros)} equivale a:
                    
                    USD $${String.format("%.2f", dolares)}
                    BRL R$${String.format("%.2f", reais)}
                """.trimIndent()
            } else {
                binding.textDolares.text = "Por favor, insira um valor válido"
            }
        } else {
            binding.textDolares.text = "Por favor, insira um valor"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}