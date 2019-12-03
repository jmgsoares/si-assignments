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
        - Revenue per item (sum item sales)
        - Expense per item (sum item orders)
        - Profit per item (sum item sales - sum item orders)
        - Total Revenue (sum all sales - sum all orders)
        - Total Expense (sum all orders)
        - Total Profit (sum all sales - sum all orders)
        - Average expense per order (separated by item)
        - Average expense per order (aggregate all items)
        - Most profitable item (if there is a tie, only one is needed)
        - Total revenue in the last hour
        - Total expense in the last hour
        - Total Profit in the last hour
        - Country with the highest sales per item and the corresponding revenue sum
    - QUESTIONS
        - I don't get the following
            - Average expense per order (separated by item) - Separated by item? how does this work? Each order only has 1 item...

- Customers
    - Module name -> customers
    - This module writes random simulated sale orders to the sales topic, with items and countries read from DB Info topic
    - We must choose at random an item and country that is in the topic and then we generate a random price and number of units
    - QUESTIONS
        - We are assuming that the price and number of units and randomly generated. Is it correct?

- Purchase Orders
    - Module name -> orders
    - This module writes random simulated purchase orders to the purchases topic, with the items read from the DB Info topic.
    - We must choose an item at random, assume a random price per unit and a random quantity
    - QUESTIONS
        - Do we also have to randomize the date of the purchase??

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