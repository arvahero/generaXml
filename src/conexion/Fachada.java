/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;
import java.sql.*;


/**
 *
 * @author Usuario
 */
public class Fachada {
    private Connection conexion=null;
    
    public void establecerConexion(){
        
        String dataBase = "jdbc:sqlserver://localhost;databaseName=xExpertis50VaciaColombia";
        
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conexion=DriverManager.getConnection(dataBase,"sa","Frutafino12");
        }catch(Exception e){
            e.getStackTrace();
        }
               
    }
    
    public ResultSet ejecutarConsulta(String consulta){
             
        ResultSet rs=null;
        PreparedStatement ps=null;
        try{
            
            ps=conexion.prepareStatement(consulta);
            rs=ps.executeQuery();            
        } catch(Exception e){
            
        }
        
        return rs;
    }
    
    public void actualizarRegistro(String sentencia){
        
        PreparedStatement ps=null;
        
        try{            
            ps=conexion.prepareStatement(sentencia);
            ps.executeUpdate();
        } catch(Exception e){
            
        }
    }
    
    public void cerrarConexion(){
        try{
            conexion.close();
        }catch(Exception e){
            
        }
        
    }
    
}
