# Java Swing Trivia Game 

A graphical desktop trivia application built with Java Swing. It connects to a live REST API to generate dynamic quizzes, featuring multiple game modes, category selection, and a persistent local leaderboard.

> **Note:** This is the graphical desktop version of this project, built to demonstrate Event-Driven Programming and the MVC pattern. For the lightweight Command-Line Interface (CLI) version, [click here](https://github.com/7rdamian/general-knowledge-quiz).

## Key Features

* **Interactive GUI:** Fully custom graphical interface built using Java Swing, featuring intuitive navigation, dynamic question rendering, and real-time score tracking.
* **Live API Integration:** Fetches thousands of unique questions dynamically from the [Open Trivia Database (OpenTDB)](https://opentdb.com/) using native HTTP requests and JSON parsing.
* **Customizable Gameplay:** Users can tailor their experience by selecting the number of questions, difficulty, and specific categories.
* **Multiple Modes:** Includes a Standard mode, a Timed mode (with a visual countdown), and a Review mode.
* **Persistent Leaderboard:** Saves and loads player scores using local File I/O to track high scores across sessions.

## Tech Stack

* **Language:** Java
* **UI Framework:** Java Swing (AWT, ActionListeners)
* **Architecture:** Model-View-Controller (MVC), Event-Driven Programming
* **Libraries:** `java.net.HttpURLConnection`, `org.json`

## How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/java-swing-trivia-game.git
   ```
2. Dependencies: Ensure the org.json library .jar file is added to your project's classpath.
3. Open the project in your preferred IDE (IntelliJ, Eclipse, etc.).
4. Compile and run the Main.java class located in src/main/Main.java to launch the application window.

## What I Learned

Transitioning this project from a command-line app to a GUI taught me the importance of the Model-View-Controller (MVC) pattern. I learned how to decouple my backend game logic from the frontend visuals and manage state across different graphical JPanel components.
