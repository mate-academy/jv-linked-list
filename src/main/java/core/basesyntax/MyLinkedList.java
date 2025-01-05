package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    static class Node<T> {
        private Node<T> prev = null;
        private T data;
        private Node<T> next = null;

        public Node() {
            this.data = null;
        }

        public Node(T data) {
            this.data = data;
        }

        public Node(Node<T> prev, T data, Node<T> next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }

        public void setData(T data) {
            this.data = data;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public T getData() { return data; }

        public Node<T> getNext() {
            return next;
        }
    }

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        Node<T> item = new Node<>(value);
        if (size == 0) {
            head = item;
        } else {
            item.setPrev(tail);
            tail.setNext(item);
        }
        tail = item;
        this.size++;
    }

    @Override
    public void add(T value, int index) {
        indexAssert(index, "size");
        if (head == null) {
            this.add(value);
            return;
        }
        Node<T> item = new Node<>(value);
        if (index == 0) {
            head.setPrev(item);
            item.setNext(head);
            head = item;
            size++;
            return;
        }
        if (index == size) {
            item.setPrev(tail);
            tail.setNext(item);
            tail = item;
            size++;
            return;
        }
        Node<T> currentItem = head;
        for (int i = 1; i <= index; i++) {
            currentItem = currentItem.getNext();
        }
        currentItem.getPrev().setNext(item);
        item.setPrev(currentItem.getPrev());
        item.setNext(currentItem);
        currentItem.setPrev(item);
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            this.add(item);
        }
    }

    @Override
    public T get(int index) {
        indexAssert(index, "index");
        if (index == 0) {
            return head.getData();
        }
        if (index == size - 1) {
            return tail.getData();
        }
        Node<T> currentItem = head;
        for (int i = 1; i <= index; i++) {
            currentItem = currentItem.getNext();
        }
        return currentItem.getData();
    }

    @Override
    public T set(T value, int index) {
        indexAssert(index, "index");
        Node<T> currentItem = head;
        for (int i = 1; i <= index; i++) {
            currentItem = currentItem.getNext();
        }
        T oldData = currentItem.getData();
        currentItem.setData(value);
        return oldData;
    }

    @Override
    public T remove(int index) {
        indexAssert(index, "index");
        if (index == 0) {
            T oldData = head.getData();
            if (size > 1) {
                head = head.getNext();
                head.getNext().setPrev(null);
            } else {
                head.setNext(null);
            }
            size--;
            return oldData;
        }
        if (index == size - 1) {
            size--;
            T oldData = tail.getData();
            tail = tail.getPrev();
            tail.getPrev().setNext(null);
            return oldData;
        }
        Node<T> currentItem = head;
        for (int i = 1; i <= index; i++) {
            currentItem = currentItem.getNext();
        }
        currentItem.getPrev().setNext(currentItem.getNext());
        currentItem.getNext().setPrev(currentItem.getPrev());
        size--;
        return currentItem.getData();
    }

    @Override
    public boolean remove(T object) {
        if (size == 0) {
            return false;
        }
        Node<T> currentItem = head;
        while (currentItem != null) {
            if ((currentItem.getData() == null) ? (currentItem.getData() == null && object == null)
                    : currentItem.getData().equals(object)) {
                if (currentItem == head) {
                    head = head.getNext();
                    if (head != null) {
                        head.setPrev(null);
                    }
                    size--;
                    return true;
                }
                if (currentItem == tail) {
                    tail = tail.getPrev();
                    tail.setNext(null);
                    size--;
                    return true;
                }
                currentItem.getNext().setPrev(currentItem.getPrev());
                currentItem.getPrev().setNext(currentItem.getNext());
                size--;
                return true;
            }
            currentItem = currentItem.getNext();
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

    private void indexAssert(int index, String context) {
        switch (context) {
            case "size":
                if (index < 0 || index > size) {
                    throw new IndexOutOfBoundsException();
                }
                break;
            case "index":
                if (index < 0 || index >= size) {
                    throw new IndexOutOfBoundsException();
                }
                break;
            default:
                System.out.println("There's no such context: " + context);
        }


    }
}
