package socialnetwork.domain.validators;

import java.net.InetAddress;

public class IdValidation {
    /**
     * Metoda verifica daca stringul se poate transforma in tipul de data long.
     *
     * @param string de tip String
     * @return true daca se poate transforma, altfel returneaza false
     */
    public static boolean isLong(String string) {
        try {
            Long.parseLong(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isInt(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
