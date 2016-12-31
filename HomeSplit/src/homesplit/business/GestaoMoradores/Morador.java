package homesplit.business.GestaoMoradores;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Grupo 35
 */
public class Morador {
    
    // Variáveis de instância
    private String username;
    private String password;
    private String nome;
    private int contacto;
    private String avatar;
    private double saldo;
    private List<String> notificacoes;
    private List<Solicitacao> solicitacoes;
    private String estado; // ver se está apagado ou não
    
    /**
     * Construtor da classe Morador. 
     * @param username Username do morador.
     * @param password Password do morador.
     * @param nome Nome do morador.
     * @param contacto Contacto do morador.
     * @param avatar Avatar do morador.
     * @param saldo Saldo do morador.
     * @param notificacoes Lista de notificacoes do morador.
     * @param solicitacoes Lista de solicitacoes do morador.
     * @param estado Estado do morador.
     */
    public Morador(String username, String password, String nome, int contacto, String avatar, Double saldo, List<String> notificacoes, List<Solicitacao> solicitacoes, String estado) {
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.contacto = contacto;
        this.avatar = avatar;
        this.saldo = saldo;
        this.notificacoes = new ArrayList<>();
        this.solicitacoes = new ArrayList<>();
        this.estado = estado;
    }
    
    /**
     * Construtor da classe Morador pela cópia de uma classe.
     * @param m Morador.
     */
    public Morador(Morador m){
        this.username = m.getUsername();
        this.password = m.getPassword();
        this.nome = m.getNome();
        this.contacto = m.getContacto();
        this.avatar = m.getAvatar();
        this.saldo = m.getSaldo();
        this.notificacoes = m.getNotificacoes();
        this.solicitacoes = m.getSolicitacoes();
        this.estado = m.getEstado();
    }
    
    /**
     * Construtor da classe Morador sem parâmetros.
     */
    public Morador(){
        this.username = " ";
        this.password = " ";
        this.nome = " ";
        this.contacto = 0;
        this.avatar = "sem-foto";
        this.saldo = 0;
        this.notificacoes = null;
        this.solicitacoes = null;
        this.estado = " ";
    }
    
    /**
     * Credita saldo na conta do morador.
     * @param valor Valor a credtiar no saldo.
     */
    public void creditarSaldo(double valor){
        this.saldo+=valor;
    }
    
    /**
     * Debita saldo na conta do morador.
     * @param valor Valor a debitar no saldo.
     */
    public void debitarSaldo(double valor){
        this.saldo-=valor;
    }
    
    /**
     * Adiciona uma dada solicitação á lista de solicitações.
     * @param s Solicitacao.
     */
    public void addSolicitacao(Solicitacao s){
        this.solicitacoes.add(s);
    }
    
    /**
     * Elimina uma dada solicitação da lista de solicitações.
     * @param s Solicitacao.
     */
    public void deleteSolicitacao(Solicitacao s){
        this.solicitacoes.remove(s);
    }
    
    /**
     * Adiciona uma dada notificação á lista de notificações.
     * @param n Notificacao.
     */
    public void addNotificacao(String n){
        this.notificacoes.add(n);
    }
    
    /**
     * Devolve o username do morador.
     * @return String
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Altera o username do morador.
     * @param username Username do morador.
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * Devolve a password do morador.
     * @return String 
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Altera a password do morador.
     * @param password Password do morador.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Devolve o nome do morador.
     * @return String
     */
    public String getNome() {
        return nome;
    }
    
    /**
     * Altera o nome do morador.
     * @param nome Nome do morador.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    /**
     * Devolve o contacto do morador.
     * @return int
     */
    public int getContacto() {
        return contacto;
    }
    
    /**
     * Altera o contacto do morador.
     * @param contacto Contacto do morador.
     */
    public void setContacto(int contacto) {
        this.contacto = contacto;
    }
    
    /**
     * Devolve o avatar do morador.
     * @return String
     */
    public String getAvatar(){
        return this.avatar;
    }
    
    /**
     * Altera o avatar do morador.
     * @param avatar Avatar do morador.
     */
    public void setAvatar(String avatar){
        this.avatar = avatar;
    }
    
    /**
     * Devolve o saldo do morador.
     * @return Double
     */
    public Double getSaldo() {
        return this.saldo;
    }
    
    /**
     * Altera o saldo do morador.
     * @param conta Saldo do morador.
     */
    public void setSaldo(Double conta) {
        this.saldo = conta;
    }
    
    /**
     * Devolve o estado do morador.
     * @return String
     */
    public String getEstado() {
        return estado;
    }
    
    /**
     * Altera o estado do morador.
     * @param estado Estado do morador.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    /**
     * Devolve a lista de notificações do morador.
     * @return List
     */
    public List<String> getNotificacoes() {
        return notificacoes;
    }
    
    /**
     * Altera a lista de notificações do morador.
     * @param notificacoes Lista de notificacoes.
     */
    public void setNotificacoes(List<String> notificacoes) {
        this.notificacoes = notificacoes;
    }

    /**
     * Devolve a lista de solicitações de pagamento do morador.
     * @return List
     */
    public List<Solicitacao> getSolicitacoes() {
        return solicitacoes;
    }
    
    /**
     * Devolve a lista de solicitações de pagamento de uma dada despesa do morador.
     * @param id_despesa ID da despesa.
     * @return List
     */
    public List<Solicitacao> getSolicitacoesDespesa(int id_despesa){
        List<Solicitacao> lista = new ArrayList<>();
        try{
            for(Solicitacao s : this.solicitacoes){
                if(s.getDespesa() == id_despesa) 
                    lista.add(s);
            }
        }
        catch (Exception e ){
            System.out.println(e.getMessage());
        }
        return lista;
    }
    
    /**
     * Altera a lista de solicitações de pagamento do morador.
     * @param solicitacoes Lista de solicitacoes.
     */
    public void setSolicitacoes(List<Solicitacao> solicitacoes) {
        this.solicitacoes = solicitacoes;
    }
    
    /**
     * Altera as informações do morador.
     * @param nome Nome do morador.
     * @param password Password do morador.
     * @param contacto Contacto do morador.
     * @param avatar Avatar do morador.
     */
    public void editarInformacao(String nome, String password, int contacto, String avatar){
        this.nome = nome;
        this.password = password;
        this.contacto = contacto;
        this.avatar = avatar;
    }
    
    /**
     * Clone da classe Morador. 
     * @return 
     */
    public Morador clone(){
        return new Morador(this);
    }
    
    /**
     * Função hashCode
     * @return int
     */
    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }
    
    /**
     * Função equals da classe Morador.
     * @param obj Objecto
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Morador other = (Morador) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (this.contacto != other.contacto) {
            return false;
        }
        
        if (!Objects.equals(this.avatar, other.avatar)) {
            return false;
        }
        
        if (!Objects.equals(this.saldo, other.saldo)) {
            return false;
        }
        if (!Objects.equals(this.notificacoes, other.notificacoes)) {
            return false;
        }

        if (!Objects.equals(this.solicitacoes, other.solicitacoes)) {
            return false;
        }
        if (this.estado != other.estado) {
            return false;
        }
        return true;
    }
    
    /**
     * Devolve String com a informação da classe Morador.
     * @return String
     */
    @Override
    public String toString() {
        return "Morador{" + "username=" + username + ", password=" + password + ", nome=" + nome + ", contacto=" + contacto + ", conta=" + saldo + ", notificacoes=" + notificacoes + ", solicitacoes=" + solicitacoes + ", estado=" + estado + '}';
    }
}
