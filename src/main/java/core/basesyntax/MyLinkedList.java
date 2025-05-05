package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    public class Node<T> {
        T data;
        Node<T> next;
        Node<T> prev;

        public Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    private Node<T> head;  
    private Node<T> tail;  
    private int size;    

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void add(T item) {
        Node<T> newNode = new Node<>(item);
        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> elements) {
        for (T element : elements) {
            add(element);  
        }
    }

    @Override
    public void add(T item, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        if (index == size) {
            add(item);  
            return;
        }

        Node<T> newNode = new Node<>(item);
        if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else {
            Node<T> current = getNodeAt(index);
            newNode.next = current;
            newNode.prev = current.prev;
            current.prev.next = newNode;
            current.prev = newNode;
        }
        size++;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeAt(index).data;
    }

    private Node<T> getNodeAt(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);

        Node<T> nodeToRemove = getNodeAt(index);
        T data = nodeToRemove.data;

        if (nodeToRemove.prev == null) {
            head = nodeToRemove.next; 
        } else {
            nodeToRemove.prev.next = nodeToRemove.next;
        }

        if (nodeToRemove.next == null) {
            tail = nodeToRemove.prev;  
        } else {
            nodeToRemove.next.prev = nodeToRemove.prev;
        }

        size--;
        return data;
    }

    @Override
    public boolean remove(T item) {
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(item)) {
                remove(current); 
                return true;
            }
            current = current.next;
        }
        return false;
    }

    private void remove(Node<T> nodeToRemove) {
        if (nodeToRemove.prev == null) {
            head = nodeToRemove.next;  
        } else {
            nodeToRemove.prev.next = nodeToRemove.next;
        }

        if (nodeToRemove.next == null) {
            tail = nodeToRemove.prev; 
        } else {
            nodeToRemove.next.prev = nodeToRemove.prev;
        }

        size--;
    }

    @Override
    public T set(T item, int index) {
        checkIndex(index);
        Node<T> node = getNodeAt(index);
        T oldValue = node.data;
        node.data = item;
        return oldValue;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }
}
