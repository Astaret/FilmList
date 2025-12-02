<h1 align="center">🎬 FilmList</h1>
<h3 align="left">
FilmList это приложение для просмотра информации о фильмах.
Приложение использует внешний api, поддерживает работу с базой данных, ведение пользовательских списков и позволяет делиться информацией о фильме через QR код.
Проект создан для практики использования современного стека технологий Android разработки: UI (Jetpack Compose), работы с сетью (Retrofit) и локального кэширования данных (Room).

Приложение реализует принципы Clean Architecture и паттерн MVVM.
</h3>
<h2 align="center">📱 Скриншоты</h2>

<table>
<tr>
  <th align="center">Список фильмов</th>
  <th align="center">Корзина/Избранное</th>
  <th align="center">Детали фильма</th>
</tr>
<tr>
  <td align="center">
     <img width="250" height="1100" alt="Screenshot 2025-12-02 235942" src="https://github.com/user-attachments/assets/c74d0e57-92f5-4ac6-99b4-a2b320d29d34" />
  </td>
  <td align="center">
    <img width="250" height="1100" alt="Screenshot 2025-12-03 000314" src="https://github.com/user-attachments/assets/1e976c4a-7cc4-41dc-9e61-e84e996d5e01" />
  </td>
  <td align="center">
     <img width="250" height="1100" alt="Screenshot 2025-12-03 000333" src="https://github.com/user-attachments/assets/40306b15-df25-47e2-a241-e70dd512dec3" />
  </td>
</tr>
</table>


<h2 align="center">🛠 Технологический стек</h2>

<p align="center">
Проект написан полностью на <b>Kotlin</b> с использованием современных библиотек и инструментов
</p>

<table>
  <colgroup>
    <col style="width: 240px;">
    <col>
  </colgroup>
<tr>
  <td><b>UI</b></td>
  <td>Jetpack Compose</td>
</tr>
<tr>
  <td><b>Асинхронность</b></td>
  <td>Kotlin Coroutines + Flow</td>
</tr>
<tr>
  <td><b>Сеть</b></td>
  <td>
    Retrofit2 — REST клиент<br>
    OkHttp3 — HTTP клиент + Interceptors
  </td>
</tr>
<tr>
  <td><b>Парсинг JSON</b></td>
  <td>Gson</td>
</tr>
<tr>
  <td><b>База данных</b></td>
  <td>Room — абстракция над SQLite (Offline-first подход)</td>
</tr>
<tr>
  <td><b>Архитектура</b></td>
  <td>MVVM, Repository Pattern</td>
</tr>
<tr>
  <td><b>DI</b></td>
  <td>Dagger Hilt</td>
</tr>
<tr>
  <td><b>Загрузка изображений</b></td>
  <td>Glide</td>
</tr>
</table>


<h2 align="center">✨ Функционал</h2>

<table>
  <colgroup>
    <col style="width: 240px;">
    <col>
  </colgroup>
<tr>
  <td><b>Просмотр списка фильмов</b></td>
  <td>Загрузка популярных фильмов или новинок с пагинацией</td>
</tr>
<tr>
  <td><b>Детальная информация</b></td>
  <td>Описание, рейтинг, дата выхода и постер в высоком качестве</td>
</tr>
<tr>
  <td><b>Offline режим</b></td>
  <td>Фильмы кэшируются в Room и доступны без интернета</td>
</tr>
<tr>
  <td><b>Обработка ошибок</b></td>
  <td>Понятные сообщения при отсутствии сети или ошибках API</td>
</tr>
</table>


<h2 align="center">🏗 Архитектура</h2>

<p align="center">
Приложение разделено на слои согласно принципам <b>Clean Architecture</b>
</p>

<table>
  <colgroup>
    <col style="width: 240px;">
    <col>
  </colgroup>
<tr>
  <th>Layer</th>
  <th>Описание</th>
</tr>
<tr>
  <td><b>Data</b></td>
  <td>
    Network — работа с API (Retrofit)<br>
    Database — локальное хранение (Room)<br>
    Repository — источник данных (сеть / кэш)
  </td>
</tr>
<tr>
  <td><b>Domain</b></td>
  <td>UseCases и бизнес-логика</td>
</tr>
<tr>
  <td><b>UI (Presentation)</b></td>
  <td>
    ViewModel — управление состоянием (StateFlow / LiveData)<br>
    Compose Screens — декларативная верстка
  </td>
</tr>
</table>

<h2 align="center">🚀 Как запустить проект</h2>

<p align="left"><b>1. Клонируйте репозиторий</b></p>

<p align="left"> git clone https://github.com/Astaret/FilmList.git</p>

<p align="left"><b>2. Откройте проект в Android Studio</b></p>

<p align="left"><b>3. Добавьте ваш auth_token в файл data/kotlin+java/utils.di/NetworkModule:</b>

<p align="left"> AUTH_TOKEN = "https://developer.themoviedb.org"</p>

<p align="left"><b>4. Синхронизируйте Gradle и запустите приложение на эмуляторе или реальном устройстве.</b></p>
