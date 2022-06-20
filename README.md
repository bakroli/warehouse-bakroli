Raktárkezelő program.

Termékek tárolása, hozzá való adatokkal, 
raktárkészlet kezeléssel.

Product = Termék, tulajdonságai:
- id, (generálodik)
- articleNumber = cikkszám
- name = neve
- description = leírása
- valid = "élő" termék e még
- productCategory = termékkategoria
- productPrice <List> = a termék árai
- actualStock - !!! nem jelenik meg az adatbázisban, a termék darabszáma

Terméknél a articleNumber, name egyedi, késöbb sem változtatható.
Ez határozza meg a terméket, összes többi tulajdonsága változhat.
(future-> amíg nincs rajta árumozgás lehessen változtatni)

valid határozza meg, hogy egy termék használatban van e.

productCategory termék besoroláshoz, nem kötelező,

ProductPrice = termék árai
- listPrice = listaár, álltalában
- minPrice = minimális ár (értékesítésnél nem lehet olcsobban)

ProductCategory = termék besoroláshoz
- prefix = kategória azonósitó
- name = neve

Order - bizonylatok
- orderNumber - bizonylat száma, automatíkusan generálódik
- orderType - a bizonyéat típusa, kötelező
- date - dátuma
- comment - megjegyzés (nem kötelező)

OrderType - a bizonylatok típusa
IN - bejövő (raktárba érkező termékek)
OUT - kimenő (raktárból kimenő termékek)

OrderDetail - a bizonylathoz tartozó termékek listája
row - lista sora (automatikusan generálodik)
order - kapcsolodó bizonylat
product - termék
numberOfItem - darabszám
pricePerItem - egységár

Product - Endpoints
-get all - összes termék lekérése
-get / articleNumber - egy termék 'article number' alapján
-post - egy termék felvítele, 'name', 'articlenumber' kötelező elem, ha nincs ár akkor az nulla érték lesz
-put - terméktulajdonságok megváltoztatása, articleNumber alapján, neve nem változtatható
-delete - termék törlése, articleNumber alapján, csak akkor törölhető ha nem volt rajta árumozgás

Order - Endpoints
-get all - összes order, de részletes tételek nélkül
-get / number - egy order de részletesen, termékekkel
-post - egy order felvítele, orderDetaillal együtt lehetésges.
order nem törölhető, nem modosítható (future -> késöbbi lehetőség a modosításra)
ha az order OUT akkor a pricePerItem nek nagyobbnak kell lenni mint a termék minPrice
ha az order OUT akkor a termékből kell legyen megfelelő raktárkészlet
ha a termék nem valid (valid = false) akkor arra nem lehet menteni
!order csak akkor mentödik el ha minden adat megfelelő

Produck Stock - raktárkészlet
számolása az orderek beérkezése alapján történik








