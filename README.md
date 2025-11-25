Опис програми:
Консольна Java-програма, яка парсить набір JSON-файлів сутності Playlist (пісні)
та формує статистику за вибраним атрибутом:
- singer
- year
- genres (текстовий, має кілька значень через кому)
Результат зберігається у XML-файлі statistics_by_{attribute}.xml,
відсортованому за кількістю від більшого до меншого.



Запуск Main (src/main/java/runner/Main.java), через меню в консолі обирається атрибут:
1 - singer
2 - year
3 - genres
Готові XML-файли зі статистикою будуть у src/main/resources/xml



Опис сутностей:
Основна сутність — Song
| Поле   | Тип    | Опис             |
| ------ | ------ | ---------------- |
| title  | String | назва пісні      |
| singer | String | виконавець       |
| year   | int    | рік випуску      |
| genres | String | жанри через кому |

Другорядна сутність — Playlist
- містить список Song
- агрегує дані, отримані з JSON-файлів



Приклад вхідних даних:
[
  {
    "name": "Blinding Lights (Live)",
    "singer": "The Weeknd",
    "genres": "pop, synthwave, dance",
    "release_year": 2020
  },
  {
    "name": "Style",
    "singer": "Taylor Swift",
    "genres": "pop, synth-pop",
    "release_year": 2014
  },
  {
    "name": "All The Good Girls Go To Hell",
    "singer": "Billie Eilish",
    "genres": "pop, dark pop, electronic",
    "release_year": 2019
  },
  {
    "name": "Physical (Remix)",
    "singer": "Dua Lipa",
    "genres": "pop, dance, electronic",
    "release_year": 2020
  },
  {
    "name": "Sunflower, Vol. 6",
    "singer": "Harry Styles",
    "genres": "pop, psychedelic pop, funk",
    "release_year": 2019
  },
  {
    "name": "Loyalty",
    "singer": "Kendrick Lamar ft. Rihanna",
    "genres": "hip hop, r&b, trap",
    "release_year": 2017
  },
  {
    "name": "I Hate U",
    "singer": "SZA",
    "genres": "r&b, alternative r&b",
    "release_year": 2021
  },
  {
    "name": "Brutal",
    "singer": "Olivia Rodrigo",
    "genres": "pop punk, alternative rock",
    "release_year": 2021
  },
  {
    "name": "Thinking Out Loud (Acoustic)",
    "singer": "Ed Sheeran",
    "genres": "pop, soft music, folk",
    "release_year": 2015
  },
  {
    "name": "Book of Love",
    "singer": "Bad Bunny",
    "genres": "latin pop, reggaeton",
    "release_year": 2023
  }
]


Приклад вихідних даних (за атрибутом singer):
<?xml version="1.0" encoding="UTF-8"?><statistics>
    <item>
        <value>2019</value>
        <count>2</count>
    </item>
    <item>
        <value>2021</value>
        <count>2</count>
    </item>
    <item>
        <value>2020</value>
        <count>2</count>
    </item>
    <item>
        <value>2017</value>
        <count>1</count>
    </item>
    <item>
        <value>2015</value>
        <count>1</count>
    </item>
    <item>
        <value>2014</value>
        <count>1</count>
    </item>
    <item>
        <value>2023</value>
        <count>1</count>
    </item>
</statistics>



Багатопоточність:
Для потокобезпечності у JSONParser використовується CopyOnWriteArrayList, для формування статистики - ConcurrentHashMap
Для експериментів з кількістю потоків використовується ExecutorService:
ExecutorService executor = Executors.newFixedThreadPool(nThreads) 
Результати експериментів:
| Кількість потоків | Час виконання (нс) | Прискорення відносно 1 потоку |
| ----------------- | ------------------ | ----------------------------- |
| **1**             | 355 287 900        | 0% (базове значення)          |
| **2**             | 313 521 900        | ≈ 11.76% швидше               |
| **4**             | 305 719 000        | ≈ 13.95% швидше               |
| **8**             | 281 321 200        | ≈ 20.82% швидше               |



