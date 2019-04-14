package client.gui;

public class Mole {
    private int id;
    private boolean up;

    public Mole(int id, boolean up) {
        this.id = id;
        this.up = false;
    }

    public int getId() {
        return this.id;
    }

    public boolean isUp() {
        return this.up;
    }

    public void moleUp() {
        this.up = true;
    }

    public void moleDown() {
        this.up = false;
    }
}
