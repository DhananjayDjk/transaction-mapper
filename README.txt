Transaction Mapper
----------------------

Description
-----------
This application is a restful api to retrieve transactions from open bank sandbox. There are 3 rest endpoints exposed by this application as described below :-

The below endpoint fetches all the open bank transactions
http://{hostname}:{port}/transaction-mapper/transactions/details

The below endpoint fetches all the open bank transactions based on transaction type
http://{hostname}:{port}/transaction-mapper/transactions/{transactionType}/details

The below endpoint fetches the total transaction amount based on the transaction type
http://{hostname}:{port}/transaction-mapper/transactions/{transactionType}/amount

-----------------------


Set Up
------
To run this application on local, checkout the project in eclipse or any other java development IDE and maven build the project using Java 8 or higher version. Then run it as Spring boot application. The rest endpoints will be ready to be consumed once the embedded tomcat is UP.

Once the embedded tomcat UP, please test the below sample endpoints on localhost :-

http://localhost:8080/transaction-mapper/transactions/details

http://localhost:8080/transaction-mapper/transactions/SANDBOX_TAN/details

http://localhost:8080/transaction-mapper/transactions/SANDBOX_TAN/amount


Dependencies
------------

This application is Basic Authorization enabled and it needs to be authorized before accessing the resource.

Please provide the below credential while consuming the endpoints :-

username : admin
password : admin


Junit Details
--------------

This project contains 5 junit test classes. Their names and corresponding usage are as below:-

TransactionMapperServicePositiveScenarioTest  -- To test positive scenarios for the service class.
TransactionMapperServiceNegativeScenarioTest  -- To test negative scenarios for the service class by passing wrong transaction type input while calling the service.
TransactionMapperControllerPositiveFlowTest   -- To test end to end positive flow scenarios for the controller class.
TransactionMapperControllerNegativeFlowTest   -- To test end to end negative flow test for the controller class by passing wrong basic auth credentials while calling the endpoint.
TransactionMapperControllerOpenApiTimeoutTest -- To test end to end negative flow test for the controller class by reducing the timeout for open api service call.


