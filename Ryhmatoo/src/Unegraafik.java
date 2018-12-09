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

    final CategoryAxis xTelg = new CategoryAxis();
    final NumberAxis yTelg = new NumberAxis();


    final StackedBarChart<String, Number> sbc = new StackedBarChart<String, Number>(xTelg, yTelg);

    final XYChart.Series<String, Number> magatudAeg = new XYChart.Series<String, Number>();
    final XYChart.Series<String, Number> puuduj��k = new XYChart.Series<String, Number>();

    public void loeSissekanded() throws FileNotFoundException {
        File fail = new File("unepaevik.txt");
        Scanner sc = new Scanner(fail, "UTF-8");
        while (sc.hasNextLine()) {
            String[] t�kid = sc.nextLine().split(" ");
            kuup�evad.add(t�kid[1]);
            int tunnid = Integer.parseInt(t�kid[9]);
            int minutid = Integer.parseInt(t�kid[11]);
            magatudAjad.add(tunnid + (minutid / 0.6));
        }

    }

    public List<String> viimasedSeitse() {
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

    public void looGraafik() throws Exception {
        Stage pealava = new Stage();
        start(pealava);
    }


    @Override
    public void start(Stage peaLava) throws Exception { //loob graafiku
        peaLava.setTitle("Unegraafik");
        sbc.setTitle("N�dalas magatud tunnid");
        xTelg.setLabel("N�dalap�evad");
        List<String> viimasedSeitse = viimasedSeitse();
        xTelg.setCategories(FXCollections.observableArrayList(viimasedSeitse));
        yTelg.setLabel("Tunnid");
        yTelg.setTickLabelFill(Color.BLUEVIOLET);
        xTelg.setTickLabelFill(Color.BLUEVIOLET);

        magatudAeg.setName("Magatud aeg");

        for (String elem : viimasedSeitse) {
            magatudAeg.getData().add(new XYChart.Data<String, Number>(elem, magatudAjad.get(viimasedSeitse.indexOf(elem))));
        }

        puuduj��k.setName("Unev�lg");
        for (String elem : viimasedSeitse) {
            if (magatudAjad.get(viimasedSeitse.indexOf(elem)) < 8) {
                puuduj��k.getData().add(new XYChart.Data<String, Number>(elem, 8 - magatudAjad.get(viimasedSeitse.indexOf(elem))));
            } else {
                puuduj��k.getData().add(new XYChart.Data<String, Number>(elem, 0));
            }
        }


        sbc.getData().addAll(magatudAeg, puuduj��k);
        Scene stseen = new Scene(sbc, 800, 600);
        peaLava.setScene(stseen);
        peaLava.getScene().getStylesheets().add("barchartstyle.css");
        peaLava.show();

    }


}
