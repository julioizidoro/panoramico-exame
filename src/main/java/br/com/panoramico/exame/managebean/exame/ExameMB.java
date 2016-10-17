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
import br.com.panoramico.exame.managebean.usuarioLogado.UsuarioLogadoMB;
import br.com.panoramico.exame.model.Exame;
import br.com.panoramico.exame.model.Exameassociado;
import br.com.panoramico.exame.model.Examedependente;
import br.com.panoramico.exame.model.Medico;
import br.com.panoramico.exame.util.Formatacao;
import br.com.panoramico.exame.util.Mensagem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class ExameMB implements Serializable{
    
    private Exame exame;
    private List<Exame> listaExames;
    @EJB
    private ExameDao exameDao;
    private Exameassociado exameassociado;
    private Examedependente examedependente;
    @EJB
    private ExameAssociadoDao exameAssociadoDao;
    @EJB
    private ExameDependenteDao exameDependenteDao;
    private Date dataInicio;
    private Date dataFinal;
    private String situacao;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private Medico medico;
    private boolean habilitarExame = false;
    private boolean habilitarConsExame = true;
    @EJB
    private MedicoDao medicoDao;
    private List<Medico> listaMedico;
    
    @PostConstruct
    public void init(){
        gerarListaExame();
    }

    public Exame getExame() {
        return exame;
    }

    public void setExame(Exame exame) {
        this.exame = exame;
    }

    public List<Exame> getListaExames() {
        return listaExames;
    }

    public void setListaExames(List<Exame> listaExames) {
        this.listaExames = listaExames;
    }

    public ExameDao getExameDao() {
        return exameDao;
    }

    public void setExameDao(ExameDao exameDao) {
        this.exameDao = exameDao;
    }

    public Exameassociado getExameassociado() {
        return exameassociado;
    }

    public void setExameassociado(Exameassociado exameassociado) {
        this.exameassociado = exameassociado;
    }

    public Examedependente getExamedependente() {
        return examedependente;
    }

    public void setExamedependente(Examedependente examedependente) {
        this.examedependente = examedependente;
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

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }
    
    
    
    public void gerarListaExame(){
        listaExames = exameDao.list("Select e from Exame e");
        if (listaExames == null) {
            listaExames = new ArrayList<Exame>();
        }
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public boolean isHabilitarExame() {
        return habilitarExame;
    }

    public void setHabilitarExame(boolean habilitarExame) {
        this.habilitarExame = habilitarExame;
    }

    public boolean isHabilitarConsExame() {
        return habilitarConsExame;
    }

    public void setHabilitarConsExame(boolean habilitarConsExame) {
        this.habilitarConsExame = habilitarConsExame;
    }

    public MedicoDao getMedicoDao() {
        return medicoDao;
    }

    public void setMedicoDao(MedicoDao medicoDao) {
        this.medicoDao = medicoDao;
    }

    public List<Medico> getListaMedico() {
        return listaMedico;
    }

    public void setListaMedico(List<Medico> listaMedico) {
        this.listaMedico = listaMedico;
    }
    
    
    
     public String novoCadastroExame() {
        return "";
    }
    

    
    public void diagnostico(Exame e){
        gerarListaMedicos();
        if (medico == null) {
            Mensagem.lancarMensagemInfo("Acesso Negado !!", "");
        }else{
            exame = e;
            habilitarConsExame = false;
            habilitarExame = true;
            if (exame == null) {
                exame = new Exame();
            }
        }
    }
    
    public void excluir(Exame exame){
        List<Exameassociado> listaExameAssociado = exameAssociadoDao.list("Select ea from Exameassociado ea where ea.exame.idexame=" + exame.getIdexame());
        List<Examedependente> listaExameDependente  = exameDependenteDao.list("Select ed from Examedependente ed where ed.exame.idexame=" + exame.getIdexame());
        if (listaExameAssociado == null || listaExameAssociado.isEmpty()) {
            for (int i = 0; i < listaExameDependente.size(); i++) {
                exameDependenteDao.remove(listaExameDependente.get(i).getIdexamedependente());
            }
        }else{
            for (int i = 0; i < listaExameAssociado.size(); i++) {
                exameAssociadoDao.remove(listaExameAssociado.get(i).getIdexameassociado());
            }
        }
        exameDao.remove(exame.getIdexame());
        Mensagem.lancarMensagemInfo("Excluido", "com sucesso");
        gerarListaExame();
    }
    
    
     public String pegarValores(Exame exame){
        List<Exameassociado> listaExameAssociado = exameAssociadoDao.list("Select ea from Exameassociado ea where ea.exame.idexame=" + exame.getIdexame());
        List<Examedependente> listaExameDependente  = exameDependenteDao.list("Select ed from Examedependente ed where ed.exame.idexame=" + exame.getIdexame());
        if (listaExameAssociado == null || listaExameAssociado.isEmpty()) {
            for (int i = 0; i < listaExameDependente.size(); i++) {
                examedependente = listaExameDependente.get(i);
                return examedependente.getDependente().getNome();
            }
        }else{
            for (int i = 0; i < listaExameAssociado.size(); i++) {
                exameassociado = listaExameAssociado.get(i);
                return exameassociado.getAssociado().getCliente().getNome();
            }
        }
        return "";
    }
     
    
    public void filtrar(){
        String sql = "Select e from Exame e";
        if (!situacao.equalsIgnoreCase("sn") || dataInicio != null || dataFinal != null) {
            sql = sql + " where";
        }
        
        if (!situacao.equalsIgnoreCase("sn")) {
            sql = sql + " e.situacao='" + situacao + "'";
            if (dataInicio != null && dataFinal != null) {
                sql = sql + " and";
            }
        }
        
        if (dataInicio != null && dataFinal != null) {
            sql = sql + " e.data>='" + Formatacao.ConvercaoDataSql(dataInicio) + "' and e.data<='" 
                    + Formatacao.ConvercaoDataSql(dataFinal) + "'";
        }
        listaExames = exameDao.list(sql);
        Mensagem.lancarMensagemInfo("", "Filtrado com sucesso");
    }
     
    public void limparFiltro(){
        situacao = "";
        dataInicio = null;
        dataFinal = null;
        gerarListaExame();
    }
    
    public void salvar(){
        exame.setMedico(medico);
        String mensagem = validarDados();
        if (mensagem.length() < 5) {
            exame = exameDao.update(exame);
            habilitarExame = false;
            habilitarConsExame = true;
            gerarListaExame();
            Mensagem.lancarMensagemInfo("Salvo", " com sucesso");
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
    
    public void cancelar(){
        habilitarConsExame = true;
        habilitarExame = false;
    }
    
    
    public void gerarListaMedicos(){
        listaMedico = medicoDao.list("Select m from Medico m where m.situacao='Ativo'");
        if (listaMedico == null) {
            listaMedico = new ArrayList<Medico>();
        }
        for (int i = 0; i < listaMedico.size(); i++) {
            if (listaMedico.get(i).getIdusuario() == usuarioLogadoMB.getUsuario().getIdusuario()) {
                medico = listaMedico.get(i);
            }
        }
    }
    
}
