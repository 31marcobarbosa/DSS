package homesplit.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import homesplit.business.GestaoDespesas.Fracao;
import homesplit.business.GestaoDespesas.Prestacao;

/**
 *
 * @author Grupo 35
 */
public class FracaoDAO implements Map<String,Fracao>{

    private Connection con;
    private int id;
    
    public FracaoDAO (int id){
        this.id = id;
    }
    
    @Override
    public int size() {
        int size = -1;

        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM Fracao WHERE Despesa = ?");
            ps.setString(1,Integer.toString(this.id));
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                size = rs.getInt(1);
            }
        }
        catch(SQLException e){
             System.out.printf(e.getMessage());
        }
        finally{
            try{
                Connect.close(con);
            }
            catch(Exception e){
                 System.out.printf(e.getMessage());
            }
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        return this.size()==0;
    }

    @Override
    public boolean containsKey(Object key) {
        boolean res = false;
        
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Fracao WHERE Morador = ? AND Despesa = ?");
            ps.setString(1,(String) key);
            ps.setString(2,Integer.toString(this.id));
            ResultSet rs = ps.executeQuery();
            res = rs.next();   
        }
        catch(SQLException e){
            System.out.printf(e.getMessage());
        }
        finally{
            try{
                Connect.close(con);
            }
            catch(Exception e){
                System.out.printf(e.getMessage());
            }
        }
        return res;
    }

    @Override
    public boolean containsValue(Object value) {
        boolean res = false;
        
        if(value.getClass().getName().equals("homesplit.business.GestaoDespesas.Fracao")){
            Fracao f = (Fracao)value;
            String s = f.getMorador();
            Fracao fra = this.get(s);
            if(fra.equals(f)){
                res=true;
            }
        }
        return res;
    }

    @Override
    public Fracao get(Object key) {
        Fracao f = new Fracao();
        
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Fracao WHERE Morador = ? AND Despesa = ?");
            ps.setString(1,(String) key);
            ps.setString(2, Integer.toString(this.id));
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                f.setMorador(rs.getString("Morador"));
                f.setValor_pago(rs.getDouble("Valor_pago"));
                f.setValor_total(rs.getDouble("Valor_total"));
                f.setDespesa(rs.getInt("Despesa"));
                f.setTemPrestacoes(rs.getInt("temPrestacoes"));
                f.setPrestacoes_pagas(rs.getInt("prestacoes_pagas"));
               
                List<Prestacao> lista = new ArrayList<>();
                ps = con.prepareStatement("SELECT * FROM Prestacao WHERE Morador = ? AND Despesa = ?");
                ps.setString(1,(String) key);
                ps.setString(2, Integer.toString(this.id));
                rs = ps.executeQuery();
                while(rs.next()){
                    Prestacao p = new Prestacao();
                    p.setId(rs.getInt("ID"));
                    p.setMorador(rs.getString("Morador"));
                    p.setDespesa(rs.getInt("Despesa"));
                    /*p.setLimite_tempo(parseSQLDate(rs.getString("Data_limte")));*/
                    p.setValor(rs.getDouble("Valor"));
                    p.setConcluida(rs.getInt("Concluida"));
                    lista.add(p);
                }
                f.setListaPrestacoes(lista);
            }
            else f = null;
        }
        catch(SQLException e){
             System.out.printf(e.getMessage());
        }
        finally{
            try{
                Connect.close(con);
            }
            catch(Exception e){
                 System.out.printf(e.getMessage());
            }
        }
        return f;
    }

    @Override
    public Fracao put(String key, Fracao value) {
        Fracao f;
        
        if(this.containsKey(key)){
            f = this.get(key);
        }
        else f = value;
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("DELETE FROM Fracao WHERE Morador = ? AND Despesa = ?");
            ps.setString(1,(String) key);
            ps.setString(2,Integer.toString(this.id));
            ps.executeUpdate();
            
            ps = con.prepareStatement("INSERT INTO Fracao (Morador, Valor_pago, Valor_total,prestacoes_pagas,temPrestacoes,Despesa) VALUES (?,?,?,?,?,?)");
            ps.setString(1,key);
            ps.setString(2,Double.toString(value.getValor_pago()));
            ps.setString(3,Double.toString(value.getValor_total()));
            ps.setString(4,Integer.toString(value.getPrestacoes_pagas()));
            ps.setString(5,Integer.toString(value.getTemPrestacoes()));
            ps.setString(6,Integer.toString(this.id));
            ps.executeUpdate();
            
            List<Prestacao> lista = value.getListaPrestacoes();
            if(lista != null){
                for(Prestacao p : lista){
                    ps = con.prepareStatement("INSERT INTO Prestacao (ID, Valor, Despesa, Concluida, Morador) VALUES (?,?,?,?,?)");
                    ps.setString(1,Integer.toString(p.getId()));
                    ps.setString(2,Double.toString(p.getValor()));
                    /*ps.setString(2,parseCalendar(p.getData_limite()));*/
                    ps.setString(3,Integer.toString(this.id));
                    ps.setString(4,Integer.toString(p.getConcluida()));
                    ps.setString(5,key);
                    ps.executeUpdate();
                }
            }
        }
        catch(SQLException e){
            System.out.printf(e.getMessage());
        }
        finally{
            try{
                Connect.close(con);
                
            }
            catch(Exception e){
                System.out.printf(e.getMessage());
            }
        }
        return f;
    }

    @Override
    public Fracao remove(Object key) {
        Fracao f = this.get((String) key);
        try{
            con = Connect.connect();
            PreparedStatement stm = con.prepareStatement("DELETE FROM Fracao WHERE Morador = ? AND Despesa = ?");
            stm.setString(1, (String) key);
            stm.setString(2,Integer.toString(this.id));
            stm.executeUpdate();
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            Connect.close(con);
        }
        return f;
    }
    
    @Override
    public void putAll(Map<? extends String, ? extends Fracao> m) {
        for(Fracao f : m.values()) {
            put(f.getMorador(), f);
        }
    }

    @Override
    public void clear() {
        try{
            con = Connect.connect();
            PreparedStatement stm = con.prepareStatement("DELETE FROM Fracao WHERE Despesa = ?");
            stm.setString(1,Integer.toString(this.id));
            stm.executeUpdate();
        }
        catch(Exception e){
            throw new NullPointerException(e.getMessage()); 
        }
        finally{
            Connect.close(con);
        }
    }
    

    @Override
    public Set<String> keySet() {
        Set<String> set = null;
        
        try{
            con = Connect.connect();
            set = new HashSet<>();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Fracao WHERE Despesa = ?");
            ps.setString(1,Integer.toString(this.id));
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                set.add(rs.getString("Morador"));
            }   
        }
        catch(SQLException e){
            System.out.printf(e.getMessage());
        }
        finally{
            try{
                Connect.close(con);
            }
            catch(Exception e){
                System.out.printf(e.getMessage());
            }
        }
        return set;
    }

    @Override
    public Collection<Fracao> values() {
        Collection<Fracao> col = new HashSet<>();
        try {
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Fracao WHERE Despesa = ?");
            ps.setString(1,Integer.toString(this.id));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Fracao f = new Fracao();
                f.setMorador(rs.getString("Morador"));
                f.setValor_pago(rs.getDouble("Valor_pago"));
                f.setValor_total(rs.getDouble("Valor_total"));
                f.setDespesa(rs.getInt("Despesa"));
                f.setPrestacoes_pagas(rs.getInt("prestacoes_pagas"));
                f.setTemPrestacoes(rs.getInt("temPrestacoes"));
                
                List<Prestacao> lista = new ArrayList<>();
                ps = con.prepareStatement("SELECT * FROM Prestacao WHERE Morador = ? AND Despesa = ?");
                ps.setString(1,rs.getString("Morador"));
                ps.setString(2,Integer.toString(this.id));
                rs = ps.executeQuery();
                while(rs.next()){
                    Prestacao p = new Prestacao();
                    p.setMorador(rs.getString("Morador"));
                    p.setDespesa(rs.getInt("Despesa"));
                    /*p.setData_limite(parseSQLDate(rs.getString("Data_limte")));*/
                    p.setValor(rs.getDouble("Valor"));
                    p.setId(rs.getInt("ID"));
                    p.setConcluida(rs.getInt("Concluida"));
                    lista.add(p);
                }
                f.setListaPrestacoes(lista);
                col.add(f);
            }
            
        } catch (Exception e) {
           throw new NullPointerException(e.getMessage());
        }
        finally {
            Connect.close(con);
        }
        return col;
    }

    @Override
    public Set<Entry<String, Fracao>> entrySet() {
        Set<String> keys = new HashSet<>(this.keySet());
        
        HashMap<String,Fracao> map = new HashMap<>();
        for(String key : keys){
            map.put(key,this.get(key));
        }
        return map.entrySet();
    }
    
    
    /**
     * Gera uma String Date MySQL a partir de um objeto GregorianCalendar.
     * @param date, a data que queremos converter para MySQL.
     * @return String a data no formato MySQL.
     */
    public String parseCalendar(GregorianCalendar date) {
        StringBuilder sb = new StringBuilder();
        sb.append(date.get(Calendar.YEAR)).append("-");
        sb.append(date.get(Calendar.MONTH)).append("-");
        sb.append(date.get(Calendar.DAY_OF_MONTH));
        return sb.toString();
    }
    
    /**
     * Transforma um campo DATE de MySQL para uma instância de GregorianCalendar.
     * @param date, String que representa data no format MySQL.
     * @return GregorianCalendar criada a partir.
     */
    public GregorianCalendar parseSQLDate(String date) {
        int year, month, day;
        String toks[] = date.split("[- ]");
        year = Integer.parseInt(toks[0].trim());
        month = Integer.parseInt(toks[1].trim());
        day = Integer.parseInt(toks[2].trim());
        return new GregorianCalendar(year,month,day);      
    }
    
}
