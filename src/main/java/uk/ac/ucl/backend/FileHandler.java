package uk.ac.ucl.backend;

import uk.ac.ucl.model.Item;
import uk.ac.ucl.model.ItemList;
import uk.ac.ucl.model.Model;

import java.io.*;

public class FileHandler
{

    /* data.txt file is assumed to not be accessible to users
       thus the readFile function expects that the file is either completely empty or that it contains data written
       to it by writeFile */
    public void writeFile(Model model)
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("./data/data.txt"));
            writer.write(String.valueOf(model.getIDCount()));
            writer.newLine();
            for (ItemList list : model.getLists().values())
            {
                writeItem(list, writer);
                writer.write("[");
                writer.newLine();
                for (Item item : list.getItems().values())
                {
                    writeItem(item, writer);
                }
                writer.write("]");
                writer.newLine();
            }
            writer.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /* Each item property is written line by line instead of using a delimiter and writing them on a single line,
    as they can restrict the characters that the user can type. For example, if a comma was used as a delimiter
    and a text item contained commas in its "value", then calling split on the line for that item would return
    a string array of length greater than 4, resulting in incorrect item properties being used to create new items. */

    private void writeItem(Item item, BufferedWriter writer) throws IOException
    {
        writer.write(item.getName());
        writer.newLine();
        writer.write(item.getValue());
        writer.newLine();
        writer.write(item.getType());
        writer.newLine();
        writer.write(String.valueOf(item.getID()));
        writer.newLine();
    }

    public Model readFile()
    {
        Model model;
        boolean valid;
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader("./data/data.txt"));
            String line = reader.readLine();
            if (line != null)
            {
                model = new Model(Integer.parseInt(line));
                valid = readLists(reader, model);
            } else return new Model();
            reader.close();

        } catch (NumberFormatException | IOException e)
        {
            e.printStackTrace();
            System.out.println("Error detected in file.");
            return new Model();
        }
        if (valid) return model;
        else return new Model();
    }

    private boolean readLists(BufferedReader reader, Model model) throws IOException, NumberFormatException
    {
        String line;
        while ((line = reader.readLine()) != null)
        {
            String name = line;
            String value = reader.readLine();
            String type = reader.readLine();
            String idString = reader.readLine();
            int id = Integer.parseInt(idString);
            ItemList list;
            boolean inModel = true;
            if (!model.getLists().containsKey(id))
            {
                list = new ItemList(name, value, type, id);
                inModel = false;
            } else
            {
                list = model.getList(idString);
            }
            line = reader.readLine();
            if (line.equals("["))
            {
                readItems(reader, model, list);
            }
            if (!inModel)
            {
                model.addList(list);
            }
        }
        return checkModel(model);
    }

    private boolean checkModel(Model model)
    {
        if (model.getList("0") != null)
        {
            model.setCurrentList("0");
            return true;
        } else
        {
            System.out.println("'main' list not present in file.");
            return false;
        }
    }

    private void readItems(BufferedReader reader, Model model, ItemList list) throws IOException, NumberFormatException
    {
        String line;
        while (!(line = reader.readLine()).equals("]"))
        {
            String name = line;
            String value = reader.readLine();
            String type = reader.readLine();
            String idString = reader.readLine();
            int itemID = Integer.parseInt(idString);
            Item item = new Item(name, value, type, itemID);
            list.addItem(item);
            model.getAllItems().put(itemID, item);
        }
    }

}
