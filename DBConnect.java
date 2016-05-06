package assignment5;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class DBConnect {
	/**
	 * This class demonstrates how to connect to MySQL and run some basic commands.
	 * 
	 * In order to use this, you have to download the Connector/J driver and add
	 * its .jar file to your build path.  You can find it here:
	 * 
	 * http://dev.mysql.com/downloads/connector/j/
	 * 
	 * You will see the following exception if it's not in your class path:
	 * 
	 * java.sql.SQLException: No suitable driver found for jdbc:mysql://localhost:3306/
	 * 
	 * To add it to your class path:
	 * 1. Right click on your project
	 * 2. Go to Build Path -> Add External Archives...
	 * 3. Select the file mysql-connector-java-5.1.24-bin.jar
	 *    NOTE: If you have a different version of the .jar file, the name may be
	 *    a little different.
	 *    
	 * The user name and password are both "root", which should be correct if you followed
	 * the advice in the MySQL tutorial. If you want to use different credentials, you can
	 * change them below. 
	 * 
	 * You will get the following exception if the credentials are wrong:
	 * 
	 * java.sql.SQLException: Access denied for user 'userName'@'localhost' (using password: YES)
	 * 
	 * You will instead get the following exception if MySQL isn't installed, isn't
	 * running, or if your serverName or portNumber are wrong:
	 * 
	 * java.net.ConnectException: Connection refused
	 */
	/** The name of the MySQL account to use (or empty for anonymous) */
	private final String userName = "root";

	/** The password for the MySQL account (or empty for anonymous) */
	private final String password = "root";

	/** The name of the computer running MySQL */
	private final String serverName = "localhost";

	/** The port of the MySQL server (default is 3306) */
	private final int portNumber = 3306;

	/** The name of the database we are testing with (this default is installed with MySQL) */
	private final String dbName = "oopda5_mt15124";
	
	/** The name of the table we are testing with */
	private final String tableName = "jdbc_test";
	
	/**
	 * Get a new database connection
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", this.userName);
		connectionProps.put("password", this.password);

		conn = DriverManager.getConnection("jdbc:mysql://"
				+ this.serverName + ":" + this.portNumber + "/" + this.dbName,
				connectionProps);

		return conn;
	}

	/**
	 * Run a SQL command which does not return a recordset:
	 * CREATE/INSERT/UPDATE/DELETE/DROP/etc.
	 * 
	 * @throws SQLException If something goes wrong
	 */
	public boolean executeUpdate(Connection conn, String command) throws SQLException {
	    Statement stmt = null;
	    try {
	        stmt = conn.createStatement();
	        stmt.executeUpdate(command); // This will throw a SQLException if it fails
	        return true;
	    } finally {

	    	// This will run whether we throw an exception or not
	        if (stmt != null) { stmt.close(); }
	    }
	}
	
	/**
	 * Connect to MySQL and do some stuff.
	 */
	public void run() {

		// Connect to MySQL
		Connection conn = null;
		
		try {
			conn = this.getConnection();
			System.out.println("Connected to database");
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return;
		}

		// Create a table
		/*try {
		    String createString =
			        "CREATE TABLE IF NOT EXISTS " + this.tableName + " ( " +
			        "ID INTEGER NOT NULL, " +
		 	        "NAME varchar(40) NOT NULL, " +
			        "STREET varchar(40) NOT NULL, " +
			        "CITY varchar(20) NOT NULL, " +
			        "STATE char(2) NOT NULL, " +
			        "ZIP char(5), " +
			        "PRIMARY KEY (ID))";
			this.executeUpdate(conn, createString);
			System.out.println("Created a table");
	    } catch (SQLException e) {
			System.out.println("ERROR: Could not create the table");
			e.printStackTrace();
			return;
		}*/
		
		// Drop the table
		/*try {
		    String dropString = "DROP TABLE " + this.tableName;
			this.executeUpdate(conn, dropString);
			System.out.println("Dropped the table");
	    } catch (SQLException e) {
			System.out.println("ERROR: Could not drop the table");
			e.printStackTrace();
			return;
		}*/
		
	/*try {
	    String dropString = "Drop table " + this.tableName;
		this.executeUpdate(conn, dropString);
		System.out.println("Dropped the table");
    } catch (SQLException e) {
		System.out.println("ERROR: Could not drop the table");
		e.printStackTrace();
		return;
	}*/
		
	}
	public void runquery() throws IOException, SQLException {

		Connection conn = null;
		
		try {
			conn = this.getConnection();
			System.out.println("Connected to database");
		}
		catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return;}
		//Reading the Actor.txt file
		FileInputStream file = new FileInputStream("C:\\Users\\Prateek\\Downloads\\actor.txt");
		DataInputStream data= new DataInputStream(file);
		BufferedReader read = new BufferedReader(new InputStreamReader(data));
		String line;
		while ((line = read.readLine()) != null)
		{
			String SerialNumber = "";
			String name = "";
			String lname = "";
			String gender = "";
			line = line.replace("'", "\'");
		   //System.out.println(line);
		   String splitted[] = line.split("\\|");
		   int size = splitted.length;
		   for(int i = 0;i<size;i++){
		   if(i==0) SerialNumber = splitted[0]; //System.out.println(splitted[0]);
		   if(i==1) name = splitted[1];
		   if(i==2) lname = splitted[2];
		   if(i==3) gender = splitted[3];
		   }
		   String insertString = "insert into actor values ("+SerialNumber+",\'"+name+"\',\'"+lname+"\',\'"+gender+"\');";
		   this.executeUpdate(conn, insertString);
		   //System.out.println(SerialNumber + " " + name + " " + MovieName + " " + gender); // just for debugging
		}
		read.close();
		
		//Reading the movie.txt file
				FileInputStream file4 = new FileInputStream("C:\\Users\\Prateek\\Downloads\\movie.txt");
				DataInputStream data4= new DataInputStream(file4);
				BufferedReader read4= new BufferedReader(new InputStreamReader(data4));
				String line4;
				while ((line4 = read4.readLine()) != null)
				{
					line4 = line4.replace("'", "\'");
					String Id = "";
					String Name = "";
					String Year = "";
				   //System.out.println(line2);
					String splitted[] = line4.split("\\|");
					int size = splitted.length;
					for(int i = 0;i<size;i++){
						if(i==0)Id = splitted[0]; //System.out.println(splitted[0]);
						if(i==1)Name = splitted[1];
						if(i==2)Year = splitted[2];
					}  
					String insertString = "insert into movie values ("+Id+",\'"+Name+"\',\'"+Year+"\');";
					this.executeUpdate(conn, insertString);
				   //System.out.println(Id + " " + Name + " " + Year); // just for debugging
				}
				read4.close();
		
				//Reading the directors.txt file
				FileInputStream file2 = new FileInputStream("C:\\Users\\Prateek\\Downloads\\directors.txt");
				DataInputStream data2= new DataInputStream(file2);
				BufferedReader read2 = new BufferedReader(new InputStreamReader(data2));
				String line2;
				while ((line2 = read2.readLine()) != null)
				{
					String SerialNumber = "";
					String FName = "";
					String LName = "";
				   //System.out.println(line2);
					line2 = line2.replace("'","\'");
				    String splitted[] = line2.split("\\|");
			int size = splitted.length;
			for(int i = 0;i<size;i++){
		   if(i==0){SerialNumber = splitted[0];} //System.out.println(splitted[0]);
		   if(i==1){FName = splitted[1];}
		   if(i==2){LName = splitted[2];}
			}
			String insertString = "insert into directors values ("+SerialNumber+",\'"+FName+"\',\'"+LName+");";
		   this.executeUpdate(conn, insertString);
		   //System.out.println(SerialNumber + " " + FName + " " + LName); // just for debugging
		}
		
		read2.close();
		

		//Reading the casts.txt file
		FileInputStream file1 = new FileInputStream("C:\\Users\\Prateek\\Downloads\\casts.txt");
		DataInputStream data1= new DataInputStream(file1);
		BufferedReader read1 = new BufferedReader(new InputStreamReader(data1));
		String line1;
		while ((line1 = read1.readLine()) != null)
		{
			String SerialNumber = "";
			String MovieId = "";
			String Role = "";
			line1 = line1.replace("'", "\'");
		   //System.out.println(line1);
		   String splitted[] = line1.split("\\|"); 
		   int size = splitted.length;
		   for(int i = 0;i<size;i++)
		   {
			   if(i==0) SerialNumber = splitted[0]; //System.out.println(splitted[0]);
			   if(i==1) MovieId = splitted[1];
			   if(i==2) Role = splitted[2];
		   }
		   String insertString = "insert into casts values ("+SerialNumber+","+MovieId+",\'"+Role+"\');";
		   this.executeUpdate(conn, insertString);
		   //System.out.println(SerialNumber + " " + MovieId + " " + Role); // just for debugging
		}
		read1.close();
		
		//Reading the genre.txt file
			FileInputStream file3 = new FileInputStream("C:\\Users\\Prateek\\Downloads\\genre.txt");
			DataInputStream data3= new DataInputStream(file3);
			BufferedReader read3 = new BufferedReader(new InputStreamReader(data3));
			String line3;
			while ((line3 = read3.readLine()) != null)
			{
				line3 = line3.replace("'", "\'");
				String MId = "";
				String Genre = "";
			   //System.out.println(line2);
			   String splitted[] = line3.split("\\|"); 
			   int size = splitted.length;
				for(int i = 0;i<size;i++){
					if(i==0)MId = splitted[0]; //System.out.println(splitted[0]);
					if(i==1)Genre = splitted[1];
				}
				String insertString = "insert into genre values ("+MId+",\'"+Genre+"\');";
				   this.executeUpdate(conn, insertString);
			   //System.out.println(MId + " " + Genre); // just for debugging
			}
			read3.close();
			
		
		
		//Reading the movie_directors.txt file
		FileInputStream file5 = new FileInputStream("C:\\Users\\Prateek\\Downloads\\movie_directors.txt");
		DataInputStream data5= new DataInputStream(file5);
		BufferedReader read5 = new BufferedReader(new InputStreamReader(data5));
		String line5;
		while ((line5 = read5.readLine()) != null)
		{
			String MId = "";
			String DId = "";
		   //System.out.println(line2);
		    line5 = line5.replace("'", "\'");
		   String splitted[] = line5.split("\\|");
		   int size= splitted.length;
		   for(int i = 0;i<size;i++){
			   if(i==0)DId = splitted[0]; //System.out.println(splitted[0]);
			   if(i==1)MId = splitted[1];
		   }
		   String insertString = "insert into movie_directors values ("+DId+","+MId+");";
		   this.executeUpdate(conn, insertString);
		   //System.out.println(MId + " " + Genre); // just for debugging
		}
		read5.close();
		// Connect to MySQL
		/*Connection conn = null;
		Statement statement = null;
		String dropString = "Select * from " + this.tableName;
		try {
			conn = this.getConnection();
			statement = conn.createStatement();
			System.out.println("Connected to database again");
			ResultSet rs = statement.executeQuery(dropString);
			while (rs.next()) {

				String userid = rs.getString("NAME");
				String username = rs.getString("STREET");

				System.out.println("Name : " + userid);
				System.out.println("street : " + username);

			}

		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return;
		}*/

		
		// Drop the table
		/*try {
		    String dropString = "DROP TABLE " + this.tableName;
			this.executeUpdate(conn, dropString);
			System.out.println("Dropped the table");
	    } catch (SQLException e) {
			System.out.println("ERROR: Could not drop the table");
			e.printStackTrace();
			return;
		}*/
	
	
	
	}
	/**
	 * Connect to the DB and do some stuff
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws IOException, SQLException {
		DBConnect app = new DBConnect();
		app.run();
		app.runquery();
	}

}
