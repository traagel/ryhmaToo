import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.stage.Stage;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
 
 
public class Main extends Application {
    Stage window;
   
    //loob uue GridPane layouti kuhu elemente paigutada, rakendub mainis alles hiljem
    public GridPane looGrid(Label tekstA1, ComboBox tunnidA, ComboBox minutidA, Label tekst1, Label tyhiLabel, Label tekstA2, ComboBox tunnidB, ComboBox minutidB, Label tekst2, Label tyhiLabel2, Label tekstA3, Label tekst3 ) {
  
    	//uus grid
    	GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10)); //padding äärtest < ^ v Insets
        grid.setVgap(8); //vertikaalne vahe gridis
        grid.setHgap(10); //horisontaalne vahe gridis
        
        //paigutab elemendid gridi
        GridPane.setConstraints(tekstA1, 0, 0, 2 ,1);
        GridPane.setConstraints(tunnidA,0,1);
        GridPane.setConstraints(minutidA, 1, 1);
        GridPane.setConstraints(tekst1, 0, 2, 2, 1);
        GridPane.setConstraints(tyhiLabel, 0, 3, 2, 1);
        GridPane.setConstraints(tekstA2, 0, 4, 2, 1);
        GridPane.setConstraints(tunnidB, 0, 5);
        GridPane.setConstraints(minutidB, 1, 5);
        GridPane.setConstraints(tekst2, 0, 6, 2, 1);
        GridPane.setConstraints(tyhiLabel2, 0, 7, 2, 1);
        GridPane.setConstraints(tekstA3, 0, 8, 2, 1);
        GridPane.setConstraints(tekst3, 0, 9, 2, 1);
       
        //lisab elemendid gridi
        grid.getChildren().addAll(tekstA1, tunnidA, minutidA, tekst1, tyhiLabel,tekstA2, tunnidB, minutidB, tekst2, tyhiLabel2, tekstA3, tekst3);
        //paugitab gridi keskele
        grid.setAlignment(Pos.CENTER);
        //paigutab kolm punkti keskele
        grid.setHalignment(tyhiLabel, HPos.CENTER);
        grid.setHalignment(tyhiLabel2, HPos.CENTER);
        return grid;
    }
   
    public String lisaAeg(String aeg, int tsyklid, boolean liida) { //liidab/lahutab stringis antud HH:mm ajast ette antud arvu tsükleid (int tsyklid)
    	DateTimeFormatter ajaKuju = DateTimeFormatter.ofPattern("HH:mm");
    	LocalTime ltAeg = LocalTime.parse(aeg, ajaKuju);
    	
    	if (liida == true)
    		return ltAeg.plusMinutes(90*tsyklid+15).format(ajaKuju); //liidab ajale tsyklite arv * 90 min +15 min magamaminekuks
    	return ltAeg.minusMinutes(90*tsyklid+15).format(ajaKuju); //lahutab ajast tsyklite arv * 90 min + 15 min magamaminekuks
    }
    
    public String praegusele(int tsyklid) { //lisab praegusele ajale 90*styklid + 15 min
    	DateTimeFormatter ajaKuju = DateTimeFormatter.ofPattern("HH:mm");
    	return LocalTime.now().plusMinutes(90*tsyklid+15).format(ajaKuju);
    }
    
    @Override
    public void start(Stage primaryStage) { //loob pealava
        window = primaryStage; //aken
        window.setTitle("uneMati"); //pealkiri
        
        //kasutusel olevad tekstid
        Label tekstA1 = new Label("Lähen magama kell"); 
        Label tekstA2 = new Label("Ärkan üles kell");
        Label tekstA3 = new Label("Lähen kohe magama");
        Label tyhiLabel = new Label("· · ·");
        Label tyhiLabel2 = new Label("· · ·");
        
        //väljundi default väärtused
        Label tekst1 = new Label("Ärka üles kell 6:30");
        Label tekst2 = new Label("Mine magama kell 23:30");
        Label tekst3 = new Label("Ärka üles kell " + praegusele(5));
        
       //kolme punkti CSS
        tyhiLabel.setStyle("-fx-font-size:30pt; -fx-font-weight: bold;");
        tyhiLabel2.setStyle("-fx-font-size:30pt; -fx-font-weight: bold;");
        tyhiLabel.setTextFill(Color.web("#1b2838"));   
        tyhiLabel.setTextFill(Color.web("#1b2838"));
        
        //uued comboboxid
        ComboBox tunnidA = new ComboBox();
        ComboBox minutidA = new ComboBox();
        ComboBox tunnidB = new ComboBox();
        ComboBox minutidB = new ComboBox();
        
        //m näitab mitu rida combobox drop down menüü näitab
        int m = 6;
        tunnidA.setVisibleRowCount(m);
        minutidA.setVisibleRowCount(m);
        tunnidB.setVisibleRowCount(m);
        minutidB.setVisibleRowCount(m);

        //tunni dropdown menüü
        for (int i = 0; i<=60; i++) {
            if (i<10) {
                tunnidA.getItems().add("0" + Integer.toString(i));
                minutidA.getItems().add("0" + Integer.toString(i));
            } else {
            	if (i <=24)
            		tunnidA.getItems().add(Integer.toString(i));
                minutidA.getItems().add(Integer.toString(i));
            }
        }
        
        //tekitab eelnevalt defineeritud gridi
        GridPane grid = looGrid(tekstA1, tunnidA, minutidA, tekst1, tyhiLabel, tekstA2, tunnidB, minutidB, tekst2, tyhiLabel2, tekstA3, tekst3);
        
        
        //tunnid ja minutid A default väärtused (23:00)
        tunnidA.getSelectionModel().select(23);
        minutidA.getSelectionModel().select(00);
        
        //annab minutid ja tunnid drop down menüüle B samad väärtused mis A, annab default väärtuseks 06:45
        tunnidB.getItems().addAll(tunnidA.getItems());
        minutidB.getItems().addAll(minutidA.getItems());
        tunnidB.getSelectionModel().select(06);
        minutidB.getSelectionModel().select(45);
 

        //lambda funktsioonid - kui muudetakse drop down menüü väärtust muudab tekste
        tunnidA.setOnAction(e -> tekst1.setText("Ärka üles kell " + lisaAeg((String) tunnidA.getValue() + ":" + (String)minutidA.getValue(),5,true)));
        minutidA.setOnAction(e -> tekst1.setText("Ärka üles kell " + lisaAeg((String) tunnidA.getValue() + ":" + (String)minutidA.getValue(),5,true)));
        tunnidB.setOnAction(e -> tekst2.setText ("Mine magama kell " + lisaAeg((String) tunnidB.getValue() + ":" + (String)minutidB.getValue(),5,false)));
        minutidB.setOnAction(e -> tekst2.setText("Mine magama kell " + lisaAeg((String) tunnidB.getValue() + ":" + (String)minutidB.getValue(),5,false)));
        
        //loob BorderPane ja paigutab sinna HBoxi sisse, unepäeviku lingi jaoks
        BorderPane border = new BorderPane();
        
        //uus hbox - üles, unepäeviku avamiseks
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.BASELINE_CENTER);
        Button käivita = new Button("Unepäevik");
        hbox.setPadding(new Insets(10, 10, 10, 10));
        
        //trycatch lambda fn
        käivita.setOnAction( e->{
			try {
				UnepäevikKäivitus.küsiAndmeid();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
        
        hbox.getChildren().add(käivita);
        border.setCenter(grid);
        border.setTop(hbox);
        
        //loob uue stseeni valmistatud grid layoutist
        Scene scene = new Scene(border, 480, 640);
        scene.getStylesheets().add("Styling.css"); //laeb css faili  
        scene.getStylesheets().add("https://fonts.googleapis.com/css?family=Roboto"); //võtab googlest fondi
        window.setScene(scene); //loo stseen
        window.setResizable(false); //ei saa akna suurust muuta
        window.show(); //näita akent
       
    }
 
    public static void main(String[] args) {
        launch(args); //käivitub
    }
}