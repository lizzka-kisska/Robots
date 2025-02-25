Учебный проект, разработанный в рамках курса “Объектно-ориентированное программирование” в УрФУ студентками второго курса Евой Марченковой и Елизаветой Антоновой. 

### Цели проекта

Основная цель проекта заключалась в получении базовых навыков, необходимых для разработки ПО, таких как:

- командная разработка;
- работа с VCS, IDE и инструментами сборки;
- работа с архитектурой приложения;
- знание основ технологий программирования и ООП в Java;
- инструменты для создания GUI.

В рамках проекта выполнены следующие задачи:
| Номер задачи | Описание задачи |
| --- | --- |
| 0 | 1. Научиться собирать проект с использованием Apache Maven.<br>2. Провести рефакторинг и исправить баг. 
| 1 | 1. Добавить окно с подтверждением на закрытие любого из окон.<br>2. Добавить пункт меню "Выйти" (с обработкой аналогичной кнопке закрытия приложения).<br>3. Исправить баг убегания робота за границу окна.<br>4. Добавить локализацию приложения (пункты меню, кнопки и т.д.). |
| 2 | 1. В методе выхода из приложения сохранять в файл состояние окон приложения (положение на экране, развёрнутое/свёрнутое состояние).<br>2. При запуске приложения следует восстанавливать состояние окон из сохранённого на предыдущем шаге файла).<br>3. При запуске приложения необходимо выводить предупреждение о наличии сохранённого состояния и предложение его восстановить (Да/Нет).<br>4. Выбранная локализация должна сохраняться (в частности, влиять на содержимое окошко с предупреждением о восстановлении из п.3). |
| 3 | 1. Добавить робота, управляемого с клавиатуры.<br>2. Изменить логику движения существовавшего робота: передвижение по произвольной траектории.<br>3. Добавить точки, которые необходимо собирать, управляя роботом.<br>4. Реализовать взаимодействие роботов: если управляемый робот дольше 3 секунд находится в радиусе 3 единиц от автономного робота, то игра оканчивается.<br>5. Добавить кусты, которые можно будет использовать в качестве "домика". |
| 4 | 1. Реализовать новое окно, в котором будут отображаться текущие координаты робота.<br>2. Реализовать паттерн наблюдатель (Observer) с роботом (робот должен стать Observable) для получения информации об изменении его состояния.<br>3. Аналогичным образом реализовать окно, в котором будет отображаться расстояние от цели до робота.<br>4. Добавить функцию "зума": изменение масштаба в окне с роботом.<br>Примечания:<br>— если роботов несколько, реализовать переключение между ними<br>— если целей несколько, отображать расстояние до ближайшей из них или той, на которую движется робот<br>— зум должен влиять только на визуализацию, но не менять координатную сетку, используемую в движении роботом (если объекты уменьшились вдвое, то визуальное расстояние тоже должно уменьшиться, но фактическое расстояние, отображаемое в окне, должно остаться прежним)<br>— требования к окнам унаследованы с предыдущих задач. |
