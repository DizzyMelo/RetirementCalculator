package com.dicedev.retirementcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicedev.retirementcalculator.databinding.ActivityMainBinding
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        AppCenter.start(
            application,
            "cbc814ca-cc25-459c-9472-fd97544a73d1",
            Analytics::class.java,
            Crashes::class.java
        )

        binding.calculateButton.setOnClickListener {
//            Crashes.generateTestCrash()
            try {
                val interestRate = binding.interestEditText.text.toString().toFloat()
                val age = binding.ageEditText.text.toString().toInt()
                val retirementAge = binding.retirementEditText.text.toString().toInt()
                val monthlySavings = binding.monthlySavingsEditText.text.toString().toInt()
                val currentSavings = binding.currentEditText.text.toString().toInt()

                val properties = hashMapOf(
                    "interest_rate" to interestRate.toString(),
                    "current_age" to age.toString(),
                    "retirement_age" to retirementAge.toString(),
                    "monthly_savings" to monthlySavings.toString(),
                    "current_savings" to currentSavings.toString()
                )

                if (interestRate <= 0) {
                    Analytics.trackEvent("wrong_interest_rate", properties)
                }

                if (retirementAge <= age) {
                    Analytics.trackEvent("wrong_retirement_age", properties)
                }

                binding.resultTextView.text = "At the current rate of $interestRate%, saving \$$monthlySavings a month, you will retire at age of $retirementAge"
            } catch (ex: Exception) {
                Analytics.trackEvent(ex.message)
            }
        }

    }
}