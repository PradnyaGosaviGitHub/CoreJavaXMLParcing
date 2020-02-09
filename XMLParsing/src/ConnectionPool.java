import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionPool extends Thread{
	File file;
	
	public ConnectionPool(File file) {
		// TODO Auto-generated constructor stub
		this.file = file;
	}
	

	
	
    @Override
	public void run() {
		// TODO Auto-generated method stub
    	try {
			createTable();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
   




	Connection conn = null;
    public Connection getJdbcConnection() throws SQLException,ClassNotFoundException {
        //Class.forName("com.mysql.jdbc.Driver");
    	System.out.println("In Connection Pool");
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/world","root","pradnya27");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;

    }
    
    public void createTable() throws ClassNotFoundException, SQLException {
		Connection conn = getJdbcConnection();
		Statement st = conn.createStatement();
		String fileName = file.getName();
		int i=0;
		String fileNameNum = fileName+i;
//		System.out.println("Filename : "+fileName);	
		int val = st.executeUpdate("CREATE TABLE "+fileName+" (FName VARCHAR(75), dob VARCHAR(75), salary VARCHAR(75))");
		i++;
//		System.out.println(val);
		System.out.println("Table Created!!!");
		conn.close();
//		System.out.println("Connection Closed!!!");
	}
    
    public void insertData(String name, String DOB, String salary) throws ClassNotFoundException, SQLException {
		Connection conn = getJdbcConnection();
		String fileName = file.getName();
//		System.out.println("Filename : "+fileName);
		int in=0;
		String fileNameNum = fileName+in;
		PreparedStatement ps = conn.prepareStatement("insert into "+fileName+" values (?,?,?)");
		in++;
		ps.setString(1, name);
		ps.setString(2, DOB);
		ps.setString(3,salary);
		
		int i = ps.executeUpdate();
//		System.out.println(i+" Values Inserted!!!");
		conn.close();
//		System.out.println("Connection Closed!!!");
		
		
	}
    
    public void deleteData() throws ClassNotFoundException, SQLException {
		Connection conn = getJdbcConnection();
		PreparedStatement ps = conn.prepareStatement("delete from Student where Id = 3");
		int i = ps.executeUpdate();
		System.out.println(i+" Record Inserted");
		conn.close();
		System.out.println("Connection Closed!!!");
	}
    
    public void method() {
    	try {
			createTable();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
    	File file = new File("f");
        ConnectionPool cp = new ConnectionPool(file);
//        cp.getJdbcConnection();
//        cp.insertData();
//        cp.deleteData();
    }
    }


