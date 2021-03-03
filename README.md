# Java Kata: Consumptions API

Code kata in Java where you have to write a REST API providing consumption data of different meters.

[A code kata is an exercise in programming which helps a programmer hone their skills through practice and repetition.](https://en.wikipedia.org/wiki/Kata_(programming))

* [Topic](#topic)
* [Frame conditions](#frame-conditions)
* [Tasks](#tasks)
* [Procedure](#procedure)
* [License](#license)

## Topic

You have to implement a simple REST API.

## Frame conditions

1. You have 2 hours of time.

   If you reach this time limit stop your work.
   It is one part of the kata to respect this time limit.
   
2. There are no restrictions on how to use the provided time.
   If you want to code the entire time, take a break or have a cup of coffee - it’s up to you.

3. This is a real world situation. You are allowed to consult the Internet, use every library you want, call a friend ...

   **BUT:** You are not allowed to do [pair programming](https://en.wikipedia.org/wiki/Pair_programming).
   **AND** If you have already done this kata before to have a look at your previous implementation.

4. Develop your code based at least on Java language level 8 (the predefined level is 11).

5. Keep the following priorities in mind while you implementing - in the mentioned order:
   1. Code quality
   2. Usage of object oriented methods
   3. Functionality

6. Given resources:

   * [`electricity-consumption.csv`](src/main/resources/data/electricity-consumption.csv): Contains hourly based consumption values for electricity meters.
        > **Hint:** Each electricity meter has a unique `meter_id` and is assigned to a building having a unique `building_id`. The electricity consumption `consumption_kwh` was measured in the unit kWh over a period of one hour. Each consumption period started at UTC time `timestamp_utc`.
   * [`heat-consumption.json`](src/main/resources/data/heat-consumption.json): Contains fifteen minutes based consumption values for heat meters.
        > **Hint:** Each heat meter has a unique `mid` and is assigned to a building having a unique `bid`. The heat consumption `v` was measured in the unit kWh over a fifteen minutes period. Each consumption period started at UTC time `ts`.
          
## Tasks

1. Your software should read all data from the given CSV and JSON files in a meaningful structure.

2. Implement an API endpoint in order to provide all consumption data ordered by time and meter.

3. Implement an API endpoint in order to provide all consumption data ordered by time for a given meter.

4. Implement an API endpoint in order to provide all energy consumption data ordered by time with a resolution of 15 minutes for a given building.

   > **Hint**: Energy consumption includes both electricity and heat. It is measured in the unit kWh.

## Procedure

1. Get the code. There are several ways for it:

   1. With fork (makes it possible to preserve your work):
      1. [Fork this repository](https://github.com/smb-kata/java-kata/fork)
      2. Clone this fork to your computer:
         ```bash
         git clone <your github url>
         # Example: git clone https://github.com/kata-master/java-kata.git
         ```

   2. Clone this repository with local branch:
      ```bash
      git clone https://github.com/smb-kata/java-kata.git
      git checkout -b master      
      ```

   3. Just download it from [here](https://github.com/smb-kata/java-kata/archive/master.zip)

2. Open in your favorite IDE.

   > **Hint**: We recommend [IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea).

3. Start the kata.

4. Discuss with your friends and/or colleges your solution.

## FAQ

##### How to run your application?

1. By IDE:<br/>
   Just click with right mouse on the [`JavaApplication`](src/main/java/org/smb/kata/java/JavaApplication.java) class and
   then on _Run_ or _Debug_.
2. By command line:
   ```bash
   ./gradlew bootRun
   ```
##### How to run your tests?

1. By IDE:<br/>
   Just click with right mouse on the root of your project tree and click on
   then on _Run Tests in 'java-kata'_ or _Debug Tests in 'java-kata'_.
2. By command line:
   ```bash
   ./gradlew test
   ```

## License

See [LICENSE](LICENSE) file.