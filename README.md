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



##### Handle large write volume: Billions of write events per day.
- dfffdfd
- dnfkfnf

##### Handle large read/query volume: Millions of merchants wish to gain insight into their business. Read/Query patterns are time-series related metrics.
##### Provide metrics to customers with at most one hour delay.
##### Run with minimum downtime.
##### Have the ability to reprocess historical data in case of bugs in the processing logic.
