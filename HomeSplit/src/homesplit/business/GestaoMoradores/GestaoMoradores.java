package homesplit.business.GestaoMoradores;

import homesplit.database.MoradorDAO;
import homesplit.database.RegistoDAO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Grupo 35
 */
public class GestaoMoradores {
    
    // Variáveis de instância
    private RegistoDAO registos;
    private MoradorDAO moradores;
    int totalMoradores; // isto na verdade é o total registos!! 
    
    /**
     * Construtor da classe GestaoMoradores sem parâmetros.
     */
    public GestaoMoradores(){
        this.registos = new RegistoDAO();
        this.moradores = new MoradorDAO();
        this.totalMoradores = moradores.size();
    }
    
    /**
     * Construtor da classe GestaoMoradores que recebe o total de moradores.
     * @param total_moradores Total de moradores.
     */
    public GestaoMoradores(int total_moradores) {
        this.registos = new RegistoDAO();
        this.moradores = new MoradorDAO();
        this.totalMoradores = total_moradores;
    }
    
    /**
     * Devolve o número total de moradores.
     * @return int
     */
    public int getTotalMoradores() {
        return totalMoradores;
    }
    
    /**
     * Devolve a lista de solicitações de um dado morador para uma dada despesa.
     * @param username Username do morador.
     * @param id_despesa ID da despesa.
     * @return List
     */
    public List<Solicitacao> getSolicitacoesDespesa(String username, int id_despesa){
        return this.moradores.get(username).getSolicitacoesDespesa(id_despesa);
    }
    
    /**
     * Altera o número total de moradores.
     * @param totalMoradores Totalde moradores.
     */
    public void setTotalMoradores(int totalMoradores) {
        this.totalMoradores = totalMoradores;
    }
    
    /**
     * Devolve o conjunto de todos os moradores.
     * @return Map
     */
    public Map<String, Morador> getListaMoradores() {
        Map<String,Morador> lista = new HashMap<>();
        for(Map.Entry<String,Morador> entry: this.moradores.entrySet()){
            lista.put(entry.getKey(),entry.getValue());
        }
        return lista;
    }
    
    /**
     * Altera o conjunto dos moradores.
     * @param listaMoradores Lista de moradores.
     */
    public void setListaMoradores(Map<String, Morador> listaMoradores) {
        this.moradores.clear();
        for(Map.Entry<String,Morador> entry: listaMoradores.entrySet()){
            this.moradores.put(entry.getKey(),entry.getValue());
        }
    }
    
    /**
     * Devolve a lista de todos os registos de moradores.
     * @return Map
     */
    public Map<String, Morador> getListaRegistos() {
        Map<String,Morador> lista = new HashMap<>();
        for(Map.Entry<String,Morador> entry: this.registos.entrySet()){
            lista.put(entry.getKey(),entry.getValue());
        }
        return lista;
    }
    
    /**
     * Altera a lista de todos os registos de moradores.
     * @param listaRegistos Lista de novos registos.
     */
    public void setListaRegistos(Map<String, Morador> listaRegistos) {
        this.registos.clear();
        for(Map.Entry<String,Morador> entry: this.registos.entrySet()){
            this.registos.put(entry.getKey(),entry.getValue());
        }
    }
    
    /**
     * Altera os dados do perfil de um dado morador.
     * @param username Username do morador.
     * @param nome Nome do morador.
     * @param password Password do morador.
     * @param contacto Contacto do morador.
     * @param avatar Avatar do morador.
     */
    public void editarPerfil(String username, String nome, String password, int contacto, String avatar){
        Morador m = this.moradores.get(username);
        m.editarInformacao(nome,password,contacto,avatar);
        this.moradores.put(username, m);
    }
    
