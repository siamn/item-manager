package uk.ac.ucl.model;

import uk.ac.ucl.backend.FileHandler;


public class ModelFactory
{
    private static Model model;

    public static Model getModel()
    {
        if (model == null)
        {
            model = new FileHandler().readFile();
        }
        return model;
    }

    public static void updateFile()
    {
        new FileHandler().writeFile(model);
    }
}
