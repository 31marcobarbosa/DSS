package homesplit.business.GestaoDespesas;

import homesplit.database.FracaoDAO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Grupo 35
 */
public class Despesa {
    
    // Variáveis de instância
    private int id_despesa;
    private String responsavel; 
    private FracaoDAO listaFracoes;
    private double valor_pago_solicitado;
    private double valor_pago_confirmado;
    private double valor_total;
    private String descricao;
    private String tipo;
    private String estado;

    /**
     * Construtor da classe Despesa. 
     * @param id_despesa ID da despesa.
     * @param responsavel Responsavel pela despesa.
     * @param valor_pago_solicitado Valor pago solicitado.
     * @param valor_pago_confirmado Valor pago confirmado.
     * @param valor_total Valor total da despesa.
     * @param descricao Descricao da despesa.
     * @param tipo Tipo de despesa.
     * @param estado Estado da despesa.
     */
    public Despesa(int id_despesa, String responsavel, double valor_pago_solicitado, double valor_pago_confirmado, double valor_total, String descricao, String tipo, String estado) {
        this.id_despesa = id_despesa;
        this.responsavel = responsavel;
        this.listaFracoes = new FracaoDAO(id_despesa);
        this.valor_pago_solicitado = valor_pago_solicitado;
        this.valor_pago_confirmado = valor_pago_confirmado;
        this.valor_total = valor_total;
        this.descricao = descricao;
        this.tipo = tipo;
        this.estado = estado;
    }
    
    /**
     * Construtor da classe Despesa pela cópia de uma classe.
     * @param d Despesa.
     */
    public Despesa(Despesa d){
        this.id_despesa = d.getId();
        this.responsavel = d.getResponsavel();
        this.listaFracoes = new FracaoDAO(d.getId());
        this.valor_pago_solicitado = d.getValor_pago_solicitado();
        this.valor_pago_confirmado = d.getValor_pago_confirmado();
        this.valor_total = d.getValor_total();
        this.descricao = d.getDescricao();
        this.tipo = d.getTipo();
        this.estado = d.getEstado();
    }
    
    /**
     * Construtor da classe Despesa sem parâmetros.
     */
    public Despesa(){
        this.id_despesa = -1;
        this.responsavel = " ";
        this.listaFracoes = null;
        this.valor_pago_solicitado = 0;
        this.valor_pago_confirmado = 0;
        this.valor_total = 0;
        this.descricao = " ";
        this.tipo = " ";
        this.estado = " ";
    }
     
    /**
     * Adiciona uma lista de frações a despesa.
     * @param f Lista de fracoes. 
     */
    public void addFracoes(Map<String,Fracao> f){
        for(Map.Entry<String,Fracao> entry : f.entrySet()){
            this.listaFracoes.put(entry.getKey(),entry.getValue());
        }
    }
    
    /**
     * Devolve o estado da despesa. 
     * @return String
     */
    public String getEstado() {
        return estado;
    }
    
    /**
     * Altera o valor do estado.
     * @param estado Estado da despesa.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    /**
     * Devolve a fração correspondente ao morador.
     * @param morador Username de um morador.
     * @return Fracao
     */
    public Fracao getFracao(String morador){
        return listaFracoes.get(morador);
    }
    
    /**
     * Paga uma prestação, registando tal procedimento.
     * @param m Username de um morador.
     */
    public void pagarPrestacao(String m){
        Fracao f = this.listaFracoes.get(m);
        f.pagarPrestacao();
        this.listaFracoes.put(m,f);
        this.valor_pago_confirmado += f.getValorPrestacoes();
    }
    
    /**
     * Verifica se a despesa está paga.
     * @return boolean
     */
    public boolean despesaPaga(){
        return (this.valor_total == this.getValor_pago_confirmado());
    }
    
    /**
     * Verifica se tem todas as frações pagas. 
     * @return boolean
     */
    public boolean fracoesPagas(){
        for(Map.Entry<String, Fracao> entry : this.listaFracoes.entrySet()) {
            if(!(entry.getValue().prestacoesPagas())) return false;
        }
        return true;
    }
    
    /**
     * Edita os dados de uma despesa. 
     * @param valor_total Valor total da despesa.
     * @param tipo Tipo de despesa.
     * @param descricao Descricao da despesa.
     */
    public void editarDados(double valor_total, String tipo, String descricao){
        this.valor_total = valor_total;
        this.descricao = descricao;
        this.tipo = tipo;
    }
    
    /**
     * Adiciona as prestações que o morador pretende ter.
     * @param morador Username de um morador.
     * @param numPrestacoes Número de prestacoes de um despesa.
     */
    public void adicionarPrestacoes(String morador , int numPrestacoes){
        Fracao f = this.listaFracoes.get(morador);
        f.adicionarPrestacoes(numPrestacoes);
        this.listaFracoes.put(morador, f);
    }
    
