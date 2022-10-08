package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

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
        if (index < 0 || index > numberOfElements) {
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
        throw new IndexOutOfBoundsException("Wrond index");
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = null;
        Node<T> oldNode = null;
        if (index == numberOfElements) {
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
        Node<T> result = giveNodeByIndex(index);
        return result == null ? null : result.item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> oldNode = giveNodeByIndex(index);

        Node<T> newNode = new Node<>(null, value, null);
        newNode.next = oldNode.next;
        if (oldNode.previous != null) {
            oldNode.previous.next = newNode;
        }
        if (oldNode.next != null) {
            oldNode.next.previous = newNode;
        }
        if (oldNode == tail) {
            tail = newNode;
        }
        if (oldNode == head) {
            head = newNode;
        }
        return oldNode == null ? null : oldNode.item;
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
        if (node.previous != null) {
            node.previous.next = node.next;
        }
        if (node.next != null) {
            node.next.previous = node.previous;
        }
        if (node == tail || index == numberOfElements - 1) {
            tail = node.previous;
        }
        if (node == head) {
            head = node.next;
        }
        numberOfElements--;
        return node.item;
    }

    private void setHead() {
        head = giveNodeByIndex(0);
    }

    private void setTail() {
        tail = giveNodeByIndex(numberOfElements);
    }

    @Override
    public boolean remove(T object) {
        int count = 0;
        Node<T> node;
        while (count < numberOfElements) {
            node = giveNodeByIndex(count);
            if (Objects.equals(node.item, object)) {
                remove(count);
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

    // ToDo: it`s not working(
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ ");
        Node<T> node = head;
        int counter = 0;
        while (counter < size()) {
            node = giveNodeByIndex(counter);
            stringBuilder.append(node == null ? "'null'" : node.toString());
            if (counter + 1 < size()) {
                stringBuilder.append(", ");
            }
            counter++;
        }
        stringBuilder.append(" ]");
        return stringBuilder.toString();
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

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(item == null ? null : item.toString());
            return stringBuilder.toString();
        }

    }
}
