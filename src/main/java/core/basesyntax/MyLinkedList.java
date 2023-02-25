package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    Node<T> first;
    Node<T> last;
    private int size;
    private static class Node<T> {
        Node<T> prev;
        T item;
        Node<T> next;

        public Node(T item)  {
//            this.prev = prev;
            this.item = item;
//            this.next = next;
        }
        private T getValue() {
            return item;
        }
    }
    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (first == null) {
            first = last = newNode;
        } else {
            this.last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        Node<T> newNode = new Node<>(value);
        if (first == null) {
            first = last = newNode;
        }
        else if (index == 0) {
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
        }
        else if (index == size) {
            newNode.prev = last;
            last.next = newNode;
            last = newNode;
        } else {
            Node<T> currentNode = getNode(index);
            newNode.next = currentNode;
            newNode.prev = currentNode.prev;
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
        }
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
        return getNode(index).getValue();

    }
//            Node<T> node = first;
//        if (index >= 0 && index < size) {
//            for (int i = 0; i < index; i++) {
//                node = node.next;
//            }
//        }
//        return node.getValue();

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = getNode(index);
        Node<T> newNode = new Node<>(value);
        if (index == 0) {
            newNode.next = current.next;
            current.next.prev = newNode;
            first = newNode;
        }
        else if (index == size - 1) {
            newNode.prev = current.prev;
            current.prev.next = newNode;
            last = newNode;
        } else {
            newNode.prev = current.prev;
            newNode.next = current.next;
            current.prev.next = newNode;
            current.next.prev = newNode;
        }

        return current.getValue();
    }

    @Override
    public T remove(int index) {
        Node<T> deletedNode = getNode(index);
        if (index == 0) {
            first = deletedNode.next;
        } else if (index == size - 1) {
            deletedNode.prev.next = null;
            last = deletedNode.prev;
        } else {
            unLink(deletedNode);
        }
        size--;
        return deletedNode.getValue();
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        while (current != null) {
            if (current.getValue() == object
                && current == first
            || current.getValue() != null
                    && current.getValue().equals(object)
                    && current.equals(first)) {
                    first = current.next;
                size--;
               return true;
            } else if (current.getValue() != null
                 && (current.getValue().equals(object)
                    && current.equals(last))) {
                current.prev.next = null;
                last = current.prev;
                size--;
                return true;
            } else if ( current.getValue() == object
                    && current.getValue() != last
            || current.getValue() != null
                    && current.getValue().equals(object) &&
            !current.getValue().equals(last)) {
                unLink(current);
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
        return first == null;
    }
    @Override
    public String toString() {
        Node<T> current = first;
        StringBuilder str = new StringBuilder();
         while (current != null) {
             str.append(current.getValue());
             current = current.next;
         }
         return str.toString();
    }
    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }
    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }
    private void unLink (Node<T> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
}
