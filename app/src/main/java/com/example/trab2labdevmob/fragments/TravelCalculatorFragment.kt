package com.example.trab2labdevmob.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.text.InputType
import androidx.fragment.app.Fragment
import com.example.trab2labdevmob.databinding.FragmentTravelCalculatorBinding

class TravelCalculatorFragment : Fragment() {
    private var _binding: FragmentTravelCalculatorBinding? = null
    private val binding get() = _binding!!

    // Definindo as constantes de preço
    private val precoCombustivel = 5.85
    private val precoPedagio = 10.50
    private val eficienciaCarro = 12.0 // 12 km por litro

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTravelCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurando os campos para aceitar apenas números
        binding.editDistancia.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        binding.editVelocidade.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        binding.editPedagios.inputType = InputType.TYPE_CLASS_NUMBER

        // Listener para o botão de calcular
        binding.buttonCalcular.setOnClickListener {
            validarEntradasECalcular()
        }
    }

    // Função para validar os campos e realizar os cálculos
    private fun validarEntradasECalcular() {
        val distanciaTexto = binding.editDistancia.text.toString()
        val velocidadeTexto = binding.editVelocidade.text.toString()
        val pedagiosTexto = binding.editPedagios.text.toString()

        // Verifica se os campos estão vazios
        if (distanciaTexto.isEmpty()) {
            mostrarToast("Por favor, insira a distância.")
            return
        }
        if (velocidadeTexto.isEmpty()) {
            mostrarToast("Por favor, insira a velocidade média.")
            return
        }
        if (pedagiosTexto.isEmpty()) {
            mostrarToast("Por favor, insira a quantidade de pedágios.")
            return
        }

        // Conversão dos valores para os tipos corretos
        val distancia = distanciaTexto.toDoubleOrNull()
        val velocidade = velocidadeTexto.toDoubleOrNull()
        val pedagios = pedagiosTexto.toIntOrNull()

        // Verifica se os valores são válidos
        if (distancia == null || distancia <= 0) {
            mostrarToast("Distância inválida.")
            return
        }
        if (velocidade == null || velocidade <= 0) {
            mostrarToast("Velocidade média inválida.")
            return
        }
        if (pedagios == null || pedagios < 0) {
            mostrarToast("Quantidade de pedágios inválida.")
            return
        }

        // Cálculos
        calcularCustoViagem(distancia, velocidade, pedagios)
    }

    // Função para calcular o custo da viagem
    private fun calcularCustoViagem(distancia: Double, velocidade: Double, pedagios: Int) {
        // Calcula o tempo de viagem (distância / velocidade média)
        val tempo = distancia / velocidade

        // Calcula a quantidade de litros necessários (distância / eficiência do carro)
        val litrosUsados = distancia / eficienciaCarro

        // Calcula o custo com combustível
        val custoCombustivel = litrosUsados * precoCombustivel

        // Calcula o custo com pedágios
        val custoPedagios = pedagios * precoPedagio

        // Calcula o custo total
        val custoTotal = custoCombustivel + custoPedagios

        // Exibe os resultados
        binding.textResultado.text = """
            Tempo de viagem: %.2f horas
            Velocidade média: %.2f km/h
            Distância: %.2f km
            Litros de combustível: %.2f L
            Custo com combustível: R$ %.2f
            Custo com pedágios: R$ %.2f
            Custo total: R$ %.2f
        """.trimIndent().format(tempo, velocidade, distancia, litrosUsados, custoCombustivel, custoPedagios, custoTotal)
    }

    // Função para mostrar uma mensagem Toast
    private fun mostrarToast(mensagem: String) {
        Toast.makeText(requireContext(), mensagem, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}