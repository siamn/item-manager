package uk.ac.ucl.model;

import java.util.LinkedHashMap;

public class ItemList extends Item
{
    /* LinkedHashMap used as it provides O(1) search, insertion, deletion and access on average and also stores
    items in the order of insertion. Items in an ItemList object are stored under string keys using their name,
    to provide fast exact match search, which is useful when there is a very large number of items
    stored in the program and the user requires results quickly. */

    private LinkedHashMap<String, Item> itemList = new LinkedHashMap<>();

    public ItemList(String name, String value, String type, int id)
    {
        super(name, value, type, id);
    }

    public void addItem(Item item)
    {
        itemList.put(item.getName(), item);
    }

    public void deleteItem(String name)
    {
        itemList.remove(name);
    }


    public LinkedHashMap<String, Item> getItems()
    {
        return itemList;
    }


    public Item getItem(String name)
    {
        return itemList.get(name);
    }
}
