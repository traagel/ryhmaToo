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

	List<String> kuupäevad = new ArrayList<>();
	List<Double> magatudAjad = new ArrayList<>();

	final CategoryAxis xTelg = new CategoryAxis(); //x-telg
	final NumberAxis yTelg = new NumberAxis(); //y-telg
	final StackedBarChart<String, Number> sbc = new StackedBarChart<String, Number>(xTelg, yTelg); //loob tulp-diagrammi isendi
	final XYChart.Series<String, Number> magatudAeg = new XYChart.Series<String, Number>(); //magatud aja seeria
	final XYChart.Series<String, Number> puudujääk = new XYChart.Series<String, Number>(); //unedefitsiidi seeria

	public void loeSissekanded() throws FileNotFoundException {
		File fail = new File("unepaevik.txt");
		Scanner sc = new Scanner(fail, "UTF-8");
		while (sc.hasNextLine()) {
			String[] tükid = sc.nextLine().split(" ");
			kuupäevad.add(tükid[1]);
			int tunnid = Integer.parseInt(tükid[9]);
			int minutid = Integer.parseInt(tükid[11]);
			magatudAjad.add(tunnid + (minutid / 0.6));
		}
		sc.close();
	}

	//loeb magatud aegade listist sisse sellel nädalal magatud ajad
	public List<String> viimasedSeitse(boolean formaat) {
		List<String> viimasedSeitse = new ArrayList<String>();
		LocalDate nädalapäev = LocalDate.now();
		while (nädalapäev.getDayOfWeek() != DayOfWeek.MONDAY) {
			nädalapäev = nädalapäev.minusDays(1);
		}
		for (int i = 0; i < 7; i++) {
			if(formaat) {
				viimasedSeitse.add(nädalapäev.format(DateTimeFormatter.ofPattern("dd. MMMM", Locale.forLanguageTag("ET"))));
			}else{
				viimasedSeitse.add(String.valueOf(nädalapäev));}
			nädalapäev= nädalapäev.plusDays(1);
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
		xTelg.setLabel("kuupäevad"); //x-telje pealkiri
		List<String> päevad = viimasedSeitse(false); //salvestab muutujasse viimased seitse kuupäeva
		xTelg.setCategories(FXCollections.observableArrayList(viimasedSeitse(true))); //loob x-telje kategooriad (kuupäevad)
		yTelg.setLabel("Tunnid"); //y-telje pealkiri
		yTelg.setTickLabelFill(Color.BLUEVIOLET);
		xTelg.setTickLabelFill(Color.BLUEVIOLET);
		magatudAeg.setName("Magatud aeg");
		puudujääk.setName("Unevõlg");

		for (String elem : päevad) { // lisab 7 nädalapäeva 
			Double magatud = 0.0; 
			Double magamata = 0.0;
			if(kuupäevad.contains(elem)) { //lisab väärtuse, kui sellel päeval leidub andmeid
				magatud = magatudAjad.get(kuupäevad.indexOf(elem));
				if(magatud < 8) // kui on unevõlg
					magamata = 8 - magatud;
			}
			// lisab tulbale väärtuse
			elem = LocalDate.parse(elem).format(DateTimeFormatter.ofPattern("dd. MMMM", Locale.forLanguageTag("ET")));
			magatudAeg.getData().add(new XYChart.Data<String, Number>(elem, magatud));
			puudujääk.getData().add(new XYChart.Data<String, Number>(elem, magamata));
		}	
		sbc.getData().addAll(magatudAeg, puudujääk); //lisab tulpdiagrammile seeriad
		Scene stseen = new Scene(sbc, 800, 600); //loob stseeni ja lisab graafiku stseenile
		peaLava.setScene(stseen); //lisab stseeni pealavale
		peaLava.getScene().getStylesheets().add("barchartstyle.css"); //laeb css faili
		peaLava.show(); //näitab pealava

	}



}
