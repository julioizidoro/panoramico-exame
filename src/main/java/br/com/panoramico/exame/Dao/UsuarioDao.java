/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.exame.Dao;

import br.com.panoramico.exame.model.Usuario;
import javax.ejb.Stateless;

@Stateless
public class UsuarioDao extends AbstractDao<Usuario>{
    
    public UsuarioDao() {
        super(Usuario.class);
    }
    
}