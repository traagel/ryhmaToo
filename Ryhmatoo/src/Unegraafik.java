import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Unegraafik extends Application {

    List<String> kuup�evad = new ArrayList<>();
    List<Double> magatudAjad = new ArrayList<>();

    final CategoryAxis xTelg = new CategoryAxis(); //x-telg
    final NumberAxis yTelg = new NumberAxis(); //y-telg


    final StackedBarChart<String, Number> sbc = new StackedBarChart<String, Number>(xTelg, yTelg); //loob tulp-diagrammi isendi

    final XYChart.Series<String, Number> magatudAeg = new XYChart.Series<String, Number>(); //magatud aja seeria
    final XYChart.Series<String, Number> puuduj��k = new XYChart.Series<String, Number>(); //unedefitsiidi seeria

    //loeb andmed sisse tekstifailist ning lisab iga p�eva magatud ajad magatudAjad listi
    public void loeSissekanded() throws FileNotFoundException {
        File fail = new File("unepaevik.txt");
        Scanner sc = new Scanner(fail, "UTF-8");
        while (sc.hasNextLine()) {
            String[] t�kid = sc.nextLine().split(" ");
            kuup�evad.add(t�kid[1]);
            int tunnid = Integer.parseInt(t�kid[9]);
            int minutid = Integer.parseInt(t�kid[11]);
            magatudAjad.add(tunnid + (minutid / 0.6)); //teisendab tunnid ja minutid ujukomaarvuks
        }
    }

    //Loeb kuup�evade listist sisse viimased seitse kuup�eva
    public List<String> viimasedSeitseKuup�eva() {
        List<String> viimasedSeitse = new ArrayList<>();
        int kordus = 7;
        if (kuup�evad.size() < 7) {
            kordus = kuup�evad.size();
        }

        for (int i = kordus; i > 0; i--) {
            viimasedSeitse.add(kuup�evad.get(kuup�evad.size() - i));
        }
        return viimasedSeitse;
    }
    //loeb magatud aegade listist sisse viimased seitse magatud aega
    public List<Double> viimasedSeitseAega() {
        List<Double> viimasedSeitse = new ArrayList<>();
        int kordus = 7;
        if (magatudAjad.size() < 7) {
            kordus = magatudAjad.size();
        }

        for (int i = kordus; i > 0; i--) {
            viimasedSeitse.add(magatudAjad.get(magatudAjad.size() - i));
        }
        return viimasedSeitse;
    }

    public void looGraafik() throws Exception { //loob graafiku k�ivitades start meetodi
        Stage pealava = new Stage();
        start(pealava);
    }


    @Override
    public void start(Stage peaLava) throws Exception { //loob pealava
        peaLava.setTitle("Unegraafik"); //pealkiri
        sbc.setTitle("N�dalas magatud tunnid"); //graafiku pealkiri
        xTelg.setLabel("N�dalap�evad"); //x-telje pealkiri
        List<String> viimasedSeitseKuup�eva = viimasedSeitseKuup�eva(); //salvestab muutujasse viimased seitse kuup�eva
        List<Double> viimasedSeitseAega = viimasedSeitseAega(); //salvestab muutujasse viimased seitse magatud aega
        xTelg.setCategories(FXCollections.observableArrayList(viimasedSeitseKuup�eva)); //loob x-telje kategooriad (kuup�evad)
        yTelg.setLabel("Tunnid"); //y-telje pealkiri
        yTelg.setTickLabelFill(Color.BLUEVIOLET);
        xTelg.setTickLabelFill(Color.BLUEVIOLET);

        magatudAeg.setName("Magatud aeg"); //tulba t�hendus (legend)
        //v��rtustab magatud aja tulbad (seeriad)
        for (String elem : viimasedSeitseKuup�eva) {
            magatudAeg.getData().add(new XYChart.Data<String, Number>(elem, viimasedSeitseAega.get(viimasedSeitseKuup�eva.indexOf(elem))));
        }

        puuduj��k.setName("Unev�lg"); //tulba t�hendus (legend)
        //v��rtustab une v�la tulbad (seeriad)
        for (String elem : viimasedSeitseKuup�eva) {
            if (viimasedSeitseAega.get(viimasedSeitseKuup�eva.indexOf(elem)) < 8) { //leiab kasutaja unev�la, arvestades, et 8h on piisav magamisaeg
                puuduj��k.getData().add(new XYChart.Data<String, Number>(elem, 8 - viimasedSeitseAega.get(viimasedSeitseKuup�eva.indexOf(elem))));
            } else {
                puuduj��k.getData().add(new XYChart.Data<String, Number>(elem, 0));
            }
        }


        sbc.getData().addAll(magatudAeg, puuduj��k); //lisab tulpdiagrammile seeriad
        Scene stseen = new Scene(sbc, 800, 600); //loob stseeni ja lisab graafiku stseenile
        peaLava.setScene(stseen); //lisab stseeni pealavale
        peaLava.getScene().getStylesheets().add("barchartstyle.css"); //laeb css faili
        peaLava.show(); //n�itab pealava

    }


}
