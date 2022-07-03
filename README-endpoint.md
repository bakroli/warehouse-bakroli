Végpontok - ENDPOINTS


API:

Products:

- GET : http://localhost:8080/products (requestbody: Product)
- GET (by article number): http://localhost:8080/products/{articleNumber} (requestbody: Product)
- POST : http://localhost:8080/products (body:ProductDto) 
- PUT : http://localhost:8080/products (body:ProductDto)
- DELETE : http://localhost:8080/products/{articleNumber}
 
ProductCategory:

- GET : http://localhost:8080/categories
- GET (by prefix): http://localhost:8080/categories/{prefix}
- POST : http://localhost:8080/categories
- DELETE : http://localhost:8080/categories/{prefix}

Oreder

- GET : http://localhost:8080/orders (requestbody: Order)
- GET (by orderNumber) : http://localhost:8080/orders/{orderNumber} (requestbody: List<OrderDetail>)
- POST http://localhost:8080/orders (body: OrderDto)


HTTP:

Fő oldal ahonnét lehet nagvigáni:
http://localhost:8080/

- http://localhost:8080/products/html
- http://localhost:8080/categories/html
- http://localhost:8080/orders/html


SWAGGER:

http://localhost:8080/swagger-ui/index.html