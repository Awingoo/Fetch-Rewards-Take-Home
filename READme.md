# Fetch Rewards Take Home
![This is an image](Fetch-Rewards.png)


## **Requirements:**

    Maven
    Java 17

## Usage:
    Run mvn clean install
    Run FetchRewardsApplication.java

## Rest API:

### Get payer transactions

`GET http://localhost:8080/transactions/`

Response body:
```
[
    {
        "id": 5,
        "payer": "DANNON",
        "points": 0,
        "timestamp": "2020-10-31T10:00:00.000+00:00"
    },
    {
        "id": 2,
        "payer": "UNILEVER",
        "points": 0,
        "timestamp": "2020-10-31T11:00:00.000+00:00"
    },
    {
        "id": 3,
        "payer": "DANNON",
        "points": 0,
        "timestamp": "2020-10-31T15:00:00.000+00:00"
    },
    {
        "id": 4,
        "payer": "MILLER COORS",
        "points": 5300,
        "timestamp": "2020-11-01T14:00:00.000+00:00"
    },
    {
        "id": 1,
        "payer": "DANNON",
        "points": 1000,
        "timestamp": "2020-11-02T14:00:00.000+00:00"
    }
]
```

### Add payer transaction

`POST http://localhost:8080/transactions/add`

Request body:
```
{
    "payer": "DANNON",
    "points": 200,
    "timestamp": "2020-10-31T15:00:00Z"
}
```

### Spend payer points

`POST http://localhost:8080/transactions/spend`

Request body:
```
{
    "points": 5000
}
```

Response body:
```
[
    {
        "payer": "UNILEVER",
        "points": -200
    },
    {
        "payer": "MILLER COORS",
        "points": -4700
    },
    {
        "payer": "DANNON",
        "points": -100
    }
]
```

### Get payer balance

`GET http://localhost:8080/transactions/balance`

Response body:

```
{
    "UNILEVER": 0,
    "MILLER COORS": 5300,
    "DANNON": 1000
}
```
