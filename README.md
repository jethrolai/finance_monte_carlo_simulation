# Finance Simulator Demo
This project is to demo a simple Monte Carlo simulation facility to calculate the future projection of a finance portfolio.  

## Set up 
1. Install Maven. if you are on mac, use homebrew `brew install maven`
2. Run test, build and demo at once by `mvn clean test package ; java -jar target/finance-simulator-1.0.0-SNAPSHOT.jar`
3. Run test, build and multiple inputs at once by `mvn clean test package ; java -jar target/finance-simulator-1.0.0-SNAPSHOT.jar data/simulations.csv`
4. If you don't have maven installed, you can download the jar here [https://s3-us-west-1.amazonaws.com/api.jethrolai.com/montecarlo_simulation/finance-simulator-1.0.0-SNAPSHOT.jar]

## Customize Simulations


## Warning
1. This initial version was done in just a couple of hours. It's neither fully unit tested nor verified. Do not use this in any actual practice.
2. There are many places for improvement. 
    * runtime can be improved in several places. For example, use mapreduce. The distributed computation can be implemented on two different levels, portfolio and simulation set. 
    * space complexity can also be optimized in many places. For example, run simulation in batch and release the results of each simulation sets and and release the memory in runtime. 
    * the synchronized block actually slow things down so there is also a better way to do this in concurrent and distributed way
    * when space and runtime complexity are both optimized, ongoing, real time analysis can be a reality. 
    * definitely need test coverage
    * input validation will also need to be improved.    


## Collaboration
  email: jethro@jethrolai.com
