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
import java.util.HashMap;
import java.util.LinkedHashMap;

@WebServlet("/list.html")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 20,
        maxRequestSize = 1024 * 1024 * 20
)
public class ItemListServlet extends HttpServlet
{

    private final Uploader uploader = new Uploader();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        Model model = ModelFactory.getModel();
        String id = request.getParameter("id");
        if (id != null)
        {
            model.setCurrentList(id);
        }

        HashMap<String, Item> items = model.getItems();
        request.setAttribute("items", items);
        request.setAttribute("name", model.getCurrentList());
        String jsp = "/itemList.jsp";
        if (model.getCurrentListID() == 0)
        {
            jsp = "/main.jsp";
        }
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher(jsp);
        dispatch.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Model model = ModelFactory.getModel();
        /* check parameters of the request and determine if the change is related to an item or an item list
        and then perform relevant actions */
        String jsp = checkParameters(request, model);
        ModelFactory.updateFile(); // a form submission means a change has occurred, so the updated model is saved
        LinkedHashMap<String, Item> items = model.getItems();
        request.setAttribute("items", items);
        request.setAttribute("name", model.getCurrentList());
        if (model.getCurrentListID() == 0)
        {
            jsp = "/main.jsp";  // custom jsp is called for the special main item list, which must always exist
        }
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher(jsp);
        dispatch.forward(request, response);
    }

    private String checkParameters(HttpServletRequest request, Model model)
    {
        String jsp = "/itemList.jsp";
        if (!checkItem(request, model))
        {
            jsp = checkItemList(request, model);
        }
        return jsp;
    }

    /* Returns whether a change was made related to items. This is used to determine whether to call the checkItemList
    method. */
    private boolean checkItem(HttpServletRequest request, Model model)
    {
        String edit = request.getParameter("edit");
        String remove = request.getParameter("remove");
        HashMap<Integer, Item> allItems = model.getAllItems();
        boolean changed = false;
        if (edit != null)
        {
            Item item = allItems.get(Integer.parseInt(edit));
            String value = request.getParameter("val");
            System.out.println("File : " + value);
            if (item.getType().equals("image"))
            {
                try
                {
                    value = uploader.upload(request);
                    System.out.println(value);
                } catch (IOException | ServletException e)
                {
                    // if an error occurs when trying to upload the image, the existing is not changed.
                    value = item.getValue();
                    e.printStackTrace();
                }
            }
            item.setValue(value);
            changed = true;
        } else if (remove != null)
        {
            Item item = allItems.get(Integer.parseInt(remove));
            model.removeItem(item);
            changed = true;
        }
        return changed;
    }

    private String checkItemList(HttpServletRequest request, Model model)
    {
        String name = request.getParameter("name");
        String action = request.getParameter("action");
        String jsp = "/itemList.jsp?id=";
        if (action != null && (!name.equals("main")) && name.length() > 0)
        {
            jsp = checkAddEdit(request, model, jsp, name, action);
        } else
        {
            // if action is null (there is no add or edit request), then the delete request has been made
            String id = request.getParameter("delete");
            System.out.println(id);
            if (id != null)
            {
                model.deleteList();
            }
            jsp = "/itemList.jsp";
        }
        return jsp;

    }

    private String checkAddEdit(HttpServletRequest request, Model model, String jsp, String name, String action)
    {
        String newName = request.getParameter("val");
        if (action.equals("add"))
        {
            model.addList(name);
            jsp = jsp + name;
        } else if (action.equals("edit") && newName.length() > 0)
        {
            model.rename(newName);
            jsp = jsp + newName;
        }
        return jsp;
    }

}