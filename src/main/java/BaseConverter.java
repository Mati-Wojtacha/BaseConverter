import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class BaseConverter {
    public static void main(String[] args) {
        String wrongValue = "2147483648";
        String redssult = convert(wrongValue, 10 , 2);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj liczbę: ");
        String value = scanner.next();
        System.out.print("Podaj bazę liczby: ");
        int baseFrom = scanner.nextInt();
        System.out.print("Podaj bazę docelową: ");
        int baseTo = scanner.nextInt();

        try {
            String result = convert(value, baseFrom, baseTo);
            System.out.println("Wynik konwersji: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String convert(String value, int baseFrom, int baseTo) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Nie podano liczby");
        }

        if (baseFrom < 2 || baseFrom > 36 || baseTo < 2 || baseTo > 36) {
            throw new IllegalArgumentException("Baza liczby musi być między 2 a 36");
        }

        int currentValue = 0;
        int power = 1;
        boolean isNegative = false;

        if (value.charAt(0) == '-') {
            isNegative = true;
            value = value.substring(1);
        }

        for (int i = value.length() - 1; i >= 0; i--) {
            char digit = value.charAt(i);
            int digitValue = Character.digit(digit, baseFrom); //zwraca -1 gdy niepoprawna wartość
            if (digitValue < 0 || digitValue >= baseFrom) {
                throw new IllegalArgumentException("Niepoprawna wartość cyfry");
            }
            currentValue += digitValue * power;
            power *= baseFrom;
        }

        StringBuilder sb = new StringBuilder();


        if (currentValue == 0) {
            sb.append("0");
        } else {
            while (currentValue > 0) {
                int digit = currentValue % baseTo;
                char digitChar = Character.forDigit(digit, baseTo);
                sb.append(digitChar);
                currentValue /= baseTo;
            }
        }

        if (isNegative) {
            sb.append("-");
        }
        sb.reverse();

        //Plik z logiem
        File file = new File("log.xml");
        boolean fileExists = file.exists();
        FileWriter writer = null;
        try {
            writer = new FileWriter(file, true);
            // Dodawanie nagłówka tylko w przypadku, gdy plik nie istnieje
            if (!fileExists) {
                writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            }
            // Dodawanie nowego wiersza z danymi wywołania funkcji
            writer.write("<log>\n");
            writer.write("    <timestamp>" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "</timestamp>\n");
            writer.write("    <value>" + value + "</value>\n");
            writer.write("    <baseFrom>" + baseFrom + "</baseFrom>\n");
            writer.write("    <baseTo>" + baseTo + "</baseTo>\n");
            writer.write("    <result>" + sb + "</result>\n");
            writer.write("</log>\n");
        } catch (IOException e) {
            System.out.println("Błąd zapisu do pliku");
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                System.out.println("Błąd zamykania pliku");
            }
        }

        return sb.toString();
    }
}