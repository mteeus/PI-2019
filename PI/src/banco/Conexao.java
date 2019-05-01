 
package banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

 


public class Conexao {
  
    public static Connection conn;
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String user = "root";
    private static final String password = "";
    private static final String url = "jdbc:mysql://127.0.0.1:3306/banco";

    public Conexao() {
        
        conn = null;
        
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            
            if (conn != null) {
            System.out.println("CONEXAO ESTABELECIDA");
             }
            
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("ERRO NA CONEXAO" + e);   
          
    }
    }
    
    // retorna conexao
    public Connection getConnection(){
        return conn;
        
    }
    //desconecta 
    public void desconectar() {
        conn = null;
        if(conn == null){
            System.out.println("CONEXAO FECHADA");
        }
        
    }
    
    
}


