package homesplit.business;

import homesplit.business.GestaoDespesas.*;
import homesplit.business.GestaoMoradores.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Grupo 35
 */

public class HomeSplit {
    
    // Variáveis de intância
    private Morador utilizador; 
    private GestaoMoradores gestao_moradores;
    private GestaoDespesas gestao_despesas; 
    
    /**
     * Construtor vazio do HomeSplit.
     */
    public HomeSplit(){
        this.utilizador = null;
        this.gestao_despesas = new GestaoDespesas();
        this.gestao_moradores = new GestaoMoradores();
    }
    
     /**
     * Iniciar sessão na aplicação.
     * @param username Username do morador.
     * @param password Password do morador.
     * @throws homesplit.business.GestaoMoradores.UsernameInvalidoException Exception
     */
    public void iniciarSessao(String username, String password) throws UsernameInvalidoException{
        try{
            this.utilizador = gestao_moradores.checkMorador(username,password);
        }
        catch(Exception e){
            throw new UsernameInvalidoException(e.getMessage());
        }
    }
    
     /**
     * Terminar sessão na aplicação. 
     */
    public void terminarSessao(){
        this.gestao_moradores.actualiza(this.utilizador);
        this.utilizador = null;
    }
    
     /**
     * Editar perfil de um morador.
     * @param nome Nome do morador.
     * @param password Password do morador.
     * @param contacto Contacto do morador.
     * @param avatar Avatar do morador.
     */
    public void editarPerfil(String nome, String password, int contacto, String avatar){
        gestao_moradores.editarPerfil(this.utilizador.getUsername(),nome,password,contacto,avatar);
        this.utilizador.editarInformacao(nome, password, contacto, avatar);
    }
    
     /**
     * Abandonar Apartamento. 
     */
    public void sairApartamento() throws DespesasPorPagarException, MoradorInexistenteException{
        try{
            gestao_despesas.semDespesas(this.utilizador.getUsername());
        }
        catch(Exception e){
            throw new DespesasPorPagarException(e.getMessage());
        }
        try{
            gestao_moradores.removerMorador(this.utilizador.getUsername());
            gestao_despesas.limparNome(this.utilizador.getUsername());
            terminarSessao();
        }
        catch(Exception e){
            throw new MoradorInexistenteException(e.getMessage());
        }
    }
     /**
     * Adicionar uma despesa na aplicação. 
     * @param tipo_despesa Tipo de despesa.
     * @param descricao Descricao da despesa.
     * @param valor Valor da despesa.
     * @param lista_moradores_incluidos Lista de moradores incluidos na despesa.
     * @throws homesplit.business.GestaoDespesas.DespesaInvalidaException Exception
     */
    public void adicionarDespesa(String tipo_despesa, String descricao, double valor, Map<String,Double> lista_moradores_incluidos) throws DespesaInvalidaException{
        try{
            Map<String,Double> lista = gestao_despesas.addDespesa(this.utilizador.getUsername(),tipo_despesa,descricao,valor,lista_moradores_incluidos);
            for(Map.Entry<String,Double> entry : lista.entrySet()){
                this.utilizador.creditarSaldo(entry.getValue());
                this.gestao_moradores.debitarSaldo(entry.getKey(),entry.getValue());
                Morador m = this.gestao_moradores.getListaMoradores().get(entry.getKey());
                m.addNotificacao("O utilizador "+this.utilizador.getNome()+" adicionou-te numa despesa!");
                this.gestao_moradores.actualiza(m);
            } 
        }
        catch(Exception e){
            throw new DespesaInvalidaException(e.getMessage());
        }
    }
    
     /**
     * Eliminar uma despesa na aplicação. 
     * @param id_despesa ID da despesa.
     * @throws homesplit.business.GestaoDespesas.DespesaAtivaException Exception
     */
    public void eliminarDespesa(int id_despesa) throws DespesaAtivaException{
        try{
            Map<String,Double> lista = gestao_despesas.deleteDespesa(id_despesa);
            for(Map.Entry<String,Double> entry : lista.entrySet()){
                this.utilizador.debitarSaldo(entry.getValue());
                this.gestao_moradores.creditarSaldo(entry.getKey(),entry.getValue());
            }
        }
        catch(Exception e){
            throw new DespesaAtivaException(e.getMessage());
        }
    }
    
