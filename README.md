# loan-management

## Running app

```bash
    ./run.sh
```

## Query Database

```bash
    docker exec -it loan-management psql -U postgres -d loan_db -c "SELECT * FROM loan_applications;"
```