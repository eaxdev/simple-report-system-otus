# Simple Report System

[![Build Status](https://github.com/eaxdev/simple-report-system-otus/workflows/build/badge.svg)](https://github.com/eaxdev/simple-report-system-otus/actions)

## Connections

- Create new connection:

```http request
POST /connections

{
  "connectionName": "ClickHouse#1",
  "description": "get data from clickhouse",
  "host": "localhost",
  "port": 8123,
  "database": "test",
  "userName": "default",
  "password": "",
  "connectionProperties": []
}
```

- Show all connections with pagination:

```http request
GET /connections?page=0&size=10
```

- Add new data model:

```http request
POST /models

{
  "name": "ontime",
  "description": "Flight data",
  "connectionId": 1,
  "tableName": "ontime",
  "criteria": {
    "eq": {
       "fieldName": "Origin",
       "value": "JFK"
    }
  }
}
```

- Show all data models with pagination:

```http request
GET /models?page=0&size=10
```

- Get data model by id:

```http request
GET /models/1
```

- Create report:

```http request
POST /reports

{
  "name": "report1",
  "description": "The number of flights per day",
  "modelId": 1,
  "outputs": [
    "DayOfWeek",
    "count(*)"
  ],
  "groupBy": "DayOfWeek",
  "criteria": {
    "and": [
      {
        "ge": {
          "fieldName": "Year",
          "value": "1988"
        }
      },
      {
        "le": {
          "fieldName": "Year",
          "value": "1990"
        }
      }
    ]
  }
}
```

- Show all reports:

```http request
GET /reports?page=0&size=10
```