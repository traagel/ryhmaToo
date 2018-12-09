import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.MINUTES;

public class Sissekanne {
    private String magamaMinekuAeg;
    private String arkamisaeg;
    private LocalDate kuupaev;
    private int aegMagamaJaamiseks;
    private int arkvelOlekuAeg;
    private int piisavUneAeg;

    public Sissekanne(String magamaMinekuAeg, String arkamisaeg, LocalDate kuupaev, int aegMagamaJaamiseks, int arkvelOlekuAeg) {
        this.magamaMinekuAeg = magamaMinekuAeg;
        this.arkamisaeg = arkamisaeg;
        this.kuupaev = kuupaev;
        this.aegMagamaJaamiseks = aegMagamaJaamiseks;
        this.arkvelOlekuAeg = arkvelOlekuAeg;
    }

    public void setPiisavUneAeg(int piisavUneAeg) {
        this.piisavUneAeg = piisavUneAeg;
    }

    public void looSissekanne() throws IOException {
        File fail = new File("unepaevik.txt");
        FileWriter pw = new FileWriter(fail.getAbsoluteFile(), true);
        pw.append("Kuupaev: " + kuupaev.toString() + " Laksin magama: " + magamaMinekuAeg + " Arkasin: " + arkamisaeg + " Magatud aeg: "
                + magamisAegNormaalkujule() + " Une efektiivsus: " + uneEfektiivsus() + "%" + System.lineSeparator());
        pw.close();
    }

    public String magamisAegNormaalkujule() {
        int tunnid = magamisAeg() / 60;
        int minutid = magamisAeg() % 60;
        return tunnid + " tundi " + minutid + " minutit";
    }

    public int magamisAeg() {
        String[] magamaMinekuAegList = magamaMinekuAeg.split(":"); //listis eraldatakse magamaminekuaja tunnid ja minutid
        LocalTime magamaKell = LocalTime.of(Integer.parseInt(magamaMinekuAegList[0]), Integer.parseInt(magamaMinekuAegList[1]));
        String[] arkamisaegList = arkamisaeg.split(":"); //listis eraldatakse arkamisaja tunnid ja minutid
        LocalTime arkamineKell = LocalTime.of(Integer.parseInt(arkamisaegList[0]), Integer.parseInt(arkamisaegList[1]));
        int magamisAeg = (int) magamaKell.until(arkamineKell, MINUTES); //leiab magamaminemisaja ja arkamisaja vahe
        //Kui kasutaja ärkab järgmisel päeval, siis väljastab until meetod negatiivse uneaja, kuna ta ei arvesta kuupäeva muutusega
        if (magamisAeg < 0) {
            return 1440 + magamisAeg; //liites negatiivsele uneajale 24h (1440 minutit) saame tegeliku uneaja
        } else {
            return magamisAeg;
        }
    }

    public double uneEfektiivsus() {
        //uneefektiivsuse arvutamise valem
        double efektiivsus = (double) (magamisAeg() - aegMagamaJaamiseks - arkvelOlekuAeg) / magamisAeg() * 100;
        return Math.round(efektiivsus);
    }

    public void soovita() {
        //võrdleb uneaega kasutaja poolt määratud piisava uneajaga ning annab vastava soovituse
        if (magamisAeg() > piisavUneAeg * 60) {
            JOptionPane.showMessageDialog(null, "Tubli! Magasid hasti.");
        } else {
            JOptionPane.showMessageDialog(null, "Magasid liiga vahe!");
        }
    }
}

