docker run -d --name testdb -e POSTGRES_DB=test -e POSTGRES_USER=testuser -e POSTGRES_PASSWORD=testuser -p 5433:5432 --name testdb postgres:14.4-alpine

