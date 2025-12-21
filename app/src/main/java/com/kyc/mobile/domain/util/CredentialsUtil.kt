package com.kyc.mobile.domain.util

import org.passay.CharacterCharacteristicsRule
import org.passay.CharacterRule
import org.passay.EnglishCharacterData
import org.passay.LengthRule
import org.passay.PasswordData
import org.passay.PasswordValidator
import org.passay.UsernameRule

class CredentialsUtil {

    companion object {

        val validator: PasswordValidator by lazy{

            val characterRule = CharacterCharacteristicsRule(
                3,
                CharacterRule(EnglishCharacterData.LowerCase, 1),
                CharacterRule(EnglishCharacterData.Digit),
                CharacterRule(EnglishCharacterData.Special)
            )

            val usernameRule =  UsernameRule(false,true)
            val lengthRule = LengthRule(8,15)

            PasswordValidator(characterRule,usernameRule,lengthRule)
        }

        fun isValidUsername(username: String): Boolean{

            val pattern = Regex("^[a-zA-Z0-9_]{6,10}$")
            return pattern.matches(username)
        }

        fun isValidPassword(password: String): Boolean{

            val pattern = Regex("^[a-zA-Z0-9_#\\.\\+\\*\\$]{8,15}$")
            val patternResult =  pattern.matches(password)

            return patternResult && validator.validate(PasswordData(password)).isValid
        }
    }
}