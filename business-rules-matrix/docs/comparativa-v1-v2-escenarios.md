# Comparativa de escenarios: v1 (Matriz) vs v2 (Drools)

Este documento contrasta ambos enfoques con los mismos datos semilla (`src/main/resources/data.sql`) y la misma entrada de API.

- `v1`: `POST /api/v1/decisions/evaluate`
- `v2`: `POST /api/v2/decisions/evaluate`

## Dataset base usado en los ejemplos

- `customerId=1` -> `VIP`, `creditScore=780`; `accountId=1` -> `ACTIVE`, `debtRatio=0.25`
- `customerId=2` -> `REGULAR`, `creditScore=650`; `accountId=2` -> `ACTIVE`, `debtRatio=0.38`
- `customerId=3` -> `NEW`, `creditScore=720`; `accountId=3` -> `ACTIVE`, `debtRatio=0.15`
- `customerId=4` -> `VIP`, `creditScore=820`; `accountId=4` -> `ACTIVE`, `debtRatio=0.10`
- `customerId=5` -> `REGULAR`, `creditScore=590`; `accountId=5` -> `SUSPENDED`, `debtRatio=0.55`

## Tabla comparativa

- Version CSV para Excel: `docs/comparativa-v1-v2-escenarios.csv`

| # | Entrada (customer/account/product/amount) | Resultado esperado v1 | Resultado esperado v2 | Equivalencia funcional | Diferencia visible |
|---|---|---|---|---|---|
| 1 | `1 / 1 / LOAN / 10000` | `APPROVED`, `ruleApplied=CREDIT_APPROVAL_RULE` | `APPROVED`, `ruleApplied=TREE_LOAN_VIP_CREDIT_APPROVAL` | Si | Cambia el identificador de regla |
| 2 | `2 / 2 / LOAN / 10000` | `REJECTED`, `ruleApplied=CREDIT_APPROVAL_RULE` | `REJECTED`, `ruleApplied=TREE_LOAN_REGULAR_CREDIT_REJECTION` | Si | v2 separa explícitamente regla de rechazo |
| 3 | `3 / 3 / LOAN / 10000` | `REJECTED`, `ruleApplied=CREDIT_APPROVAL_RULE` | `REJECTED`, `ruleApplied=TREE_LOAN_NEW_CREDIT_REJECTION` | Si | v2 distingue hoja NEW de rechazo |
| 4 | `1 / 1 / INVESTMENT / 10000` | `APPROVED`, `ruleApplied=PREMIUM_BENEFIT_RULE` | `APPROVED`, `ruleApplied=TREE_INVESTMENT_VIP_PREMIUM_BENEFIT` | Si | v2 reporta nodo del árbol |
| 5 | `2 / 2 / INVESTMENT / 10000` | `APPROVED`, `ruleApplied=DISCOUNT_RULE` | `APPROVED`, `ruleApplied=TREE_INVESTMENT_REGULAR_DISCOUNT` | Si | Misma lógica de descuento, distinto nombre |
| 6 | `3 / 3 / INVESTMENT / 10000` | `APPROVED`, `ruleApplied=COMMISSION_RULE` | `APPROVED`, `ruleApplied=TREE_INVESTMENT_NEW_COMMISSION` | Si | Misma comisión, distinto nombre |
| 7 | `4 / 4 / INSURANCE / 10000` | `APPROVED`, `ruleApplied=DISCOUNT_RULE` | `APPROVED`, `ruleApplied=TREE_INSURANCE_VIP_DISCOUNT` | Si | v2 explicita rama VIP/INSURANCE |
| 8 | `2 / 2 / INSURANCE / 10000` | `APPROVED`, `ruleApplied=COMMISSION_RULE` | `APPROVED`, `ruleApplied=TREE_INSURANCE_REGULAR_COMMISSION` | Si | v2 explicita rama REGULAR/INSURANCE |
| 9 | `5 / 5 / LOAN / 1500` | `REJECTED`, `ruleApplied=NONE` | `REJECTED`, `ruleApplied=TREE_GATE_SUSPENDED_ACCOUNT` | Parcial | v2 tiene compuerta explícita para cuentas suspendidas |
| 10 | `3 / 3 / INSURANCE / 900` | `REJECTED`, `ruleApplied=NONE` | `REJECTED`, `ruleApplied=TREE_FALLBACK_NO_RULE_CONFIGURED` | Si | v2 muestra fallback del árbol |

## Lectura rápida por tipo de diferencia

- **Mismo resultado, distinto mecanismo**: escenarios `1..8`.
- **Regla de compuerta explícita en Drools**: escenario `9`.
- **Fallback explícito de árbol**: escenario `10`.

## Comandos de prueba rápida

```powershell
$base = "http://localhost:8099"

# Escenario 1
$body = @'
{
  "customerId": 1,
  "accountId": 1,
  "productType": "LOAN",
  "amount": 10000.00
}
'@
Invoke-RestMethod -Method Post -Uri "$base/api/v1/decisions/evaluate" -ContentType "application/json" -Body $body
Invoke-RestMethod -Method Post -Uri "$base/api/v2/decisions/evaluate" -ContentType "application/json" -Body $body

# Escenario 9 (cuenta suspendida)
$bodySuspended = @'
{
  "customerId": 5,
  "accountId": 5,
  "productType": "LOAN",
  "amount": 1500.00
}
'@
Invoke-RestMethod -Method Post -Uri "$base/api/v1/decisions/evaluate" -ContentType "application/json" -Body $bodySuspended
Invoke-RestMethod -Method Post -Uri "$base/api/v2/decisions/evaluate" -ContentType "application/json" -Body $bodySuspended
```

## Evidencia automatizada

La prueba de integración que verifica coexistencia y escenarios clave está en:

- `src/test/java/com/businessrules/matrix/DecisionImplementationsIntegrationTest.java`

