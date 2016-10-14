/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.exame.Dao;

import br.com.panoramico.exame.model.Passaporte;
import javax.ejb.Stateless;

@Stateless
public class PassaporteDao extends AbstractDao<Passaporte>{
    
    public PassaporteDao() {
        super(Passaporte.class);
    }
    
}
