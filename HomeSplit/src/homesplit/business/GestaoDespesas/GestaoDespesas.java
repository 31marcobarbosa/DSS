package homesplit.business.GestaoDespesas;

import homesplit.business.GestaoMoradores.Morador;
import homesplit.database.DespesaDAO;
import homesplit.database.HistoricoDAO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Grupo 35
 */
public class GestaoDespesas {
    
    // Variáveis de instância
    private HistoricoDAO historicoDespesas;
    private DespesaDAO listaDespesas;
    private int total_despesas; 
    
    /**
     * Construtor da classe GestaoDespesas sem parâmetros.
     */
    public GestaoDespesas(){
        this.historicoDespesas= new HistoricoDAO(); 
        this.listaDespesas = new DespesaDAO();
        this.total_despesas = this.listaDespesas.size()+this.historicoDespesas.size();
    }
    
    /**
     * Construtor da classe GestaoDespesas que recebe o total de despesas.
     * @param total_despesas Número total de despesas.
     */
    public GestaoDespesas(int total_despesas) {
        this.historicoDespesas= new HistoricoDAO(); 
        this.listaDespesas = new DespesaDAO();
        this.total_despesas = total_despesas;
    }
    
    /**
     * Devolve a despesa correspondente a um id.
     * @param id_despesa ID da despesa.
     * @return Despesa
     */
    public Despesa getDespesa(int id_despesa){
        if(this.listaDespesas.containsKey(id_despesa)) return this.listaDespesas.get(id_despesa);
        else return this.historicoDespesas.get(id_despesa);
    }
    
    /**
     * Devolve o histórico de despesas.
     * @return Map
     */
    public Map<Integer, Despesa> getHistoricoDespesas() {
        Map<Integer,Despesa> lista = new HashMap<>();
        for(Map.Entry<Integer,Despesa> entry: this.historicoDespesas.entrySet()){
            lista.put(entry.getKey(),entry.getValue());
        }
        return lista;
    }
    
    /**
     * Altera o histórico de despesas.
     * @param historicoDespesas Lista de despesas para o historico.
     */
    public void setHistoricoDespesas(Map<Integer, Despesa> historicoDespesas) {
        this.historicoDespesas.clear();
        for(Map.Entry<Integer,Despesa> entry: historicoDespesas.entrySet()){
            this.historicoDespesas.put(entry.getKey(),entry.getValue());
        }
    }
    
    /**
     * Devolve o número total de despesas, incluido o histórico.
     * @return int
     */
    public int getTotal_despesas() {
        return total_despesas;
    }
    
    /**
     * Altera o número total de despesas.
     * @param total_despesas Número total de despesas.
     */
    public void setTotal_despesas(int total_despesas) {
        this.total_despesas = total_despesas;
    }
    
    /**
     * Devolve a lista de despesas.
     * @return Map
     */
    public Map<Integer,Despesa> getListaDespesas() {
        Map<Integer,Despesa> lista = new HashMap<>();
        for(Map.Entry<Integer,Despesa> entry: this.listaDespesas.entrySet()){
            lista.put(entry.getKey(),entry.getValue());
        }
        return lista;
    }
    
    /**
     * Altera a lista de despesas.
     * @param listaDespesas Lista de despesas.
     */
    public void setListaDespesas(Map<Integer,Despesa> listaDespesas) {
        this.listaDespesas.clear();
        for(Map.Entry<Integer,Despesa> entry: listaDespesas.entrySet()){
            this.listaDespesas.put(entry.getKey(),entry.getValue());
        }
    }
    
    /**
     * Verifica se um determinado utilizador não tem despesas por pagar.
     * @param username Username do morador.
     * @return boolean 
     * @throws DespesasPorPagarException Exception
     */
    public boolean semDespesas(String username) throws DespesasPorPagarException{
        ArrayList listaMoradores;
        for(Despesa d: listaDespesas.values()){
            listaMoradores = (ArrayList) d.getMoradores();
            if(listaMoradores.contains(username)) throw new DespesasPorPagarException("O morador está incluído em despesas que ainda se encontram ativas!");
        }
        return true;
    }
    
