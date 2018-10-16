package automationtests.assertions;

/**
 * Created by ford.arnett on 8/30/18
 */
public enum AssertionCategories {
    Home("Home"),
    Offers("Offers");


    private String category;

    AssertionCategories(String category) {
        this.category = category;
    }

    public String toString() {
        return category;
    }
}
