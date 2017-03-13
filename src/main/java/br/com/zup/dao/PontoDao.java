package br.com.zup.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.zup.persistece.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.springframework.stereotype.Component;

import br.com.zup.model.Ponto;

/**
 * Created by rleonel on 12/03/17.
 */

@Component
public class PontoDao {


  // Dummy database. Initialize with some dummy values.
  private static List<Ponto> pontoList;
  {
    pontoList = new ArrayList<Ponto>();

    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    Session session = sessionFactory.getCurrentSession();
    session.beginTransaction();
    Query query = session.createQuery("from Ponto ");
    List<Ponto> list = query.list();

    for (Ponto ponto: list) {
      pontoList.add(new Ponto(ponto.getId(),ponto.getX(),ponto.getY(),ponto.getDescricao()));
    }

    session.close();
  }

  /**
   * Returns list of pontos from dummy database.
   *
   * @return list of pontos
   */
  public List list() {
    return pontoList;
  }

  /**
   * Return ponto object for given id from dummy database. If ponto is
   * not found for id, returns null.
   *
   * @param id
   *            ponto id
   * @return ponto object for given id
   */
  public Ponto get(int id) {

    for (Ponto p : pontoList) {
      if (p.getId() == id) {
        return p;
      }
    }
    return null;
  }

  /**
   * Create new ponto in dummy database. Updates the id and insert new
   * ponto in list.
   *
   * @param ponto
   *            Ponto object
   * @return ponto object with updated id
   */
  public Ponto create(Ponto ponto) {
    Ponto pontoAux = pontoList.get(pontoList.size() - 1);
    ponto.setId(pontoAux.getId()+1);
    pontoList.add(ponto);
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    Session session = sessionFactory.getCurrentSession();
    session.beginTransaction();
    session.save(ponto);
    session.getTransaction().commit();
    return ponto;
  }

  /**
   * Delete the ponto object from dummy database. If ponto not found for
   * given id, returns null.
   *
   * @param id
   *            the ponto id
   * @return id of deleted ponto object
   */
  public int delete(int id) {

    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    Session session = sessionFactory.getCurrentSession();
    session.beginTransaction();
    session.delete(this.get(id));
    session.getTransaction().commit();
    pontoList.remove(this.get(id));
    return id;
  }

  /**
   * Update the ponto object for given id in dummy database. If ponto
   * not exists, returns null
   *
   * @param id
   * @param ponto
   * @return ponto object with id
   */
  public Ponto update(int id, Ponto ponto) {

    for (Ponto p : pontoList) {
      if (p.getId() == id) {
        ponto.setId(p.getId());
        pontoList.remove(p);
        pontoList.add(ponto);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(ponto);
        session.close();
        return ponto;
      }
    }

    return null;
  }

}