    /**
     * Adiciona uma despesa á gestão de despesas.
     * @param responsavel Username do morador responsavel pela despesa.
     * @param tipo Tipo de despesa.
     * @param descricao Descricao da despesa.
     * @param valor_total Valor total da despesa.
     * @param lista_moradores Lista de moradores (usernames) pertencentes a despesa.
     * @return Ma
     * @throws DespesaInvalidaException Exception
     */
    public Map<String,Double> addDespesa(String responsavel, String tipo, String descricao, double valor_total, Map<String,Double> lista_moradores) throws DespesaInvalidaException{
        
        Map<String,Fracao> lista_fracoes = new HashMap<>();
        Map<String,Double> lista = new HashMap<>();
        double counter = 0;
        
        if(valor_total <= 0) throw new DespesaInvalidaException("Valor da despesa é inválido!");
        
        for(Map.Entry<String,Double> entry : lista_moradores.entrySet()){
            counter += entry.getValue();
            double valor = (double) (valor_total*((entry.getValue())*0.01));
            Fracao f = new Fracao(entry.getKey(),total_despesas,null,0,valor,0);
            lista_fracoes.put(entry.getKey(),f);
            lista.put(entry.getKey(),valor);
        }
        
        if(counter > 100 || counter < 0) throw new DespesaInvalidaException("As percentagens não são válidas!");
           
        Despesa despesa = new Despesa(total_despesas,responsavel,0,0,valor_total,descricao,tipo,"ativa");
        this.listaDespesas.put(total_despesas,despesa);
        despesa.addFracoes(lista_fracoes);
        total_despesas++;
        return lista;
    }
    
    /**
     * Apaga uma despesa da gestão de despesas, enviando-a para o histórico.
     * @param id ID da despesa.
     * @return Map
     * @throws DespesaAtivaException Exception
     */
    public Map<String,Double> deleteDespesa(int id) throws DespesaAtivaException{
        Despesa d = listaDespesas.get(id);
        if(d.getValor_pago_solicitado() != 0 || d.getValor_pago_confirmado() != 0)
            throw new DespesaAtivaException("Não é possível eliminar esta despesa uma vez que já foram efetuados pagamentos!");
        d.setEstado("historico");
        historicoDespesas.update(d);
        Map<String,Double> lista = new HashMap<>();
        for(String m : d.getMoradores()){
            Map<String,Fracao> fracoes = d.getListaFracoes();
            if(fracoes.containsKey(m)){
                lista.put(m,d.getListaFracoes().get(m).getValor_total());
            }
        }
        return lista;
    }
    
    /**
     * Liquida uma despesa da gestão de despesas, enviando-a para o histórico.
     * @param id ID da despesa.
     * @throws DespesaAtivaException Exception
     */
    public void liquidarDespesa(int id) throws DespesaAtivaException{
        Despesa d = listaDespesas.get(id);
        if(d.despesaPaga()){
            d.setEstado("historico");
            historicoDespesas.update(d);
        }
        else if(d.fracoesPagas()){
            d.setValor_pago_confirmado(d.getValor_total());
            d.setEstado("historico");
            historicoDespesas.update(d);
        }
        else throw new DespesaAtivaException("A despesa que pretende liquidar não se encontra completamente paga!");
    }
    
    /**
     * Edita os dados de uma dada despesa.
     * @param id_despesa ID da despesa.
     * @param tipo Tipo de despesa.
     * @param descricao Descricao da despesa.
     * @param valor_total Valor total da despesa.
     * @param lista_moradores Lista de moradores pertencentes a despesa.
     * @return Map
     * @throws DespesaInvalidaException Exception
     */
    public Map<String,Double> editarDespesa(int id_despesa, String tipo, String descricao, double valor_total, Map<String,Double> lista_moradores) throws DespesaInvalidaException{
        
        Map<String,Double> lista = new HashMap<>();
        float counter = 0;
        
        if(valor_total <= 0) throw new DespesaInvalidaException("Valor da despesa é inválido!");
        
        Map<String,Fracao> listaAntiga = this.listaDespesas.get(id_despesa).getListaFracoes();
        
        for(Map.Entry<String, Double> entry : lista_moradores.entrySet()){
            if(listaAntiga.containsKey(entry.getKey())){
                counter += entry.getValue();
                double valor = (double) (valor_total*(entry.getValue()*0.01));
                Fracao f = listaAntiga.get(entry.getKey());
                double valor_antigo = f.getValor_total();
                f.setValor_total(valor);
                listaAntiga.put(entry.getKey(),f);
                lista.put(entry.getKey(),(valor-valor_antigo));
            }
            else{
                counter += entry.getValue();
                double valor = (double) (valor_total*(entry.getValue()*0.01));
                Fracao f = new Fracao(entry.getKey(),id_despesa,null,0,valor,0);
                listaAntiga.put(entry.getKey(),f);
                lista.put(entry.getKey(),valor);
            }
        }
        
        if(counter > 100 || counter < 0) throw new DespesaInvalidaException("As percentagens não são válidas!");
                
        Despesa d = this.listaDespesas.get(id_despesa);
        d.editarDados(valor_total,tipo,descricao);
        this.listaDespesas.put(id_despesa,d);
        d.addFracoes(listaAntiga);
        return lista;
    }
    
