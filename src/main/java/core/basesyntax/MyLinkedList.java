package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    public void setSize() {
        this.size = size;
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<T>(null, value, null);
            tail = head;
        } else {
            Node<T> newNode = new Node<T>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        indexExist(index);
        if (index == size) {
            add(value);
        }
        if (index == 0) {
            Node<T> newNode = new Node<T>(null, value, head);
            newNode.next = head;
            head = newNode;
            size++;
        } else {
            Node<T> current = nodeGetByIndex(index - 1);
            Node<T> newNode = new Node<T>(null, value, null);
            newNode.next = current.next;
            current.next = newNode;
            newNode.prev = current;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        indexExist(index);
        if (index == size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                break;
            }
            currentNode = currentNode.next;
        }
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        if (value == null) {
            throw new NullPointerException();
        }
        Node<T> newNode = new Node<T>(value);
        indexExist(index);
        if (index == 0) {
            head.next = newNode.next;
            head = newNode;
            return newNode.value;
        }
        if (index == size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> currentNode = nodeGetByIndex(index);
        if (currentNode.equals(head) && currentNode != null || currentNode == head) {
            currentNode.next = newNode.next;
            currentNode.value = newNode.value;
            currentNode = head;
        }
        if (currentNode.equals(tail) && currentNode != null || currentNode == tail) {
            currentNode.prev = newNode.prev;
            currentNode.value = newNode.value;
            currentNode = tail;
        }
        currentNode.prev = newNode.prev;
        currentNode.value = newNode.value;
        currentNode.next = newNode.next;
        return nodeGetByIndex(index).value;
    }

    @Override
    public T remove(int index) {
        indexExist(index);
        if (index == size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> currentNode = nodeGetByIndex(index);
        if (currentNode.equals(head) && currentNode != null || currentNode == head) {
            currentNode.next = head;
            currentNode = null;
        }
        if (currentNode.equals(tail) && currentNode != null || currentNode == tail) {
            currentNode.prev = tail;
            currentNode = null;
        } else {
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
        }
        size--;
        return get(index);
    }

    @Override
    public boolean remove(T object) {
        if (object != null) {
            int index = 0;
            Node<T> currentNode = head;
            for (int i = 0; i < size; index++, i++) {
                if (get(i).equals(object)) {
                    break;
                }
                currentNode = currentNode.next;
            }
            if (currentNode != null) {
                remove(get(index));
                return true;
            }
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

    private void indexExist(int index) throws IndexOutOfBoundsException {
        if (index < 0 && index > size || size == 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> nodeGetByIndex(int index) {
        indexExist(index);
        if (index == size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                break;
            }
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }

        public Node(T value) {
            this.value = value;
        }
    }
}
