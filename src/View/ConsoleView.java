package View;

public class ConsoleView {
  public static final char escape = (char) 27;

  public static void PrintErrorMessage(String p_message) {
    System.out.println(ConsoleView.escape + "[31m" + p_message);
  }

  public static void PrintSuccessMessage(String p_message) {
    System.out.println(ConsoleView.escape + "[32m" + p_message);
  }

  public static void PrintWarningMessage(String p_message) {
    System.out.println(ConsoleView.escape + "[33m" + p_message);
  }

  public static void PrintErrorMessageInline(String p_message) {
    System.out.print(ConsoleView.escape + "[31m" + p_message);
  }

  public static void PrintSuccessMessageInline(String p_message) {
    System.out.print(ConsoleView.escape + "[32m" + p_message);
  }

  public static void PrintWarningMessageInline(String p_message) {
    System.out.print(ConsoleView.escape + "[33m" + p_message);
  }
}
