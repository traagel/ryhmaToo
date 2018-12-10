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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
		sc.close();

	}

	public List<String> viimasedSeitse(boolean formaat) {
		List<String> viimasedSeitse = new ArrayList<String>();
		LocalDate n�dalap�ev = LocalDate.now();
		while (n�dalap�ev.getDayOfWeek() != DayOfWeek.MONDAY) {
			n�dalap�ev = n�dalap�ev.minusDays(1);
		}
		for (int i = 0; i < 7; i++) {
			if(formaat) {
				viimasedSeitse.add(n�dalap�ev.format(DateTimeFormatter.ofPattern("dd. MMMM", Locale.forLanguageTag("ET"))));
			}else{
				viimasedSeitse.add(String.valueOf(n�dalap�ev));}
			n�dalap�ev= n�dalap�ev.plusDays(1);
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
		xTelg.setLabel(LocalDate.now().get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear())+". n�dal");
		List<String> p�evad = viimasedSeitse(false);
		xTelg.setCategories(FXCollections.observableArrayList(viimasedSeitse(true)));
		yTelg.setLabel("Tunnid");
		yTelg.setTickLabelFill(Color.BLUEVIOLET);
		xTelg.setTickLabelFill(Color.BLUEVIOLET);

		magatudAeg.setName("Magatud aeg");
		puuduj��k.setName("Unev�lg");

		for (String elem : p�evad) {
			Double magatud = 0.0; 
			Double magamata = 0.0;
			if(kuup�evad.contains(elem)) {
				magatud = magatudAjad.get(kuup�evad.indexOf(elem));
				if(magatud < 8) 
					magamata = 8 - magatud;
			}
			// lisab 7 n�dalap�eva 
			//lisab v��rtuse, kui sellel p�eval leidub andmeid
			elem = LocalDate.parse(elem).format(DateTimeFormatter.ofPattern("dd. MMMM", Locale.forLanguageTag("ET")));
			magatudAeg.getData().add(new XYChart.Data<String, Number>(elem, magatud));
			puuduj��k.getData().add(new XYChart.Data<String, Number>(elem, magamata));
		}	

		sbc.getData().addAll(magatudAeg, puuduj��k);
		Scene stseen = new Scene(sbc, 800, 600);
		peaLava.setScene(stseen);
		peaLava.getScene().getStylesheets().add("barchartstyle.css");
		peaLava.show();

	}


}
