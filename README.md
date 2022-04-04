# Sudoku Smasher
Sudoku Smasher is a desktop client/remote server sudoku puzzle solver written in Java. The client will have a simple GUI that mirrors the layout of a Sudoku puzzle and allows for easy user input. The server will handle attempting to solve the puzzle, either sending a solved sudoku puzzle back to the client or a message saying that it is unsolvable. The server will utilize multithreading to serve many clients simultaneously and log all attempted solutions internally. The client will then either display the solved puzzle or a message saying that it was unsolvable. 

![An example puzzle](https://cdn.britannica.com/42/97142-131-E3E24AA5/sudoku-puzzle-games.jpg?q=60)

# How to run   
- Download the latest binary from the Release section on the right on GitHub.  
- Open a shell and navigate to where the .jar file has been downloaded.
- Run on the command line with
```
java -jar Sudoku-smasher.jar  
```
- You will see the output of a good and bad puzzle run in the terminal 

# How to contribute
Follow this project board to know the latest status of the project: https://trello.com/b/JFsOyaK5/sudoku-board
Please send a message to one of us if you would like to contribute

### How to build
- Use this github repository: ... 
- The most recent, stable release will be on the main branch. Other branches will be implementing features in progress.
- The most recent version of Intellij is recommended although the project should work on any Java IDE.
- In Intellij, navigate to File > Project Structure > Artifacts
- Click the plus button to add a new artifact, select 'from modules with dependencies'
- Select the main function in the package you are trying to build and hit 'ok'.
- Go to Build > Build Artifacts and select 'Build' for the .jar you just made.
- You should be able to find your .jar build in the out/artifacts folder
