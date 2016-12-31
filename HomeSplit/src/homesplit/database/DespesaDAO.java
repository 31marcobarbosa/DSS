package homesplit.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import homesplit.business.GestaoDespesas.Despesa;
/**
 *
 * @author Grupo 35
 */
public class DespesaDAO implements Map<Integer,Despesa> {

    private Connection con;
    
    @Override
    public int size() {
        int size = -1;

        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) from Despesa WHERE Estado = ?");
            ps.setString(1,"ativa");
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
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Despesa WHERE ID = ? AND Estado = ?");
            ps.setString(1,Integer.toString((Integer) key));
            ps.setString(2,"ativa");
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
        
        if(value.getClass().getName().equals("homesplit.business.GestaoDespesas.Despesa")){
            Despesa d = (Despesa)value;
            int id = d.getId();
            Despesa de = this.get(id);
            if(de.equals(d)){
                res=true;
            }
        }
        return res;
    }

    @Override
    public Despesa get(Object key) {
        Despesa d = new Despesa();
        
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Despesa WHERE ID = ? AND Estado = ?");
            ps.setString(1,Integer.toString((Integer) key));
            ps.setString(2, "ativa");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                d.setId(rs.getInt("ID"));
                d.setValor_pago_solicitado(rs.getDouble("Valor_pago_solicitado"));
                d.setValor_pago_confirmado(rs.getDouble("Valor_pago_confirmado"));
                d.setValor_total(rs.getDouble("Valor_total"));
                d.setDescricao(rs.getString("Descricao"));
                d.setTipo(rs.getString("Tipo"));
                /*d.setData_limite(parseSQLDate(rs.getString("Data_limite")));*/
                d.setResponsavel(rs.getString("Morador_Responsavel"));
                d.setFracoes(new FracaoDAO(rs.getInt("ID")));
                d.setEstado(rs.getString("Estado"));
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
        return d;
    }

    @Override
    public Despesa put(Integer key, Despesa value) {
        Despesa d;
        
        if(this.containsKey(key)){
            d = this.get(key);
        }
        else d = value;
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("DELETE FROM Despesa WHERE ID = ?");
            ps.setString(1,Integer.toString(key));
            ps.executeUpdate();
            
            ps = con.prepareStatement("INSERT INTO Despesa (ID, Valor_pago_solicitado, Valor_pago_confirmado, Valor_total, Descricao, Tipo, Morador_Responsavel,Estado) VALUES (?,?,?,?,?,?,?,?)");
            ps.setString(1,Integer.toString(key));
            ps.setString(2,Double.toString(value.getValor_pago_solicitado()));
            ps.setString(3,Double.toString(value.getValor_pago_confirmado()));
            ps.setString(4,Double.toString(value.getValor_total()));
            ps.setString(5,value.getDescricao());
            ps.setString(6,value.getTipo());
            /*ps.setString(6,parseCalendar(value.getData_limite()));*/
            ps.setString(7,value.getResponsavel());
            ps.setString(8,value.getEstado());
            ps.executeUpdate();
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
        return d;
    }

    @Override
    public Despesa remove(Object key) {
        Despesa d = this.get((Integer) key);
        try{
            con = Connect.connect();
            PreparedStatement stm = con.prepareStatement("DELETE FROM Despesa WHERE ID = ? AND Estado = ?");
            stm.setInt(1, (Integer)key);
            stm.setString(2,"ativa");
            stm.executeUpdate();
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            Connect.close(con);
        }
        return d;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Despesa> m) {
        for(Despesa d : m.values()) {
            put(d.getId(), d);
        }
    }

    @Override
    public void clear() {
        try{
            con = Connect.connect();
            Statement stm = con.createStatement();
            stm.executeUpdate("DELETE FROM Despesa");
        }
        catch(Exception e){
            throw new NullPointerException(e.getMessage()); 
        }
        finally{
            Connect.close(con);
        }
    }

    @Override
    public Set<Integer> keySet() {
        Set<Integer> set = null;
        
        try{
            con = Connect.connect();
            set = new HashSet<>();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Despesa WHERE Estado = ?");
            ps.setString(1,"ativa");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                set.add(rs.getInt("ID"));
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
    public Collection<Despesa> values() {
        Collection<Despesa> col = new HashSet<>();
        try {
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Despesa WHERE Estado = ?");
            ps.setString(1,"ativa");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Despesa d = new Despesa();
                d.setId(rs.getInt("ID"));
                d.setValor_pago_solicitado(rs.getDouble("Valor_pago_solicitado"));
                d.setValor_pago_confirmado(rs.getDouble("Valor_pago_confirmado"));
                d.setValor_total(rs.getDouble("Valor_total"));
                d.setDescricao(rs.getString("Descricao"));
                d.setTipo(rs.getString("Tipo"));
                /*d.setData_limite(parseSQLDate(rs.getString("Data_limte")));*/
                d.setResponsavel(rs.getString("Morador_Responsavel"));
                d.setFracoes(new FracaoDAO(rs.getInt("ID")));
                d.setEstado(rs.getString("Estado"));
                col.add(d);
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
    public Set<Entry<Integer, Despesa>> entrySet() {
        Set<Integer> keys = new HashSet<>(this.keySet());
        
        HashMap<Integer,Despesa> map = new HashMap<>();
        for(Integer key : keys){
            map.put(key,this.get(key));
        }
        return map.entrySet();
    }
    
    public void update (Despesa d){
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("UPDATE Despesa SET Valor_pago_solicitado = ?, Valor_pago_confirmado = ?, "
                    + "Valor_total = ? , Tipo = ?, Morador_Responsavel = ?,Estado = ? WHERE ID = ?");
            ps.setString(1,Double.toString(d.getValor_pago_solicitado()));
            ps.setString(2,Double.toString(d.getValor_pago_confirmado()));
            ps.setString(3,Double.toString(d.getValor_total()));
            ps.setString(4,d.getTipo());
            /*ps.setString(5,parseCalendar(d.getLimite_tempo()));*/
            ps.setString(5,d.getResponsavel());
            ps.setString(6,d.getEstado());
            ps.setString(7,Integer.toString(d.getId()));
            ps.executeUpdate();
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
