#!/bin/bash
API_URL="http://localhost:8080/api"
JSON="Content-Type:application/json"

echo "POPULANDO LA BASE DE DATOS"

echo "CREANDO RECURSOS"
for i in {1..3}
do
	curl -s -X POST $API_URL'/resources' -H $JSON -d '{"name":"RECURSO '$i'", "detail":"DETALLE DE RECURSO '$i'", "category":{"name":"CATEGORIA 1"}}' | jq
done

for i in {4..6}
do
	curl -s -X POST $API_URL'/resources' -H $JSON -d '{"name":"RECURSO '$i'", "detail":"DETALLE DE RECURSO '$i'", "category":{"name":"CATEGORIA 2"}}' | jq
done

echo "CREANDO USUARIOS"
for i in {1..3}
do
	curl -s -X POST $API_URL'/users' -H $JSON -d '{"role":"STANDARD_USER", "name":"NOMBRE '$i'", "identificationNumber":'$i$i$i$i$i$i', "email":"EMAIL'$i'@EMAIL.COM", "phoneNumber":"'$i$i$i$i$i$i'"}' | jq
done

echo "ABILITANDO RECURSOS"
for i in {2..5}
do
	curl -s -X PUT $API_URL'/resources/status/'$i -H $JSON -d '{"status":"OPERATIONAL"}' | jq
done

echo "CREANDO RESERVAS"
	curl -X POST $API_URL'/reservations' -H $JSON -d '{"startDate":"2026-12-24 20:00", "endDate":"2026-12-25 00:00", "user":{"id":2}, "resource":{"id":3}}' | jq
	curl -X POST $API_URL'/reservations' -H $JSON -d '{"startDate":"2026-12-25 10:00", "endDate":"2026-12-25 18:00", "user":{"id":3}, "resource":{"id":3}}' | jq
	curl -X PUT $API_URL'/reservations/status/2' -H $JSON -d '{"CONFIRMED"}' | jq
	curl -X POST $API_URL'/reservations' -H $JSON -d '{"startDate":"2026-12-25 12:00", "endDate":"2026-12-25 15:00", "user":{"id":2}, "resource":{"id":3}}' | jq
