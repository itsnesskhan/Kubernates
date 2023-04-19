@echo off
:loop
curl -w "\n" http://localhost:8080/api/v1/order/get-all
timeout /t 1 /nobreak >nul
goto loop
