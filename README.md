# WAREHOUSE - Raktárkezelés
by bakroli


Raktárkezelés megvalósítás csillagászati eszközökhöz.
A kialakítás konkrét elképzelések szerint. 

### Főbb jellemzők:
- a termékekhez történő igény szerinti kialakított jellemzők és azok kezelése
- a raktárba történő ki és és betárazás megvalósítása
- ezekhez történő végpontok
- adatokkal történő feltöltés
- lehetőség szerint vizuális megjelenítés

### A részletes megvalósítást az egyes dokumentumok tartalmazzák:

- terméklista (product) kialakítás -> README-product.md
- bizonylatok (orders) és raktárkezelés -> README-order.md
- végpontok -> README-endpoint.md
- postman -> README-Postman.md
- technikai információk - README-tech.md


### Programozási koncepció:

A kívánt adatok megfelelő tárolása és kezelése.

A raktárkezelő egyik legfontosabb kérdése, hogy a termékekből mennyi is van raktáron.
Ez az érték akár tárolható is az adatbázisban, és minden műveletkor az adott érték módosításával. 
Így könnyen és gyorsan elérhető a raktárkészlet.
Felmerült viszont a kérdés, hogy ez az érték mindig az adott be és kivételezésektől függ tehát számítható, ezért nem fixen tárolva, 
hanem az adott helyzetben számítva legyen mindig elérhető.

Ezáltal a projekt az adatok tárolásán és kezelésén kívül ennek a kérdésnek a megválaszolásán is dolgozik.

