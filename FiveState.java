package introai;
// This program defines a state for 3x2 five length puzzle
public class FiveState {
    
    private String name;
    private String value;
    private int depth;
    
    public FiveState(String name, String value, int depth) {
        setName(name);          // XXXXXX format of string name
        setValue(value);        // the up(U), down(D) etc movement used to create it N for start State
        setDepth(depth);        // depth level, 0 for startState
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
    
    /**
     * @return the depth
     */
    public int getDepth() {
        return depth;
    }

    /**
     * @param depth the depth to set
     */
    public void setDepth(int depth) {
        this.depth = depth;
    }
    @Override
    public String toString() {
        return("Name: " + getName() + "Value: " + getValue());
    }
}
