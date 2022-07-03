Termékek - PRODUCT

Termék (Product) tulajdonságai:
- cikkszám (article number)
- név (name)
- leírása (description)
- a termék aktív/inaktív (valid)
- termék kategoria (product category)
- lista ára (list price)
- minimális eladási ár (min price)

Cikkszám (articleNumber): felhasználó adja meg, kötelező elem, jelen esetben egy szám (Long), egyedinek kell lennie.

Name (name): felhasználó adja meg, kötelező megadni, egyedinek kell lennie. Minimum két karakter hosszúnak kell lennie.

Így minden terméke egy egyedi Cikkszám-Név páros határoz meg. Ez lesz az identitása, ezek késöbb már nem változtahatóak meg.

Leírás (description) - kötelező elem, ez késöbb változtatható, nem lehet üres.

Termék aktívitás (valid) - ezzel a beállítással lehet megadni, hogy egy termék használatban van-e.
Olyan termék nem lehet inaktív (valid = false) amin még van árukészlet. Inaktív termékre már nem lehet árut bevételezni.
Eyg termék vagy aktív (true) vagy (false), köztes állapot nem lehetséges.

Termék kategória (product Category) - a termékeket lehet besorolni egy kialakított kategória szerint, nem kötelező, a termék lehet kategória nélkül is.

Lista ár (list price) - a termék ára pozitiv szám és lehet nulla is (pl bemutató, ajándék termékek), nem lehet viszont negatív szám.

Minimum ár(min price) - mint a Lista ér (min price), kimenő terméknél a kimenő termék ára ennél nem lehet alacsonyabb.

Fontos követelmény, hogy a Lista ár sosem lehet kissebb mint a Minimum ár!


Fogalmilag a termékhez (Product) kapcsolodik még az aktuális raktárkészlet értéke (actualStock), ami mindig egy számított érték.
Kiszámításához lásd -> order.md (raktárkezelés alatt).

****

PRODUCT CATEGORY

Ígény volt egy Termék kategória kialakítására (Product Category)
Ennek két tulajdonsága:
- kategória azonosító (prefix)
- kategórai neve (name)

kategória azonosító (prefix): kötelező elem, 2-8 karakter hosszú, csak betű és szám, közepén kötőjelet tartalmazhat (-)
kategórai neve (name) : kötelező elem, 2-20 karakter hosszú, csak betű és szám, közepén kötőjelet tartalmazhat (-)

A termékeket nem kell kötelezően termék-kategóriába besorolni, mert előfordulhatnak olyan egyedi, vagy a rendszeren átmenő de csak kapcsolosó termékek amik nem képeznek jelentős tételt.
