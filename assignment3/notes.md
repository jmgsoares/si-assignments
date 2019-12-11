#### Assignment Notes

##### Architecture
- DB

- Backend
    - Module name -> backend
    - This module is to expose a REST API to read the results that are persisted in the DB (we use normal SQL queries, there is no need to go with ORMs)

- CLI
    - Module name -> cli
    - This module is to allow the administrator to interact with the rest api

- Streams
    - Module Name -> streams
    - This reads from sales and purchase topics and should compute the following and write the results to result topics
       - OK - Revenue per item (sum item sales)
       - OK - Expense per item (sum item purchases)
       - OK - Profit per item (sum item sales - sum item purchases)
       - OK - Total Revenue (sum all sales)
       - OK - Total Expense (sum all purchases)
        OK - Total Profit (sum all sales - sum all purchases)
       - OK - Average expense per order (separated by item)
       - OK - Average expense per order (aggregate all items)
        - Most profitable item (if there is a tie, only one is needed)
        OK - Total revenue in the last hour
        OK - Total expense in the last hour
        OK - Total Profit in the last hour
        - Country with the highest sales per item and the corresponding revenue sum

- Customers
    - Module name -> customers
    - This module writes random simulated sale orders to the sales topic, with items and countries read from DB Info topic
    - We must choose at random an item and country that is in the topic and then we generate a random price and number of units

- Purchase Orders
    - Module name -> orders
    - This module writes random simulated purchase orders to the purchases topic, with the items read from the DB Info topic.
    - We must choose an item at random, assume a random price per unit and a random quantity

##### Kafka

- Topics
    - DB Info
        - Has Items and Countries
    - Sales
        - Has sales

    - Purchases
        - Has orders

    - Results
        - We need several streams to store each of the results being computed 

- Connectors
    - DB -> DB Info
        - Get information stored on DB (items and countries)
    - Results -> DB
        - Read the results topics and persist the information into the database
        - We have to study well with the sandbox we have created in order to be able to persist several topics to the DB with just on connector. 


##### Data Structures
- Countries
    - Data Fields
        - Id
        - Name
- Items
    - Data Fields
        - Id
        - Name
- Order
    - Data Fields
        - Item
        - Quantity
        - Total sum
- Sale
    - Data Fields
        - Country
        - Item
        - Quantity
        - Total sum

##### General Notes
- All the computation has to be done in the streams with kafka
- We have to do some tests with the connectors in order to see the persistance on the data base to be able to access the best way to go. I would say 1 connector way is enough