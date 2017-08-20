/**
 * Simple Key value pair class
 * Created by Oludare Balogun on 8/20/2017.
 */
public class KeyValuePair<E,K extends Comparable<K>> {

    private K key;
    private E value;

    /**
     * Constructs a new key-value pair
     * @param key key of pair
     * @param value vallue of pair
     */
    public KeyValuePair(K key, E value){
        this.key = key;
        this.value = value;
    }

    /**
     * Return the key of the pair
     * @return the key of the pair
     */
    public K getKey(){
        return key;
    }

    /**
     * Return the value of the pair
     * @return The value of the pair
     */
    public  E getValue(){
        return value;
    }
}
