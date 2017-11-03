# drPoloniex
Poloniex Exchange Real-time Data Receiver 

The Poloniex Data Receiver is a simple application designed to run in a Docker container.  It currently connects to the Poloniex Exchange (PubSub) queue and receives real-time exchange data. This data is then parsed into DTO objects. 

Later in the process the data receiver will persist the data to a database (Maria) and published to a Message Queue (RabbitMQ) for processing by Crypto Currency analyis agents. 
