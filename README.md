# Json-PlaceHolder-Application

Проект представляет собой прокси-сервер, обрабатывающий запросы к https://jsonplaceholder.typicode.com/
## Описание функциональности

1. **Обработчики запросов:** Проксируют запросы к различным ресурсам на https://jsonplaceholder.typicode.com/:
    ### /posts
    - `GET /api/v1/posts`: Получить список постов.
    - `GET /api/v1/posts/{post_id}`: Получить информацию о постe.
    - `POST /api/v1/posts`: Создать новый пост.
    - `PUT /api/v1/posts/{post_id}`: Обновить существующий пост.
    - `DELETE /api/v1/posts/{post_id}`: Удалить пост.
    ### /users
    - `GET /api/v1/users`: Получить список пользователей.
    - `GET /api/v1/users/{user_id}`: Получить информацию о пользователе.
    - `POST /api/v1/users`: Создать нового пользователя.
    - `PUT /api/v1/users/{user_id}`: Обновить существующего пользователя.
    - `DELETE /api/v1/users/{user_id}`: Удалить пользователя.
    ### /albums
    - `GET /api/v1/albums`: Получить список альбомов.
    - `GET /api/v1/albums/{album_id}`: Получить информацию об альбоме.
    - `POST /api/v1/albums`: Создать новый альбом.
    - `PUT /api/v1/albums/{album_id}`: Обновить существующий альбом.
    - `DELETE /api/v1/albums/{album_id}`: Удалить альбом.
   ### /auth
   - `GET /api/auth`: Добавить пользователя.


2. **Базовая авторизация и ролевая модель доступа:** Реализована ролевая модель доступа со следующими ролями:

    - `ROLE_ADMIN` - полный доступ ко всем обработчикам.
    - `ROLE_USERS` - доступ для просмотра и редактирования информации о пользователях.
    - `ROLE_POSTS` - доступ для просмотра и редактирования информации о постах.
    - `ROLE_ALBUMS` - доступ для просмотра и редактирования информации об альбомах.
    - `ROLE_POSTS_VIEWER` - доступ для просмотра постов.
    - `ROLE_POSTS_EDITOR` - доступ для редактирования постов.
    - `ROLE_USERS_VIEWER` - доступ для просмотра пользователей.
    - `ROLE_USERS_EDITOR` - доступ для редактирования пользователей.
    - `ROLE_ALBUMS_VIEWER` - доступ для просмотра альбомов.
    - `ROLE_ALBUMS_EDITOR` - доступ для редактирования альбомов.

3. **Кэширование данных:** Для снижения числа запросов к jsonplaceholder реализован Inmemory кэш, который сначала обновляется, а затем отправляет запросы к jsonplaceholder.

4. **База данных PostgreSQL и создание тестовых данных:** База данных создается автоматически и заполняется тестовыми данными при запуске приложения.

5. **Тестирование:** Для обеспечения корректной работы кода реализованы юнит тесты.

6. **Реализация WebSocket:** Добавлена конечная точка для WebSocket с базовой авторизацией и ролевой моделью.

7. **Ведение журнала действий (audit):** Реализована система ведения журнала действий с использованием PostgreSQL

## Запуск проекта
   Необходимо использовать Docker. Достаточно открыть репозиторий в любой удобной IDE и нажать "Start"