package core.basesyntax;

import java.util.List;

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

    @Override
    public void add(T value, int index) {
        Node<T> newNode;

        if (index == numberOfElements) {
            add(value);
        } else {
            newNode = new Node<>(null, value, null);
            connectNodeBeforeIndex(newNode, index);
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
        return result.item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> oldNode = giveNodeByIndex(index);
        Node<T> newNode = new Node<>(null, value, null);
        changeConnectionFromToNode(oldNode, newNode);
        return oldNode.item;
    }

    @Override
    public T remove(int index) {
        Node<T> node = giveNodeByIndex(index);
        disconnectNode(node);
        numberOfElements--;
        return node.item;
    }

    @Override
    public boolean remove(T object) {
        int count = 0;
        Node<T> node;
        while (count < numberOfElements) {
            node = giveNodeByIndex(count);
            if (node.item == null
                    ? node.item == object : node.item.equals(object)) {
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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ ");
        Node<T> node;
        int counter = 0;
        while (counter < size()) {
            node = giveNodeByIndex(counter);
            stringBuilder.append(node);
            if (counter + 1 < size()) {
                stringBuilder.append(", ");
            }
            counter++;
        }
        stringBuilder.append(" ]");
        return stringBuilder.toString();
    }

    private Node<T> giveNodeByIndex(int index) {
        if (index < 0 || index > numberOfElements) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
        Node<T> result;
        if (index > numberOfElements / 2) {
            result = giveNodeByIndexFromTail(index);
        } else {
            result = giveNodeByIndexFromHead(index);
        }
        return result;
    }

    private Node<T> giveNodeByIndexFromTail(int index) {
        Node<T> node = tail;
        int counter = numberOfElements;

        while (node != null) {
            counter--;
            if (counter == index) {
                return node;
            }
            node = node.previous;

        }
        throw new IndexOutOfBoundsException("Wrong index");
    }

    private Node<T> giveNodeByIndexFromHead(int index) {
        Node<T> node = head;
        int counter = 0;

        while (node != null) {
            if (counter == index) {
                return node;
            }
            node = node.next;
            counter++;
        }
        throw new IndexOutOfBoundsException("Wrong index");
    }

    private void connectNodeBeforeIndex(Node<T> node, int index) {
        Node<T> oldNode = giveNodeByIndex(index);
        node.previous = oldNode.previous;
        if (oldNode.previous != null) {
            oldNode.previous.next = node;
        }
        oldNode.previous = node;
        node.next = oldNode;
        checkAndSwitchHeadAndTail(oldNode, node);
    }

    private void checkAndSwitchHeadAndTail(Node<T> oldNode, Node<T> newNode) {
        if (oldNode == head && newNode.previous == null) {
            head = newNode;
        } else if (oldNode == tail && newNode.next == null) {
            tail = newNode;
        }
    }

    private void changeConnectionFromToNode(Node<T> from, Node<T> to) {
        to.next = from.next;
        to.previous = from.previous;
        if (from.previous != null) {
            from.previous.next = to;
        }
        if (from.next != null) {
            from.next.previous = to;
        }
        checkAndSwitchHeadAndTail(from, to);
    }

    private void disconnectNode(Node<T> node) {
        if (node.previous != null) {
            node.previous.next = node.next;
        }
        if (node.next != null) {
            node.next.previous = node.previous;
        }
        if (node == tail) {
            tail = node.previous;
        }
        if (node == head) {
            head = node.next;
        }
    }

    private static class Node<E> {
        private final E item;
        private Node<E> previous;
        private Node<E> next;

        public Node(Node<E> previous, E item, Node<E> next) {
            this.item = item;
            this.previous = previous;
            this.next = next;
        }

        @Override
        public String toString() {
            return (item == null ? null : item.toString());
        }

    }
}
