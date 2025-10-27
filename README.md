<div align="center">

# ⚙️ Administrator

![Java](https://img.shields.io/badge/Java-17%2B-orange?logo=openjdk&logoColor=white)
![PaperMC](https://img.shields.io/badge/Paper-1.19.4-blue?logo=minecraft&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-Build-success?logo=gradle&logoColor=white&color=02303A)
![License](https://img.shields.io/github/license/noinsts/administrator?color=brightgreen)
![Last Commit](https://img.shields.io/github/last-commit/noinsts/administrator?logo=github&color=blueviolet)
![Repo Size](https://img.shields.io/github/repo-size/noinsts/administrator?color=teal)
![Issues](https://img.shields.io/github/issues/noinsts/administrator?color=yellow)
![Pull Requests](https://img.shields.io/github/issues-pr/noinsts/administrator?color=lightblue)

Плагін для Paper-сервера Minecraft, який додає корисні адміністративні команди та візуальні покращення для виживання з друзями.

</div>

## 🧩 Функції

### 📍 Команди

- `/getcoords [player]` - показує ваші або вибраного гравця координати 
- `/backdeath [player]` - телепортує вас або вказаного гравця до його останньої точки смерті

### 🎨 Кольорове маркування вимірів

Гравці автоматично отримують різні кольори в чаті та табі залежно від виміру, в якому знаходяться:

| Вимір         | Колір              | Опис           |
|---------------|--------------------|----------------|
| 🌍 Overworld  | 🟢 **Зелений**     | Звичайний світ |
| ☠️ The Nether | 🔴 **Червоний**    | Пекло          |
| 🌌 The End    | 🟣 **Фіолетовий**  | Край           |

## 🚀 Швидкий старт

### ✅ Вимоги

- Java **17+**
- Paper Server **1.19.4**
- Gradle (для збірки з вихідного коду)

### 💾 Встановлення

1. Завантажте файл `.jar` з [Releases](https://github.com/noinsts/administrator/releases).
2. Помістіть його в директорію `plugins/` вашого сервера.
3. Перезапустіть сервер
4. Готово! 🎉

### 🛠 Збірка з вихідного коду

```bash
# Клонуємо репозиторій
git clone https://github.com/noinsts/administator.git
cd administator

# Зберіть проект
./gradlew build

# Файл .jar буде в папці build/libs 
```

## 🎮 Використання

Приклади:

```
/getcoords
-> 📍 Координати [ваш нік] [світ гравця]: X: 125.3, Y: 68.0, Z: -45.7

/getcoords Steve
-> 📍 Координати Steve [світ гравця]: X: 1.0, Y: 1.0, Z: 1.0

/backdeath
-> Гравця [ваш нік] телепортовано до точки смерті.

/backdeath Steve
-> Гравця Steve телепортовано до точки смерті.
```

## ⚙️ Технічні деталі

| Параметр                   | Значення             |
|----------------------------|----------------------|
| **Мінімальна версія Java** | 17                   |
| **Paper API**              | 1.19.4-R0.1-SNAPSHOT |
| **Система збірки**         | Gradle               |
| **Зберігання даних**       | In-memory            |

## 📝 Права доступу

Плагін **не використовує систему прав** - усі команди доступні кожному гравцеві. 

Ідеально підходить для приватних серверів з друзями! 💬

## 🗺️ Roadmap

Секція для майбутніх плагів та ідей.

## 🤝 Внесок у проєкт

Будь-які **ідеї, pull requests та issue** — завжди вітаються!

Якщо маєш пропозицію — створи [Issue](https://github.com/noinsts/administrator/issues)! 💡

## 🛠️ Архітектура проєкту

```
administrator/
├── gradle/                                         # Системні файли Gradle
├── src/
│   └── main/
│       ├── java/
│       │   └── com/noinsts/administrator/
│       │       ├── commands/                       # Команди плагіна
│       │       │   ├── BackDeathCommand.java       # Телепортація до точок смерті (/backdeath)
│       │       │   └── GetCoordsCommand.java       # Вивід координат гравця (/getcoords)
│       │       │
│       │       ├── listeners/                      # Слухачі подій (івенти)
│       │       │   ├── ColoredNameListener.java    # Задач кольорові нікнейми гравців
│       │       │   └── PlayerDeathListener.java    # Зберігає координати про смерті гравця
│       │       │
│       │       ├── managers/                       # Клас-менеджери даних
│       │       │   └── DeathLocationManager.java   # Зберігає координати смерті гравців
│       │       │
│       │       └── Main.java                       # Головний клас плагіна (точка входу)
│       │
│       └── resources/
│           └── plugin.yml                          # Конфігурація плагіна (ім'я, версія, команди)
├── .gitignore                                      # Ігноровані файли Git
├── build.gradle                                    # Основний скрипт для збірки Gradle
├── gradle.properties                               # Додаткові параметри збірки
├── LICENSE                                         # Ліцензія проєкту
├── README.md                                       # Документація та опис плагіна
└── settings.gradle                                 # Налаштування структури проєкту Gradle
```

## 📄 Ліцензія

Цей проєкт розповсюджується під ліцензією [MIT](https://opensource.org/license/mit). Детальніше див. [LICENSE](./LICENSE).

## 👤 Автор

[Andriy (noinsts)](https://github.com/noinsts/) - автор і розробник проєкту.
