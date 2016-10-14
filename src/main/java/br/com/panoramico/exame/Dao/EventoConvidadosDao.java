/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.exame.Dao;

import br.com.panoramico.exame.model.Eventoconvidados;
import javax.ejb.Stateless;

/**
 *
 * @author Kamilla Rodrigues
 */

@Stateless
public class EventoConvidadosDao extends AbstractDao<Eventoconvidados>{
    
    public EventoConvidadosDao() {
        super(Eventoconvidados.class);
    }
    
}
