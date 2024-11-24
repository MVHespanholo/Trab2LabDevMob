package com.example.trab2labdevmob.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.text.InputType
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.trab2labdevmob.databinding.FragmentPowerConsumptionBinding

class PowerConsumptionFragment : Fragment() {
    private var _binding: FragmentPowerConsumptionBinding? = null
    private val binding get() = _binding!!

    // Definindo as constantes
    private val tarifaPadrao = 0.75 // Valor padrão da tarifa
    private val diasNoMes = 30 // Média de dias no mês

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPowerConsumptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar os campos para aceitar apenas números
        binding.editPotencia.inputType = InputType.TYPE_CLASS_NUMBER
        binding.editHoras.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        binding.editDias.inputType = InputType.TYPE_CLASS_NUMBER
        binding.editTarifa.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

        // Configurar hints com valores padrão
        binding.editTarifa.hint = "Tarifa kWh (Padrão: R$ ${String.format("%.2f", tarifaPadrao)})"
        binding.editDias.hint = "Dias de uso no mês: $diasNoMes"

        // Listener para o botão de calcular
        binding.buttonCalcular.setOnClickListener {
            calcularConsumo()
        }

        // Listener para o botão de limpar
        binding.buttonLimpar.setOnClickListener {
            limparCampos()
        }
    }

    private fun calcularConsumo() {
        val potenciaStr = binding.editPotencia.text.toString()
        val horasStr = binding.editHoras.text.toString()
        val diasStr = binding.editDias.text.toString()
        val tarifaStr = binding.editTarifa.text.toString()

        if (potenciaStr.isEmpty() || horasStr.isEmpty()) {
            mostrarToast("Por favor, preencha os campos obrigatórios")
            return
        }

        val potencia = potenciaStr.toDoubleOrNull()
        val horas = horasStr.toDoubleOrNull()
        val dias = if (diasStr.isEmpty()) diasNoMes else diasStr.toIntOrNull() ?: diasNoMes
        val tarifa = if (tarifaStr.isEmpty()) tarifaPadrao else tarifaStr.toDoubleOrNull() ?: tarifaPadrao

        if (potencia == null || potencia <= 0) {
            mostrarToast("Potência inválida")
            return
        }
        if (horas == null || horas <= 0 || horas > 24) {
            mostrarToast("Horas inválidas")
            return
        }
        if (dias <= 0 || dias > 31) {
            mostrarToast("Quantidade de dias inválida")
            return
        }

        // Cálculos
        val consumoDiario = (potencia * horas) / 1000 // Converte watts para kWh
        val consumoMensal = consumoDiario * dias
        val custoMensal = consumoMensal * tarifa

        // Exibe os resultados
        binding.textResultado.text = """
            Consumo diário: %.2f kWh
            Consumo mensal: %.2f kWh
            Tarifa: R$ %.2f
            Custo mensal aproximado: R$ %.2f
        """.trimIndent().format(consumoDiario, consumoMensal, tarifa, custoMensal)

        binding.textResultado.visibility = View.VISIBLE
    }

    private fun limparCampos() {
        binding.editPotencia.setText("")
        binding.editHoras.setText("")
        binding.editDias.setText("")
        binding.editTarifa.setText("")
        binding.textResultado.text = ""
        binding.textResultado.visibility = View.GONE
    }

    private fun mostrarToast(mensagem: String) {
        Toast.makeText(requireContext(), mensagem, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}