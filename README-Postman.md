## POSTMAN
(a minták valid értékeket tartalmaznak, a betöltött adatbázishoz!)

### PRODUCT

*GET:*
- localhost:8080/products

*GET by articleNumber:*

- localhost:8080/products/1001

*POST:*

- localhost:8080/products

{
"articleNumber": 2000,
"name": "PS-2000",
"description": "Galileo Telescopo bigbang",
"valid": true,
"productCategory": "PS",
"listPrice": 2500.0,
"minPrice": 2000.0
}

Válasz a bodyban : NEW Product save OK, product article number: 2000

"articleNumber" ,"name" kötelező eleme és mindig egyedinek kell lennie, két egyforma nem lehet.
"description" kötelező elem, nem lehet üres, de nem kell gyedinek lennie

A "valid", "productCategory", "listPrice", "minPrice" értékek NEM kötelezőek, lehet null vagy ki is maradhat!
Ekkor kezdeti értékkel lesz feltöltve.
"valid": false, "productCategory": null, "listPrice": 0.0, "minPrice": 0.0

*PUT:*

- localhost:8080/products

{
"articleNumber": 2000,
"description": null,
"valid": null,
"productCategory": null,
"listPrice": 3000.0,
"minPrice": 2500.0
}

Az egyedüli kötelező elem a "articleNumber", az ehhez kapcsolódó termék tulajdonságai változnak meg (feltéve ha létezik a termék).
A product "name" nem változtatható, de ha a PUT ban benne marad nem okoz hibát, nem veszi figyelembe.
Amit nem akarunk változtatni az lehet null (mint fenn a példában) vagy ki is maradhat.

!Hiányosság: Ha az adatok valamelyes megfelelők de még se lehet változtatni, akkor is az írja ki, hogy "updating", de igazából nem változtatott semmit.
Csak akkor változtat, ha a termék (product) tulajdonságai megfelelnek a hozzá kapcsolódó elvárásoknak.

*DELETE:*

- localhost:8080/products/1003

Termék csak akkor törölhető ha nem volt rajta árumozgás!
Ezt jelzi is: "Cannot be Deleted".


### ORDER

*GET:*
- localhost:8080/orders

*GET by orderNumber:*
- localhost:8080/orders/1

*POST:*
- localhost:8080/orders

{
"date": "2022-07-03",
"comment": "hello mentor",
"orderType": "OUT",
"orderDetails": [
{
"articleNumber": 1001,
"numberOfItem": 1,
"pricePerItem": 900
},
{
"articleNumber": 1005,
"numberOfItem": 1,
"pricePerItem": 1500
}
]
}

A "coment" en kívül minden szűkséges, az "orderDetails" nem lehet üres vagy null.
Az Order fontos szabályairól lásd -> order.md (Bizonylatolás szabályai, (Order rules))

Sikeres mentés esetén visszakapjuk a bizonylat számát (orderNumber):
"Order save, Order number: 9".


!Hiányosság: Egy bizonylat (Order) többször is elküldésre kerülhet, mindig el lesz mentve, függetlenül attól ha minden küldött adat ugyanaz.
Ez nem hiba, a rendszer nem tudhatja, hogy valóban kétszer akarjuk ugyan azt vagy téves második küldés. Ezt a számlázó rész megléte küszölheti ki a bizonylatok egymáshoz kapcsolódásával.
(fejlesztő megjegyzés: egy kapcsolódó bizonylat tulajdonság kellett volna ami egyedi, ekkor ez meg lett volna oldva, későn jutott ez megvilágosodásra, dehát jobb később mint még később)

### PRODUCT CATEGORY

*GET:*

- localhost:8080/categories

*GET by prefix:*

- localhost:8080/categories/PS

*POST*

- localhost:8080/categories

{
"prefix": "MT",
"name": "Mentor-Telescope"
}

Válasz : Save new category: XT

*DELETE:*

- localhost:8080/categories/TP

csak akkor törölhető ha nincs olyan termék aminek ez a kategóriája

válasz: DELETING prefix: TP


