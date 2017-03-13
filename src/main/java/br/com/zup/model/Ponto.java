package br.com.zup.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;


/**
 * Created by rleonel on 12/03/17.
 */
@Entity
@Table(name = "PONTO")
public class Ponto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="ID")
  private int id;
  @Column(name = "EIXO_X")
  private int x; // Coordenada do eixo x
  @Column(name = "EIXO_Y")
  private int y; // Coordenada do eixo y
  @Column(name = "DESCRICAO")
  private String descricao; // Descrição do ponto

  public Ponto(int id,int x, int y, String descricao) {
    this.id = id;
    this.x = x;
    this.y = y;
    this.descricao = descricao;
  }

  public Ponto() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }
}
