POSTMAN
(a minták valid értékeket tartalmaznak, a betöltött adatbázishoz!)

PRODUCT

GET:
- localhost:8080/products

GET by articleNumber:

- localhost:8080/products/1001

POST:

- localhost:8080/products

{
"articleNumber": 2000,
"name": "PS-1600",
"description": "Galileo Telescopo",
"valid": true,
"productCategory": "PS",
"listPrice": 1600.0,
"minPrice": 1300.0
}

Válasz a bodyban : NEW Product save OK, product article number: 2000

A "valid", "productCategory", "listPrice", "minPrice" értékek NEM kötelezőek, lehet null vagy ki is maradhat!
Ekkor kezdeti értékkel lesz feltöltve.
"valid": fale, "productCategory": null, "listPrice": 0.0, "minPrice": 0.0

PUT:

- localhost:8080/products

{
"articleNumber": 2000,
"valid": false,
"listPrice": 0.0,
"minPrice": 0.0
}


