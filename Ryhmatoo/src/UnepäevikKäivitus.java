import javax.swing.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnepäevikKäivitus {
	private static Matcher m;
	
	public static void algAndmed() throws IOException {
		küsiAndmeid(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")), LocalTime.now().plusHours(8).format(DateTimeFormatter.ofPattern("HH:mm")), "0 minutit", "0 minutit");
	}
	public static void küsiAndmeid(String alg1, String alg2, String alg3, String alg4) throws IOException { 
		//väljad
		JTextField väli1 = new JTextField();
		JTextField väli2 = new JTextField();
		JTextField väli3 = new JTextField();
		JTextField väli4 = new JTextField();

		// mida kasutaja võiks sisestada
		väli1.setText(alg1);
		väli2.setText(alg2);
		väli3.setText(alg3);
		väli4.setText(alg4);

		Object[] väljad = {
				"magama mineku aeg", väli1, 
				"ärkamise aeg", väli2, 
				"aeg mis kulub magama minekuks", väli3, 
				"öösel ärkvel olemise aeg", väli4};

		int nupp = JOptionPane.showConfirmDialog(null, väljad,"Andmete sisestamine", JOptionPane.OK_CANCEL_OPTION);
		
		//kas andmed korrektsed
		if(nupp == 0 && (väli1.getText().equals("") || väli2.getText().equals("") || väli3.getText().equals("") || väli4.getText().equals(""))) { 
			JOptionPane.showMessageDialog(null, "sisestasid midagi valesti");
			küsiAndmeid(väli1.getText(), väli2.getText(), väli3.getText(), väli4.getText());
		}else if(nupp == 0){
			vajalikudAndmed(väli1.getText(), väli2.getText(), väli3.getText(), väli4.getText());
		}
	}

	public static void vajalikudAndmed(String magamaMinek, String arkamine, String magamaJäämine, String arkvelOlemine) throws IOException {
		m= Pattern.compile("\\d+").matcher(magamaJäämine); //numbri leidmine tekstist
		m.find();
		int aegMagamaJaamiseks = Integer.valueOf(m.group(0));
		m= Pattern.compile("\\d+").matcher(arkvelOlemine); //numbri leidmine tekstist
		m.find();
		int arkvelOlekuAeg = Integer.valueOf(m.group());

		Unepaevik sissekanne = new Unepaevik(magamaMinek, arkamine, LocalDate.now(), aegMagamaJaamiseks, arkvelOlekuAeg);
		sissekanne.looSissekanne();
		sissekanne.setPiisavUneAeg(6);
		sissekanne.soovita();
	}
}