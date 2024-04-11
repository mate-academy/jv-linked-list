package core.basesyntax;



import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if(tail == null) {
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
    public void add(T value, int index) {
        if (index < 0 || index > size){
            throw new IndexOutOfBoundsException("Index: " + index + "out of bounds, size: " + size);
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> current = getNode(index);
        Node<T> newNode = new Node<>(current.prev ,value, current.next);
        if(current.prev == null) {
            head = newNode;
        } else {
            current.prev.next = newNode;
        }
        current.prev = newNode;
        size++;

    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).data;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node <T> current = getNode(index);
        T oldData = current.data;
        current.data = value;
        return oldData;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = getNode(index);
        if(current == head) {
            head = current.next;
        } else {
            current.prev.next = current.next;
        }
        if (current == tail) {
            tail = current.prev;
        } else {
            current.next.prev = current.prev;
        }
        size--;
        return current.data;
    }

    @Override
    public boolean remove(T object) {
        Node <T> current = head;
        while(current != null) {
            if (current.data.equals(object)) {
                current.prev.next = current.next;
                current.next.prev = current.prev;

                size--;
                return true;
            }
            current = current.next;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + "out of bounds, size: " + size);
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> current;
        if(index <= size/2) {
            current = head;
            for(int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        if (current == null) {
            throw new IndexOutOfBoundsException("Index: " + index + "out of bounds, size: " + size);
        }
        return current;
    }

    private static class Node <T> {
        T data;
        Node <T> next;
        Node <T> prev;

        Node(Node <T> prev, T data, Node <T> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

}
