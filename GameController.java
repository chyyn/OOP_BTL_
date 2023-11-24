package Main.Controller;

import javafx.fxml.FXML;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class GameController {
    private Map<String, String> cards = new HashMap<>();
    private List<String> cardNames = new ArrayList<>();
    private Set<String> flippedCards = new HashSet<>();
    private String firstFlippedCard = null;

    @FXML
    public void initialize() {

    }

    public GameController() {
        loadCardsFromFile("src/main/resources/Data/Dictionary.txt");
    }

    private void loadCardsFromFile(String filename) {
        try {
            Scanner scanner = new Scanner(new File("src/main/resources/Data/Dictionary.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" : ");
                if (parts.length == 2) {
                    String cardName1 = parts[0].trim() + "_card";
                    String cardName2 = parts[1].trim() + "_card";
                    cards.put(cardName1, parts[0]);
                    cards.put(cardName2, parts[1]);
                    cardNames.add(cardName1);
                    cardNames.add(cardName2);
                }
            }
            scanner.close();
            // Shuffle the cardNames list to randomize the order of the cards
            Collections.shuffle(cardNames);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }
    }

    public void flipCard(String cardName) {
        if (cards.containsKey(cardName) && !flippedCards.contains(cardName)) {
            System.out.println("Flipped card: " + cardName + " - " + cards.get(cardName));
            flippedCards.add(cardName);
            if (firstFlippedCard == null) {
                firstFlippedCard = cardName;
            } else {
                checkForMatch(firstFlippedCard, cardName);
                firstFlippedCard = null;
            }
        } else {
            System.out.println("Invalid card name or card already flipped.");
        }
    }

    public void checkForMatch(String cardName1, String cardName2) {
        if (flippedCards.contains(cardName1) && flippedCards.contains(cardName2)) {
            if (cards.get(cardName1).equals(cards.get(cardName2))) {
                System.out.println("Match found: " + cardName1 + " and " + cardName2);
            } else {
                System.out.println("No match. Flipping cards back over.");
                flippedCards.remove(cardName1);
                flippedCards.remove(cardName2);
            }
        } else {
            System.out.println("Both cards must be flipped over to check for a match.");
        }
    }
}
