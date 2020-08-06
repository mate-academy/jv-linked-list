package core.basesyntax;

import java.util.Iterator;
import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> firstNode;
    private Node<T> lastNode;

    public MyLinkedList() {
        size = 0;
    }

    private static class Node<T> {
        T value;
        Node<T> next;
        Node<T> prev;

        public Node(T value, Node<T> next, Node<T> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public boolean add(T value) {
        addNodeToTheEnd(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("This index is out of bounds.");
        }
        if (index == size) {
            addNodeToTheEnd(value);
        } else {
            Node<T> soughtForNode = findNodeByIndex(index);
            Node<T> previousNode = soughtForNode.prev;
            Node<T> newNode = new Node<>(value, soughtForNode, previousNode);
            soughtForNode.prev = newNode;
            if (previousNode == null) {
                firstNode = newNode;
            } else {
                previousNode.next = newNode;
            }
            size++;
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        if (list.size() == 0) {
            return false;
        }
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            add((T) iterator.next());
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> soughtForNode = findNodeByIndex(index);
        return soughtForNode.value;
    }

    // DOES THIS METHOD REPLACE THE NODE OR SHIFT IT?
    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> nodeToBeShifted = findNodeByIndex(index);
        T previousValue = nodeToBeShifted.value;
        nodeToBeShifted.value = value;
        return previousValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeToBeRemoved = findNodeByIndex(index);
        removeNodeLinks(nodeToBeRemoved);
        return nodeToBeRemoved.value;
    }

    @Override
    public boolean remove(T t) {
        Node<T> nodeToBeRemoved = firstNode;
        for (int i = 0; i < size; i++) {
            if (nodeToBeRemoved.value == t
                    || nodeToBeRemoved.value != null && nodeToBeRemoved.value.equals(t)) {
                removeNodeLinks(nodeToBeRemoved);
                return true;
            }
            nodeToBeRemoved = nodeToBeRemoved.next;
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

    private Node<T> findNodeByIndex(int index) {
        Node<T> soughtForNode;
        if (index < size >> 1) {
            soughtForNode = firstNode;
            for (int i = 0; i < index; i++) {
                soughtForNode = soughtForNode.next;
            }
        } else {
            soughtForNode = lastNode;
            for (int i = size - 1; i > index; i--) {
                soughtForNode = soughtForNode.prev;
            }
        }
        return soughtForNode;
    }

    private void removeNodeLinks(Node<T> nodeToBeRemoved) {
        Node<T> previousNode = nodeToBeRemoved.prev;
        Node<T> nextNode = nodeToBeRemoved.next;
        if (previousNode == null) {
            firstNode = nextNode;
        } else {
            previousNode.next = nextNode;
        }
        if (nextNode == null) {
            lastNode = previousNode;
        } else {
            nextNode.prev = previousNode;
        }
        size--;
    }

    private void addNodeToTheEnd(T value) {
        Node<T> currentLastNode = lastNode;
        lastNode = new Node<>(value, null, currentLastNode);
        if (currentLastNode == null) {
            firstNode = lastNode;
        } else {
            currentLastNode.next = lastNode;
        }
        size++;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("This index is out of bounds.");
        }
    }
}

/* public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> header;
    private Node<T> firstNode;
    private Node<T> lastNode;

    public MyLinkedList() {
        size = 0;
        header = new Node();
        lastNode = header;
        firstNode = header.next;
    }

    private static class Node<T> {
        T value;
        Node<T> next;
        Node<T> prev;

        public Node() {
            this.value = null;
            this.next = this.prev = null;
        }

        public Node(T value, Node<T> next, Node<T> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public boolean add(T value) {
        if (size < 1) {
            lastNode = new Node(value, header, header);
            size++;
        } else {
            Node newNode = new Node(value, header, lastNode);
            //newNode.prev = lastNode;
            lastNode.next = newNode;
            header.prev = newNode;
            lastNode = newNode;
            size++;
        }
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size+1) {
            throw new IndexOutOfBoundsException("This index is out of bounds.");
        }
        if (size <= 1) {
            lastNode = new Node(value, header, header);
            size++;
            return;
        }
        Node<T> currentNode = firstNode;
        int counter = 0;
        while (counter != index) {
            currentNode = currentNode.next;
            counter++;
        }
        Node<T> newNode = new Node(value, currentNode, lastNode);
        lastNode.next = newNode;
        currentNode.prev = newNode;
        lastNode = newNode;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        return false;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> currentNode = header.next;
        int counter = 0;
        while (counter != index) {
            currentNode = currentNode.next;
            counter++;
        }
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);

        return null;
    }

   @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> currentNode = header.next;
        int counter = 0;
        while (counter != index) {
            currentNode = currentNode.next;
        }

        Node<T> previousNode = currentNode.prev;
        Node<T> nextNode = currentNode.next;
        previousNode.next = nextNode;
        nextNode.prev = previousNode;
        size--;
        return currentNode.value;
    }

  @Override
    public boolean remove(T t) {
    Node<T> currentNode = header.next;
        for (int i = 0; i < size; i++) {
            if (currentNode.value != t && (t != null && !t.equals(currentNode.value))) {
                currentNode = currentNode.next;
            }
            Node<T> previous = currentNode.prev;
            Node<T> next = currentNode.next;
            previous.next = next;
            next.prev = previous;
            size--;
            return true;
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
            throw new IndexOutOfBoundsException("This index is out of bounds.");
        }
    }
} */

 /* @Override
   public boolean remove(T t) {
        if (t == null) {
       //     for (Node<T> x = header.next; x != null; x = x.next) {
            Node<T> currentNode = header.next;
            for (int i = 0; i < size; i++) {
                if (currentNode.value == null) {
                    Node<T> previousNode = currentNode.prev;
                    Node<T> nextNode = currentNode.next;
                    previousNode.next = nextNode;
                    nextNode.prev = previousNode;
                    size--;
                    return true;
                }
                if (t.equals(currentNode.value)) {
                    Node<T> previousNode = currentNode.prev;
                    Node<T> nextNode = currentNode.next;
                    previousNode.next = nextNode;
                    nextNode.prev = previousNode;
                    size--;
                    return true;
                }
            }
        }
       return false;
    } */
