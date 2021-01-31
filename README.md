# :1234: N-puzzle
Solver of an N-Puzzle that uses a modified version of A*, Greedy and Uniform Cost search algorithms.
You can choose between Manhattan, Hamming or Euclidean admissable heuristics.

## How to use

 Run using the gradle wrapper

  ```./gradlew run```
  
 This will build the app; generate a random 3x3 puzzle (8-puzzle) and solve it using A* and Manhattan heuristic.
 Program args can be modified in the build.gradle:
 * -a \<algorithm\> - one of the three algorithms
 * -h \<heuristic\> - required for astar and greedy; forbidden for uniform
 * -r \<N\> - optional; will genereate a N*N solvable puzzle
 * -f \<file path\> - optional; will read the input from file
 *   | - optional; repeatable; gives the possibility to enter args again; will run another solver in a separate thread;
     * so ```['-a astar -h manhattan -r 3 | -a astar -h euclidean -r 4']``` will solve two puzzles in parallel
 
 #### If no argument for the input source is provided, you will be promted to manually enter input in the following format:
 ```
 3
 1 2 3
 4 5 6
 7 8 0
 ```
 
  #### Some puzzles may be unsolvable. The solution is in snail format:
 ```
 1 2 3
 8 0 4
 7 6 5
 ```

## How to test

You can run all the tests using ``` ./gradlew test ``` or individual tests using ```./gradlew test --tests <test Class/Method>```
