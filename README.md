# Radio station
#### Для установки та запуску необхідно виконати наступні кроки: ####
1.	Завантажити проект у IDE, наприклад IntelliJ IDEA: File → New → Project from Version Control. У поле URL вставити посилання на репозиторій: https://github.com/StaceyBaytalyuk/radio_station
2.	Створити базу даних під назвою `radiostation` у СУБД PostgreSQL за допомогою утиліти pgAdmin.
3.	Переконатися, що у файлі `src/main/resources/application.properties` значення поля `username` та `password` відповідають встановленим у pgAdmin.
4.	Запустити проект.
5.	У браузері перейти за посиланням http://localhost:8080/mainMenu
6.	Початкові дані таблиць можна згенерувати в головному меню натиснувши кнопку **Генерувати дані таблиць**. Буде згенеровано ведучих, трансляції та їх частини. Зв’язки між ними необхідно задати вручну на свій розсуд. А саме: на сторінці **Ведучі** додати ведучого до трансляції; на сторінці **Частини трансляції** додати частини до трансляції.
> ⚠️ Зверніть увагу, що пісні можна додати лише за допомогою **Додати безкоштовну частину трансляції**, а всі платні частини (реклами та інтерв’ю) тільки за допомогою **Додати платну частину трансляції**.
7.	Коли зв’язки встановлено, можна переглянути деталі трансляції. Для цього на сторінці **Трансляції** необхідно натиснути кнопку **info** навпроти потрібної трансляції.
