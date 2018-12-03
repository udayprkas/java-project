import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.* ; 
import javax.servlet.http.HttpSession;
/**
 *
 * @author lenovo
 */
@WebServlet(urlPatterns = {"/contact_info"})
public class contact_info extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) { 
            String pickup = request.getParameter("fullname");
            String dropup = request.getParameter("phone1");
            String stopover = request.getParameter("Email");
            String date1 = request.getParameter("subject");
            String time = request.getParameter("message");
            

            Class.forName("org.apache.derby.jdbc.ClientDriver");  
            Connection con=DriverManager.getConnection("jdbc:derby://localhost:1527/data","root","qwerty");  
            Statement stmt = con.createStatement();      
            String query = "insert into contact values('"+pickup+"','"+dropup+"','"+stopover+"','"+date1+"','"+time+"')";             
            String query2 = "select * from contact"; 
            ResultSet rs = stmt.executeQuery(query2);
            HttpSession session = request.getSession();
           int count =0;
            int flag =0;
            while(rs.next()){
                if(pickup.equals(rs.getString(2))){
                    count++;                   
                    out.println("username already exists!!");
                    break;
                }
            }            
            if(count==0){
                stmt.executeUpdate(query);
                out.println("Sucessfully inserted");
                
                //out.println(password);
                flag =1;
//                session.setAttribute("username",user);
//                session.setAttribute("password","password");   
            }                        
            if(flag!=1 && count!=1){  
                out.println("success");
                out.println("failed to insert the data!!");
            }  
        }
        catch(Exception e){ System.out.println(e);}  
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {        
        processRequest(request, response);        
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {        
        processRequest(request, response);        
    }
}