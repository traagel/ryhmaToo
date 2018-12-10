import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
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
	private double unev�lg = 0;
	List<String> kuup�evad = new ArrayList<>();
	List<Double> magatudAjad = new ArrayList<>();

	final CategoryAxis xTelg = new CategoryAxis(); //x-telg
	final NumberAxis yTelg = new NumberAxis(); //y-telg
	final StackedBarChart<String, Number> sbc = new StackedBarChart<String, Number>(xTelg, yTelg); //loob tulp-diagrammi isendi
	final XYChart.Series<String, Number> magatudAeg = new XYChart.Series<String, Number>(); //magatud aja seeria
	final XYChart.Series<String, Number> puuduj��k = new XYChart.Series<String, Number>(); //unedefitsiidi seeria

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

	//loeb magatud aegade listist sisse sellel n�dalal magatud ajad
	public List<String> kaheksaP�eva(boolean formaat) {
		List<String> kaheksaP�eva = new ArrayList<String>();
		LocalDate n�dalap�ev = LocalDate.now();
		while (n�dalap�ev.getDayOfWeek() != DayOfWeek.MONDAY) {
			n�dalap�ev = n�dalap�ev.minusDays(1);
		}
		for (int i = 0; i < 8; i++) {
			if(formaat) {
				kaheksaP�eva.add(n�dalap�ev.format(DateTimeFormatter.ofPattern("dd. MMMM", Locale.forLanguageTag("ET"))));
			}else{
				kaheksaP�eva.add(String.valueOf(n�dalap�ev));}
			n�dalap�ev= n�dalap�ev.plusDays(1);
		}
		return kaheksaP�eva;
	}

	public void looGraafik() throws Exception { //loob graafiku k�ivitades start meetodi
		Stage pealava = new Stage();
		start(pealava);
	}

	@Override
	public void start(Stage peaLava) throws Exception { //loob pealava
		peaLava.setTitle("Unegraafik"); //pealkiri
		sbc.setTitle("N�dalas magatud tunnid"); //graafiku pealkiri
		xTelg.setLabel("kuup�evad"); //x-telje pealkiri
		List<String> p�evad = kaheksaP�eva(false); //salvestab muutujasse viimased seitse kuup�eva
		xTelg.setCategories(FXCollections.observableArrayList(kaheksaP�eva(true))); //loob x-telje kategooriad (kuup�evad)
		yTelg.setLabel("Tunnid"); //y-telje pealkiri
		yTelg.setTickLabelFill(Color.BLUEVIOLET);
		xTelg.setTickLabelFill(Color.BLUEVIOLET);
		
		magatudAeg.setName("Magatud aeg");
		puuduj��k.setName("Unev�lg");

		for (String elem : p�evad) { // lisab 7 n�dalap�eva 
			Double magatud = 0.0; 
			Double magamata = 0.0;
			if(kuup�evad.contains(elem)) { //lisab v��rtuse, kui sellel p�eval leidub andmeid
				magatud = magatudAjad.get(kuup�evad.indexOf(elem));
				unev�lg+= 8 - magatud;
				if(unev�lg < 0)
					unev�lg = 0;
				magamata = unev�lg;
			}
			// lisab tulbale v��rtuse
			elem = LocalDate.parse(elem).format(DateTimeFormatter.ofPattern("dd. MMMM", Locale.forLanguageTag("ET")));
			magatudAeg.getData().add(new XYChart.Data<String, Number>(elem, magatud));
			puuduj��k.getData().add(new XYChart.Data<String, Number>(elem, magamata));
		}	
		sbc.getData().addAll(magatudAeg, puuduj��k); //lisab tulpdiagrammile seeriad
		Scene stseen = new Scene(sbc, 800, 600); //loob stseeni ja lisab graafiku stseenile
		peaLava.setScene(stseen); //lisab stseeni pealavale
		peaLava.getScene().getStylesheets().add("barchartstyle.css"); //laeb css faili
		peaLava.show(); //n�itab pealava

	}



}
