package Model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import Interface.EndProcess;
import View.ConsoleView;

public class Book extends ModelBase {

  private int m_id;
  private String m_name;
  private float m_price;

  public Book(int p_id, String p_name, float p_price) {
    super();
    setM_id(p_id);
    setM_name(p_name);
    setM_price(p_price);
  }

  public Book() {
    super();
    setM_id(-1);
    setM_name("DEFAULT NAME");
    setM_price(0.0f);
  }

  // region GETTER & SETTER
  public float getM_price() {
    return m_price;
  }

  public void setM_price(float m_price) {
    this.m_price = m_price;
  }

  public String getM_name() {
    return m_name;
  }

  public void setM_name(String m_name) {
    this.m_name = m_name;
  }

  public int getM_id() {
    return m_id;
  }

  public void setM_id(int m_id) {
    this.m_id = m_id;
  }
  // endregion

  /**
   * Escreve o objeto em uma forma de vetor de bytes Estrutura: 1° Lapide 2°
   * Tamanho 3° ID 4° Nome do livro 5° Preço do livro
   */
  @Override
  public byte[] Writable() {
    byte[] auxi;
    try {
      ByteArrayOutputStream array = new ByteArrayOutputStream();
      ByteArrayOutputStream arrayAux = new ByteArrayOutputStream();
      DataOutputStream aux = new DataOutputStream(arrayAux);
      DataOutputStream stream = new DataOutputStream(array);
      // pegando o tamanho todo
      aux.writeByte(getM_isActive());
      aux.writeInt(this.m_id);
      aux.writeUTF(this.m_name);
      aux.writeFloat(this.m_price);
      // escrevendo de vez
      stream.writeByte(this.getM_isActive());
      Short s = Short.parseShort((Integer.toString(aux.size() + 3)));
      setM_arraySize(s);
      stream.writeInt(getM_arraySize());
      stream.writeInt(this.m_id);
      stream.writeUTF(this.m_name);
      stream.writeFloat(this.m_price);
      auxi = array.toByteArray();
    } catch (Exception e) {
      ConsoleView.PrintErrorMessage("OPERAÇÂO NEGADA: \n" + e.getMessage() + "\n");
      auxi = null;
    }
    return auxi;
  }

  @Override
  public Book Readble(byte[] p_array, EndProcess p_onComplete) {
    // ! FIX: a leitura está em um modo fixo
    Book toReturn = new Book();
    StringBuilder strBuilder = new StringBuilder();
    // Setando Lapide
    toReturn.setM_isActive(p_array[0]);
    // Setando Tamanho
    strBuilder.append(
        Byte.toString(p_array[1]) + Byte.toString(p_array[2]) + Byte.toString(p_array[3]) + Byte.toString(p_array[4]));
    Short size = new Short(strBuilder.toString());
    toReturn.setM_arraySize(size);
    strBuilder.delete(0, 3);
    // Setando ID
    strBuilder.append(
        Byte.toString(p_array[5]) + Byte.toString(p_array[6]) + Byte.toString(p_array[7]) + Byte.toString(p_array[8]));
    toReturn.setM_id(Integer.parseInt(strBuilder.toString()));
    strBuilder.delete(0, 3);
    // Setando Nome
    int length = p_array.length;
    for (int i = 9; i < length - 4; i++) {
      strBuilder.append(new Byte(p_array[i]));
    }
    toReturn.setM_name(strBuilder.toString());
    // Setando Preço
    strBuilder.append(Byte.toString(p_array[length - 1]) + Byte.toString(p_array[length - 2])
        + Byte.toString(p_array[length - 3]) + Byte.toString(p_array[length - 4]));
    toReturn.setM_price(new Float(strBuilder.toString()));
    strBuilder.delete(0, 3);
    // On Complete do metodo
    if (p_onComplete != null) {
      p_onComplete.OnEndProcess();
    }
    return toReturn;
  }
}