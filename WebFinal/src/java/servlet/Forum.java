package servlet;

import classi.Gruppo;
import classi.Post;
import classi.Utente;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.MetodiGruppi;
import services.MetodiPost;
import services.MetodiUtenti;

public class Forum extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter out = response.getWriter();
       
            String grid = request.getParameter("id");
            
            
            ArrayList<Post> listaPost = new ArrayList<Post>();
            
            Gruppo g = MetodiGruppi.searchGruppoById((Integer.parseInt(grid)));
            Utente u = MetodiUtenti.searchUtenteByID(g.getProprietario());
            listaPost = MetodiPost.listaDeiPost(g);
            
            out.println("<!DOCTYPE html>");
            out.println("<html> ");
            out.println("    <head> ");
            out.println("        <title>Gruppo</title> ");
            out.println("        <meta charset=\"UTF-8\">");
            out.println("        <meta name=\"viewport\" content=\"width=device-width\">");
            out.println("        <link rel=\"stylesheet\" type=\"text/css\" href=\"Css/style.scss\" media=\"screen\" />");
            out.println("    </head>");
            out.println("    <body>");
            out.println("        <div class=\"container\">");
            out.println("            <div class=\"maintitle\">");
            out.println("                "+g.getNome()+"");
            out.println("            </div>");
            out.println("            <div class=\"imgdiv\">");
            out.println("                <img class=\"adminimg\" src=\"UploadedAvatar" +"/" + u.getAvatar()+ "\">");
            out.println("                <div class=\"imgdescriptor\">");
            out.println("                    <b>Admin</b><br>");
            out.println("                    "+u.getUsername()+"");
            out.println("                </div>");
            out.println("                <div>");
            out.println("                    <button class=\"custombtn\">Fine</button>");
            out.println("                </div>");
            out.println("            </div>");
            out.println("            <div class=\"newscontainer\">");

            Iterator i = listaPost.iterator(); 
            while(i.hasNext()) {
                Post p = (Post) i.next();
                Utente utentePost =  MetodiUtenti.searchUtenteByID(p.getUtente());
                out.println("                <div class=\"post\">");
                out.println("                    <div class=\"postinfo\">");
                out.println("                        <p>"+utentePost.getUsername()+"</p>");
                out.println("                        <img class=\"adminimg\" src=\"UploadedAvatar" +"/" + u.getAvatar()+ "\">");
                out.println("                        <p>"+p.getData()+"</p>");
                out.println("                    </div>");
                out.println("                    <div class=\"posttext\">");
                out.println("                        "+p.getTesto()+"");
                out.println("                    </div>");
                out.println("                </div>");
            }
            out.println("            </div>");
            
            
            
            //aggiungere un post
            out.println("            <form action=\"addPost\">"); 
            out.println("               <input name = \""+g.getID()+"\"type=\"submit\" value=\"aggiungipost\">");
            out.println("            </form>");
            
            //eliminare il gruppo
            out.println("            <form action=\"eliminaGruppo\" method=\"POST\">"); 
            out.println("               <input name = \""+g.getID()+"\"type=\"submit\" value=\"elimina gruppo\">");
            out.println("            </form>");
             
             
            out.println("        </div>");
            out.println("    </body>");
            out.println("</html>");   
            
    }
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {processRequest(request, response);
        } catch (SQLException ex) {Logger.getLogger(Forum.class.getName()).log(Level.SEVERE, null, ex);}
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {processRequest(request, response);
        } catch (SQLException ex) {Logger.getLogger(Forum.class.getName()).log(Level.SEVERE, null, ex);}
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
