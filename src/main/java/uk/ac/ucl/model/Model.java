package uk.ac.ucl.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Model
{

    private int idCount = 0;
    private ItemList currentList;

    /* Items and ItemLists in these hash maps are stored under their id to provide fast access to items using
    their unique identifier. */
    private final HashMap<Integer, Item> items = new HashMap<>();
    private final HashMap<Integer, ItemList> lists = new HashMap<>();

    public Model()
    {
        ItemList main = new ItemList("main", "none", "itemList", idCount);
        items.put(idCount, main);
        lists.put(idCount, main);
        idCount++;
        currentList = main;
    }

    public Model(int idCount)
    {
        this.idCount = idCount;
    }


    public int getCurrentListID()
    {
        return currentList.getID();
    }

    public String getCurrentList()
    {
        return currentList.getName();
    }


    public void rename(String newName)
    {
        ItemList list = getList(currentList.getValue());
        if (list != null)
        {
            list.deleteItem(currentList.getName());
            currentList.setName(newName);
            list.addItem(currentList);
        }
    }

    public ItemList getList(String id)
    {
        return lists.get(Integer.parseInt(id));
    }

    public Item addItem(String name, String value, String type)
    {
        Item item = new Item(name, value, type, idCount);
        items.put(idCount, item);
        currentList.addItem(item);
        idCount++;
        return item;
    }


    public void addList(String name)
    {
        ItemList item = new ItemList(name, String.valueOf(currentList.getID()), "itemList", idCount);
        lists.put(idCount, item);
        items.put(idCount, item);
        currentList.addItem(item);
        idCount++;
    }

    // addList variant used for when reading from files
    public void addList(ItemList itemList)
    {
        lists.put(itemList.getID(), itemList);
        items.put(itemList.getID(), itemList);
    }

    // The 0th item is the special "main" itemList which contains all other lists and items
    public void removeItem(Item item)
    {
        if (item.getID() != 0)
        {
            items.remove(item.getID());
            currentList.deleteItem(item.getName());
        }
    }

    public Item getItem(int id)
    {
        return items.get(id);
    }

    public void setCurrentList(String id)
    {
        int idNum = Integer.parseInt(id);
        if (lists.containsKey(idNum))
        {
            currentList = lists.get(idNum);
        } else
        {
            currentList = lists.get(0);
        }
    }

    public LinkedHashMap<String, Item> getItems()
    {
        return currentList.getItems();
    }

    public HashMap<Integer, Item> getAllItems()
    {
        return items;
    }


    public ArrayList<Item> search(String keyword)
    {
        ArrayList<Item> results = new ArrayList<>();
        for (ItemList list : lists.values())
        {
            results.add(list.getItem(keyword));
        }
        return results;
    }

    public ArrayList<Item> deepSearch(String keyword)
    {
        ArrayList<Item> results = new ArrayList<>();
        for (Item item : items.values())
        {
            if (item.getName().contains(keyword))
            {
                results.add(item);
            } else if (item.getType().equals("text") || item.getType().equals("url"))
            {
                if (item.getValue().contains(keyword))
                {
                    results.add(item);
                }
            }
        }
        return results;
    }

    /* deleteList does not delete any of the lists nested inside the current item list by design.
    The nested lists and the items inside those lists can still be accessed by the user by using the normal (deep)
    search function. */
    public void deleteList()
    {
        if (currentList.getID() != 0)
        {
            ItemList list = currentList;
            lists.remove(list.getID());
            items.remove(list.getID());
            if (lists.containsKey(Integer.parseInt(list.getValue())))
            {
                setCurrentList(list.getValue());
                currentList.deleteItem(list.getName());
            } else
            {
                setCurrentList("0");
            }
        }
    }

    public int getIDCount()
    {
        return idCount;
    }

    public HashMap<Integer, ItemList> getLists()
    {
        return lists;
    }

}
