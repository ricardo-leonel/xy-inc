package br.com.zup.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.zup.dao.PontoDao;
import br.com.zup.model.Ponto;


/**
 * Created by rleonel on 12/03/17.
 */
@RestController
public class PontoRestController {

  @Autowired
  private PontoDao pontoDao;


  @RequestMapping("/calcular/{x}/{y}/{dist}")
  public List calcular(@PathVariable("x") int x,@PathVariable("y") int y,@PathVariable("dist") int dist) {//Welcome page, non-rest
    List<Ponto> listBase = pontoDao.list();
    List<Ponto> proximos = new ArrayList<Ponto>();
    for (Ponto p:listBase) {
      double d = calculaDistancia(x,y,p.getX(),p.getY());
      if(d <= dist){
        proximos.add(p);
      }
    }
    return proximos;

  }


  @RequestMapping("/list")
  public List getPontos() {
    return pontoDao.list();
  }

  @RequestMapping("/ponto/{id}")
  public ResponseEntity getPonto(@PathVariable("id") int id) {

    Ponto ponto = pontoDao.get(id);
    if (ponto == null) {
      return new ResponseEntity("No Ponto found for ID " + id, HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity(ponto, HttpStatus.OK);
  }

  @RequestMapping(value = "/create/{x}/{y}/{desc}")
  public ResponseEntity createPonto(@PathVariable("x") int x,@PathVariable("y") int y,@PathVariable("desc") String desc ) {
    Ponto ponto = new Ponto();

    if(x < 0){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Eixo x não deve ser negativo");
    }
    if(y < 0){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Eixo y não deve ser negativo");
    }

    ponto.setDescricao(desc);
    ponto.setX(x);
    ponto.setY(y);
    pontoDao.create(ponto);

    return new ResponseEntity(ponto, HttpStatus.OK);
  }

  @RequestMapping("/delete/{id}")
  public ResponseEntity deletePonto(@PathVariable int id) {

    if (0 == pontoDao.delete(id)) {
      return new ResponseEntity("No Pontos found for ID " + id, HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity(id, HttpStatus.OK);

  }

  @RequestMapping("/update/{id}")
  public ResponseEntity updatePontos(@PathVariable int id, @RequestBody Ponto ponto) {

    ponto = pontoDao.update(id, ponto);

    if (null == ponto) {
      return new ResponseEntity("No Ponto found for ID " + id, HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity(ponto,HttpStatus.OK);
  }

  private double calculaDistancia(int x1,int y1,int x2,int y2){
      return Math.sqrt( Math.pow( (x1 - x2),2 ) +    Math.pow( (y1 - y2),2 ) );
  }

}