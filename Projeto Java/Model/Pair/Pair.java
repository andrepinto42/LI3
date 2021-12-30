package Model.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pair<U, V>
{
    private  U first;       // the first field of a pair
    private  V second;      // the second field of a pair
 
    // Constructs a new pair with specified values
    private Pair(U first, V second)
    {
        this.first = first;
        this.second = second;
    }
 
    @Override
    // Checks specified object is "equal to" the current object or not
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
 
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
 
        Pair<?, ?> pair = (Pair<?, ?>) o;
 
        // call `equals()` method of the underlying objects
        if (!first.equals(pair.first)) {
            return false;
        }
        return second.equals(pair.second);
    }
 
    @Override
    // Computes hash code for an object to support hash tables
    public int hashCode()
    {
        // use hash codes of the underlying objects
        return 31 * first.hashCode() + second.hashCode();
    }
 
    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

    public U getFirst()
    {
        return first;
    }

    public V getSecond()
    {
        return second;
    }

    public void setFirst(U f)
    {
        this.first = f;
    }
    public void setSecond(V s)
    {
        this.second = s;
    }
 
    // Factory method for creating a typed Pair immutable instance
    public static <U, V> Pair <U, V> make(U a, V b)
    {
        // calls private constructor
        return new Pair<>(a, b);
    }
}

