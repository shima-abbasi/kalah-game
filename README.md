# Kalah Game
This is a java web application that runs the game of 6-stone Kalah. 
For the general rules of the game please refer to Wikipedia: https://en.wikipedia.org/wiki/Kalah.
The default implementation of this app for 6-stone. 

## About The Game
* Each of the two players has six pits in front of him/her. 
* To the right of the six pits, each player has a larger pit, his Kalah or house.
* At the start of the game, six stones are put In each pit.
* The player who begins picks up all the stones in any of their own pits, and sows the stones on to the right, one in each of the following pits, including his own Kalah. 
* No stones are put in the opponent's' Kalah. If the players last stone lands in his own Kalah, he gets another turn. This can be repeated any number of times before it's the other player's turn.
 
## Technology Used
* [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) - Programming language
* [Maven 3.6.0](https://maven.apache.org/download.cgi) - Build tool
* [Spring boot 2.1.6](https://projects.spring.io/spring-boot/) - Backend Framework
* [In-memory H2](https://www.h2database.com/html/main.html) - DataBase

### Play The Game 2 API Using Browser or Postman
* [CreateGame](http://localhost:8080/games)
* [Move](http://localhost:8080/games/{gameId}/pits/{pitId})

# Author
* [Shima Abbasi](https://github.com/shima-abbasi) - github




