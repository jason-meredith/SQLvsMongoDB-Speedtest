# CST8276 Group Research Assignment

Our goal with this project is to analyze the average insertion
times of identical data into both a Relational database compared
to a Document-Oriented database. We accomplish this by generating
sample data objects, deriving two insertion statements (one for
MySQL and another Mongodb) from each object, and capturing
the time it takes to complete the insertion. This process is repeated
until an accurate average can be determined.

To determine if the complexity of the data plays a role in the insertion
time (the thought being that if a data object contains an Array, inserting
into an SQL database would require multiple insertions into multiple tables while
an insertion into a document-oriented database is still a single insertion)
the complexity of the Sample Data Objects can be increased/decreased during runtime
to test for a correlation between insertion time and data complexity.

## Overview
To get an idea of how this program works, checkout  the`generators.Example` class or
some of the JUnit test cases.


**Generators Package**

The Generators package contains the core of the backend, with `SampleDataGenerator`
controlling the creation of Sample Data Objects and passing them off to the database 
connectors.

In `SampleDataGenerator` there is the `ExecutorService` called _threadPool_
that runs the two vital threads of execution: one continually populates an `ArrayBlockingQueue`
with Sample Data Objects based on the current Complexity Level, while the 
other thread being run will `take()` objects from our queue mentioned above, clone it
for each `InsertionThread` associated with this generator and place an identical
copy in each of their respective queues.

After instantiating a `SampleDataGenerator` you must add an `InsertionThread` for each
database you are testing. Each one of these `InsertionThread` is a Runnable 
being run by the _threadPool_ in the `SampleDataGenerator`. Each InsertionThread
has a queue that contains Sample Data Objects that are is constantly polled while
running to pass these Sample Data Objects to the `DatabaseConnector` that was associated
with an `InsertionThread` upon its instantiation.

Every time an `InsertionThread` passes a Sample Data Object to a `DatabaseConnector` for
insertion the `DatabaseConnector` will return an `InsertionResult`. These are stored in the
`InsertionThread` and can be retrieved through the `SampleDataGenerator` using `results()` 
which returns a HashMap of ArrayList<InsertionResult> with the `InsertionThread` name
as the key.

Sample Data Objects are generated using the `SampleDataFactory` which will produce a new
Sample Data Object using it's static `getSampleData()` method. Sample Data Object complexity
is adjusted using `setComplexity(int)` which will adjust the complexity of Object returned
by `getSampleData()`.

**Database Package**

The Database package contains the the `DatabaseConnector` interface. Implementations of this
interface are passed to InsertionThreads upon their instantiation and control how Sample Data
Objects are inserted into their respective database and return `InsertionResults` upon 
insertion, showing data vital to our study such as how long the insertion took to complete
and the complexity of the data.

**Sampledata Package**

Sample Data Objects are objects the implement the `SampleData` interface. They must have a
method for returning a HashMap that provides representations of itself in the form of an
insertion statements for each database being tested, the key being `InsertionThread` name;
it must have a method for cloning itself; and it must have a method that returns a unique identifier that can match
it self to any of it's clones.

Other than those rules a Sample Data Object can contain any number of fields and values, hashes
and/or arrays.

## To Do

**Database Package**

The `DatabaseConnection` implementations still need to be completed, as in providing a 
connection to a database that can run a String as a query (the insert statement). We
still need an implementation for both MySQL as well as MongoDB. The package is self-contained
enough that only the two files, `MySQLConnection.java` and `MongoDBConnector.java` need
be modified and the comments therein explain pretty concisely what needs to be done.

**Sampledata Package**

Fairly simple, more `SampleData` implementations need to be created. The more implementations
we have and the bigger difference in complexity between the least and most complex the more
accurate our results will be. Use Lists, HashMaps, associations, whatever as long as it can
be represented as both a Java Object and as an SQL/MongoDB insertion statement(s).

**Front-End Interface**

Any kind of interface (web, JavaFX/Swing, Command-Line) will do as long as it can call all
the methods of the `SampleDataGeneratorInterface` and grok the data returned by `getResults()`
into some kind of conclusion.