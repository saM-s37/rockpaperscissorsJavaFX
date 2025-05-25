

package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameLayout extends Application {
    private int userSelection;
    private int compSelection;
    private String playerPick;
    private String computerPick;

    private Text resultMessage;
    private Button playAgain;

    private Scene loginScene;
    private Scene playScene;
    private Scene resultScene;

    private Image rockImage;
    private Image paperImage;
    private Image scissorsImage;

    private Media winMedia;
    private Media loseMedia;
    private Media startMedia;
    private Media drawMedia;

    private ImageView compImageView;

    private String currentUsername;
    private int wins, draws, losses;
    private static final String USER_FILE = "users.txt";
    private Map<String, String> users = new HashMap<>();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        loadUsers();

        rockImage = new Image(getClass().getResourceAsStream("/rock.png"));
        paperImage = new Image(getClass().getResourceAsStream("/paper.png"));
        scissorsImage = new Image(getClass().getResourceAsStream("/scissors.png"));

        try {
            winMedia = new Media(getClass().getResource("/win.mp3").toExternalForm());
            loseMedia = new Media(getClass().getResource("/lose.mp3").toExternalForm());
            startMedia = new Media(getClass().getResource("/start.mp3").toExternalForm());
            drawMedia = new Media(getClass().getResource("/draw.mp3").toExternalForm());
        } catch (Exception e) {
            System.err.println("Could not load media files: " + e.getMessage());
        }

        Label userLabel = new Label("Username:");
        TextField userField = new TextField();
        userField.setPromptText("Enter username");

        Label passLabel = new Label("Password:");
        PasswordField passField = new PasswordField();
        passField.setPromptText("Enter password");

        Label statusLabel = new Label();

        Button startButton = new Button("Start Game");
        startButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14;");
        startButton.setOnAction(e -> {
            String username = userField.getText().trim();
            String password = passField.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                statusLabel.setText("Fields cannot be empty");
                return;
            }

            if (users.containsKey(username)) {
                if (users.get(username).equals(password)) {
                    currentUsername = username;
                    loadStats();
                    playSound(startMedia);
                    new Thread(() -> {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ignored) {}
                        javafx.application.Platform.runLater(() -> primaryStage.setScene(playScene));
                    }).start();
                } else {
                    statusLabel.setText("Incorrect password");
                }
            } else {
                users.put(username, password);
                saveUsers();
                currentUsername = username;
                wins = draws = losses = 0;
                saveStats();
                playSound(startMedia);
                new Thread(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored) {}
                    javafx.application.Platform.runLater(() -> primaryStage.setScene(playScene));
                }).start();
            }
        });

        VBox loginBox = new VBox(10, userLabel, userField, passLabel, passField, startButton, statusLabel);
        loginBox.setPadding(new Insets(40));
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setStyle("-fx-background-color: linear-gradient(to bottom right, #1E3C72, #2A5298);");

        loginScene = new Scene(loginBox, 400, 300);

        Button rock = new Button("Rock");
        Button paper = new Button("Paper");
        Button scissors = new Button("Scissors");

        styleGameButton(rock);
        styleGameButton(paper);
        styleGameButton(scissors);

        rock.setOnAction(e -> {
            userSelection = 1;
            playerPick = "Rock";
            showResult(primaryStage);
        });
        paper.setOnAction(e -> {
            userSelection = 2;
            playerPick = "Paper";
            showResult(primaryStage);
        });
        scissors.setOnAction(e -> {
            userSelection = 3;
            playerPick = "Scissors";
            showResult(primaryStage);
        });

        HBox playHBox = new HBox(20, rock, paper, scissors);
        playHBox.setAlignment(Pos.CENTER);
        playHBox.setPadding(new Insets(30));
        playHBox.setStyle("-fx-background-color: linear-gradient(to bottom right, #8E2DE2, #4A00E0);");

        playScene = new Scene(playHBox, 600, 250);

        resultMessage = new Text();
        resultMessage.setFont(Font.font("Arial", FontPosture.ITALIC, 18));
        resultMessage.setStyle("-fx-fill: #FFFFFF;");

        compImageView = new ImageView();
        compImageView.setFitWidth(100);
        compImageView.setPreserveRatio(true);

        VBox resultVBox = new VBox(20, resultMessage, compImageView);
        resultVBox.setAlignment(Pos.CENTER);

        playAgain = new Button("Play Again");
        styleGameButton(playAgain);
        playAgain.setOnAction(e -> primaryStage.setScene(playScene));

        Button backToStart = new Button("Back to Login");
        styleGameButton(backToStart);
        backToStart.setOnAction(e -> primaryStage.setScene(loginScene));

        HBox buttonsHBox = new HBox(25, playAgain, backToStart);
        buttonsHBox.setAlignment(Pos.CENTER);
        buttonsHBox.setPadding(new Insets(15));

        BorderPane resultRoot = new BorderPane();
        resultRoot.setCenter(resultVBox);
        resultRoot.setBottom(buttonsHBox);
        resultRoot.setPadding(new Insets(30));
        resultRoot.setStyle("-fx-background-color: linear-gradient(to bottom right, #0F2027, #203A43, #2C5364);");

        resultScene = new Scene(resultRoot, 600, 300);

        primaryStage.setTitle("Rock Paper Scissors");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    users.put(parts[0], parts[1]);
                }
            }
        } catch (IOException ignored) {}
    }

    private void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE))) {
            for (Map.Entry<String, String> entry : users.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException ignored) {}
    }

    private void loadStats() {
        File statsFile = new File(currentUsername + "_stats.txt");
        if (statsFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(statsFile))) {
                wins = Integer.parseInt(reader.readLine());
                draws = Integer.parseInt(reader.readLine());
                losses = Integer.parseInt(reader.readLine());
            } catch (IOException | NumberFormatException e) {
                wins = draws = losses = 0;
            }
        } else {
            wins = draws = losses = 0;
        }
    }

    private void saveStats() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentUsername + "_stats.txt"))) {
            writer.write(wins + "\n" + draws + "\n" + losses);
        } catch (IOException ignored) {}
    }

    private void styleGameButton(Button button) {
        button.setFont(Font.font("Verdana", 16));
        button.setStyle("-fx-background-color: #6a11cb; -fx-background-radius: 15; -fx-text-fill: white; -fx-padding: 10 20 10 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 1); -fx-cursor: hand;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #2575fc; -fx-background-radius: 15; -fx-text-fill: white; -fx-padding: 10 20 10 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0, 0, 2); -fx-cursor: hand;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #6a11cb; -fx-background-radius: 15; -fx-text-fill: white; -fx-padding: 10 20 10 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 1); -fx-cursor: hand;"));
    }

    private void showResult(Stage stage) {
        compSelection = new Random().nextInt(3) + 1;
        switch (compSelection) {
            case 1 -> computerPick = "Rock";
            case 2 -> computerPick = "Paper";
            default -> computerPick = "Scissors";
        }

        String resultText;
        boolean playWinSound = false;
        boolean playLoseSound = false;
        boolean playDrawSound = false;

        if ((userSelection == 1 && compSelection == 3)
                || (userSelection == 2 && compSelection == 1)
                || (userSelection == 3 && compSelection == 2)) {
            resultText = "You win! 🎉";
            wins++;
            playWinSound = true;
        } else if (userSelection == compSelection) {
            resultText = "It's a tie!";
            draws++;
            playDrawSound = true;
        } else {
            resultText = "Computer wins! 😞";
            losses++;
            playLoseSound = true;
        }

        resultMessage.setText(currentUsername + " | Wins: " + wins + ", Draws: " + draws + ", Losses: " + losses +
                "\nYou picked " + playerPick + ", computer picked " + computerPick + ".\n" + resultText);

        Image compImage = switch (compSelection) {
            case 1 -> rockImage;
            case 2 -> paperImage;
            default -> scissorsImage;
        };
        compImageView.setImage(compImage);

        saveStats();

        try {
            if (playWinSound && winMedia != null) {
                playSound(winMedia);
            } else if (playLoseSound && loseMedia != null) {
                playSound(loseMedia);
            } else if (playDrawSound && drawMedia != null) {
                playSound(drawMedia);
            }
        } catch (Exception ex) {
            System.err.println("Sound error: " + ex.getMessage());
        }

        stage.setScene(resultScene);
    }

    private void playSound(Media media) {
        if (media != null) {
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        }
    }
}
