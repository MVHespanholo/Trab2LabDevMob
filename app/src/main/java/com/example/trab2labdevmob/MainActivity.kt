package com.example.trab2labdevmob

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.trab2labdevmob.databinding.ActivityMainBinding
import com.example.trab2labdevmob.fragments.TemperatureConverterFragment
import com.example.trab2labdevmob.fragments.TravelCalculatorFragment
import com.example.trab2labdevmob.fragments.CurrencyConverterFragment
import com.example.trab2labdevmob.fragments.PowerConsumptionFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar o ViewPager2
        binding.viewPager.adapter = ScreenPagerAdapter(this)

        // Configurar botões de navegação
        binding.btnPrevious.setOnClickListener {
            if (binding.viewPager.currentItem > 0) {
                binding.viewPager.currentItem = binding.viewPager.currentItem - 1
            }
        }

        binding.btnNext.setOnClickListener {
            if (binding.viewPager.currentItem < 3) {
                binding.viewPager.currentItem = binding.viewPager.currentItem + 1
            }
        }

        // Atualizar visibilidade dos botões
        updateNavigationButtons(0)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateNavigationButtons(position)
            }
        })
    }

    private fun updateNavigationButtons(position: Int) {
        binding.btnPrevious.isEnabled = position > 0
        binding.btnNext.isEnabled = position < 3

        binding.btnPrevious.alpha = if (binding.btnPrevious.isEnabled) 1.0f else 0.5f
        binding.btnNext.alpha = if (binding.btnNext.isEnabled) 1.0f else 0.5f
    }
}

private class ScreenPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CurrencyConverterFragment()
            1 -> TemperatureConverterFragment()
            2 -> TravelCalculatorFragment()
            3 -> PowerConsumptionFragment()
            else -> throw IllegalArgumentException("Invalid position $position")
        }
    }
}