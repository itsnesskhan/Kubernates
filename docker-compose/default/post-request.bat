@echo off
:loop
curl -X POST -H "Content-Type: application/json" -d '{"orderDate":"2023-04-19T11:30:00","orderStatus":"PENDING","item_id":1}' -w "%{http_code}\n" http://localhost:8080/api/v1/order/save
timeout /t 2 /nobreak >nul
goto loop
