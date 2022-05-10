package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    public int size;
    Node<T> first;
    Node<T> last;

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        public Node(T item, Node<T> next, Node<T> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private void checkIndexAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private void checkIndexSet(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private void checkIndexRemove(int index) {
        if (index < 0 || index == size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }


    @Override
    public void add(T value) {
        if (size == 0) {
            Node node = new Node<>(value, null, null);
            first = node;
            last = node;
        }
        if (size > 0) {
            Node node = new Node<>(value, null, null);
            Node currentNode = first;
            while (currentNode.next != null) {
                currentNode = currentNode.next;
            }
            currentNode.next = node;
            node.prev = currentNode;
            last = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexAdd(index);
        if (size == 0) {
            Node node = new Node<>(value, null, null);
            first = node;
            last = node;
        }
        if (index == size) {
            this.add(value);
            return;
        }
        if (index == 0) {
            Node node = new Node<>(value, null, null);
            node.next = first;
            first.prev = node;
            first = node;
            size++;
            return;
        }

        if (size > 0) {
            Node node = new Node<>(value, null, null);
            int count = 0;
            Node currentNode = first;
            while (count != index) {
                currentNode = currentNode.next;
                count++;
            }
            currentNode.prev.next = node;
            node.prev = currentNode.prev;
            currentNode.prev = node;
            node.next = currentNode;
            size++;
        }
    }
    @Override
    public void addAll(List<T> list) {
        for (T listItem: list) {
            this.add(listItem);
        }
    }

    @Override
    public T get(int index) {
        checkIndexSet(index);
        int count = 0;
        Node currentNode = first;
        while (count != index) {
            currentNode = currentNode.next;
            count++;
        }
        return (T) currentNode.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndexSet(index);
        int count = 0;
        Node currentNode = first;
        while (count != index) {
            currentNode = currentNode.next;
            count++;
        }
        Node returnNode = new Node(currentNode.item, currentNode.next, currentNode.prev);
        currentNode.item = value;
        return (T) returnNode.item;
    }

    @Override
    public T remove(int index) {
        checkIndexRemove(index);
        int count = 0;
        if (index == 0 && size == 1) {
            size--;
            return (T) first.item;
        }
        if (index == 0) {
            first.next.prev = null;
            Node returnNode = first;
            first = first.next;
            size--;
            return (T) returnNode.item;
        }
        if (index == size - 1) {
            last.prev.next = null;
            Node returnNode = last;
            last = last.prev;
            size--;
            return (T) returnNode.item;
        }
        Node currentNode = first;
        while (count != index) {
            currentNode = currentNode.next;
            count++;
        }
        currentNode.prev.next = currentNode.next;
        currentNode.next.prev = currentNode.prev;
        size--;
        return (T) currentNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node currentNode = first;
        int index = 0;
        while (index < size) {
            if (currentNode.item == object || (currentNode != null && object != null && currentNode.item.equals(object))) {
                remove(index);
                return true;
            }
            currentNode = currentNode.next;
            index++;
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
}
