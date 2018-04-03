package project2_berhow;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Project2_Berhow extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        HBox root = new HBox();
        VBox actions = new VBox();

        //list
        ListView<MediaItem> library = new ListView<>();//having issues reading from file
        Library lib = new Library();
        library.setItems(lib.getCollection());

        //add new media section
        VBox addNewMedia = new VBox();
        HBox addRow1 = new HBox();
        Label title = new Label("Title:     ");
        TextField titleResponse = new TextField();
        addRow1.getChildren().addAll(title, titleResponse);     //title: titleResponse
        HBox addRow2 = new HBox();
        Label format = new Label("Format: ");
        TextField formatResponse = new TextField();
        Button add = new Button("Add");
        addRow2.getChildren().addAll(format, formatResponse);   //format: formatResponse
        addNewMedia.getChildren().addAll(addRow1, addRow2, add);//addRow1\naddRow2\nadd
        addNewMedia.setSpacing(5);//gets the fields off of each other

        add.setOnAction(e -> {
            try {
                String newTitle = titleResponse.getText();
                String newFormat = formatResponse.getText();
                MediaItem media = new MediaItem(newTitle);
                media.setMediaType(newFormat);
                lib.newMedia(media);
                lib.displayMediaLibrary();
                titleResponse.clear();
                formatResponse.clear();
                List tempList = lib.getCollection();
                Collections.sort(tempList);
                library.refresh();
            
            } catch (Exception a) {
                Alert alert = new Alert(AlertType.ERROR, a.getMessage());
                alert.show();
                System.out.println(a.getMessage());//this will be either no title or no format response
            }
        });

        //remove
        Button removeMedia = new Button("Remove");
        removeMedia.setOnAction(e -> {
            int index = library.getSelectionModel().getSelectedIndex();
            if (index != -1) {
                try {
                    library.getItems().remove(index);
                
                } catch (Exception a) {//this block will not be exicuted since the program will not use the button unless index!=-1
                    Alert alert = new Alert(AlertType.ERROR, a.getMessage());
                    alert.show();
                    System.out.println(a.getMessage());
                }
            }

        });

        //return 
        Button returnMedia = new Button("Return");
        returnMedia.setOnAction(e -> {
            int index = library.getSelectionModel().getSelectedIndex();
            if (index != -1) {
                try {
                    lib.returnMedia(library.getItems().get(index));
                    library.refresh();

                } catch (Exception a) {//this block will not be exicuted since the program will not use the button unless index!=-1
                    Alert alert = new Alert(AlertType.ERROR, a.getMessage());
                    alert.show();
                    System.out.println(a.getMessage());
                }
            }
        });

        //loan
        VBox loanMedia = new VBox();
        HBox loanRow1 = new HBox();
        Label loanedTo = new Label("Loaned To: ");
        TextField loanedToResponse = new TextField();
        loanRow1.getChildren().addAll(loanedTo, loanedToResponse);//loanedTo: loanedToResponse
        HBox loanRow2 = new HBox();
        Label loanedOn = new Label("Loaned On: ");
        TextField loanedOnResponse = new TextField();
        Label loanFormat = new Label("mm-dd-yyyy");
        Button loan = new Button("Loan");
        loanRow2.getChildren().addAll(loanedOn, loanedOnResponse, loanFormat);//loanedOn: loanedOnResponse (mm-dd-yyyy)
        loanMedia.getChildren().addAll(loanRow1, loanRow2, loan);//loanRow1\nloanRow2\nloanRow3
        loanMedia.setSpacing(5);//keeps fields off of each other

        loan.setOnAction(e -> {
            int index = library.getSelectionModel().getSelectedIndex();
            if (index != -1) {
                try {
                    String rentersName = loanedToResponse.getText();
                    String loanDate = loanedOnResponse.getText();
                    lib.loanMedia(library.getItems().get(index), rentersName, loanDate);
                    library.refresh();
                    loanedToResponse.clear();
                    loanedOnResponse.clear();
                    
                } catch (Exception a) {//this will be either no rentersName or no loanDate
                    Alert alert = new Alert(AlertType.ERROR, a.getMessage());
                    alert.show();
                    System.out.println(a.getMessage());
                }
            }
        });

        //sort
        VBox sortMedia = new VBox();
        Label sort = new Label("Sort");
        RadioButton sortByTitle = new RadioButton("By Title");
        RadioButton sortByDate = new RadioButton("By Date");
        sortMedia.getChildren().addAll(sort, sortByTitle, sortByDate);
        ToggleGroup sortButtons = new ToggleGroup();
        sortByTitle.setToggleGroup(sortButtons);
        sortByTitle.setSelected(true);
        sortByDate.setToggleGroup(sortButtons);
        sortMedia.setSpacing(5);
        
        sortByTitle.setOnAction(e ->{
            List tempList = lib.getCollection();
            Collections.sort(tempList);
            library.refresh();
        });
        
        sortByDate.setOnAction(e ->{
           List tempList = lib.getCollection();
           tempList.sort(new DateLoanedComparator());
           tempList.sort(new LoanComparator()); 
           library.refresh();
        });

        //add actions to actions(VBox)
        actions.getChildren().addAll(addNewMedia, removeMedia, returnMedia, loanMedia, sortMedia);
        actions.setPadding(new Insets(0, 10, 0, 10));
        actions.setSpacing(15);

        //add library and actions to root
        root.getChildren().addAll(library, actions);
        root.setPadding(new Insets(5, 5, 5, 5));

        Scene scene = new Scene(root, 600, 400);

        primaryStage.setOnCloseRequest((WindowEvent we) -> {
            try {
                lib.writeBinFile();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        primaryStage.setTitle("Media Colleciton");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
