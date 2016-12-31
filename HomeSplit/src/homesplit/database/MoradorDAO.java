package homesplit.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import homesplit.business.GestaoMoradores.Morador;
import homesplit.business.GestaoMoradores.Solicitacao;

/**
 *
 * @author Grupo 35
 */
public class MoradorDAO implements Map<String,Morador>  {
    
    private Connection con;
    
    @Override
    public int size() {
        int size = -1;

        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM Morador WHERE Estado = ?");
            ps.setString(1,"ativo");
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
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Morador WHERE Username = ? AND Estado = ?");
            ps.setString(1,(String) key);
            ps.setString(2,"ativo");
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
        
        if(value.getClass().getName().equals("homesplit.business.GestaoMoradores.Morador")){
            Morador m = (Morador)value;
            String user = m.getUsername();
            Morador mr = this.get(user);
            if(mr.equals(m)){
                res=true;
            }
        }
       return res;
    }

    @Override
    public Morador get(Object key) {
        Morador m = new Morador();
        
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Morador WHERE Username = ? AND Estado = ?");
            ps.setString(1,(String) key);
            ps.setString(2,"ativo");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                m.setUsername(rs.getString("Username"));
                m.setPassword(rs.getString("Password"));
                m.setNome(rs.getString("Nome"));
                m.setAvatar(rs.getString("Avatar"));
                m.setContacto(rs.getInt("Contacto"));
                m.setSaldo(rs.getDouble("Saldo"));
                m.setEstado(rs.getString("Estado"));
                
                List<String> notificacoes = new ArrayList();
                ps = con.prepareStatement("SELECT * FROM Notificacao WHERE Morador = ?");
                ps.setString(1,(String) key);
                rs = ps.executeQuery();
                while(rs.next()){
                    notificacoes.add(rs.getString("Info"));
                }
                m.setNotificacoes(notificacoes);
                
                List<Solicitacao> solicitacoes = new ArrayList();
                ps = con.prepareStatement("SELECT * FROM Solicitacao WHERE Morador_Responsavel = ?");
                ps.setString(1,(String) key);
                rs = ps.executeQuery();
                while(rs.next()){
                    solicitacoes.add(new Solicitacao(rs.getString("Morador_Responsavel"),rs.getString("Morador"),rs.getInt("Despesa"),rs.getInt("Prestacao"),rs.getDouble("Valor")));
                }
                m.setSolicitacoes(solicitacoes);
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
        return m;
    }

    @Override
    public Morador put(String key, Morador value) {
        Morador m;

        if(this.containsKey(key)){
            m = this.get(key);
        }
        else m = value;
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("DELETE FROM Morador WHERE Username = ?");
            ps.setString(1,key);
            ps.executeUpdate();
            
            ps = con.prepareStatement("INSERT INTO Morador (Username,Password,Nome,Contacto,Avatar,Saldo,Estado) VALUES (?,?,?,?,?,?,?)");
            ps.setString(1,key);
            ps.setString(2,value.getPassword());
            ps.setString(3,value.getNome());
            ps.setString(4,Integer.toString(value.getContacto()));
            ps.setString(5,value.getAvatar());
            ps.setString(6,Double.toString(value.getSaldo()));
            ps.setString(7,value.getEstado());
            ps.executeUpdate();
            
            List<String> lista = value.getNotificacoes();
            if(lista!=null){
                for(String info : lista){
                    ps = con.prepareStatement("INSERT INTO Notificacao (Info,Morador) VALUES (?,?)");
                    ps.setString(1,info);
                    ps.setString(2,key);
                    ps.executeUpdate();
                }
            }
           
            List<Solicitacao> listaSol = value.getSolicitacoes();
            if(listaSol!=null){
                for(Solicitacao s : listaSol){
                    ps = con.prepareStatement("INSERT INTO Solicitacao (Morador_Responsavel,Morador,Despesa,Prestacao,Valor) VALUES (?,?,?,?,?)");
                    ps.setString(1,key);
                    ps.setString(2,s.getMorador());
                    ps.setString(3,Integer.toString(s.getDespesa()));
                    ps.setString(4,Integer.toString(s.getPrestacao()));
                    ps.setString(5,Double.toString(s.getValor()));
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
        return m;
    }

    @Override
    public Morador remove(Object key) {
        Morador m = this.get((String) key);
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("DELETE FROM Morador WHERE Username = ? AND Estado = ?");
            ps.setString(1, (String) key);
            ps.setString(2,"ativo");
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
        return m;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Morador> m) {
        for(Morador morador : m.values()) {
            put(morador.getUsername(), morador);
        }
    }

    @Override
    public void clear() {
        
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("DELETE FROM Morador WHERE Estado = ?");
            ps.setString(1,"ativo");
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

    @Override
    public Set<String> keySet() {
        Set<String> set = null;
        
        try{
            con = Connect.connect();
            set = new HashSet<>();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Morador WHERE Estado = ? ");
            ps.setString(1,"ativo");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                set.add(rs.getString("Username"));
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
    public Collection<Morador> values() {
        Set<Morador> set = new HashSet<>();
        Set<String> keys = new HashSet<>(this.keySet());
        for(String key : keys){
            set.add(this.get(key));
        }
        return set;
    }

    @Override
    public Set<Entry<String, Morador>> entrySet() {
        Set<String> keys = new HashSet<>(this.keySet());
        
        HashMap<String,Morador> map = new HashMap<>();
        for(String key : keys){
            map.put(key,this.get(key));
        }
        return map.entrySet();
    }
    
    public void addSolicitacao (String morador, Solicitacao s){
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("INSERT INTO Solicitacao (Morador_Responsavel,Morador,Despesa,Prestacao,Valor) VALUES (?,?,?,?,?)");
            ps.setString(1,morador);
            ps.setString(2,s.getMorador());
            ps.setString(3,Integer.toString(s.getDespesa()));
            ps.setString(4,Integer.toString(s.getPrestacao()));
            ps.setString(5,Double.toString(s.getValor()));
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
    
}
