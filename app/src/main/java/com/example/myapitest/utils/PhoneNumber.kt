package com.example.myapitest.utils

class PhoneNumber {
    companion object {
        fun formatPhoneNumber(phone: String): String {
            return if (phone.startsWith("+55")) phone else "+55$phone"
        }
    }
}