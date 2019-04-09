# PaytmLabs SDE Challenge

## Coding Question

Write an interface for a data structure that can provide the moving average of the last N elements added, add elements to the structure and get access to the elements. Provide an efficient implementation of the interface for the data structure.

### Minimum Requirements

1. Provide a separate interface (IE `interface`/`trait`) with documentation for the data structure
2. Provide an implementation for the interface
3. Provide any additional explanation about the interface and implementation in a README file.

## Coding Question : Solution

<b>Algorithm</b>


Double function movingAvarageNext(number : N)<br>
	queue.add(N)<br>
	sum = sum + N <br>
if(queue.size()>N)<br>
	sum = sum-queue.get()<br><br>

return average=sum/queue.size

<br><br>

<b>Artifacts:</b>

- <b>Interface : ``IMovingAverage``:</b> defines the contract/api data-structure for calculating moving average of last N(windowSize) elements added. It has two main operation.
- ``public double next(Double newElement)`` : Takes newElement of the type Double and calculate moving average of last N(windowSize) elements added. 
- ``public LinkedList<Double> getAllElements()`` : Returns all the elements currently in the internal data-structure in the form of new LinkedList. It doesn't give/return direct reference to internal queue elements. 


- <b>Concrete Implementation Class:</b> ``MovingAverageImpl`` : Provides implementation of the interface IMovingAverage.
- It uses internal data-structure as queue to store the elements for which moving average is to be calculate.
- Method keeps track of total <b>'sum'</b> of all the elements,  <b>windowSize</b> - Window size of elements for which moving average to be calculated.
- Total sum is calculated upon element addition, if element queue size is greater than windowSize(N) then first element is removed(poll) from queue and also from total 'sum' 
- If element queue size is less than  windowSize(N) then element is simply added(offer) to sum.
- Average is calculated based on total 'sum' and queue size. Average is calculate with 2 decimal points precision.

<b>Complexity:</b>

- Data-structure operations calculate the moving average with time complexity of O(1).
- Data-structure operations calculate the moving average with space complexity of O(1). [because upper bound of queue(N) is known up-front, that gives constant space/size complexity].

<b>Tests:</b>

- MovingavgApplicationTests is JUnit test cases for both the method of the concrete implementation for  next and getAllElements.
- It covers all possible positive as well as negative test cases for the MovingAverageImpl.


<b>How to run:</b>

MovingavgApplication is spring boot main class. Upon execution of this class will initialize MovingAverageImpl with windowSize 5.

## Design Question

Design A Google Analytic like Backend System.
We need to provide Google Analytic like services to our customers. Please provide a high level solution design for the backend system. Feel free to choose any open source tools as you want.

### Requirements

1. Handle large write volume: Billions of write events per day.
2. Handle large read/query volume: Millions of merchants wish to gain insight into their business. Read/Query patterns are time-series related metrics.
3. Provide metrics to customers with at most one hour delay.
4. Run with minimum downtime.
5. Have the ability to reprocess historical data in case of bugs in the processing logic.


## Design Question : Solution

The high-level approach and software/harware stack components arrangement for the system like Google Analytics is as captured in diagram below.
  
