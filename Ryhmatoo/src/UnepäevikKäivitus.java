import javax.swing.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Unep�evikK�ivitus {
	private static Matcher m;
	public static void k�siAndmeid() throws IOException {
		//v�ljad
		JTextField field1 = new JTextField();
		JTextField field2 = new JTextField();
		JTextField field3 = new JTextField();
		JTextField field4 = new JTextField();

		// mida kasutaja v�iks sisestada
		field1.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
		field2.setText(LocalTime.now().plusHours(8).format(DateTimeFormatter.ofPattern("HH:mm")));
		field3.setText("0 minutit");
		field4.setText("0 minutit");

		Object[] objects = {
				"magama mineku aeg", field1, 
				"�rkamise aeg", field2, 
				"aeg mis kulub magama minekuks", field3, 
				"��sel �rkvel olemise aeg", field4};

		int nupp = JOptionPane.showConfirmDialog(null, objects,"Andmete sisestamine", JOptionPane.OK_CANCEL_OPTION);
		
		//kas andmed korrektsed
		if(nupp == 0 && !field1.getText().equals("") && !field2.getText().equals("") && !field3.getText().equals("") && !field4.getText().equals("")) { 
			vajalikudAndmed(field1.getText(), field2.getText(), field3.getText(), field4.getText());
		}else {
			JOptionPane.showMessageDialog(null, "sisestasid midagi valesti");
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