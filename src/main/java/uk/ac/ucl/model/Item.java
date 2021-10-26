package uk.ac.ucl.model;

public class Item
{
    private String name;
    private String value;
    private final String type;
    private final int id;

    /* Item types are itemList, url, text and image.
    Value attribute of list item objects contains the id of the item list containing that list in string form.
    For text and URL items, value contains the text or URL string.
    For images, value contains the relative path of the image after it is uploaded.
    ID attribute exists to provide a unique identifier for items so that items in different item lists can have
    the same name. */

    public Item(String name, String value, String type, int id)
    {
        this.name = name;
        this.value = value;
        this.type = type;
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public String getValue()
    {
        return value;
    }

    public String getType()
    {
        return type;
    }

    public int getID()
    {
        return id;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
