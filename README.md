Money Exchange.

## Getting Started
1. Clone the repo
   ```bash
    git clone git@github.com:maciej-moscicki-dev/money-exchange.git
   ```
2. Build project - use JDK 21 at least.
   ```bash
   mvn clean install
   ```   
3. Make sure your Docker Desktop is UP.
4. Build docker image for database.
   ```bash
   $ docker-compose build
   ```
   ```bash
   $ docker-compose up
   ```
5. Run the application - [MoneyExchangeApplication.java](src/main/java/com/finance/moneyexchange/MoneyExchangeApplication.java) 

Swagger path -> http://localhost:8080/swagger-ui/index.html#/

Enjoy testing.
    
