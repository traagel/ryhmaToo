import javax.swing.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Unep�evikK�ivitus {
	private static Matcher m;
	
	public static void algAndmed() throws IOException {
		k�siAndmeid(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")), LocalTime.now().plusHours(8).format(DateTimeFormatter.ofPattern("HH:mm")), "0 minutit", "0 minutit");
	}
	public static void k�siAndmeid(String alg1, String alg2, String alg3, String alg4) throws IOException { 
		//v�ljad
		JTextField v�li1 = new JTextField();
		JTextField v�li2 = new JTextField();
		JTextField v�li3 = new JTextField();
		JTextField v�li4 = new JTextField();

		// mida kasutaja v�iks sisestada
		v�li1.setText(alg1);
		v�li2.setText(alg2);
		v�li3.setText(alg3);
		v�li4.setText(alg4);

		Object[] v�ljad = {
				"magama mineku aeg", v�li1, 
				"�rkamise aeg", v�li2, 
				"aeg mis kulub magama minekuks", v�li3, 
				"��sel �rkvel olemise aeg", v�li4};

		int nupp = JOptionPane.showConfirmDialog(null, v�ljad,"Andmete sisestamine", JOptionPane.OK_CANCEL_OPTION);
		
		//kas andmed korrektsed
		if(nupp == 0 && (v�li1.getText().equals("") || v�li2.getText().equals("") || v�li3.getText().equals("") || v�li4.getText().equals(""))) { 
			JOptionPane.showMessageDialog(null, "sisestasid midagi valesti");
			k�siAndmeid(v�li1.getText(), v�li2.getText(), v�li3.getText(), v�li4.getText());
		}else if(nupp == 0){
			vajalikudAndmed(v�li1.getText(), v�li2.getText(), v�li3.getText(), v�li4.getText());
		}
	}

	public static void vajalikudAndmed(String magamaMinek, String arkamine, String magamaJ��mine, String arkvelOlemine) throws IOException {
		m= Pattern.compile("\\d+").matcher(magamaJ��mine); //numbri leidmine tekstist
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