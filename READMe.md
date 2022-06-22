### What it does?
This application is a demo of how can one create a leaderboard with the redis cache.
Upon running, it loads the data.json in locally running redis cache and creates a leaderboard.

### Prerequisites
1. JDK 11
2. Spring Assistant plugin in IntelliJ.
3. Start the Redis server, with following command
```redis-server```

### How to run
run as
```./gradlew bootRun```

go to ```http://localhost:5000/api/hello?name=Harshil```
output should be
```Hello Harshil```

Go to ```http://localhost:5000/api/list/top10```
it should output a json containing the top 10 companies based on the market cap.

### References
1. https://github.com/redis-developer/basic-redis-leaderboard-demo-java
2. https://redis.io/docs/getting-started/installation/install-redis-on-mac-os/
3. https://spring.io/guides/gs/spring-boot/
