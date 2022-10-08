package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int numberOfElements;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {
        numberOfElements = 0;
        head = null;
        tail = null;
    }

    @Override
    public void add(T value) {
        if (numberOfElements == 0) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }
        numberOfElements++;
    }

    private Node<T> giveNodeByIndex(int index) {
        if (index < 0 || index >= numberOfElements) {
            throw new IndexOutOfBoundsException("Wrond index");
        }
        Node<T> node = head;
        int counter = 0;
        while (node != null) {
            if (counter == index) {
                return node;
            }
            node = node.next;
            counter++;
        }
        return null;
    }

    @Override
    public void add(T value, int index) {
        if ((index == 0 && head == null) || (index == numberOfElements)) {
            add(value);


        } else {
            Node<T> node = giveNodeByIndex(index);
            Node<T> newNode = new Node(node.previous, value, node);
            node.previous.next = newNode;
            node.previous = newNode;
            numberOfElements++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return giveNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> oldNode = giveNodeByIndex(index);
        Node<T> newNode = new Node<>(oldNode.previous, value, oldNode.next);
        if (oldNode.previous != null) {
            oldNode.previous.next = newNode;
        }
        if (oldNode.next != null) {
            oldNode.next.previous = newNode;
        }
        if (oldNode == head) {
            head = newNode;
        }
        if (oldNode == tail) {
            tail = newNode;
        }
        return oldNode.item;
    }

//    @Override
    /* сука, стільки часу витратив на реалізацію, а воно не так повинно працювати */
    public T addOnPosition(T value, int index) {
        Node<T> newNode = null;
        Node<T> oldNode = null;
        if (head == null && index == 0) {
            add(value);
        } else {
            oldNode = giveNodeByIndex(index);
            newNode = new Node<>(null, value, null);
            if (oldNode != null) {
                newNode.previous = oldNode.previous;
                newNode.next = oldNode;
                if (oldNode.previous != null) {
                    oldNode.previous.next = newNode;
                }
                oldNode.previous = newNode;
                if (oldNode == head) {
                    head = newNode;
                }
                if (oldNode == tail) {
                    tail = newNode;
                }

            }
        }
        numberOfElements++;
        return oldNode.item;
    }

    @Override
    public T remove(int index) {
        Node<T> node = giveNodeByIndex(index);
        node.previous.next = node.next;
        node.next.previous = node.previous;
        numberOfElements--;
        return node.item;
    }

    @Override
    public boolean remove(T object) {
        int count = 0;
        Node<T> node = giveNodeByIndex(count);
        while (node != null) {
            if (node.item.equals(object)) {
                remove(count);
                numberOfElements--;
                return true;
            }
            count++;
        }
        return false;
    }

    @Override
    public int size() {
        return numberOfElements;
    }

    @Override
    public boolean isEmpty() {
        return numberOfElements == 0;
    }

    private static class Node<E> {
        private E item;
        private Node<E> previous;
        private Node<E> next;

        public Node(Node<E> previous, E item, Node<E> next) {
            this.item = item;
            this.previous = previous;
            this.next = next;
        }

        public void setPrevious(Node<E> node) {
            previous = node;
        }

        private void setNext(Node<E> node) {
            next = node;
        }

        private void setItem(E value) {
            item = value;
        }


    }
}
