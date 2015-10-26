package fellows.kpcb;

/**
 * Created by Keshav Parwal on 10/9/2015.
 *
 * Synchronized, thread-safe HashMap implementation in Java.
 * Uses external chaining to handle collisions, and resizes
 * the backing array when the load factor is surpassed. To further
 * avoid the problem of fixed size backing arrays, the size is
 * doubled when the load factor is surpassed.
 */
public class HashMap<T> {
    private final double MAX_LOAD_FACTOR = .67;
    private final int DEFAULT_SIZE = 10;
    private final int MINIMUM_SIZE = 4;
    private NodeList<T>[] backingArray;
    private int size;

    public int size() {
        return size;
    }

    /**
     * Constructor that doesn't take any arguments, defaults size of backing array to 10 elements.
     */
    public HashMap() {
        super();
        backingArray = new NodeList[DEFAULT_SIZE];
        size = 0;
    }

    /**
     * @return load factor of the hash map
     */
    public float load() {
        return (float) (size * 1.0 / backingArray.length);
    }

    /**
     * Constructor that takes initial size allocation argument. This does not prevent the hash map from resizing as
     * necessary.
     * @param sizeAllocation You can give the array an initial size allocation.
     */
    public HashMap(int sizeAllocation) {
        super();
        if (sizeAllocation < MINIMUM_SIZE) {
            sizeAllocation = MINIMUM_SIZE; //Anything less would trigger doubling the array upon first use
        }
        backingArray = new NodeList[sizeAllocation];
        size = 0;
    }

    /**
     * Helper method that doubles the size of the backing array whenever the load factor is exceeded. This is an
     * O(n) operation, but it does not effect the amortized runtime of the data structure's operations.
     */
    private void doubleArray() {
        NodeList<T>[] newArray = new NodeList[backingArray.length*2];
        for (NodeList<T> nodeList : backingArray) {
            if (nodeList != null) {
                NodeList.Node currentNode = nodeList.getHead();
                while (currentNode != null) {
                    String key = currentNode.getKey();
                    T data = (T)currentNode.getData(); //it is safe to presume that this will be of type T
                    int newHashCode = key.hashCode() % newArray.length;
                    if (newArray[newHashCode] == null) {
                        newArray[newHashCode] = new NodeList<>();
                    }
                    newArray[newHashCode].set(key, data);
                    currentNode = currentNode.getNext();
                }
            }
        }
        backingArray = newArray;
    }

    /**
     * This is an O(1) operation if the hashing function has few collisions, O(n) when the hashing function has many
     * collisions.
     *
     * This return value is hardcoded to true because the only failure mode of this operation is an OutOfMemoryError,
     * which is in itself an abnormal state that the data structure should not account for.
     * @param key String value that acts as the key in a key-value pair
     * @param value Any object
     * @return true
     */
    public boolean set(String key, T value) {
        if (key == null || value == null || key.equals("")) {
            throw new IllegalArgumentException();
        }
        int hashCode = key.hashCode() % backingArray.length; // Java Strings have an effective in-built hash function

        size++; //pre-increment size to test whether the array needs to be resized
        double loadFactor = size * 1.0 / backingArray.length; // enforce floating point division with 1.0
        synchronized (this) {
            if (loadFactor > MAX_LOAD_FACTOR) {
                doubleArray();
            }
            if (backingArray[hashCode] == null) {
                backingArray[hashCode] = new NodeList<>();
                backingArray[hashCode].set(key, value);
            } else {
                NodeList<T> nodeList = backingArray[hashCode];
                nodeList.set(key, value);
            }
        }
        return true;
    }

    /**
     * This method searches for the key-value pair represented by the key argument, and if it exists in the HashMap
     * it deletes it.
     *
     * This is an O(1) operation when the hashing function has few collisions, O(n) when the hashing function has many
     * collisions.
     * @param key String value that acts as the key in a key-value pair
     * @return itemDeleted, which is true if the key-value pair was successfully deleted.
     */
    public boolean delete (String key) {
        if (key == null || key.equals("")) {
            throw new IllegalArgumentException();
        }
        int hashCode = key.hashCode() % backingArray.length; // Java Strings have an effective in-built hash function
        T deletedObject = null;
        synchronized (this) {
            if (backingArray[hashCode] != null) {
                NodeList<T> nodeList = backingArray[hashCode];
                deletedObject = nodeList.delete(key);
            }
        }
        boolean itemDeleted = deletedObject != null;
        if (itemDeleted) {
            size--;
        }
        return itemDeleted;
    }

