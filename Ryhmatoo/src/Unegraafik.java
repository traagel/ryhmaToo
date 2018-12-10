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

    List<String> kuupäevad = new ArrayList<>();
    List<Double> magatudAjad = new ArrayList<>();

    final CategoryAxis xTelg = new CategoryAxis(); //x-telg
    final NumberAxis yTelg = new NumberAxis(); //y-telg


    final StackedBarChart<String, Number> sbc = new StackedBarChart<String, Number>(xTelg, yTelg); //loob tulp-diagrammi isendi

    final XYChart.Series<String, Number> magatudAeg = new XYChart.Series<String, Number>(); //magatud aja seeria
    final XYChart.Series<String, Number> puudujääk = new XYChart.Series<String, Number>(); //unedefitsiidi seeria

    //loeb andmed sisse tekstifailist ning lisab iga päeva magatud ajad magatudAjad listi
    public void loeSissekanded() throws FileNotFoundException {
        File fail = new File("unepaevik.txt");
        Scanner sc = new Scanner(fail, "UTF-8");
        while (sc.hasNextLine()) {
            String[] tükid = sc.nextLine().split(" ");
            kuupäevad.add(tükid[1]);
            int tunnid = Integer.parseInt(tükid[9]);
            int minutid = Integer.parseInt(tükid[11]);
            magatudAjad.add(tunnid + (minutid / 0.6)); //teisendab tunnid ja minutid ujukomaarvuks
        }
    }

    //Loeb kuupäevade listist sisse viimased seitse kuupäeva
    public List<String> viimasedSeitseKuupäeva() {
        List<String> viimasedSeitse = new ArrayList<>();
        int kordus = 7;
        if (kuupäevad.size() < 7) {
            kordus = kuupäevad.size();
        }

        for (int i = kordus; i > 0; i--) {
            viimasedSeitse.add(kuupäevad.get(kuupäevad.size() - i));
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

    public void looGraafik() throws Exception { //loob graafiku käivitades start meetodi
        Stage pealava = new Stage();
        start(pealava);
    }


    @Override
    public void start(Stage peaLava) throws Exception { //loob pealava
        peaLava.setTitle("Unegraafik"); //pealkiri
        sbc.setTitle("Nädalas magatud tunnid"); //graafiku pealkiri
        xTelg.setLabel("Nädalapäevad"); //x-telje pealkiri
        List<String> viimasedSeitseKuupäeva = viimasedSeitseKuupäeva(); //salvestab muutujasse viimased seitse kuupäeva
        List<Double> viimasedSeitseAega = viimasedSeitseAega(); //salvestab muutujasse viimased seitse magatud aega
        xTelg.setCategories(FXCollections.observableArrayList(viimasedSeitseKuupäeva)); //loob x-telje kategooriad (kuupäevad)
        yTelg.setLabel("Tunnid"); //y-telje pealkiri
        yTelg.setTickLabelFill(Color.BLUEVIOLET);
        xTelg.setTickLabelFill(Color.BLUEVIOLET);

        magatudAeg.setName("Magatud aeg"); //tulba tähendus (legend)
        //väärtustab magatud aja tulbad (seeriad)
        for (String elem : viimasedSeitseKuupäeva) {
            magatudAeg.getData().add(new XYChart.Data<String, Number>(elem, viimasedSeitseAega.get(viimasedSeitseKuupäeva.indexOf(elem))));
        }

        puudujääk.setName("Unevõlg"); //tulba tähendus (legend)
        //väärtustab une võla tulbad (seeriad)
        for (String elem : viimasedSeitseKuupäeva) {
            if (viimasedSeitseAega.get(viimasedSeitseKuupäeva.indexOf(elem)) < 8) { //leiab kasutaja unevõla, arvestades, et 8h on piisav magamisaeg
                puudujääk.getData().add(new XYChart.Data<String, Number>(elem, 8 - viimasedSeitseAega.get(viimasedSeitseKuupäeva.indexOf(elem))));
            } else {
                puudujääk.getData().add(new XYChart.Data<String, Number>(elem, 0));
            }
        }


        sbc.getData().addAll(magatudAeg, puudujääk); //lisab tulpdiagrammile seeriad
        Scene stseen = new Scene(sbc, 800, 600); //loob stseeni ja lisab graafiku stseenile
        peaLava.setScene(stseen); //lisab stseeni pealavale
        peaLava.getScene().getStylesheets().add("barchartstyle.css"); //laeb css faili
        peaLava.show(); //näitab pealava

    }


}
