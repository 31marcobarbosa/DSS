package homesplit.business.GestaoMoradores;

import java.util.Objects;

/**
 *
 * @author Grupo 35
 */
public class Solicitacao {
    
    // Variáveis de instância
    private String morador_responsavel;
    private String morador;
    private int despesa;
    private int prestacao;
    private double valor;
    
    /**
     * Construtor da classe Solicitacao. 
     * @param morador_responsavel Username do morador responsavel.
     * @param morador Username do morador.
     * @param despesa ID da despesa.
     * @param prestacao ID da prestacao.
     * @param valor Valor da solicitacao.
     */
    public Solicitacao(String morador_responsavel, String morador, int despesa, int prestacao,double valor) {
        this.morador_responsavel = morador_responsavel;
        this.morador = morador;
        this.despesa = despesa;
        this.prestacao = prestacao;
        this.valor = valor;
    }
    
    /**
     * Construtor da classe Solicitacao pela cópia de uma classe.
     * @param s Solicitacao.
     */
    public Solicitacao(Solicitacao s){
        this.morador_responsavel = s.getMorador_responsavel();
        this.morador = s.getMorador();
        this.despesa = s.getDespesa();
        this.prestacao = s.getPrestacao();
        this.valor = s.getValor();
    }
    
    /**
     * Devolve o morador responsável pela despesa associada á solicitação.
     * @return String
     */
    public String getMorador_responsavel() {
        return morador_responsavel;
    }
    
    /**
     * Altera o morador responsável pela despesa associada á solicitação.
     * @param morador_responsavel Username do morador responsavel.
     */
    public void setMorador_responsavel(String morador_responsavel) {
        this.morador_responsavel = morador_responsavel;
    }
    
    /**
     * Devolve o valor da solicitação.
     * @return double
     */
    public double getValor() {
        return valor;
    }
    
    /**
     * Altera o valor da solicitação.
     * @param valor Valor da solicitacao.
     */
    public void setValor(double valor) {
        this.valor = valor;
    }
    
    /**
     * Devolve o morador que fez a solicitação.
     * @return String
     */
    public String getMorador() {
        return morador;
    }
    
    /**
     * Altera o morador que fez a solicitação.
     * @param morador Username do morador.
     */
    public void setMorador(String morador) {
        this.morador = morador;
    }
    
    /**
     * Devolve a despesa associada á solicitação.
     * @return int
     */
    public int getDespesa() {
        return despesa;
    }
    
    /**
     * Altera a despesa associada á solicitação.
     * @param despesa ID da despesa.
     */
    public void setDespesa(int despesa) {
        this.despesa = despesa;
    }
    
    /**
     * Devolve a prestacao associada á solicitação.
     * @return int
     */
    public int getPrestacao() {
        return prestacao;
    }
    
    /**
     * Altera a prestacao associada á solicitação.
     * @param prestacao ID da despesa.
     */
    public void setPrestacao(int prestacao) {
        this.prestacao = prestacao;
    }
    
    /**
     * Clone da classe Solicitacao. 
     * @return 
     */
    public Solicitacao clone(){
        return new Solicitacao(this);
    }
    
    /**
     * Função hashCode
     * @return int
     */
    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }
    
    /**
     * Função equals da classe Solicitacao.
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
        final Solicitacao other = (Solicitacao) obj;
        if (!Objects.equals(this.morador, other.morador)) {
            return false;
        }
        if (this.despesa != other.despesa) {
            return false;
        }
        if (this.prestacao != other.prestacao) {
            return false;
        }
        if (Double.doubleToLongBits(this.valor) != Double.doubleToLongBits(other.valor)) {
            return false;
        }
        return true;
    }

    /**
     * Devolve String com a informação da classe Solicitacao.
     * @return String
     */
    @Override
    public String toString() {
        return this.morador+" deseja pagar "+this.valor+"€";
    }
}