    /**
     * Paga prestação de uma dada despesa.
     * @param id_despesa ID da despesa.
     * @param m Username do morador.
     */
    public void pagarPrestacao(int id_despesa,String m){ //throws PrestacoesPagasException{
        Despesa d = this.listaDespesas.get(id_despesa);
        if(d.getFracao(m).prestacoesPagas()){
            //throw new PrestacoesPagasException("Já pagou todas as suas prestações!");
        }
        else{
            d.pagarPrestacao(m);
            this.listaDespesas.update(d);
        }
    }
    
    /**
     * Devolve todas as despesas em que um dado morador está incluido.
     * @param morador Username do morador.
     * @return Map
     */
    public Map<Integer, Despesa> consultarDespesas(String morador){
        Map<Integer,Despesa> lista = new HashMap<>();
        for(Despesa d: this.listaDespesas.values()){
            if(d.getMoradores().contains(morador)){
                lista.put(d.getId(),d);
            }
        }
        return lista;
    }
    
    /**
     * Devolve todas as despesas em que um dado morador é responsável.
     * @param morador Username do morador.
     * @return Map
     */
    public Map<Integer, Despesa> consultarDespesasResponsavel(String morador){
        Map<Integer,Despesa> lista = new HashMap<>();
        for(Despesa d: this.listaDespesas.values()){
            if(d.getResponsavel().equals(morador)){
                lista.put(d.getId(),d);
            }
        }
        return lista;
    }
    
    /**
     * Devolve o histórico de todas as despesas em que um morador esteve incluido.
     * @param morador Username do morador.
     * @return Map
     */
     public Map<Integer, Despesa> consultarHistoricoDespesas(String morador){
        Map<Integer,Despesa> historico = new HashMap<>();
        for(Despesa d: this.historicoDespesas.values()){
            if(d.getMoradores().contains(morador)){
                historico.put(d.getId(),d.clone());
            }
        }
        return historico;
    }
     
     /**
     * Devolve todas as despesas em que um morador está incluido.
     * @param id_despesa ID da despesa.
     * @param morador Username do morador.
     * @param numPrestacoes Número de prestacoes.
     */
     public void adicionarPrestacoes(int id_despesa,String morador ,int numPrestacoes){
         Despesa d = this.listaDespesas.get(id_despesa);
         d.adicionarPrestacoes(morador, numPrestacoes);
     }
    
     /**
     * Assinala como apagado o nome de um dado morador em todas as despesas em que este participou.
     * @param morador Username do morador.
     */
     public void limparNome(String morador){
         String nova = morador+" (Apagado)";
         
         for(Map.Entry<Integer,Despesa> entry: this.historicoDespesas.entrySet()){
             Despesa d = entry.getValue();
             if(d.getResponsavel().equals(morador)){
                 d.setResponsavel(nova);
                 this.historicoDespesas.put(d.getId(), d);
             }
             else if(d.getMoradores().contains(morador)){
                 Map<String,Fracao> lista = d.getListaFracoes();
                 Fracao f = lista.remove(morador);
                 lista.put(nova,f);
                 d.setListaFracoes(lista);
             } 
         }
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
     * Função equals da classe GestaoDespesas.
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
        final GestaoDespesas other = (GestaoDespesas) obj;
        if (!Objects.equals(this.listaDespesas, other.listaDespesas)) {
            return false;
        }
        if (!Objects.equals(this.historicoDespesas, other.historicoDespesas)) {
            return false;
        }
        if (this.total_despesas != other.total_despesas) {
            return false;
        }
        return true;
    }
    
    /**
     * Devolve String com a informação da classe GestaoDespesas.
     * @return String
     */
    @Override
    public String toString() {
        return "GestaoDespesas{" + "listaDespesas=" + listaDespesas + ", historicoDespesas=" + historicoDespesas + ", total_despesas=" + total_despesas + '}';
    }
}
