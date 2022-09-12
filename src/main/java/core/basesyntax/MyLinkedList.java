package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    int size;
    Node<T> first;
    Node<T> last;
    @Override
    public void add(T value) {
        Node<T> lastNode = last;
        Node<T> newNode = new Node<>(lastNode, value, null);
        last = newNode;
        if (lastNode == null) {
            first = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        check_index(index);
        if (index == size) {
            add(value);
        } else {
            linkBefore(value, get_node(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        Object[] arrayFromList = list.toArray();
        int lengthArray = arrayFromList.length;

        Node<T> lastNode = last;
        for (Object value : arrayFromList) {
            T tValue = (T) value;
            Node<T> newNode = new Node<>(lastNode, tValue, null);
            if (lastNode == null) {
                first = newNode;
            } else {
                lastNode.next = newNode;
            }
            lastNode = newNode;
        }
        last = lastNode;
        size += lengthArray;
    }

    @Override
    public T get(int index) {
        check_equal_index(index);
        Node<T> node = get_node(index);
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        check_equal_index(index);
        Node<T> currentNode = get_node(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        check_equal_index(index);
        return unlink(get_node(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> node = first; node != null; node = node.next) {
                if (node.value == null) {
                    unlink(node);
                    return true;
                }
            }
        } else {
            for (Node<T> node = first; node != null; node = node.next) {
                if (object.equals(node.value)) {
                    unlink(node);
                    return true;
                }
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

    static class Node<T> {
        Node<T> prev;
        T value;
        Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private void linkBefore(T value, Node<T> currentNode) {
        Node<T> previousNode = currentNode.prev;
        Node<T> newNode = new Node<>(previousNode, value, currentNode);
        currentNode.prev = newNode;
        if (previousNode == null) {
            first = newNode;
        } else {
            previousNode.next = newNode;
        }
        size++;
    }

    private T unlink(Node<T> node) {
        T value = node.value;
        Node<T> prevNode = node.prev;
        Node<T> nextNode = node.next;

        if (prevNode == null) {
            first = nextNode;
        } else {
            prevNode.next = nextNode;
            node.prev = null;
        }

        if (nextNode == null) {
            last = prevNode;
        } else {
            nextNode.prev = prevNode;
            node.next = null;
        }

        node.value = null;
        size--;
        return value;
    }

    private void check_index(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void check_equal_index(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> get_node(int index) {
        if (index < (size >> 1)) {
            Node<T> first_node = first;
            for (int i = 0; i < index; i++) {
                first_node = first_node.next;
            }
            return first_node;
        } else {
            Node<T> last_node = last;
            for (int i = size - 1; i > index; i--) {
                last_node = last_node.prev;
            }
            return last_node;
        }
    }
}
