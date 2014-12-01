import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;


public class FileParser {


	private static Connection conn = null;
	private static Statement stmt = null;

	// Constructure with JDBC connection
	public static void initialize()
	{
		try{
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn = DriverManager.getConnection("jdbc:mysql://localhost/CloudServices","root","pulsor150");
			System.out.println("COnn:"+conn);
		}
		catch (SQLException e) {
			e.printStackTrace();

		}

		System.out.println("Connection open to MYSQL");
	}
	public static void main(String args[]) 
	{
		try{
			BufferedReader br = new BufferedReader(new FileReader("C:/part-r-00000"));
			String line;
			while ((line = br.readLine()) != null) 
			{
				String ops[]=line.toString().split("\\s+");

				StringBuilder builder=new StringBuilder();
				for(int i=1;i<ops.length;i++)
				{

					builder.append(ops[i]);
				}

				String outcome=builder.toString();
				System.out.println(ops[0]);
				System.out.println(outcome);

				///Code for mysql
				initialize();
				ResultSet rs;




				stmt = conn.createStatement();
				String date=new java.util.Date().toString();
				String query1 = "INSERT INTO `cloudservices`.`tfid`  VALUES ('" +ops[0] + "', '" +outcome+"');";
				System.out.println(query1);
				stmt.executeUpdate(query1);

				shutdown();

			}
			br.close();
		}



		catch(Exception e)
		{
			e.printStackTrace();	
		}


	}
	
	
	public static  void shutdown()
	{
		try{
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
