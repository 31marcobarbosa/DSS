package homesplit.business.GestaoDespesas;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Grupo 35
 */
public class Fracao {
    
    // Variáveis de instância
    private String morador;
    private int despesa;
    private List<Prestacao> listaPrestacoes;
    private double valor_pago;
    private double valor_total;
    private int prestacoes_pagas;
    private int temPrestacoes;
    
    /**
     * Construtor da classe Fração. 
     * @param morador Username do morador.
     * @param despesa ID da depsesa.
     * @param listaPrestacoes Lista de prestacoes.
     * @param valor_pago Valor pago.
     * @param valor_total Valor total.
     * @param prestacoes_pagas Número de prestacoes pagas.
     */
    public Fracao(String morador,int despesa, List<Prestacao> listaPrestacoes, double valor_pago, double valor_total, int prestacoes_pagas) {
        this.morador = morador;
        this.despesa = despesa;
        this.listaPrestacoes = new ArrayList<>();
        this.valor_pago = valor_pago;
        this.valor_total = valor_total;
        this.prestacoes_pagas = prestacoes_pagas;
        this.temPrestacoes = 0;
    }
    
    /**
     * Construtor da classe Fracao pela cópia de uma classe.
     * @param f Classe fracao.
     */
    public Fracao(Fracao f){
        this.morador = f.getMorador();
        this.despesa = f.getDespesa();
        this.listaPrestacoes = new ArrayList<>();
        for(Prestacao p : f.getListaPrestacoes()) {
            this.listaPrestacoes.add(p.clone());
        }
        this.valor_pago = f.getValor_pago();
        this.valor_total = f.getValor_total();
        this.prestacoes_pagas = f.getPrestacoes_pagas();
    }
    
    /**
     * Construtor da classe Fracao sem parâmetros.
     */
    public Fracao(){
        this.morador = " ";
        this.despesa = -1;
        this.listaPrestacoes = null;
        this.valor_pago = 0;
        this.valor_total = 0;
        this.prestacoes_pagas = 0;
    }
    
    /**
     * Verifica se a Fracao tem prestações associadas.
     * @return int
     */
    public int getTemPrestacoes() {
        return this.temPrestacoes;
    }
    
    /**
     * Altera a variável temPrestacoes.
     * @param temPrestacoes Indica se tem prestacoes,isto é, para valor 1 tem, para 0 não tem.
     */
    public void setTemPrestacoes(int temPrestacoes) {
        this.temPrestacoes = temPrestacoes;
    }    
    
    /**
     * Adiciona prestações á fração.
     * @param n Número de prestacoes.
     */
    public void adicionarPrestacoes(int n){
        double novo_valor = valor_total/n;
        for(int i=0;i<n;i++){
            Prestacao p = new Prestacao(i+1,this.morador,this.despesa,novo_valor," ",0);
            this.listaPrestacoes.add(p);
        }
        this.temPrestacoes = 1;
    }
    
    /**
     * Paga uma prestação da fração.
     */
    public void pagarPrestacao(){
        Prestacao p = this.listaPrestacoes.get(prestacoes_pagas);
        this.valor_pago += p.getValor();
        p.setConcluida(1);
        this.prestacoes_pagas ++;
    }
    
    /**
     * Verifica se a fração tem as prestações todas pagas.
     * @return boolean
     */
    public boolean prestacoesPagas(){
        if(this.listaPrestacoes.isEmpty()) return false;
        else if (this.valor_pago>=this.valor_total) return true;
        else return prestacoes_pagas == this.listaPrestacoes.size();
    }
    
    /**
     * Verifica se a Fracao tem prestações associadas.
     * @return boolean
     */
    public boolean temPrestacoes(){
        return this.temPrestacoes==1;
    }
    
    /**
     * Devolve o morador associado á fração.
     * @return String
     */
    public String getMorador() {
        return morador;
    }
    
    /**
     * Altera o dono da fração.
     * @param morador Username do morador.
     */
    public void setMorador(String morador) {
        this.morador = morador;
    }

    /**
     * Devolve a despesa associada á fração.
     * @return int
     */
    public int getDespesa() {
        return despesa;
    }
    
    /**
     * Altera a despesa associada á fração.
     * @param despesa ID da despesa.
     */
    public void setDespesa(int despesa) {
        this.despesa = despesa;
    }
    
    /**
     * Devolve o valor das prestações associadas á fração.
     * @return double
     */
    public double getValorPrestacoes(){
        return this.listaPrestacoes.get(prestacoes_pagas-1).getValor();
    }
    
    /**
     * Devolve a próxima prestação a pagar.
     * @return Prestacao
     */
    public Prestacao getProxPrestacao(){
        return this.listaPrestacoes.get(prestacoes_pagas);
    }
    
    /**
     * Devolve a lista de prestações associadas á fração.
     * @return List
     */
    public List<Prestacao> getListaPrestacoes() {
        return listaPrestacoes;
    }
    
    /**
     * Altera a lista de prestações associada á fração.
     * @param listaPrestacoes Lista de prestacoes.
     */
    public void setListaPrestacoes(List<Prestacao> listaPrestacoes) {
        this.listaPrestacoes = listaPrestacoes;
    }
    
    /**
     * Retorna o valor da fração pago até ao momento.
     * @return double
     */
    public double getValor_pago() {
        return valor_pago;
    }
    
    /**
     * Altera o valor da fração pago até ao momento.
     * @param valor_pago Valor pago.
     */
    public void setValor_pago(double valor_pago) {
        this.valor_pago = valor_pago;
    }
    
    /**
     * Devolve o valor total da fração.
     * @return double
     */
    public double getValor_total() {
        return valor_total;
    }
    
    /**
     * Altera o valor total da fração.
     * @param valor_total Valor total.
     */
    public void setValor_total(double valor_total) {
        this.valor_total = valor_total;
    }
    
    /**
     * Devolve o número de prestações da fração pagas até ao momento.
     * @return int
     */
    public int getPrestacoes_pagas() {
        return prestacoes_pagas;
    }
    
    /**
     * Altera o número de prestações da fração pagas até ao momento.
     * @param prestacoes_pagas Prestacoes pagas.
     */
    public void setPrestacoes_pagas(int prestacoes_pagas) {
        this.prestacoes_pagas = prestacoes_pagas;
    }
    
    /**
     * Clone da classe Fracao.
     * @return fracao
     */
    public Fracao clone(){
        return new Fracao(this);
    }
    
    /**
     * Calcula o hashcode da Fracao.
     * @return int
     */
    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }
    
    /**
     * Função equals da classe Fracao.
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
        final Fracao other = (Fracao) obj;
        if (!Objects.equals(this.listaPrestacoes, other.listaPrestacoes)) {
            return false;
        }
        if (Double.doubleToLongBits(this.valor_pago) != Double.doubleToLongBits(other.valor_pago)) {
            return false;
        }
        if (Double.doubleToLongBits(this.valor_total) != Double.doubleToLongBits(other.valor_total)) {
            return false;
        }
        if (this.prestacoes_pagas != other.prestacoes_pagas) {
            return false;
        }
        return this.temPrestacoes == other.temPrestacoes;
    }

    /**
     * Devolve String com a informação da classe Fracao.
     * @return String
     */
    @Override
    public String toString() {
        if(temPrestacoes==1){
            return "            Valor a pagar: " + (valor_total - valor_pago) + " Nº de prestações: " + (listaPrestacoes.size() - prestacoes_pagas);
        }
        else{
            return "            Valor a pagar: " + (valor_total - valor_pago) + " Nº de prestações: A definir";
        }
    }
}