     /**
     * Liquidar uma despesa na aplicação. 
     * @param id_despesa ID da despesa.
     * @throws homesplit.business.GestaoDespesas.DespesaAtivaException Exception
     */
    public void liquidarDespesa(int id_despesa) throws DespesaAtivaException{
        try{
            gestao_despesas.liquidarDespesa(id_despesa);
        }
        catch(Exception e){
            throw new DespesaAtivaException(e.getMessage());
        }
    }
    
     /**
     * Consultar a lista de despesas por pagar. 
     * @return Map Lista de despesas por pagar.
     */
    public Map<Integer,Despesa> consultarDespesas(){
        Map<Integer,Despesa> lista_despesas = gestao_despesas.consultarDespesas(this.utilizador.getUsername());
        return lista_despesas;
    }
    
    /**
     * Consultar a lista de despesas que é responsável. 
     * @return Map Lista de despesas que é responsável.
     */
    public Map<Integer,Despesa> consultarDespesasResponsavel(){
        Map<Integer,Despesa> lista_despesas = gestao_despesas.consultarDespesasResponsavel(this.utilizador.getUsername());
        return lista_despesas;
    }
    
    /**
     * Consultar o histórico de despesas, isto é, a lista de despesas já terminadas, ou eliminadas. 
     * @return Map Lista de despesas que estão no histórico.
     */
    public Map<Integer,Despesa> consultarHistoricoDespesas(){
        Map<Integer,Despesa> lista_despesas = gestao_despesas.consultarHistoricoDespesas(this.utilizador.getUsername());
        return lista_despesas;
    }
    
    /**
     * Consultar a lista de notificações. 
     * @return List Lista de notificacoes.
     */
    public List<String> consultarNotificacoes(){
        List<String> lista_notificacoes = new ArrayList<>();
        for(String n: this.utilizador.getNotificacoes()){
            lista_notificacoes.add(n);
        }
        return lista_notificacoes;
    }
    
    /**
     * Aceitar a solicitação de um pagamento.
     * @param s Solicitacao.
     */
    public void aceitarPagamento(Solicitacao s){
        String n = "Pagamento da despesa "+s.getDespesa()+" confirmado!";
        this.gestao_despesas.pagarPrestacao(s.getDespesa(), s.getMorador());
        this.utilizador.deleteSolicitacao(s);
        Morador m = this.gestao_moradores.getListaMoradores().get(s.getMorador());
        m.addNotificacao(n);
        this.gestao_moradores.actualiza(m);
        this.utilizador.debitarSaldo(s.getValor());
        this.gestao_moradores.creditarSaldo(s.getMorador(),s.getValor());
        this.gestao_moradores.actualiza(this.utilizador);
    }
    
    /**
     * Rejeitar a solicitação de um pagamento. 
     * @param s Solicitacao.
     */
    public void rejeitarPagamento(Solicitacao s){
        String n = "Pagamento da despesa "+s.getDespesa()+" rejeitado!";
        this.utilizador.deleteSolicitacao(s);
        Morador m = this.gestao_moradores.getListaMoradores().get(s.getMorador());
        m.addNotificacao(n);
        this.gestao_moradores.actualiza(m);
        this.gestao_moradores.actualiza(this.utilizador);
    }
    
     /**
     * Lista de solicitações de uma despesa onde o morador é responsável.
     * @param id_depesa ID da despesa.
     * @return List
     */
    public List<Solicitacao> getSolicitacoes(int id_depesa){
        return this.gestao_moradores.getSolicitacoesDespesa(this.utilizador.getUsername(), id_depesa);
    }
    
     /**
     * Editar as informações de uma despesa. 
     * @param id_despesa ID da despesa.
     * @param tipo Tipo de despesa.
     * @param descricao Descricao da despesa.
     * @param valor_total Valor total da despesa.
     * @param lista_moradores Lista de moradores que pertencem a despesa.
     * @throws homesplit.business.GestaoDespesas.DespesaInvalidaException Exception
     */
    public void editarDespesa(int id_despesa, String tipo, String descricao, double valor_total, Map<String,Double> lista_moradores) throws DespesaInvalidaException{
        try{
            Map<String,Double> lista = this.gestao_despesas.editarDespesa(id_despesa, tipo, descricao, valor_total, lista_moradores);
            for(Map.Entry<String,Double> entry : lista.entrySet()){
                this.utilizador.creditarSaldo(entry.getValue());
                this.gestao_moradores.debitarSaldo(entry.getKey(),entry.getValue());
            }
        }
        catch(Exception e){
            throw new DespesaInvalidaException(e.getMessage());
        }
    }
    
