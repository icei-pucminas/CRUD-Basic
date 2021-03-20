import Controller.BooksController;
import Model.Book;

public class Main {
  public static void main(String[] args) {
    // Livros de exemplo
    Book l1 = new Book(-1, "Eu, Robô", 14.9F);
    Book l2 = new Book(-1, "Eu Sou A Lenda", 21.99F);
    Book l3 = new Book(-1, "Número Zero", 34.11F);
    int id1, id2, id3;
    BooksController controller = BooksController.GetInstance();

    try {

      // Abre (cria) o arquivo de livros

      // Insere os três livros
      id1 = controller.CreateBook(l1);
      l1.setM_id(id1);
      id2 = controller.CreateBook(l3);
      l2.setM_id(id2);
      id3 = controller.CreateBook(l3);
      l3.setM_id(id3);

      // Busca por dois livros
      System.out.println(controller.ReadBookID(id3).getM_name());
      System.out.println(controller.ReadBookID(id1).getM_name());

      // Altera um livro para um tamanho maior e exibe o resultado
      l2.setM_name("APAguei sen querer");
      controller.Update(l2);
      System.out.println(controller.ReadBookID(l2.getM_id()).getM_name());

      // Altera um livro para um tamanho menor e exibe o resultado
      l1.setM_name("I. Asimov");
      controller.Update(l1);
      System.out.println(controller.ReadBookID(l1.getM_id()).getM_name());

      // Excluir um livro e mostra que não existe mais
      controller.DeleteBookId(id3);
      Book l = controller.ReadBookID(l1.getM_id());
      if (l == null)
        System.out.println("Livro excluído");
      else
        System.out.println(l.getM_name());

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
