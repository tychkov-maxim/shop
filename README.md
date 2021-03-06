# Online Store system
Система интернет магазина предоставляет доступ пользователю для осуществления покупок товаров, выбрав их в одной из категорий. Добавление категорий, товаров и начисления средств на счет пользователя для покупок осуществляет администратор. После того как пользователь приобрел товары, его заказ добавляется в раздел "обрабатываемые заказы". Администратор обрабывает этот заказ и отправляет его пользователю. Далее, заказ перемещается в раздел пользователя "отправленные заказы", где после получения товаров пользователь отмечает, что получил заказ.
## Варианты использования
### Анонимный пользователь
* вход в систему;
* регистрация в системе;
* просмотр товаров по категориям;
* добавление товаров в корзину;
* удаление товаров из корзины;
* выбор языка.

### Авторизованный пользователь
* осуществление заказа;
* просмотр информации о профиле;
* просмотр информации о заказах;
* выход из системы;
* просмотр товаров по категориям;
* добавление товаров в корзину;
* удаление товаров из корзины;
* выбор языка.

### Администратор
* добавление новой категории;
* добавление нового товара;
* обработка текущих заказов;
* повысить пользователя до администратора;
* изменить количество денежных средств на счету пользователя;
* осуществление заказа;
* просмотр информации о профиле;
* просмотр информации о заказах;
* выход из системы;
* просмотр товаров по категориям;
* добавление товаров в корзину;
* удаление товаров из корзины;
* выбор языка.

Схема БД: https://github.com/tychkov-maxim/shop/blob/master/scheme.jpg