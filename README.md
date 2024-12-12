## Стек технологий
1. Java == 8
2. Maven == 4.0.0
## Запуск
Для запуска в консоли необходимо перейти в директорию где хранится jar-файл 
(/out/artifacts/TestTaskJava_jar) и прописать команду для его запуска.
```env
java -jar TestTaskJava.jar -s -a -p sample- in1.txt in2.txt
```
1. "-s" - краткая статистика
2. "-f" - полная статистика
3. "-a" - перезаписать файлы
4. "-o <path>" - сохранить данные по указонму пути (path), если не указывать путь, то 
по умолчанию сохранить в директории с jar-файлом
5. "-p <name>" - сохранить данные с указаным именем (name), если не указывать имя, то
по умолчанию имя файла будет содержать только тип данных который он хранит