package com.example.otpapp

import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.experimental.and

class MainActivity : AppCompatActivity() {

    private lateinit var otpTextView: TextView
    private lateinit var generateButton: MaterialButton
    private lateinit var otpEditText: EditText
    lateinit var submitButton: MaterialButton
    lateinit var container: LinearLayout

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        otpTextView = findViewById(R.id.otpTextView)
        generateButton = findViewById(R.id.generateButton)
        otpEditText = findViewById(R.id.otpEditText)
        submitButton = findViewById(R.id.submitButton)

        generateButton.setOnClickListener {
            val otpGenerator = OTPGenerator()
            val generatedOtp = otpGenerator.generateOTP("zxcvbnm963") // Replace with your secret key
            otpTextView.text = "Generated OTP: $generatedOtp"
        }
        generateButton.setOnClickListener {
            startCountdown()
        }
    }

    private fun startCountdown() {
        // Disable the generateButton during the countdown
        generateButton.isEnabled = false

        object : CountDownTimer(10000, 1000) { // 10 seconds countdown
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                otpTextView.text = "Generating OTP in $secondsRemaining seconds"
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onFinish() {
                // Re-enable the generateButton after the countdown
                generateButton.isEnabled = true

                // Generate and display the OTP
                val otpGenerator = OTPGenerator()
                val generatedOtp = otpGenerator.generateOTP("zxcvbnm963") // Replace with your secret key
                otpTextView.text = "Generated OTP: $generatedOtp"
                otpEditText.visibility = View.VISIBLE
                submitButton.visibility = View.VISIBLE

                submitButton.setOnClickListener {
                    val enteredOtp = otpEditText.text.toString()

                    if (enteredOtp.equals(generatedOtp)) {
                        showToast("OTP Verified")
                        navigateToSuccessFragment()
                    } else {
                        showToast("Incorrect OTP. Please try again.")
                    }
                }
            }
        }.start()
    }

    private fun navigateToSuccessFragment() {
        val successFragment = SuccessFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, successFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

class OTPGenerator {
    @RequiresApi(Build.VERSION_CODES.O)
    fun generateOTP(secretKey: String): String {
        try {
            val hmacKey = SecretKeySpec(secretKey.toByteArray(), "HmacSHA256")
            val hmac = Mac.getInstance("HmacSHA256")
            hmac.init(hmacKey)

            // Get the current time in 10-second intervals
            val time = System.currentTimeMillis() / 10000
            val message = time.toString().toByteArray()

            // Compute the HMAC hash
            val hash = hmac.doFinal(message)

            // Extract 6 digits from the hash
            val offset = hash[hash.size - 1] and 0xF
            val truncatedHash = hash.copyOfRange(offset.toInt(), offset + 6)

            // Convert the truncated hash to a 6-digit OTP using Android's Base64
            return android.util.Base64.encodeToString(truncatedHash, android.util.Base64.NO_WRAP).substring(0, 6)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: InvalidKeyException) {
            e.printStackTrace()
        }

        return ""
    }
}