    /**
     * Devolve a lista dos moradores (Usernames) que tem uma fração na despesa. 
     * @return List
     */
    public List<String> getMoradores(){
        ArrayList<String> listaMoradores = new ArrayList<>();
        for(String m : listaFracoes.keySet()){
            listaMoradores.add(m);
        }
        listaMoradores.add(this.responsavel);
        return listaMoradores;
    }
    
    /**
     * Devolve o id da despesa. 
     * @return int
     */
    public int getId() {
        return id_despesa;
    }
    
    /**
     * Altera o valor do id da despesa.
     * @param id_despesa ID da despesa.
     */
    public void setId(int id_despesa) {
        this.id_despesa = id_despesa;
    }
    
    /**
     * Devolve o username do responsável. 
     * @return String
     */
    public String getResponsavel() {
        return responsavel;
    }
    
    /**
     * Altera o responsável, alterando o respetivo username. 
     * @param responsavel Username do responsavel da despesa.
     */
    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }
    
    /**
     * Devolve a lista de todas as frações da despesa. 
     * @return Map
     */
    public Map<String, Fracao> getListaFracoes() {
        Map<String,Fracao> lista = new HashMap<>();
        for(Map.Entry<String,Fracao> entry: this.listaFracoes.entrySet()){
            lista.put(entry.getKey(),entry.getValue());
        }
        return lista;
    }
    
    /**
     * Atribui uma lista de frações a despesa. 
     * @param listaFracoes Lista de fracoes.
     */
    public void setListaFracoes(Map<String, Fracao> listaFracoes) {
        this.listaFracoes.clear();
        for(Map.Entry<String,Fracao> entry: listaFracoes.entrySet()){
            this.listaFracoes.put(entry.getKey(),entry.getValue());
        }
    }
    
    /**
     * Atribui a classe DAO que representa a lista de frações. 
     * @param f Classe FracaoDAO.
     */
    public void setFracoes(FracaoDAO f){
        this.listaFracoes = f;
    }
    
    /**
     * Devolve o valor pago solicitado. 
     * @return double 
     */
    public double getValor_pago_solicitado() {
        return valor_pago_solicitado;
    }
    
    /**
     * Altera o valor pago solicitado. 
     * @param valor_pago_solicitado Valor pago solicitado.
     */
    public void setValor_pago_solicitado(double valor_pago_solicitado) {
        this.valor_pago_solicitado = valor_pago_solicitado;
    }
    
    /**
     * Devolve o valor pago confirmado.
     * @return double
     */
    public double getValor_pago_confirmado() {
        return valor_pago_confirmado;
    }
    
    /**
     * Atribui o valor pago confirmado.
     * @param valor_pago_confirmado Valor pago confirmado.
     */
    public void setValor_pago_confirmado(double valor_pago_confirmado) {
        this.valor_pago_confirmado = valor_pago_confirmado;
    }
    
    /**
     * Devolve o valor total.
     * @return double
     */
    public double getValor_total() {
        return valor_total;
    }
    
    /**
     * Atribui um valor ao valor total. 
     * @param valor_total Valor total.
     */
    public void setValor_total(double valor_total) {
        this.valor_total = valor_total;
    }
    
    /**
     * Devolve a descrição da despesa.
     * @return String.
     */
    public String getDescricao() {
        return descricao;
    }
    
    /**
     * Atribui um novo valor a descrição da despesa.
     * @param descricao Descricao da despesa.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    /**
     * Devolve o tipo da despesa.
     * @return String
     */
    public String getTipo() {
        return tipo;
    }
    
    /**
     * Atribui um valor ao tipo da despesa.
     * @param tipo Tipo de despesa.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
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
     * Clone da classe Despesa. 
     * @return Despesa.
     */
    public Despesa clone(){
        return new Despesa(this);
    }
    
    /**
     * Função equals da classe Despesa.
     * @param obj Objecto.
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
        final Despesa other = (Despesa) obj;
        if (this.id_despesa != other.id_despesa) {
            return false;
        }
        if (!Objects.equals(this.responsavel, other.responsavel)) {
            return false;
        }
        if (!Objects.equals(this.listaFracoes, other.listaFracoes)) {
            return false;
        }
        if (Double.doubleToLongBits(this.valor_pago_solicitado) != Double.doubleToLongBits(other.valor_pago_solicitado)) {
            return false;
        }
        if (Double.doubleToLongBits(this.valor_pago_confirmado) != Double.doubleToLongBits(other.valor_pago_confirmado)) {
            return false;
        }
        if (Double.doubleToLongBits(this.valor_total) != Double.doubleToLongBits(other.valor_total)) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.tipo, other.tipo)) {
            return false;
        }
        return true;
    }
    
    /**
     * Devolve String com a informação da classe Despesa.
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder info;
        info = new StringBuilder();
        info.append("("+this.id_despesa+")");
        info.append("   " + this.descricao);
        info.append("   Valor: " + this.valor_total + "€");
        return info.toString();
    }
}