    /**
     * Verifica o login de um dado utilizador.
     * @param username Username do morador.
     * @param password Password do morador.
     * @return Morador
     * @throws homesplit.business.GestaoMoradores.PasswordInvalidaException Exception
     * @throws UsernameInvalidoException Exception
     */
    public Morador checkMorador(String username, String password) throws UsernameInvalidoException, PasswordInvalidaException{
        Morador morador;
        if(this.moradores.containsKey(username)){
            morador = this.moradores.get(username);
           if(morador.getPassword().equals(password)) return morador;
           else throw new PasswordInvalidaException("A password está incorreta!");
        }
        else if(this.registos.containsKey(username)){
            throw new UsernameInvalidoException("O seu registo ainda não foi validado!");
        }
        else throw new UsernameInvalidoException("Este username não existe!");
    }
    
    /**
     * Remove um dado morador da lista de moradores.
     * @param username Username do morador.
     * @throws homesplit.business.GestaoMoradores.MoradorInexistenteException Exception
     */
    public void removerMorador(String username) throws MoradorInexistenteException{
        if(this.moradores.containsKey(username)){
            this.moradores.remove(username);
            this.totalMoradores--;
        }
        else throw new MoradorInexistenteException("Morador não existe!");
    }
    
    /**
     * Adiciona um morador aos registos de moradores.
     * @param m Morador.
     * @throws UsernameInvalidoException Exception
     */
    public void addRegisto(Morador m) throws UsernameInvalidoException{
        if(this.registos.containsKey(m.getUsername()) || this.moradores.containsKey(m.getUsername())){
            throw new UsernameInvalidoException("Este username já se encontra em uso!");
        }
        this.moradores.put(m.getUsername(),m);
        this.totalMoradores++;
    }
    
    /**
     * Elimina o registo de um dado morador.
     * @param username Username do morador.
     * @throws UsernameInvalidoException Exception
     */
    public void deleteRegisto(String username) throws UsernameInvalidoException{
        if(this.registos.containsKey(username)){
            this.registos.remove(username);
        }
        else throw new UsernameInvalidoException("Não há nenhum registo com este username!");
    }
    
    /**
     * Adiciona um dado morador á lista de moradores.
     * @param m Morador.
     */
    public void addMorador(Morador m){
        m.setEstado("ativo");
        this.moradores.put(m.getUsername(),m);
    }
    
    /**
     * Adiciona uma solicitação de pagamento para uma dada despesa de um dado morador.
     * @param morador Username do morador.
     * @param s Solicitacao.
     */
    public void addSolicitacao(String morador, Solicitacao s){
        Morador m = this.moradores.get(morador);
        m.addSolicitacao(s);
        this.moradores.put(morador, m);
    }
    
    /**
     * Debita saldo da conta de um dado morador.
     * @param morador Username do morador.
     * @param valor Valor da conta corrente.
     */
    public void debitarSaldo(String morador, double valor){
        Morador m = this.moradores.get(morador);
        m.debitarSaldo(valor);
        this.moradores.put(morador, m);
    }
    
    /**
     * Credita saldo na conta de um dado morador.
     * @param morador Username do morador.
     * @param valor Valor da conta corrente.
     */
    public void creditarSaldo(String morador, double valor){
        Morador m = this.moradores.get(morador);
        m.creditarSaldo(valor);
        this.moradores.put(morador, m);
    }
    
    /**
     * Actualiza um dado morador da lista de moradores.
     * @param morador Morador.
     */
    public void actualiza(Morador morador){
        if(this.moradores.containsKey(morador.getUsername()))
            this.moradores.put(morador.getUsername(), morador);
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
     * Função equals da classe GestaoMoradores.
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
        final GestaoMoradores other = (GestaoMoradores) obj;
        if (!Objects.equals(this.moradores, other.moradores)) {
            return false;
        }
        return this.totalMoradores == other.totalMoradores;
    }
    
    /**
     * Devolve String com a informação da classe GestaoMoradores.
     * @return String
     */
    @Override
    public String toString() {
        return "GestaoMoradores{" + "listaMoradores=" + this.moradores + ", listaRegistos=" + this.moradores + ", totalMoradores=" + totalMoradores + '}';
    }   
}
