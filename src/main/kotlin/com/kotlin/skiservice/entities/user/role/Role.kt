package com.kotlin.skiservice.entities.user.role

enum class Role {
    REGISTRATION_MANAGER, // регистратор (создание данных о клиентах)
    RENTAL_MANAGER, // прокат: выдача / приём оборудования
    PAYMENT_MANAGER, // оплата
    ADMIN, // полный доступ
    USER_ADMIN // админ, который ТОЛЬКО создаёт пользователей
}