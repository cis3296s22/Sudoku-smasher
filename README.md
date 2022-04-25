# Sudoku Smasher
Sudoku Smasher is a desktop client/remote server sudoku puzzle solver written in Java. The client consists of a simple GUI that mirrors the layout of a Sudoku puzzle and allows for easy user input. The server will handle attempting to solve the puzzle, either sending a solved sudoku puzzle back to the client or a message saying that it is unsolvable. The server will utilize multithreading to serve many clients simultaneously and log all attempted solutions internally. The client will then either display the solved puzzle or a message saying that it was unsolvable. 

<p align="center">
   <img width="707" alt="Screen Shot 2022-04-25 at 1 54 36 PM" src="https://user-images.githubusercontent.com/66076114/165146111-6d5f6fc6-fe11-4764-8fbe-ce92aa595e24.png">
</p>
# How to run

- Download the latest binary from the Release section on the right on GitHub.  
- Open two shells and navigate to where the .jar files have been downloaded.
- Run on the command line with one command per shell window
```
java -jar Server.jar
java -jar Client.jar  
```
- You will see terminal outputs for both programs as well as a GUI
- The programs will not function properly if the client is run before the server

# How to contribute
Follow this project board to know the latest status of the project: https://trello.com/b/JFsOyaK5/sudoku-board
Please send a message to one of us if you would like to contribute

### How to build
- Use this github repository: ... 
- The most recent, stable release will be on the main branch. Other branches will be implementing features in progress.
- The most recent version of Intellij is recommended although the project should work on any Java IDE.
- For the client, make sure the MANIFEST.MF file says Main-Class:SmasherClient.ClientMain. For the server it should read Main-Class:SmasherServer.ServerMain
- In Intellij, navigate to File > Project Structure > Artifacts
- Click the plus button to add a new artifact, select 'from modules with dependencies'
- Select the main function in the package you are trying to build and hit 'ok'.
- Go to Build > Build Artifacts and select 'Build' for the .jar you just made.
- You should be able to find your .jar build in the out/artifacts folder