     /**
     * Define o número de prestações para pagar uma despesa.
     * @param id_despesa ID da despesa.
     * @param numPrestacoes Número de prestacoes.
     */
    public void setPrestacoesDespesa(int id_despesa, int numPrestacoes){
        this.gestao_despesas.adicionarPrestacoes(id_despesa,this.utilizador.getUsername(),numPrestacoes);
    }
    
     /**
     * Verifica se tem as prestações definidas. 
     * @param id_despesa ID da despesa.
     * @return boolean 
     */
    public boolean prestacoesDefinidas(int id_despesa){
        return gestao_despesas.getDespesa(id_despesa).getFracao(this.utilizador.getUsername()).temPrestacoes();
    }
    
     /**
     * Adiciona uma solicitação de um pagamento ao morador responsável pela despesa.
     * @param id_despesa ID da despesa.
     */
    public void solicitarPagamentoPrestacao(int id_despesa){
        Despesa d = gestao_despesas.getDespesa(id_despesa);
        Prestacao p = d.getFracao(this.utilizador.getUsername()).getProxPrestacao();
        Solicitacao s = new Solicitacao(d.getResponsavel(),this.utilizador.getUsername(),id_despesa,p.getId(),p.getValor());
        this.gestao_moradores.addSolicitacao(d.getResponsavel(),s);
        String n = "O utilizador "+this.utilizador.getUsername()+" solicitou o pagamento da despesa "+id_despesa+"!";
        Morador m = this.gestao_moradores.getListaMoradores().get(d.getResponsavel());
        m.addNotificacao(n);
        this.gestao_moradores.actualiza(m);
    }
    
     /**
     * Obter o valor da prestação a pagar pelo morador.
     * @param id_despesa ID da despesa.
     * @return double
     */
    public double getValorPrestacao(int id_despesa){
        Despesa d = gestao_despesas.getDespesa(id_despesa);
        Prestacao p = d.getFracao(this.utilizador.getUsername()).getProxPrestacao();
        return p.getValor();
    }
    
     /**
     * Obter uma despesa.
     * @param id_despesa ID da despesa.
     * @return Despesa
     */
    public Despesa getDespesa(int id_despesa){
        return this.gestao_despesas.getDespesa(id_despesa);
    }
    
     /**
     * Regitar novo morador na aplicação. 
     * @param username Username do novo registo.
     * @param password Password do novo registo.
     * @param nome Nome do novo registo.
     * @param contacto Contacto do novo registo.
     * @throws homesplit.business.GestaoMoradores.UsernameInvalidoException Exception
     */
    public void efetuarRegisto(String username, String password, String nome, int contacto) throws UsernameInvalidoException{
        Morador m = new Morador(username,password,nome,contacto,"sem-foto",(double) 0,null,null,"registo");
        try{
            if(gestao_moradores.getTotalMoradores() == 0){
                gestao_moradores.addMorador(m);
                gestao_moradores.setTotalMoradores(1);
            }
            else gestao_moradores.addRegisto(m);
        }
        catch(Exception e){
            throw new UsernameInvalidoException(e.getMessage());
        }
    }
    
     /**
     * Validar o registo de um novo morador na aplicação. 
     * @param username Username do um registo.
     * @throws homesplit.business.GestaoMoradores.UsernameInvalidoException Exception
     */
    public void validarRegisto(String username) throws UsernameInvalidoException{
        try{
            gestao_moradores.addMorador(gestao_moradores.getListaRegistos().get(username));
            /*gestao_moradores.deleteRegisto(username);*/
        }
        catch(Exception e){
            throw new UsernameInvalidoException(e.getMessage());
        }
    }
    
     /**
     * Rejeitar o registo de um novo morador na aplicação. 
     * @param username Username de um registo.
     * @throws homesplit.business.GestaoMoradores.UsernameInvalidoException Exception
     */
    public void rejeitarRegisto(String username) throws UsernameInvalidoException{
        try{
            gestao_moradores.deleteRegisto(username);
        }
        catch(Exception e){
            throw new UsernameInvalidoException(e.getMessage());
        }
    }
    
     /**
     * Devolve o morador autenticar.
     * @return Morador
     */
    public Morador getUtilizador(){
        return this.utilizador;
    }
    
     /**
     * Devolve a classe que gere os moradores.
     * @return GestaoMoradores
     */
    public GestaoMoradores getGestao_moradores() {
        return gestao_moradores;
    }
    
     /**
     * Devolve a classe que gere as despesas.
     * @return GestaoDespesas
     */
    public GestaoDespesas getGestao_despesas() {
        return gestao_despesas;
    }
}
