FROM mysql
ENV MYSQL_DATABASE=moneyexchange \
    MYSQL_ROOT_PASSWORD=moneyexchange

ADD data.sql /docker-entrypoint-initdb.d

EXPOSE 3306