![PayTM Analytics System](https://github.com/mehul-kaps/SDEChallenge/blob/master/images/Analytics-Backend.png)

There will be multiple aspects of high level design as follow.

1. Firewall
2. Load Balancer
3. WebServer
4. API Gateway and Discovery Services
5. Kubernetes cluster for EventWriteService and QueryService
6. KAFKA Broker
7. Druid Cluster
8. Spark Cluster




1. <b>Firewall:</b> User traffic from internet will be routed through firewall to ensure and remediate any http/https attacks Or non-http/https attack are stopped at the gate itself and those connection will be terminated from propogating into the system.
2. <b>Load Balancer:</b> Load balancer like F5 will be used in-front of webservers to ensure request are being routed to multiple available webservers. In case of failure load balancer will use available webservers to route the request.
3. <b>Webserver</b>: Simply act as request forwarder from load balancer to API Gateway
4. <b>API Gateway and Discovery Services:</b> API Gateway is the program that will act as single entry point to the PayTM Analytics System. Along with it, if we have multiple cluster of services available then discovery service can be used for locating where the exact required service is situated. Might as well discovery service offered by Kubernetes can be leveraged too.  
5. <b>Kubernetes cluster for EventWriteService and QueryService</b>: This is kubernetes cluster setup for pure Microservices EventWriteService and QueryService.
6. <b>KAFKA Broker:</b> KAFKA Broker will provide high throughput write because of it's configuration of multiple partitions.
7. <b>Druid Cluster:</b> Druid’s combines OLAP/analytic databases, timeseries databases, and search systems
8. <b>Spark Cluster:</b> Provides analytics and ETL capabilities to the time series data. - Spark is Widely deemed the successor to Hadoop MapReduce, Apache Spark is a fast and general engine for large-scale data processing.



##### Handle large write volume: Billions of write events per day.
- System can easily handles the huge amount of write volume because of kubernetes native traffic sensitive scaling of Microservice EventWriteService. Depending on need kubernetes automatically increase and decrease the instances needed for the processing. This layer also handles the business logic needed for processing.
- Actual data store will happen at KAFKA Cluster as EventWriteService will publish received messages to KAFKA Topic. KAFKA Topic's partitions are spread across multiple server and KAFKA topic is partitioned based on date/timestamp.
- Druid complements KAFKA's large processing capabilities by consuming messages from KAFKA and provide different dimensions of the services like OLAP/analytic databases, timeseries databases, and search systems. Its highly scalable and fault tolerant database best fitted for our need.

##### Handle large read/query volume: Millions of merchants wish to gain insight into their business. Read/Query patterns are time-series related metrics.
- Druid and Spark both have native support for providing millions of request per minute/hour/day. Also time series data nature is native to both the tools.
- Repeat requests for particular metrics/query will be cached in ReadCache for further performance boost.
- Further more Spark will be clustered based on geographic location, Merchant type or an other criteria. With this kind of clustering it will boost performance and ensure data proximity for serving data from nearest cluster to avoid network traversal and slowness.    
- Kafka maintains a time index for each topic. So data will be stored with time index in KAFKA. The same data will be steamed into druid where it will be stored as time-series data. However Druid will not be primary source for query as it contains all kind of data structured and unstructured. So Druid will be providing/offering limited query capability in this case. Main source for query server will be Spark database cluster.


##### Provide metrics to customers with at most one hour delay.
- QueryService provides different querying capabilities about the events being written at high rate. QueryService mostly rely on the data from Spark, but sometimes for data which doesnt need ETL processing can directly be ready from Druid.
- KAFKA topic is partitioned and clustered which is time based index which allows to read time data based on timestamp.
- Druid reads data from KAFKA and store in it's database. Data which doesnt need any ETL processing can directly be read from Druid database. It's supported by Druids Sub-second OLAP Queries and Power analytics application.
- Actual data analytics and batches runs in Spark Database because there are times when data coming in as event are unstructured and not heterogeneous. Spark is best at running data analytics by combining SQL, DataStreaming and complex data analytics. Spark continuously reads data from Druid , applies ETL on data and store the data in the format needed by user. 
- Spark continuously process the data and keep generating the metrics by slicing and dicing the data as per definition of metrics for each user/client. Spark would be able to easily meet the requirement of providing metrics to customers with at most one hour delay.

##### Run with minimum downtime:
- Systems at multiple level work in tandem to ensure its designed for worst case scenario. So system at each level are fault tolerant and highly scalable.
- Load Balancer : Ensures to route http(s) request to available webserver, ensuring high availability. Also raise alert if its not able to connect to any webserver. Multiple webserver ensures fault tolerance nature of http request being forwarded from Load balancer to down systems.
- Kubernetes Cluster: Kubernetes cluster ensures availability and reliability of Microservices beneath. Kubernetes can scale-up or down based on need and volume.
- KAFKA Broker: KAFKA by default is very reliable system and more over KAFKA is setup in cluster mode which will fortify that KAFKA will be available always without data integrity issues occurring because of KAFKA cluster node/paritition failures.
- Spark Cluster: Ensures availability throughout the day and during node failure as other node takes over the query processing.


##### Have the ability to reprocess historical data in case of bugs in the processing logic.
- One of the native capability of KAFKA Topic is replay message. Also as message remains on the topic even after consumer consumes it, That means KAFKA cluster store and replicate each message published on its TOPIC. This gives us very flexible message replay capabilities based on date/timestamp/any other key factor by which events have been stored.
- Once message is replayed from KAFKA, further flow in the system will remain as it is and it will start processing data as normal processing.


