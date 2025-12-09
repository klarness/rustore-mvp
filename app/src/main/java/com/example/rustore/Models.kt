package com.example.rustore

import androidx.annotation.DrawableRes

enum class AppCategory(val displayName: String) {
    FINANCE("Финансы"),
    TOOLS("Инструменты"),
    GAMES("Игры"),
    GOVERNMENT("Государственные"),
    TRANSPORT("Транспорт"),
    MEDIA("Медиа"),
    SOCIAL("Социальные")
}

data class AppScreenshot(
    @DrawableRes val resId: Int
)

data class AppInfo(
    val id: Int,
    val name: String,
    val developer: String,
    val category: AppCategory,
    val shortDescription: String,
    val fullDescription: String,
    val ageRating: String,
    @DrawableRes val iconRes: Int,
    val screenshots: List<AppScreenshot>
)

object FakeAppRepository {

    private val apps = listOf(
        AppInfo(
            id = 1,
            name = "VK Pay",
            developer = "VK",
            category = AppCategory.FINANCE,
            shortDescription = "Платежи и переводы в один клик.",
            fullDescription = "VK Pay позволяет быстро и безопасно оплачивать услуги, переводить деньги друзьям и управлять своими финансами прямо с телефона.",
            ageRating = "6+",
            iconRes = R.drawable.ic_launcher_foreground,
            screenshots = listOf(
                AppScreenshot(R.drawable.ic_launcher_background),
                AppScreenshot(R.drawable.ic_launcher_background),
                AppScreenshot(R.drawable.ic_launcher_background)
            )
        ),
        AppInfo(
            id = 2,
            name = "Госуслуги",
            developer = "Минцифры России",
            category = AppCategory.GOVERNMENT,
            shortDescription = "Госуслуги в вашем смартфоне.",
            fullDescription = "Приложение позволяет получать государственные услуги онлайн, записываться на приём и получать уведомления о важных событиях.",
            ageRating = "12+",
            iconRes = R.drawable.ic_launcher_foreground,
            screenshots = listOf(
                AppScreenshot(R.drawable.ic_launcher_background),
                AppScreenshot(R.drawable.ic_launcher_background)
            )
        ),
        AppInfo(
            id = 3,
            name = "RuStore Игры",
            developer = "VK",
            category = AppCategory.GAMES,
            shortDescription = "Подборка популярных игр.",
            fullDescription = "Каталог игр с подборками по жанрам, рейтингу и рекомендациям. Устанавливайте и пробуйте новые игры каждый день.",
            ageRating = "12+",
            iconRes = R.drawable.ic_launcher_foreground,
            screenshots = List(4) { AppScreenshot(R.drawable.ic_launcher_background) }
        ),
        AppInfo(
            id = 4,
            name = "Городской транспорт",
            developer = "Город",
            category = AppCategory.TRANSPORT,
            shortDescription = "Маршруты и оплата проезда.",
            fullDescription = "Следите за расписанием транспорта, строите маршруты и оплачивайте проезд прямо в приложении.",
            ageRating = "0+",
            iconRes = R.drawable.ic_launcher_foreground,
            screenshots = listOf(
                AppScreenshot(R.drawable.ic_launcher_background),
                AppScreenshot(R.drawable.ic_launcher_background)
            )
        ),
        AppInfo(
            id = 5,
            name = "RuTools",
            developer = "VK",
            category = AppCategory.TOOLS,
            shortDescription = "Набор полезных инструментов.",
            fullDescription = "Сборник утилит для повседневных задач: сканер документов, заметки, менеджер файлов и многое другое.",
            ageRating = "6+",
            iconRes = R.drawable.ic_launcher_foreground,
            screenshots = List(3) { AppScreenshot(R.drawable.ic_launcher_background) }
        ),
        AppInfo(
            id = 6,
            name = "Музыка Online",
            developer = "Music Corp",
            category = AppCategory.MEDIA,
            shortDescription = "Музыка без ограничений.",
            fullDescription = "Слушайте любимые треки, создавайте плейлисты и открывайте новые релизы каждый день.",
            ageRating = "12+",
            iconRes = R.drawable.ic_launcher_foreground,
            screenshots = List(3) { AppScreenshot(R.drawable.ic_launcher_background) }
        ),
        AppInfo(
            id = 7,
            name = "КиноДом",
            developer = "Cinema LLC",
            category = AppCategory.MEDIA,
            shortDescription = "Фильмы и сериалы онлайн.",
            fullDescription = "Каталог фильмов и сериалов с рекомендациями по жанрам, актёрам и рейтингу.",
            ageRating = "16+",
            iconRes = R.drawable.ic_launcher_foreground,
            screenshots = List(4) { AppScreenshot(R.drawable.ic_launcher_background) }
        ),
        AppInfo(
            id = 8,
            name = "Мессенджер VK Talk",
            developer = "VK",
            category = AppCategory.SOCIAL,
            shortDescription = "Мгновенный обмен сообщениями.",
            fullDescription = "Общайтесь с друзьями, создавайте чаты, отправляйте стикеры и файлы.",
            ageRating = "12+",
            iconRes = R.drawable.ic_launcher_foreground,
            screenshots = List(3) { AppScreenshot(R.drawable.ic_launcher_background) }
        ),
        AppInfo(
            id = 9,
            name = "Домашний бюджет",
            developer = "Finance Apps",
            category = AppCategory.FINANCE,
            shortDescription = "Управление личными расходами.",
            fullDescription = "Планируйте бюджет, отслеживайте расходы и доходы, анализируйте статистику.",
            ageRating = "0+",
            iconRes = R.drawable.ic_launcher_foreground,
            screenshots = List(3) { AppScreenshot(R.drawable.ic_launcher_background) }
        ),
        AppInfo(
            id = 10,
            name = "Заметки Pro",
            developer = "Tools Studio",
            category = AppCategory.TOOLS,
            shortDescription = "Заметки, списки и напоминания.",
            fullDescription = "Создавайте заметки, списки дел и напоминания с синхронизацией между устройствами.",
            ageRating = "0+",
            iconRes = R.drawable.ic_launcher_foreground,
            screenshots = List(3) { AppScreenshot(R.drawable.ic_launcher_background) }
        )
    )

    fun getApps(categoryFilter: AppCategory? = null): List<AppInfo> {
        return if (categoryFilter == null) {
            apps
        } else {
            apps.filter { it.category == categoryFilter }
        }
    }

    fun getAppById(id: Int): AppInfo? {
        return apps.find { it.id == id }
    }

    fun getCategoriesWithCounts(): List<Pair<AppCategory, Int>> {
        return AppCategory.values().map { category ->
            category to apps.count { it.category == category }
        }.filter { it.second > 0 }
    }
}
