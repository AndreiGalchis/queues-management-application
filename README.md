# Queues Management Application

A Java Swing simulation that models how clients are distributed to multiple service queues over time.

## Features

- Configure number of clients, number of queues, simulation interval, and arrival/service ranges
- Choose dispatch strategy:
  - `SHORTEST_QUEUE` (fewest clients waiting)
  - `SHORTEST_TIME` (lowest total waiting time)
- Run concurrent queue processing (one thread per server)
- View simulation logs in a Swing window
- Persist logs to `simulation_log.txt`

## Project Structure

```
QueuesManagementApplication/
  pom.xml
  src/
	main/java/org/example/
	  BusinessLogic/
	  GUI/
	  Model/
	  Main.java
  docs/
	diagrams/
	samples/
  README.md
  .gitignore
```

## Tech Stack

- Java
- Swing (GUI)
- Maven

## Build and Run

1. Build the project:

```bash
mvn clean package
```

2. Run the app:

```bash
mvn exec:java -Dexec.mainClass="org.example.Main"
```


## Documentation

- UML and use-case diagrams are in `docs/diagrams`
- Input samples are in `docs/samples`

