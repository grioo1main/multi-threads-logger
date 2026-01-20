# Multi-Threaded Logger

Многопоточное приложение для асинхронной генерации, сортировки и записи логов в файлы.

## 🏗️ Архитектура

```
LogGenerator → [LogQueue] → LogSorter → [4x ArrayBlockingQueue] → 4x LogWriter → files
```

**Поток данных:**
1. **LogGenerator** — создаёт логи с разными уровнями (ERROR, WARN, INFO, DEBUG)
2. **LogQueue** — кастомная потокобезопасная очередь (capacity: 1000)
3. **LogSorter** — распределяет логи по уровням в отдельные очереди
4. **LogWriter** × 4 — записывают логи в файлы с батчинг-оптимизацией

## 🔧 Технологии

- **Java Core:** Multithreading, Concurrency API
- **Синхронизация:** `ReentrantLock`, `Condition`, `BlockingQueue`
- **Thread-safe счётчики:** `AtomicLong`
- **I/O:** `BufferedWriter` с оптимизированным flush

## 📂 Структура проекта

```
src/
├── LogLevel.java          # Enum уровней логирования
├── LogEntity.java         # Модель лог-сообщения (UUID, timestamp, memory info)
├── LogQueue.java          # Кастомная блокирующая очередь (ReentrantLock)
├── LogGenerator.java      # Producer логов
├── LogSorter.java         # Фильтр и распределитель
├── LogWriter.java         # Consumer, запись в файл
└── Main.java              # Точка входа + graceful shutdown

logs/
├── error.log
├── warn.log
├── info.log
└── debug.log
```

## 🚀 Запуск

```bash
# Компиляция
javac *.java

# Запуск
java Main
```

### Практические навыки
- Синхронизация доступа с `ReentrantLock`
- Использование `Condition` для точного управления потоками
- Работа с `BlockingQueue` и `ExecutorService`
- Оптимизация I/O операций
- Мониторинг и отладка многопоточных систем
- Обработка ошибок и graceful shutdown


## 📄 Структура логов

Каждый лог содержит:
- **Timestamp:** `HH:mm:ss.SSS`
- **Level:** `[ERROR/WARN/INFO/DEBUG]`
- **Thread:** Имя потока-источника
- **ID:** Уникальный идентификатор (8 символов UUID)
- **Memory:** Использование памяти в МБ
- **Message:** Текст сообщения

