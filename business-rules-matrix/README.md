# Business Rules Matrix + Drools Decision Tree

Proyecto Spring Boot con dos implementaciones paralelas del mismo problema de negocio:

- `v1` Matriz de decisión basada en `Map<RuleCoordinate, BusinessRule>` y configuración en `application.yml`.
- `v2` Árbol de decisión con Drools usando archivos `.drl` cargados desde `decision-tree.rule-files`.

## Estructura clave

- `src/main/java/com/businessrules/matrix/...` implementación actual v1.
- `src/main/java/com/businessrules/matrix/drools/...` implementación v2 con Drools.
- `src/main/resources/rules/*.drl` reglas del árbol de decisión Drools.

## Endpoints

### Versión 1 - Matriz

- `POST /api/v1/decisions/evaluate`
- `GET /api/v1/decisions/rules`

### Versión 2 - Árbol con Drools

- `POST /api/v2/decisions/evaluate`
- `GET /api/v2/decisions/rules`
- `GET /api/v2/decisions/tree`

## Ejecutar

```powershell
Set-Location "D:\Diversos Revisas\New folder\SpringMatriz\business-rules-matrix"
.\mvnw.cmd spring-boot:run
```

La aplicación usa H2 en memoria y expone la consola en:

- `http://localhost:8099/h2-console`

Swagger/OpenAPI:

- `http://localhost:8099/swagger-ui.html`

## Probar rápidamente

### v1 Matriz

```powershell
$body = @'
{
  "customerId": 1,
  "accountId": 1,
  "productType": "LOAN",
  "amount": 10000.00
}
'@
Invoke-RestMethod -Method Post -Uri "http://localhost:8099/api/v1/decisions/evaluate" -ContentType "application/json" -Body $body
```

### v2 Drools

```powershell
$body = @'
{
  "customerId": 1,
  "accountId": 1,
  "productType": "LOAN",
  "amount": 10000.00
}
'@
Invoke-RestMethod -Method Post -Uri "http://localhost:8099/api/v2/decisions/evaluate" -ContentType "application/json" -Body $body
```

## Configuración de la versión Drools

En `src/main/resources/application.yml`:

```yaml
decision-tree:
  rule-files:
    - rules/decision-tree-gates.drl
    - rules/decision-tree-loan.drl
    - rules/decision-tree-investment.drl
    - rules/decision-tree-insurance.drl
```

Esto permite cambiar el conjunto de archivos de reglas sin afectar la matriz v1.

## Validación

La solución incluye pruebas de integración que verifican:

- que `v1` sigue operativa,
- que `v2` aprueba casos equivalentes,
- que `v2` rechaza cuentas suspendidas,
- y que `v2` usa fallback cuando no existe una hoja del árbol.

## Comparativa de escenarios

- Revisa `docs/comparativa-v1-v2-escenarios.md` para ver entradas de prueba y salida esperada de `v1` vs `v2` escenario por escenario.
- Version CSV para Excel: `docs/comparativa-v1-v2-escenarios.csv`.

