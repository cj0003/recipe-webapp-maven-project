package com.boats.recipe.webapp.resources;

public final class Actions {

     //The creation of a new user
    public static final String CREATE_USER = "CREATE_USER";

    //The deletion of a user
    public static final String DELETE_USER = "DELETE_USER";

    //The update of a user
    public static final String UPDATE_USER = "UPDATE USER";

    //Reading the user
    public static final String READ_USER = "READ_USER";

    //The creation of a new recipe
    public static final String CREATE_RECIPE = "CREATE_RECIPE";

    //The deletion of a recipe
    public static final String DELETE_RECIPE = "DELETE_RECIPE";

    //The update of a recipe
    public static final String UPDATE_RECIPE = "UPDATE_RECIPE";

    //Reading the recipe
    public static final String READ_RECIPE = "READ_RECIPE";

    //The listing of all the recipes
    public static final String LIST_RECIPES = "LIST_RECIPES";

    private Actions() {
        // Ensure non-instability
        throw new AssertionError(String.format("No instances of %s allowed.", Actions.class.getName()));
    }


}
