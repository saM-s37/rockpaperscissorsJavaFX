# 🎮 Rock Paper Scissors Game

*A modern desktop implementation of the classic game with JavaFX*

---

### 📖 About The Project

This project is a desktop implementation of the classic "Rock, Paper, Scissors" game built with Java and JavaFX. Unlike simple console versions, our implementation features user account management, statistics tracking, visual effects, and sound accompaniment. The goal was to create a polished, complete application that demonstrates solid understanding of object-oriented programming principles.

### ✨ Key Features

- 🔐 **User Authentication System** - Secure login with password protection
- 📊 **Individual Player Statistics** - Track wins, losses, and draws
- 🎨 **Modern UI Design** - Gradient backgrounds and smooth animations
- 🔊 **Sound Effects** - Audio feedback for all game actions
- 💾 **Data Persistence** - Save progress between sessions
- 🖼️ **Visual Feedback** - Display computer's choice with images

### 🏗️ Project Structure

```
RockPaperScissorsGame/
├── src/
│   └── GameLayout.java        # Main application class
├── resources/                 # Images and audio files
│   ├── rock.png
│   ├── paper.png
│   ├── scissors.png
│   ├── win.mp3
│   ├── lose.mp3
│   ├── start.mp3
│   └── draw.mp3
└── data/                      # User data files
    ├── users.txt
    └── [username]_stats.txt
```

### 🔧 Technical Implementation

**Architecture Design:**
- Single-class architecture in `GameLayout.java` for simplified navigation and compact structure
- Three distinct JavaFX scenes: Login, Game, and Results - each with custom styling
- File-based data persistence for reliable user data storage

**Core Technologies:**
- **JavaFX** - Modern GUI framework for rich user interfaces
- **CSS Styling** - Gradient backgrounds and hover animations
- **Multithreading** - Separate threads for audio and UI responsiveness
- **File I/O** - Robust data persistence with error handling

### 🎯 Game Logic Example

```java
// Winner determination logic
if ((userSelection == 1 && compSelection == 3) ||
    (userSelection == 2 && compSelection == 1) ||
    (userSelection == 3 && compSelection == 2)) {
    resultText = "You win! 🎉";
    wins++;
    playWinSound = true;
}
```

### 🛠️ Technical Challenges & Solutions

- **JAR Resource Loading**: Used `getClass().getResourceAsStream()` for reliable resource access
- **Scene Transitions**: Implemented smooth transitions with proper event handling
- **Cross-platform Audio**: Wrapped all audio operations in try-catch blocks for stability
- **Data Integrity**: Automatic file creation and error recovery for corrupted data

### 🎵 Audio System

Each user action is accompanied by appropriate sound effects:
- 🎵 Login success sound
- 🏆 Victory sound
- 😞 Defeat sound
- 🤝 Draw sound

All audio playback runs in separate threads to maintain UI responsiveness.

### 📸 Application Screenshots
The game features three distinct scenes:
- Login Scene: Clean, gradient-styled interface for user authentication
- Game Selection Scene: Interactive buttons for Rock, Paper, and Scissors choices
- Results Scene: Displays game outcome with visual and textual feedback

![Screenshot 2025-05-24 152726](https://github.com/user-attachments/assets/bf0e0c03-eb16-420c-b093-dc0a9d20a60b)
![Screenshot 2025-05-24 152750](https://github.com/user-attachments/assets/331461ae-bdb1-4755-90d8-aea06f155578)
![Screenshot 2025-05-24 153119](https://github.com/user-attachments/assets/4c119976-274c-46b8-bafa-b1a6dbe8056b)


### ✅Installation Procedure
To run this JavaFX application:
1. Requirements: JDK 11+ and an IDE (IntelliJ IDEA recommended).
2. Git clone: https://github.com/saM-s37/rockpaperscissorsJavaFX
3. Import: Open the project in your IDE (e.g., import pom. xm/).
4. JavaFX: Ensure JavaX SDK is added as a library, and VM options are set (e.g., -module-path /path/to/laxatt sak/lib-add-modules javatx.contro/s.javaix.ccmL.javaixmedia). Verify the resources directory is marked as
   "Resources Root"
5. Run: Build the project and run the GameLayout java file

### 📚 Learning Resources
- **AI Assistants**: ChatGPT, Claude.ai, Gemini.ai for debugging and code optimization
- **Course Materials**: Professor Ahmet's Java and OOP fundamentals
- **Online Tutorials**: YouTube JavaFX and UI design guides

### 🔮 Future Enhancements

- 🌐 **Multiplayer Online Mode** - Play against friends remotely
- 🏆 **Tournament Format** - Competitive brackets and rankings
- 🎨 **Theme Customization** - Multiple UI themes and user preferences
- 📈 **Advanced Statistics** - Win streaks, strategy analysis, and detailed reports

### 🎓 What We Learned

- Building complete GUI applications with JavaFX
- File handling and robust error management
- Object-oriented design principles in practice
- User experience design and interface polish

### 🏁 Conclusion

This project successfully bridges theoretical knowledge with practical application, covering the complete development cycle of a desktop application. Despite the simple concept, the implementation encompasses crucial development aspects: from interface design to data storage and thread management. Working on this project provided valuable experience and serves as a foundation for creating more complex applications in the future.

