import java.time.DateTimeException;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.Period;

public class NameProcessor {
    public static void getDataFromUser() {
        Scanner sc = new Scanner(System.in);
        String ans = sc.nextLine();
        String[] arr = ans.split(" ");
        arr = format(arr);
        sc.close();

        String name, surname, patronymic, birthData;
        String[] birthDataArray;

        try {
            name = arr[1];
            surname = arr[0];
            patronymic = arr[2];
            birthData = arr[3];
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.print("Вы ввели не все данные!");
            return;
        }

        //Возраст

        birthDataArray = getYearsMonthsDays(birthData);
        if (birthDataArray.length != 3) {
            System.out.println("Вы неверно ввели данные о рождении!");
            return;
        }
        LocalDate birthDate;
        int ageInYears, ageInMonths, ageInDays;
        try {
            //Преобразуем числа из массива в дату
           birthDate = LocalDate.of(Integer.parseInt(birthDataArray[2]), Integer.parseInt(birthDataArray[1]),
                   Integer.parseInt(birthDataArray[0]));

            //Считаем возраст
            ageInYears = Period.between(birthDate, LocalDate.now()).getYears();
            ageInMonths = Period.between(birthDate, LocalDate.now()).getMonths();
            ageInDays = Period.between(birthDate, LocalDate.now()).getDays();
        } catch (NumberFormatException e) {
            System.out.println("Вы неверно ввели данные о рождении!");
            return;
        } catch (DateTimeException e) {
            System.out.println("Недействительная дата рождения!");
            return;
        }
        if (ageInDays < 0 || ageInYears < 0 || ageInMonths < 0) {
            System.out.println("Недействительная дата рождения!");
            return;
        }
        //Инициалы

        System.out.println(("" + surname.charAt(0)).toUpperCase() + surname.substring(1) + (" " + (name.charAt(0))).toUpperCase() + (". " + patronymic.charAt(0)).toUpperCase() + ".");

        //Определение пола

        System.out.print("Пол: ");
        if (patronymic.endsWith("вич")) {
            System.out.println("Мужской");
        }
        else if (patronymic.endsWith("вна")) {
            System.out.println("Женский");
        }
        else {
            System.out.println("Не определен");
        }

        //Возраст

        System.out.print("Возраст: " + adeEnding(ageInYears, ageInMonths, ageInDays));
    }
    private static String[] format(String[] arr) { //Форматируем строку и делаем буквы в нижнем регистре, чтобы было удобнее определять пол
        ArrayList<String> save = new ArrayList<>();
        for (String s : arr) {
            if (!s.trim().isEmpty()) {
                //удаляем пробелы и добавляем все символы в нижнем регистре
                save.add(s.toLowerCase());
            }
        }
        return save.toArray(new String[0]);
    }
    private static String[] getYearsMonthsDays(String data) { //функция для вытаскивания значений из строки
        ArrayList<String> ans = new ArrayList<>();
        StringBuilder save = new StringBuilder();
        // StringBuilder для более эффективной конкатенации строк

        for (int i = 0; i < data.length(); ++i) {
            char currentChar = data.charAt(i);

            // Проверяем, является ли символ цифрой
            if (Character.isDigit(currentChar)) {
                save.append(currentChar);
            } else {
                // Если текущий символ не цифра и есть накопленные цифры
                if (!save.isEmpty()) {
                    ans.add(save.toString()); // Добавляем накопленное число в список
                    save.setLength(0);
                }
            }
        }

        // Если в конце строки остались накопленные цифры, добавляем их в список
        if (!save.isEmpty()) {
            ans.add(save.toString());
        }

        return ans.toArray(new String[0]);
    }
    private static String adeEnding(int ageInYears, int ageInMonth, int ageInDays) {
        //окончания для возраста

        StringBuilder ans = new StringBuilder();
        ans.append(ageInYears).append(" ").append(getYearEnding(ageInYears));

        if (ageInMonth > 0) {
            ans.append(" ").append(ageInMonth).append(" месяцев");
        }

        if (ageInDays > 0) {
            ans.append(" ").append(ageInDays).append(" ").append(getDayEnding(ageInDays));
        }

        return ans.toString();
    }

    private static String getYearEnding(int years) {
        if (years % 10 == 1 && years % 100 != 11) {
            return "год";
        } else if (years % 10 >= 2 && years % 10 <= 4 && (years % 100 < 10 || years % 100 >= 20)) {
            return "года";
        } else {
            return "лет";
        }
    }

    private static String getDayEnding(int days) {
        if (days % 10 == 1 && days % 100 != 11) {
            return "день";
        } else if (days % 10 >= 2 && days % 10 <= 4 && (days % 100 < 10 || days % 100 >= 20)) {
            return "дня";
        } else {
            return "дней";
        }
    }
}
