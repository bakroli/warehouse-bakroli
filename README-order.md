RAKTÁRKEZELÉS

A raktárkezelés szempontjai:
- a raktárkészlet termékeként nem lehet negtív, nem lehet olyan terméket kivenni a raktárból amin nincs készlet vagy kivétel után a készletérték negatív lenne.
- termékmozgás csak aktív terméken lehet (valid = true)
- csak egész darabszámokat használjunk.

Fontos meghatározás! A termék raktárkészlete:
Az adott termék aktuális raktárkészlet értékének meghatározásához mindig a be és kivételezések sorrendje alapján történik (orderNumber).
Ez az érték (hogy ténylegesen mennyi van egy adott termékből raktáron) nem lehet negatív.

---

Bizonylatok - ORDER

Ezek biztositják a raktárba történő be- illetve kivételezést.

Bizonylat (Order) tulajdonságai:
- száma (orderNumber) ez automatikusan generálódik, késöbb ez alapján hivatkozhatunk rá.
- dátum (date), kötelező elem, nem lehet nagyobb az aktuális dátumnál
- bionylat típusa (orderType) - kötelező elem, csak IN vagy OUT lehet, azt jelenti, hogy a raktárba befelé történik az áru mozgása (IN), vagy kifele (OUT)
- megjegyzés a bizonylathoz (comment)
- bizonylat sorai (orderDetail) a bionylathoz tartozó áruk, nem lehet üres.

Bizonylat sorai - (OrderDetail) - minden sor az alábbiakt tartalmazza:
- termék cikkszáma (articleNumber), kötelező elem
- darabszám (numberOfItem), amennyit ki vagy bevételezünk, kötelező elem, nem lehet nulla
- egységár, amennyiért a terméket eladjuk vagy vesszük, kötelező elem, ez lehet nulla érték (pl ajándék, alakatrész)


A bizonylat csak akkor kerül be a rendszerbe ha minden tekintetben megfelel:
- a dátum megfelelő (date)
- a bizonylat típusa megfelelő (orderType = IN/OUT)
- a bizonylatnak legalább van egy sora (minimum egy OrderType)
- a bizonylatsorokban van cikkszám (articleNumber), és az létező és aktív (product valid = true)
- a bizonylatsorokban van darabszám (numberOfItem), és az nagyobb mint nulla
- a bizonylatsorokban van egységár (pricePerItem) és az nagyobb vagy egyenlő mint nulla
- ha a bizonylat típusa kivétel (orderType = OUT) akkor rendelkezni kell az termékekből elegendő darabszámmal
- ha a bizonylat típusa kivétel (orderType = OUT) akkor az egységárnak nagyobb vagy egyenlőnek kell lennie mint a termékhez kapcsolodó minimum árnak (order.pricePerItem>=product.minPrice).

Ha a bizonylat (order) megfelelő akkor kap egy számot (orderNumber) amivel lehet rá hivatkozni.

Az elmentett bizonylat (order) késöbb már nem modosítható!

