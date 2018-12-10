import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.swing.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main extends Application {
    Stage window;
    int tsyklid = 5;
    Scene peaStseen;

    public void setTsyklid(int tsyklid) {
        this.tsyklid = tsyklid;
    }

    public int getTsyklid() {
        return tsyklid;
    }

    //loob uue GridPane layouti kuhu elemente paigutada, rakendub mainis alles hiljem
    public GridPane looGrid(Label tekstA1, ComboBox tunnidA, ComboBox minutidA, Label tekst1, Label tyhiLabel, Label tekstA2, ComboBox tunnidB, ComboBox minutidB, Label tekst2, Label tyhiLabel2, Label tekstA3, Label tekst3) {

        //uus grid
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10)); //padding äärtest < ^ v Insets
        grid.setVgap(8); //vertikaalne vahe gridis
        grid.setHgap(10); //horisontaalne vahe gridis

        //paigutab elemendid gridi
        GridPane.setConstraints(tekstA1, 0, 0, 2, 1);
        GridPane.setConstraints(tunnidA, 0, 1);
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
        grid.getChildren().addAll(tekstA1, tunnidA, minutidA, tekst1, tyhiLabel, tekstA2, tunnidB, minutidB, tekst2, tyhiLabel2, tekstA3, tekst3);
        //paugitab gridi keskele
        grid.setAlignment(Pos.CENTER);
        //paigutab kolm punkti keskele
        grid.setHalignment(tyhiLabel, HPos.CENTER);
        grid.setHalignment(tyhiLabel2, HPos.CENTER);
        return grid;
    }

    //unepäeviku Hboxi loomine
    public HBox unepäevik() {

        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.BASELINE_CENTER);
        Button käivita = new Button("Unepäevik");
        hbox.setPadding(new Insets(10, 10, 10, 10));

        // Unepäevik
        BorderPane border = new BorderPane();
        taidaPaevikuSisu(border);

        Scene paevikuStseen = new Scene(border, 480, 640);

        HBox hbox2 = new HBox(10);
        hbox2.setAlignment(Pos.BASELINE_CENTER);
        hbox2.setPadding(new Insets(10, 10, 5, 10));

        Button uneMati = new Button("UneMati");
        hbox2.getChildren().add(uneMati);

        border.setTop(hbox2);

        uneMati.setOnAction(i -> window.setScene(peaStseen));

        paevikuStseen.getStylesheets().add("Styling.css"); //laeb css faili
        paevikuStseen.getStylesheets().add("https://fonts.googleapis.com/css?family=Roboto"); //võtab googlest fondi

        käivita.setOnAction(i -> {
            window.setScene(paevikuStseen); 
            window.show();
        });

        hbox.getChildren().add(käivita);
        return hbox;
    }

    private void taidaPaevikuSisu(BorderPane border) { //meetod päeviku sisu täitmiseks
        DatePicker kuupaevaValik = new DatePicker(); //loob DatePicker isendi, mille abil saab valida kuupäeva
        kuupaevaValik.setMinWidth(400); //seab laiuse
        kuupaevaValik.setPromptText("Sisesta kuupäev"); //selgitus

        TextField magamaMinekuAeg = new TextField(); //magama mineku aja sisestus
        magamaMinekuAeg.setPromptText("Sisesta magama mineku aeg (HH:mm)"); //selgitus

        TextField arkamisAeg = new TextField(); //ärkamise aja sisestus
        arkamisAeg.setPromptText("Sisesta ärkamise aeg (HH:mm)");

        TextField kulusMagamaJaamiseks = new TextField(); //magama jäämiseks kulunud aja sisestus
        kulusMagamaJaamiseks.setPromptText("Sisesta aeg, mis kulus magama minekuks");

        TextField kauaOlinArkvel = new TextField(); //ärkvel oleku aja sisestus
        kauaOlinArkvel.setPromptText("Sisesta aeg, kaua olid ärkvel");

        Button looSissekanne = new Button("Loo sissekanne"); //loob nupu "Loo sissekanne"
        looSissekanne.setMinWidth(400);
        looSissekanne.setOnMouseClicked(i -> {
            try {
                //salvestab kasutaja antud andmed muutujatesse ja vajadusel teisendab
                LocalDate kuupaev = kuupaevaValik.getValue();
                String magamaMinekuAegText = magamaMinekuAeg.getText();
                String arkamisAegText = arkamisAeg.getText();
                int kulusMagamaJaamiseksText = Integer.parseInt(kulusMagamaJaamiseks.getText());
                int kauaOlinArkvelText = Integer.parseInt(kauaOlinArkvel.getText());

                //Sissekande nupule vajutades puhastatakse väljad
                kuupaevaValik.setValue(null);
                magamaMinekuAeg.clear();
                arkamisAeg.clear();
                kulusMagamaJaamiseks.clear();
                kauaOlinArkvel.clear();

                //loob Sissekande isendi
                Sissekanne sissekanne = new Sissekanne(magamaMinekuAegText, arkamisAegText, kuupaev, kulusMagamaJaamiseksText,
                        kauaOlinArkvelText);
                try {
                    sissekanne.looSissekanne(); //loob sissekande
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (NumberFormatException e) { //Teavitab kasutajat vigase sisendi korral
                JOptionPane.showMessageDialog(null, "Vigane sisend", "NumberFormatException", JOptionPane.INFORMATION_MESSAGE);
            }

        });

        Canvas canvas = new Canvas(200, 180); //loob lõuendi
        GraphicsContext graafikaKontekst = canvas.getGraphicsContext2D(); //loob graafikakonteksti, et pilti kuvada
        Image image = new Image(getClass().getResourceAsStream("lambs.png")); //loob pildi .png failist
        graafikaKontekst.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight()); //lisab pildi graafikakontekstile

        Button looGraafik = new Button("Nädala andmed"); //loob nupu "Loo graafik"
        looGraafik.setOnMouseClicked(i -> {
            Unegraafik unegraafik = new Unegraafik(); //loob Unegraafik isendi
            try {
                unegraafik.loeSissekanded(); //loeb sissekanded sisse
                unegraafik.looGraafik(); //loob graafiku
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        looGraafik.setMinWidth(400);

        VBox vBox = new VBox(10); //loob vertikaalkasti isendi
        vBox.setAlignment(Pos.CENTER); //seab kasti keskele
        vBox.getChildren().addAll(kuupaevaValik, magamaMinekuAeg, arkamisAeg, kulusMagamaJaamiseks, kauaOlinArkvel,
                looSissekanne, canvas, looGraafik); //lisab kasutaja sisestused, nupud ja lõuendi vertikaalkasti
        vBox.setMaxWidth(400);
        border.setCenter(vBox);
    }

    public String lisaAeg(String aeg, int tsyklid, boolean liida) { //liidab/lahutab stringis antud HH:mm ajast ette antud arvu tsükleid (int tsyklid)
        DateTimeFormatter ajaKuju = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime ltAeg = LocalTime.parse(aeg, ajaKuju);

        if (liida == true)
            return ltAeg.plusMinutes(90 * tsyklid + 15).format(ajaKuju); //liidab ajale tsyklite arv * 90 min +15 min magamaminekuks
        return ltAeg.minusMinutes(90 * tsyklid + 15).format(ajaKuju); //lahutab ajast tsyklite arv * 90 min + 15 min magamaminekuks
    }

    public String praegusele(int tsyklid) { //lisab praegusele ajale 90*styklid + 15 min
        DateTimeFormatter ajaKuju = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.now().plusMinutes(90 * tsyklid + 15).format(ajaKuju);
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
        Label tekst3 = new Label("Ärka üles kell " + praegusele(getTsyklid()));


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
        for (int i = 0; i <= 60; i++) {
            if (i < 10) {
                tunnidA.getItems().add("0" + Integer.toString(i));
                minutidA.getItems().add("0" + Integer.toString(i));
            } else {
                if (i <= 24)
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
        tunnidA.setOnAction(e -> tekst1.setText("Ärka üles kell " + lisaAeg((String) tunnidA.getValue() + ":" + (String) minutidA.getValue(), getTsyklid(), true)));
        minutidA.setOnAction(e -> tekst1.setText("Ärka üles kell " + lisaAeg((String) tunnidA.getValue() + ":" + (String) minutidA.getValue(), getTsyklid(), true)));
        tunnidB.setOnAction(e -> tekst2.setText("Mine magama kell " + lisaAeg((String) tunnidB.getValue() + ":" + (String) minutidB.getValue(), getTsyklid(), false)));
        minutidB.setOnAction(e -> tekst2.setText("Mine magama kell " + lisaAeg((String) tunnidB.getValue() + ":" + (String) minutidB.getValue(), getTsyklid(), false)));

        //loob BorderPane ja paigutab sinna HBoxi sisse, unepäeviku lingi jaoks
        BorderPane border = new BorderPane();

        //uus hbox - üles, unepäeviku avamiseks
        HBox hbox = unepäevik();
        //Unepäeviku Hboxi lõpp

        //Tsüklite arvu valiku HBox

        HBox hbox2 = new HBox(10);
        hbox2.setPadding(new Insets(10, 10, 10, 10));
        Label label = new Label("Unetsüklite arv:");

        Button bt1 = new Button("4");
        Button bt2 = new Button("5");
        Button bt3 = new Button("6");

        bt1.setOnAction(e -> {
            bt1.setStyle("-fx-text-fill:#66c0f4");
            bt2.setStyle(null);
            bt3.setStyle(null);
            setTsyklid(4);
            tekst1.setText("Ärka üles kell " + lisaAeg((String) tunnidA.getValue() + ":" + (String) minutidA.getValue(), getTsyklid(), true));
            tekst1.setText("Ärka üles kell " + lisaAeg((String) tunnidA.getValue() + ":" + (String) minutidA.getValue(), getTsyklid(), true));
            tekst2.setText("Mine magama kell " + lisaAeg((String) tunnidB.getValue() + ":" + (String) minutidB.getValue(), getTsyklid(), false));
            tekst2.setText("Mine magama kell " + lisaAeg((String) tunnidB.getValue() + ":" + (String) minutidB.getValue(), getTsyklid(), false));
            tekst3.setText("Ärka üles kell " + praegusele(getTsyklid()));

        });
        bt2.setOnAction(e -> {
            bt2.setStyle("-fx-text-fill:#66c0f4");
            bt1.setStyle(null);
            bt3.setStyle(null);
            setTsyklid(5);
            tekst1.setText("Ärka üles kell " + lisaAeg((String) tunnidA.getValue() + ":" + (String) minutidA.getValue(), getTsyklid(), true));
            tekst1.setText("Ärka üles kell " + lisaAeg((String) tunnidA.getValue() + ":" + (String) minutidA.getValue(), getTsyklid(), true));
            tekst2.setText("Mine magama kell " + lisaAeg((String) tunnidB.getValue() + ":" + (String) minutidB.getValue(), getTsyklid(), false));
            tekst2.setText("Mine magama kell " + lisaAeg((String) tunnidB.getValue() + ":" + (String) minutidB.getValue(), getTsyklid(), false));
            tekst3.setText("Ärka üles kell " + praegusele(getTsyklid()));
        });
        bt3.setOnAction(e -> {
            bt3.setStyle("-fx-text-fill:#66c0f4");
            bt2.setStyle(null);
            bt1.setStyle(null);
            setTsyklid(6);
            tekst1.setText("Ärka üles kell " + lisaAeg((String) tunnidA.getValue() + ":" + (String) minutidA.getValue(), getTsyklid(), true));
            tekst1.setText("Ärka üles kell " + lisaAeg((String) tunnidA.getValue() + ":" + (String) minutidA.getValue(), getTsyklid(), true));
            tekst2.setText("Mine magama kell " + lisaAeg((String) tunnidB.getValue() + ":" + (String) minutidB.getValue(), getTsyklid(), false));
            tekst2.setText("Mine magama kell " + lisaAeg((String) tunnidB.getValue() + ":" + (String) minutidB.getValue(), getTsyklid(), false));
            tekst3.setText("Ärka üles kell " + praegusele(getTsyklid()));
        });
        bt2.setStyle("-fx-text-fill:#66c0f4");
        hbox2.getChildren().addAll(label, bt1, bt2, bt3);
        hbox2.setAlignment(Pos.BASELINE_CENTER);

        border.setCenter(grid);
        border.setTop(hbox);
        border.setBottom(hbox2);

        //loob uue stseeni valmistatud grid layoutist
        peaStseen = new Scene(border, 480, 640);
        peaStseen.getStylesheets().add("Styling.css"); //laeb css faili
        peaStseen.getStylesheets().add("https://fonts.googleapis.com/css?family=Roboto"); //võtab googlest fondi
        window.setScene(peaStseen); //loo stseen
        window.setResizable(false); //ei saa akna suurust muuta
        window.show(); //näita akent
    }

    public static void main(String[] args) {
        launch(args); //käivitub
    }
}
