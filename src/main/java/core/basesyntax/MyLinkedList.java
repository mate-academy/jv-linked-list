package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(null, value, null);
        size++;
        if (tail == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index >= 0 && index <= size) {
            if (index == size) {
                add(value);
                return;
            }
            Node<T> node = new Node<>(null, value, null);
            size++;
            if (index == 0) {
                node.next = head;
                head.prev = node;
                head = node;
            } else {
                int counter = 0;
                Node<T> currentNode = head;
                while (counter < index - 1) {
                    currentNode = currentNode.next;
                    counter++;
                }
                node.next = currentNode.next;
                node.prev = currentNode;
                currentNode.next.prev = node;
                currentNode.next = node;
            }
        } else {
            throw new IndexOutOfBoundsException("There is no such index.");
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
        if (!isEmpty() && index >= 0 && index < size) {
            int counter = 0;
            Node<T> currentNode = head;
            while (currentNode != null) {
                if (index == counter) {
                    break;
                }
                currentNode = currentNode.next;
                counter++;
            }
            return currentNode.value;
        } else {
            throw new IndexOutOfBoundsException("There is no such index.");
        }
    }

    @Override
    public T set(T value, int index) {
        if (!isEmpty() && index >= 0 && index < size) {
            int counter = 0;
            Node<T> currentNode = head;
            while (currentNode != null) {
                if (counter == index) {
                    break;
                }
                counter++;
                currentNode = currentNode.next;
            }
            T oldValiue = currentNode.value;
            currentNode.value = value;
            return oldValiue;
        } else {
            throw new IndexOutOfBoundsException("There is no such index.");
        }
    }

    @Override
    public T remove(int index) {
        if (!isEmpty() && index >= 0 && index < size) {
            int counter = 0;
            Node<T> currentNode = head;
            while (currentNode != null) {
                if (counter == index) {
                    break;
                }
                currentNode = currentNode.next;
                counter++;
            }
            if (currentNode == head && size == 1) {
                head = null;
                tail = null;
                size = 0;
                return currentNode.value;
            }
            if (currentNode == head) {
                head = currentNode.next;
                currentNode.next.prev = null;
                size--;
                return currentNode.value;
            }
            if (currentNode == tail) {
                tail = currentNode.prev;
                currentNode.prev.next = null;
                size--;
                return currentNode.value;
            }
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
            size--;
            return currentNode.value;
        } else {
            throw new IndexOutOfBoundsException("There is no such index.");
        }
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.value == null && object == null
                    || currentNode != null && currentNode.value.equals(object)) {
                if (currentNode == head && size == 1) {
                    head = null;
                    tail = null;
                    size--;
                    return true;
                }
                if (currentNode == head) {
                    head = currentNode.next;
                    currentNode.next.prev = null;
                    size--;
                    return true;
                }
                if (currentNode == tail) {
                    tail = currentNode.prev;
                    currentNode.prev.next = null;
                    size--;
                    return true;
                }
                currentNode.prev.next = currentNode.next;
                currentNode.next.prev = currentNode.prev;
                size--;
                return true;
            }
            currentNode = currentNode.next;
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
