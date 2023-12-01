package com.example.finalapplicationshare.utils

import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Pattern

class InputValidate : InputFilter {

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        for (i in start until end) {
            if (source!![i] == '\n' || source[i] == '\r') {
                return ""
            }
        }
        return null
    }

    companion object {
        fun isValidEmail(email: String?): Boolean {
            return email != null && email.contains("@") && email.endsWith(".com")
        }

        fun containsSpecialCharacter(input: String?): Boolean {
            val specialCharacterRegex = "[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]"
                val pattern = Pattern.compile(specialCharacterRegex)
                val matcher = pattern.matcher(input)
                return matcher.find()
        }
    }
}
