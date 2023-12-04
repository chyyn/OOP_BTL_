package Main.Controller;

import Main.Animation.SlideAnimation;
import Main.DictionaryCMD.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DictionaryController implements Initializable {

    @FXML
    private ImageView addButton;

    @FXML
    private ImageView deleteButton;

    @FXML
    private ImageView editButton;

    @FXML
    private JFXButton favoriteButton;

    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private JFXListView<String> listView;

    @FXML
    private TextField searchBox;

    @FXML
    private WebView webView;

    @FXML
    private AnchorPane blockPane;
    @FXML
    private AnchorPane notificationPane;
    @FXML
    private JFXButton confirmButton;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private JFXButton removeWordButton;

    private ObservableList<String> searchList = FXCollections.observableArrayList();
    //private static Word selectedWord;
    private static HTMLWord selectedHtmlWord = new HTMLWord(null, null, null);
    private ArrayList<HTMLWord> searchHtmlWordList = new ArrayList<HTMLWord>();
    HTMLDictionary htmlDictionary = new HTMLDictionary();

    private WebEngine webEngine;
    @FXML
    void addWord(MouseEvent event) {
        HTMLDictionary.getFullWords().add(getAddWord());
        HTMLDictionary.saveEditToFile();
        HTMLDictionary.updateSortFullWords();
        addButton.setVisible(true);
        htmlEditor.setDisable(true);


    }
    HTMLWord getAddWord() {
        //HTMLWord res = new HTMLWord()
        return null;
    }


    @FXML
    void deleteWord(ActionEvent event) {
        if (selectedHtmlWord.getWordTarget() == null) return;

        // accept
        if (FavoriteWord.isInFavoriteList(selectedHtmlWord)) {
            FavoriteWord.removeFavoriteWords(selectedHtmlWord);
            favoriteButton.getStyleClass().removeAll("active");
        }
        String english = selectedHtmlWord.getWordTarget();
        HTMLDictionary.getFullWords().removeIf(word -> word.getWordTarget().equals(english));
        //refresh lại từ điển sau khi xóa
        HTMLDictionary.saveEditToFile();
        try {
            HTMLDictionary.insertFromHTMLFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        selectedHtmlWord.setWordTarget(null);
        selectedHtmlWord.getWordExplain().clear();
        refreshDisplay();

        blockPane.setVisible(true);
        SlideAnimation.SlideToAnimation(notificationPane, 0, 0, 0.5);
    }

    public void refreshDisplay() {
        //chuyển search về default
        searchBox.setText("");
        //reset wordListView
        searchList.clear();
        for (Word tmp : HTMLDictionary.getFullWords()) {
            searchList.add(tmp.getWordTarget());
        }
        listView.setItems(searchList);
        //reset webView
        updateWebview("");
    }

    @FXML
    void editWord(MouseEvent event) {
        htmlEditor.setHtmlText(selectedHtmlWord.getWordExplain().get(0));
        listView.setDisable(true);
    }

    @FXML
    void markWord(ActionEvent event) {
        if (selectedHtmlWord.getWordTarget() == null) return;
        if (FavoriteWord.isInFavoriteList(selectedHtmlWord)) {
            FavoriteWord.removeFavoriteWords(selectedHtmlWord);
            favoriteButton.getStyleClass().removeAll("active");
        } else {
            FavoriteWord.addFavoriteWords(selectedHtmlWord);
            favoriteButton.getStyleClass().addAll("active");
        }
    }

    @FXML
    void playVocieUS(MouseEvent event) {

    }

    @FXML
    void playVoiceUK(MouseEvent event) {

    }

    @FXML
    void searchWithPrefix(KeyEvent event) {
        searchList.clear();
        if (searchBox.getText().isEmpty()) {
            for (HTMLWord tmp : HTMLDictionary.getFullWords()) {
                searchList.add(tmp.getWordTarget());
            }
        } else {
            searchHtmlWordList = htmlDictionary.searcher(searchBox.getText());
            for (HTMLWord tmp : searchHtmlWordList) {
                searchList.add(tmp.getWordTarget());
            }
        }
        listView.setItems(searchList);


    }

    @FXML
    void showDefinition(MouseEvent event) {
        String checkEmty = listView.getSelectionModel().getSelectedItem();
        if (checkEmty == null) return;
       /* resetActiveButton();
        showMeaningButton.getStyleClass().addAll("active");
        transTabSupport(showMeaningButton);*/
        // Lấy mục được chọn từ ListView
        String selectedWordInListView = listView.getSelectionModel().getSelectedItem();
        // Tìm từ trong danh sách đầy đủ để lấy thông tin chi tiết

        selectedHtmlWord = htmlDictionary.returnSearchWord(selectedWordInListView);
        //Check favorite
//        if (FavouriteWords.checkFavorite(selectedHtmlWord)) {
//            markButton.getStyleClass().addAll("active");
//        } else {
//            markButton.getStyleClass().removeAll("active");
//        }

        // Hiển thị thông tin lên Web View
        updateWebview(selectedHtmlWord.getWordExplain().get(0));

    }


    void initdata() {
        DictionaryManagement dictionaryManagement = new DictionaryManagement();
        dictionaryManagement.insertFromFile();
        for (Word tmp : HTMLDictionary.getFullWords()) {
            searchList.add(tmp.getWordTarget());
        }
        listView.setItems(searchList);
        updateWebview("");
    }

    public void updateWebview(String text) {
        webEngine.loadContent(text);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        htmlEditor.setVisible(false);
        blockPane.setVisible(false);
        notificationPane.setVisible(true);
        notificationPane.setTranslateY(700);

        webEngine = webView.getEngine();
        initdata();
        //listView.setOnMouseClicked(this::showDefinition);
        removeWordButton.setOnMouseClicked(event -> {
            blockPane.setVisible(true);
            SlideAnimation.SlideToAnimation(notificationPane, 0, 0, 0.5);
        });
        confirmButton.setOnMouseClicked(event -> {
            //remove word here       <-------------------------------------------------
            //...
            if (selectedHtmlWord.getWordTarget() == null) return;

            // accept
            if (FavoriteWord.isInFavoriteList(selectedHtmlWord)) {
                FavoriteWord.removeFavoriteWords(selectedHtmlWord);
                favoriteButton.getStyleClass().removeAll("active");
            }
            String english = selectedHtmlWord.getWordTarget();
            HTMLDictionary.getFullWords().removeIf(word -> word.getWordTarget().equals(english));
            //refresh lại từ điển sau khi xóa
            HTMLDictionary.saveEditToFile();
            try {
                HTMLDictionary.insertFromHTMLFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            selectedHtmlWord.setWordTarget(null);
            selectedHtmlWord.getWordExplain().clear();
            refreshDisplay();



            blockPane.setVisible(false);
            SlideAnimation.SlideToAnimation(notificationPane, 0, 700, 0.5);
        });
        cancelButton.setOnMouseClicked(event -> {
            blockPane.setVisible(false);
            SlideAnimation.SlideToAnimation(notificationPane, 0, 700, 0.5);
        });
    }
}