    /**
     * This method searches the HashMap for a key, and returns the value associated with the key if the key-value pair
     * is in the HashMap.
     *
     * This is an O(1) operation when the hashing function has few collisions, O(n) when the hashing function has many
     * collisions.
     *
     * @param key String value that acts as the key in a key-value pair
     * @return The object corresponding the the String key
     */
    public T get (String key) {
        if (key == null || key.equals("")) {
            throw new IllegalArgumentException();
        }
        int hashCode = key.hashCode() % backingArray.length; // Java Strings have an effective in-built hash function
        T returnValue = null;
        if (backingArray[hashCode] != null) {
            returnValue = backingArray[hashCode].get(key);
        }
        return returnValue;
    }

    /**
     * Private class representing a rudimentary singly-linked LinkedList with head and tail references. This is what
     * comprises the actual backing array.
     */
    private class NodeList<T> {
        Node<T> head;
        Node<T> tail;

        public Node<T> getTail() {
            return tail;
        }

        public void setTail(Node<T> tail) {
            this.tail = tail;
        }

        public Node<T> getHead() {
            return head;
        }

        public void setHead(Node<T> head) {
            this.head = head;
        }


        /**
         * O(n) worst-case operation that ensures that no repeated keys are inserted into the HashMap. It is sufficient
         * to only search this linked list for potential repeated values because the hashing function guarantees that
         * such a value will be in this NodeList.
         * @param key
         * @param value
         * @return true if there was already a key with the same value in the NodeList.
         */
        private boolean set(String key, T value) {
            Node<T> newNode = new Node<>(key, value);
            boolean valueReplaced = false;
            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                Node<T> currentNode = head;
                while (currentNode != null) {
                    if (currentNode.getKey().equals(key)) {
                        currentNode.setData(value);
                        valueReplaced = true;
                        break;
                    }
                    currentNode = currentNode.getNext();
                }
                if (!valueReplaced) {
                    newNode.setNext(head);
                    head = newNode;
                }
            }
            return valueReplaced;
        }

        /**
         *
         * @param key
         * @return the value of the item deleted from the NodeList.
         */
        private T delete(String key) {
            T deletedObject = null;
            if (head.getKey().equals(key)) {
                deletedObject = head.getData();
                if (tail == head) {
                    tail = null;
                    head = null;
                } else {
                    head = head.getNext();
                }
            } else {
                Node<T> currentNode = head.getNext();
                Node<T> prevNode = head;
                while (currentNode.getNext() != null) {
                    currentNode = currentNode.getNext();
                    prevNode = prevNode.getNext();
                    if (currentNode.getKey().equals(key)) {
                        if (tail == currentNode) {
                            deletedObject = tail.getData();
                            tail = prevNode;
                            tail.setNext(null);
                        } else {
                            deletedObject = currentNode.getData();
                            prevNode.setNext(currentNode.getNext());
                        }
                        break;
                    }
                }
            }
            return deletedObject;
        }

        private T get(String key) {
            T returnValue = null;
            if (head != null) {
                Node<T> currentNode = head;
                while (currentNode != null) {
                    if (currentNode.getKey().equals(key)) {
                        returnValue = currentNode.getData();
                    }
                    currentNode = currentNode.getNext();
                }
            }
            return returnValue;
        }

        /**
         * Helper container class for storing key and value data, as well as a reference to the next Node in the
         * NodeList.
         * @param <T>
         */
        private class Node<T> {
            private String key;
            private T data;
            private Node next;

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public T getData() {
                return data;
            }

            public void setData(T data) {
                this.data = data;
            }

            public Node getNext() {
                return next;
            }

            public void setNext(Node next) {
                this.next = next;
            }

            Node(String dataString, T value) {
                key = dataString;
                data = value;
            }
        }
    }
}
