# Cash Register

For the first part of the assignment, we would like you to implement a cash register.

See `src/main/java/com/adyen/android/assignment/CashRegister.kt`

Criteria:
- The `CashRegister` gets initialized with some `Change`.
- When performing a transaction, it either returns a `Change` object or fails with a `TransactionException`.
- The `CashRegister` keeps track of the `Change` that's in it.

Bonus points:
- The cash register returns the minimal amount of change (i.e. the minimal amount of coins / bills).
