Money Exchange Application.

This application allows to:
- Create new user with account balance in PLN.
- Fetch information about user using UUID.
- Fetch information about todays USD/PLN and PLN/USD exchange rate. 
  Note: If exchange rate for today is not available, you will receive proper communicate to wait until exchange rate is available.
        For testing purpose feel free to change url in application.properties for historic data.
- Exchange customer money from PLN to USD.
- Exchange customer money from USD to PLN.

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
    
