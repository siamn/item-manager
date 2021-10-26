package uk.ac.ucl.servlets;

import uk.ac.ucl.model.Item;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/item.html")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 20,
        maxRequestSize = 1024 * 1024 * 20
)
public class ItemServlet extends HttpServlet
{
    private final Uploader uploader = new Uploader();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        Model model = ModelFactory.getModel();
        int id = Integer.parseInt(request.getParameter("id"));
        Item item = model.getItem(id);
        request.setAttribute("item", item);
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/item.jsp");
        dispatch.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Model model = ModelFactory.getModel();
        // get attributes of the item through request parameters
        String name = request.getParameter("name");
        String value = request.getParameter("val");
        String type = request.getParameter("type");
        if (type.equals("image"))
        {
            value = uploader.upload(request);
        }

        Item item = model.addItem(name, value, type);
        ModelFactory.updateFile();  // a form submission means a change has occurred, so the updated model is saved
        request.setAttribute("item", item);
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/item.jsp");
        dispatch.forward(request, response);
    }

}