/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.exame.managebean.exame;

import br.com.panoramico.exame.Dao.ExameAssociadoDao;
import br.com.panoramico.exame.Dao.ExameDao;
import br.com.panoramico.exame.Dao.ExameDependenteDao;
import br.com.panoramico.exame.Dao.MedicoDao;
import br.com.panoramico.exame.model.Associado;
import br.com.panoramico.exame.model.Dependente;
import br.com.panoramico.exame.model.Exame;
import br.com.panoramico.exame.model.Exameassociado;
import br.com.panoramico.exame.model.Examedependente;
import br.com.panoramico.exame.model.Medico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class CadExameMB implements Serializable{
    
    private Exame exame;
    private Medico medico;
    private Examedependente examedependente;
    private Exameassociado exameassociado;
    private List<Medico> listaMedico;
    @EJB
    private MedicoDao medicoDao;
    @EJB
    private ExameDao exameDao;
    @EJB
    private ExameAssociadoDao exameAssociadoDao;
    @EJB
    private ExameDependenteDao exameDependenteDao;
    private boolean habilitarExame = false;
    private boolean habilitarConsExame = true;
    
    
    @PostConstruct
    public void init(){
        if (exame == null) {
            exame = new Exame();
        }else{
            medico = exame.getMedico();
        }
        gerarListaMedicos();
    }

    public Exame getExame() {
        return exame;
    }

    public void setExame(Exame exame) {
        this.exame = exame;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public List<Medico> getListaMedico() {
        return listaMedico;
    }

    public void setListaMedico(List<Medico> listaMedico) {
        this.listaMedico = listaMedico;
    }

    public MedicoDao getMedicoDao() {
        return medicoDao;
    }

    public void setMedicoDao(MedicoDao medicoDao) {
        this.medicoDao = medicoDao;
    }

    public ExameDao getExameDao() {
        return exameDao;
    }

    public void setExameDao(ExameDao exameDao) {
        this.exameDao = exameDao;
    }

    public Examedependente getExamedependente() {
        return examedependente;
    }

    public void setExamedependente(Examedependente examedependente) {
        this.examedependente = examedependente;
    }

    public Exameassociado getExameassociado() {
        return exameassociado;
    }

    public void setExameassociado(Exameassociado exameassociado) {
        this.exameassociado = exameassociado;
    }

    public ExameAssociadoDao getExameAssociadoDao() {
        return exameAssociadoDao;
    }

    public void setExameAssociadoDao(ExameAssociadoDao exameAssociadoDao) {
        this.exameAssociadoDao = exameAssociadoDao;
    }

    public ExameDependenteDao getExameDependenteDao() {
        return exameDependenteDao;
    }

    public void setExameDependenteDao(ExameDependenteDao exameDependenteDao) {
        this.exameDependenteDao = exameDependenteDao;
    }
    
    
    
    public void gerarListaMedicos(){
        listaMedico = medicoDao.list("Select m from Medico m where m.situacao='Ativo'");
        if (listaMedico == null) {
            listaMedico = new ArrayList<Medico>();
        }
    }
    
    public void cancelar(){
        RequestContext.getCurrentInstance().closeDialog(new Exame());
    }
    
    public void salvar(){
        exame.setMedico(medico);
        String mensagem = validarDados();
        if (mensagem.length() < 5) {
            exame = exameDao.update(exame);
            habilitarExame = false;
            habilitarConsExame = true;
        }
    }
    
    public String validarDados(){
        String msg = "";
        if (exame.getData() == null) {
            msg = msg + " você não informou a data \r\n";
        }
        if (exame.getDiagnostico().equalsIgnoreCase("")) {
            msg = msg + " você não informou o diagnostico \r\n";
        }
        if (exame.getMedico() == null) {
            msg = msg + " você não informou o medico";
        }
        return msg;
    }
    
    public void calcularValidade() {
        Calendar c = new GregorianCalendar();
        c.setTime(exame.getData());
        c.add(Calendar.DAY_OF_MONTH, 89);
        Date data = c.getTime();
       exame.setDatavalidade(data);
    }
}
