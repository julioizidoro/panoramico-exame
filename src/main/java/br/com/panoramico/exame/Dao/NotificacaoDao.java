package br.com.panoramico.exame.Dao;

import br.com.panoramico.exame.model.Notificacao;
import javax.ejb.Stateless;

@Stateless
public class NotificacaoDao extends AbstractDao<Notificacao>{
    
    public NotificacaoDao() {
        super(Notificacao.class);
    }
    
}
