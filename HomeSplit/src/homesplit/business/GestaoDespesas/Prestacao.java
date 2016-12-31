package homesplit.business.GestaoDespesas;

import java.util.Objects;

/**
 *
 * @author Grupo 35
 */
public class Prestacao {
    
    // Variáveis de instância
    private int id;
    private String morador;
    private int despesa;
    private double valor;
    private String limite_tempo;
    private int concluida; // não é obrigatoriamente necessário!!    

    /**
     * Construtor da classe Prestacao. 
     * @param id ID da prestacao.
     * @param morador Username do morador.
     * @param despesa ID da despesa.
     * @param valor Valor da prestacao.
     * @param limite_tempo data limite da prestacao.
     * @param concluida indica se esta concluida ou não.
     */
    public Prestacao(int id, String morador, int despesa, double valor, String limite_tempo, int concluida) {
        this.id = id;
        this.morador = morador;
        this.despesa = despesa;
        this.valor = valor;
        this.limite_tempo = limite_tempo;
        this.concluida = concluida;
    }
    
    /**
     * Construtor da classe Prestacao pela cópia de uma classe.
     * @param p Prestacao.
     */
    public Prestacao(Prestacao p){
        this.id = p.getId();
        this.morador = p.getMorador();
        this.despesa = p.getDespesa();
        this.valor = p.getValor();
        this.limite_tempo = p.getLimite_tempo();
        this.concluida = p.getConcluida();
    }
    
    /**
     * Construtor da classe Prestacao sem parâmetros.
     */
    public Prestacao(){
        this.id = -1;
        this.valor = 0;
        this.limite_tempo = null;
        this.concluida = 0;
    }
    
    /**
     * Devolve o morador associado á prestação.
     * @return String
     */
    public String getMorador() {
        return morador;
    }
    
    /**
     * Altera o morador associado á prestação.
     * @param morador Username do morador.
     */
    public void setMorador(String morador) {
        this.morador = morador;
    }
    
    /**
     * Devolve a despesa associada á prestação.
     * @return int
     */
    public int getDespesa() {
        return despesa;
    }
    
    /**
     * Altera a despesa associada á prestação.
     * @param despesa ID da despesa.
     */
    public void setDespesa(int despesa) {
        this.despesa = despesa;
    }
    
    /**
     * Devolve o id da prestação.
     * @return int
     */
    public int getId() {
        return id;
    }
    
    /**
     * Altera o id da prestação.
     * @param id ID da prestacao.
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Devolve o valor associado á prestação.
     * @return double
     */
    public double getValor() {
        return valor;
    }
    
    /**
     * Aletra o valor associado á prestação.
     * @param valor Valor da prestacao.
     */
    public void setValor(double valor) {
        this.valor = valor;
    }
    
    /**
     * Devolve o limite de tempo associado ao pagamento da prestação.
     * @return String
     */
    public String getLimite_tempo() {
        return limite_tempo;
    }
    
    /**
     * Altera o limite de tempo associado ao pagamento da prestação.
     * @param limite_tempo Data limite da prestacao.
     */
    public void setLimite_tempo(String limite_tempo) {
        this.limite_tempo = limite_tempo;
    }
    
    /**
     * Devolve 1 se a prestação estiver paga, 0 caso contrário.
     * @return int
     */
    public int getConcluida() {
        return concluida;
    }
    
    /**
     * Altera o valor associado verificação do pagamento da prestação.
     * @param concluida Indica se está concluida ou não.
     */
    public void setConcluida(int concluida) {
        this.concluida = concluida;
    }
    
    /**
     * Clone da classe Prestacao. 
     * @return 
     */
    public Prestacao clone(){
        return new Prestacao(this);
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
     * Função equals da classe Prestacao.
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
        final Prestacao other = (Prestacao) obj;
        if (this.id != other.id) {
            return false;
        }
        if (Double.doubleToLongBits(this.valor) != Double.doubleToLongBits(other.valor)) {
            return false;
        }
        if (!Objects.equals(this.limite_tempo, other.limite_tempo)) {
            return false;
        }
        if (this.concluida != other.concluida) {
            return false;
        }
        return true;
    }

    /**
     * Devolve String com a informação da classe Prestacao.
     * @return String
     */
    @Override
    public String toString() {
        return "Prestacao{" + "valor=" + valor + ", limite_tempo=" + limite_tempo + ", concluida=" + concluida + '}';
    }    
}
