Feature: Бот для сайта клавагонки

  Background: Я нахожусь на главной странице сайта
    Given Открываем сайт "https://w2.dwar.ru/#"

  Scenario: Проверка элементов
    And Проверить, что открыта стартовая страница
    And Проверить, что тайтл страницы = 'Легенда: Наследие Драконов — бесплатная онлайн игра | Браузерная MMORPG онлайн игра про Драконов — одна из старейших бесплатных ролевых online игр. Скачать и играть в лучшую многопользовательскую RPG браузерную игру онлайн бесплатно.'
    And Открыть новость под номером 3
    And Считать кол-во вкладок
    And перейти на вторую вкладку браузера
    And Нажать на кнопку обсудить на форуме
    And проверить, что в блоке отображаются списки:
      | Новости проекта   |
      | Таверна           |
      | Беседка           |
      | Конкурсы          |
      | События           |
      | Творчество        |
      | Приемная менторов |
      | Хранители форума  |
      | Пособия по игре   |
      | Клиент Dwarium    |


