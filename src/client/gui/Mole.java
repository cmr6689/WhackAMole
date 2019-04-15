package client.gui;

/**
 * Class that represents the moles with an integer
 * id and a boolean value representing up or down
 * @author Cameron Riu
 * @author Michael Madden
 */
public class Mole {
    private int id;
    private boolean up;

    /**
     * Constructor to make a mole
     * @param id - id of the mole
     * @param up - if true then the mole is up
     */
    public Mole(int id, boolean up) {
        this.id = id;
        this.up = false;
    }

    /**
     * Return the mole's id
     * @return id number
     */
    public int getId() {
        return this.id;
    }

    /**
     * if the mole is up
     * @return true if mole is up
     */
    public boolean isUp() {
        return this.up;
    }

    /**
     * Set the mole to be up
     */
    public void moleUp() {
        this.up = true;
    }

    /**
     * Set the mole to be down
     */
    public void moleDown() {
        this.up = false;
    }
}
