
package gestion_biblio;



import java.net.Socket;

import java.sql.*;


public class db_connection {

    Connection connection;
    Statement statement;
    String SQL;
   
 String url;
    String username;
    String password="helloroot";
    Socket etudiant;
    int Port;
    String Host;

  
  public db_connection(String url, String username, String password, String Host, int Port) {

      
        this.url = url;
        this.username = username;
        this.password = "helloroot";
        this.Host = Host;
        this.Port = Port;
    }

   
 public Connection connexionDatabase() {

      
  try {
            Class.forName("com.mysql.jdbc.Driver");
          
  connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) 
{System.err.println(e);//
        }
        return connection;
    }

 
   public Connection closeconnexion() {

        try {
            connection.close();
        } catch (Exception e) {System.err.println(e);//
        }
        return connection;
    }

    public ResultSet exécutionQuery(String sql) {
        connexionDatabase();
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            System.out.println(sql);
        } catch (SQLException ex) {System.err.println(ex);//
        }
        return resultSet;
    }

    public String exécutionUpdate(String sql) {
        connexionDatabase();
        String result = "";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            result = sql;

        } catch (SQLException ex) {
            result = ex.toString();
        }
        return result;

    }

//Fungsi untuk eksekusi query select semua kolom
    public ResultSet querySelectAll(String nomTable) {

        connexionDatabase();
        SQL = "SELECT * FROM " + nomTable;
        System.out.println(SQL);
        return this.exécutionQuery(SQL);

    }

//Overload fungsi untuk eksekusi query select semua kolom dengan where
    public ResultSet querySelectAll(String nomTable, String état) {

        connexionDatabase();
        SQL = "SELECT * FROM " + nomTable + " WHERE " + état;
        return this.exécutionQuery(SQL);

    }

//Fungsi untuk eksekusi query select dengan kolom spesifik
    public ResultSet querySelect(String[] nomColonne, String nomTable) {

        connexionDatabase();
        int i;
        SQL = "SELECT ";

        for (i = 0; i <= nomColonne.length - 1; i++) {
            SQL += nomColonne[i];
            if (i < nomColonne.length - 1) {
                SQL += ",";
            }
        }

        SQL += " FROM " + nomTable;
        return this.exécutionQuery(SQL);

    }

//Overload fungsi untuk eksekusi query select dengan kolom spesifik dengan where
    public ResultSet fcSelectCommand(String[] nomColonne, String nomTable, String état) {

        connexionDatabase();
        int i;
        SQL = "SELECT ";

        for (i = 0; i <= nomColonne.length - 1; i++) {
            SQL += nomColonne[i];
            if (i < nomColonne.length - 1) {
                SQL += ",";
            }
        }

        SQL += " FROM " + nomTable + " WHERE " + état;
        return this.exécutionQuery(SQL);

    }

    public String queryInsert(String nomTable, String[] contenuTableau) {

        connexionDatabase();
        int i;
        SQL = "INSERT INTO " + nomTable + " VALUES(";

        for (i = 0; i <= contenuTableau.length - 1; i++) {
            SQL += "'" + contenuTableau[i] + "'";
            if (i < contenuTableau.length - 1) {
                SQL += ",";
            }
        }

        SQL += ")";
        return this.exécutionUpdate(SQL);

    }
//Fungsi eksekusi query insert

    public String queryInsert(String nomTable, String[] nomColonne, String[] contenuTableau) {

        connexionDatabase();
        int i;
        SQL = "INSERT INTO " + nomTable + "(";
        for (i = 0; i <= nomColonne.length - 1; i++) {
            SQL += nomColonne[i];
            if (i < nomColonne.length - 1) {
                SQL += ",";
            }
        }
        SQL += ") VALUES(";
        for (i = 0; i <= contenuTableau.length - 1; i++) {
            SQL += "'" + contenuTableau[i] + "'";
            if (i < contenuTableau.length - 1) {
                SQL += ",";
            }
        }

        SQL += ")";
        return this.exécutionUpdate(SQL);

    }

//Fungsi eksekusi query update
    public String queryUpdate(String nomTable, String[] nomColonne, String[] contenuTableau, String état) {

        connexionDatabase();
        int i;
        SQL = "UPDATE " + nomTable + " SET ";

        for (i = 0; i <= nomColonne.length - 1; i++) {
            SQL += nomColonne[i] + "='" + contenuTableau[i] + "'";
            if (i < nomColonne.length - 1) {
                SQL += ",";
            }
        }

        SQL += " WHERE " + état;
        return this.exécutionUpdate(SQL);

    }

//Fungsi eksekusi query delete
    public String queryDelete(String nomtable) {

        connexionDatabase();
        SQL = "DELETE FROM " + nomtable;
        return this.exécutionUpdate(SQL);

    }

//Overload fungsi eksekusi query delete dengan where
    public String queryDelete(String nomTable, String état) {

        connexionDatabase();
        SQL = "DELETE FROM " + nomTable + " WHERE " + état;
        return this.exécutionUpdate(SQL);

    }
}
