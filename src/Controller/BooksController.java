package Controller;

import java.io.RandomAccessFile;

import Interface.EndProcess;
import Interface.IBuildIn;
import Interface.ISchema;
import Interface.ISearchIn;
import Interface.UpdateDeleteProess;
import Model.Book;
import Utilitary.*;
import View.ConsoleView;
import jdk.jshell.execution.StreamingExecutionControl;

public class BooksController {

  private static BooksController m_instance = null;

  private int m_lastId = 0;

  public static BooksController GetInstance() {
    if (m_instance == null) {
      m_instance = new BooksController();
    }
    return m_instance;
  }

  public int getLastId() {
    return m_lastId;
  }

  public void setLastId(int m_lastId) {
    this.m_lastId = m_lastId;
  }

  public int CreateBook(Book p_toWrite) {
    EndProcess endProcess = () -> {
      // do something on end
      this.GetInstance().setLastId(getLastId() + 1);
    };
    p_toWrite.setM_id(m_lastId);
    int varReturn = CRUDManager.Create(p_toWrite.Writable(), DatabaseSchemaName.LIVRO, endProcess);
    if (varReturn != -1) {
      ConsoleView.PrintSuccessMessage("O Livro foi inserido com sucesso");
      varReturn = p_toWrite.getM_id();
      setLastId(getLastId() + 1);
    } else {
      ConsoleView.PrintErrorMessage("Ocorreu um erro e o livro não será inserido, mais ");
    }
    return varReturn;
  }

  public Book ReadBookName(String p_bookName) {
    return Read(GetNameQuery(p_bookName));
  }

  public Book ReadBookPrice(float p_bookPrice) {
    return Read(GetPriceQuery(p_bookPrice));
  }

  public Book ReadBookID(int p_id) {
    return Read(GetIDQuery(p_id));
  }

  private Book Read(ISearchIn p_query) {
    ISchema schema = (ram, dos) -> {
      try {
        dos.writeByte(ram.readByte());
        dos.writeShort(ram.readShort());
        dos.writeInt(ram.readInt());
        dos.writeUTF(ram.readUTF());
        dos.writeFloat(ram.readFloat());
      } catch (Exception e) {
        // TODO: handle exception
        ConsoleView.PrintErrorMessage("OPERAÇÂO NEGADA: \n" + e.getMessage());
      }
    };
    IBuildIn<Book> builder = (bytes) -> {
      try {
        return new Book().Readble(bytes, null);
      } catch (Exception e) {
        // TODO: handle exception
        ConsoleView.PrintErrorMessage("OPERAÇÂO NEGADA: \n" + e.getMessage());
        return null;
      }
    };
    return builder.Build(CRUDManager.Read(DatabaseSchemaName.LIVRO, p_query, builder, schema));
  }

  public boolean DeleteBookByName(String p_name) {
    return Delete(GetNameQuery(p_name));
  }

  public boolean DeleteBookPrice(float p_bookPrice) {
    return Delete(GetPriceQuery(p_bookPrice));
  }

  public boolean DeleteBookId(int p_id) {
    return Delete(GetIDQuery(p_id));
  }

  private boolean Delete(ISearchIn p_query) {
    UpdateDeleteProess delete = (ram, model) -> {
      try {
        ram.writeByte(ModelState.INACTIVE);
      } catch (Exception e) {
        // TODO: handle exception
      }
    };
    return CRUDManager.Delete(DatabaseSchemaName.LIVRO, p_query, GetBuilder(), GetSchema(), delete);
  }

  public boolean Update(Book p_book) {
    UpdateDeleteProess delete = (ram, model) -> {
      try {
        ram.writeByte(ModelState.INACTIVE);
      } catch (Exception e) {
        // TODO: handle exception
      }
    };
    CRUDManager.Delete(DatabaseSchemaName.LIVRO, GetIDQuery(p_book.getM_id()), GetBuilder(), GetSchema(), null);
    return (CRUDManager.Create(p_book.Writable(), DatabaseSchemaName.LIVRO, null)) != -1;
  }

  private ISearchIn<Book> GetIDQuery(int p_id) {
    ISearchIn<Book> query = (book) -> {
      return book.getM_id() == p_id && book.getM_isActive() == ModelState.ACTIVE;
    };
    return query;
  }

  private ISearchIn<Book> GetNameQuery(String p_name) {
    ISearchIn<Book> query = (book) -> {
      return book.getM_name().equals(p_name) && book.getM_isActive() == ModelState.ACTIVE;
    };
    return query;
  }

  private ISearchIn<Book> GetPriceQuery(float p_price) {
    ISearchIn<Book> query = (book) -> {
      return (book.getM_price() == p_price) && book.getM_isActive() == ModelState.ACTIVE;
    };
    return query;
  }

  private ISchema GetSchema() {
    ISchema schema = (ram, dos) -> {
      try {
        dos.writeByte(ram.readByte());
        dos.writeShort(ram.readShort());
        dos.writeInt(ram.readInt());
        dos.writeUTF(ram.readUTF());
        dos.writeFloat(ram.readFloat());
      } catch (Exception e) {
        // TODO: handle exception
        ConsoleView.PrintErrorMessage("OPERAÇÂO NEGADA: \n" + e.getMessage());
      }
    };
    return schema;
  }

  private IBuildIn<Book> GetBuilder() {
    IBuildIn<Book> builder = (bytes) -> {
      try {
        return new Book().Readble(bytes, null);
      } catch (Exception e) {
        // TODO: handle exception
        ConsoleView.PrintErrorMessage("OPERAÇÂO NEGADA: \n" + e.getMessage());
        return null;
      }
    };
    return builder;
  }
}
