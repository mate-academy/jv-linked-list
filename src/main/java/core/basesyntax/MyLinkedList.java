package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node first;
    private Node last;
    private int size = 0;

    public MyLinkedList() {
        first = null;
        last = null;
    }

    @Override
    public void add(T value) {
        Node addedNode = new Node(value);
        if (size == 0) {
            first = addedNode;
        } else {
            addedNode.setPrev(last);
            last.setNext(addedNode);
        }
        last = addedNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is out of bound");
        }
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node addedNode = new Node(value);
            addedNode.setNext(first);
            first.setPrev(addedNode);
            first = addedNode;
            size++;
        } else {
            Node searchedNode = findNodeByIndex(index);
            Node addedNode = new Node(value);
            addedNode.setNext(searchedNode);
            addedNode.setPrev(searchedNode.getPrev());
            searchedNode.getPrev().setNext(addedNode);
            searchedNode.setPrev(addedNode);
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T node : list) {
            add(node);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node searchedNode = findNodeByIndex(index);
        return searchedNode.getValue();
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node node = findNodeByIndex(index);
        T oldValue = node.getValue();
        node.setValue(value);
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node node = findNodeByIndex(index);
        if (node == first) {
            if (first == last) {
                first = null;
                last = null;
            } else {
                first = node.getNext();
                node.getNext().setPrev(null);
            }
        } else if (node == last) {
            last = node.getPrev();
            node.getPrev().setNext(null);
        } else {
            node.getPrev().setNext(node.getNext());
            node.getNext().setPrev(node.getPrev());
        }
        size--;
        return node.getValue();
    }

    @Override
    public boolean remove(T object) {
        Node node = first;
        int index = 0;
        while (node != null && !Objects.equals(node.getValue(), object) && index != size) {
            node = node.getNext();
            index++;
        }
        if (node == null) {
            return false;
        }
        remove(index);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public Node getFirst() {
        return first;
    }

    public Node getLast() {
        return last;
    }

    public int getSize() {
        return size;
    }

    public void setFirst(Node first) {
        this.first = first;
    }

    public void setLast(Node last) {
        this.last = last;
    }

    public void setSize(int size) {
        this.size = size;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bound");
        }
    }

    private Node findNodeByIndex(int index) {
        int listIndex = 0;
        Node searchedNode = first;
        while (listIndex != index) {
            searchedNode = searchedNode.getNext();
            listIndex++;
        }
        return searchedNode;
    }

    private class Node {
        private T value;
        private Node prev;
        private Node next;

        public Node(T value) {
            this.setValue(value);
            setPrev(null);
            setNext(null);
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
