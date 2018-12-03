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
@WebServlet(urlPatterns = {"/signup_info"})
public class signup_info extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) { 
            String user = request.getParameter("email");
            String password = request.getParameter("password");
            String first = request.getParameter("firstname");
            String last = request.getParameter("lastname");
            String country = request.getParameter("country");
            String phone = request.getParameter("mobile");
            String date = request.getParameter("date");
            Class.forName("org.apache.derby.jdbc.ClientDriver");  
            Connection con=DriverManager.getConnection("jdbc:derby://localhost:1527/data","root","qwerty");  
            Statement stmt = con.createStatement();      
            String query = "insert into login_data values('"+first+"','"+last+"','"+country+"','"+user+"','"+phone+"','"+date+"','"+password+"')";             
            String query2 = "select * from login_data"; 
            ResultSet rs = stmt.executeQuery(query2);
            HttpSession session = request.getSession();
           int count =0;
            int flag =0;
            while(rs.next()){
                if(user.equals(rs.getString(4))){
                    count++;                   
                    out.println("username already exists!!");
                    break;
                }
            }            
            if(count==0){
                stmt.executeUpdate(query);
         //       out.println("Sucessfully inserted");
                
            //    out.println(user);
                //out.println(password);
                flag =1;
                response.sendRedirect("index.html");
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