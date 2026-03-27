# Queues Management Application

A Java Swing simulation that models how clients are distributed to multiple service queues over time.

This project was originally developed for a university assignment and then reorganized into a clean, portfolio-friendly structure.

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

If `exec:java` is not available in your local setup, run from your IDE with `org.example.Main` as entry point.

## Documentation

- UML and use-case diagrams are in `docs/diagrams`
- Input samples are in `docs/samples`

## Notes

- The app currently logs to a local file named `simulation_log.txt` in the repository root.
- Build outputs (`target/`) and IDE files are ignored by `.gitignore`.
