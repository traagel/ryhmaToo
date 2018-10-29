import javax.swing.*;
import java.io.IOException;
import java.time.LocalDate;

public class UnepäevikKäivitus {
    public static void main(String[] args) throws Exception {
        String magamaMinek = JOptionPane.showInputDialog(null, "Sisesta magama mineku aeg ", "Andmete sisestamine", JOptionPane.QUESTION_MESSAGE);
        String arkamine = JOptionPane.showInputDialog(null, "Sisesta arkamise aeg ", "Andmete sisestamine", JOptionPane.QUESTION_MESSAGE);
        int aegMagamaJaamiseks = Integer.parseInt(JOptionPane.showInputDialog(null, "Sisesta aeg, mis kulus magama jaamiseks:  ", "Andmete sisestamine", JOptionPane.QUESTION_MESSAGE));
        int arkvelOlekuAeg = Integer.parseInt(JOptionPane.showInputDialog(null, "Sisesta oosel arkvel oleku aeg: ", "Andmete sisestamine", JOptionPane.QUESTION_MESSAGE));

        Unepaevik sissekanne = new Unepaevik(magamaMinek, arkamine, LocalDate.now(), aegMagamaJaamiseks, arkvelOlekuAeg);
        sissekanne.looSissekanne();
        sissekanne.setPiisavUneAeg(6);
        sissekanne.soovita();

    }
}
