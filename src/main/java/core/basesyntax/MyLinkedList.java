package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public void add(T value) {
        // Create a new node with the given value
        Node<T> newNode = new Node<>(value);
        // Insert if the list is empty
        if (head == null) {
            head = newNode;
            tail = newNode;
            size++;
        } else {
            // Insert if the list is not empty
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        // Check for index out of bounds
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index: " + index + " is incorrect!");
        }
        // Create a new node with the given value
        Node<T> newNode = new Node<>(value);
        // Insert at the head
        if (index == 0) {
            // Insert into an empty list
            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                head.setPrev(newNode);
                newNode.setNext(head);
                head = newNode;
            }
        } else {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
            // Insert into the middle of the list
            if (index != size) {
                newNode.setNext(current.getNext());
                current.setNext(newNode);
                newNode.getNext().setPrev(newNode);
                newNode.setPrev(current);
            } else {
                // Insert into the end of the list
                tail.setNext(newNode);
                newNode.setPrev(tail);
                tail = newNode;
            }
        }
        // Increment the size of the list
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        // Check if the provided list is null
        if (list == null) {
            throw new IllegalArgumentException("Provided list must not be null.");
        }
        // Add elements from the provided list to the current linked list
        for (T item : list) {
            this.add(item, size);
        }
    }

    @Override
    public T get(int index) {
        // Check for index out of bounds
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index: " + index + " is incorrect!");
        }
        // Create a current node
        Node<T> current = head;
        // Iterate for the nodes by index
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        // Return the node with the given index
        return current.getValue();
    }

    @Override
    public T set(T value, int index) {
        // Check for index out of bounds
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + " is incorrect!");
        }
        // Iterate for the nodes by index
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        // Create the variety with the previous value
        T oldValue = current.getValue();
        // Set the given value for the node by index
        current.setValue(value);
        return oldValue;
    }

    @Override
    public T remove(int index) {
        // Check for index out of bounds
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + " is incorrect!");
        }
        // Remove head
        if (index == 0) {
            T oldValue = head.getValue();
            head = head.getNext();
            if (head != null) {
                head.setPrev(null);
            } else {
                tail = null;
            }
            size--;
            return oldValue;
        }
        // Remove tail
        if (index == size - 1) {
            T oldValue = tail.getValue();
            tail = tail.getPrev();
            if (tail != null) {
                tail.setNext(null);
            } else {
                head = null;
            }
            size--;
            return oldValue;
        }
        // Remove from the middle
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        T oldValue = current.getValue();
        current.getPrev().setNext(current.getNext());
        current.getNext().setPrev(current.getPrev());
        size--;
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        // Iterate over nodes
        Node<T> current = head;
        while (current != null) {
            if  (Objects.equals(current.getValue(), object)) {
                // Remove head
                if (current == head) {
                    head = head.getNext();
                    if (head != null) {
                        head.setPrev(null);
                    } else {
                        tail = null;
                    }
                // Remove tail
                } else if (current == tail) {
                    tail = tail.getPrev();
                    if (tail != null) {
                        tail.setNext(null);
                    } else {
                        head = null;
                    }
                // Remove from the middle
                } else {
                    current.getPrev().setNext(current.getNext());
                    current.getNext().setPrev(current.getPrev());
                }
                size--;
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
