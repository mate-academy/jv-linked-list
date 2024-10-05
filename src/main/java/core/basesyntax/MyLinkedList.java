package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private static class Node<T> {

        private T element;
        private Node<T> next;
        private Node<T> prev;

        public Node(T element, Node<T> next, Node<T> prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            addFirstNode(value);
        } else {
            addNodeToEnd(value);
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (size == 0) {
            addFirstNode(value);
            return;
        }
        if (index == 0) {
            addToTop(value);
            return;
        }
        if (index == size) {
            addNodeToEnd(value);
            return;
        }
        Node<T> currentNode;

        if (index <= size / 2) {
            int count = 0;
            currentNode = first;
            while (count != index) {
                currentNode = currentNode.next;
                count++;
            }
        } else {
            int count = size - 1;
            currentNode = last;
            while (count != index) {
                currentNode = currentNode.prev;
                count--;
            }
        }
        Node<T> newNode = new Node<>(value, currentNode, currentNode.prev);
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (index == 0) {
            return first.element;
        }
        if (index == size - 1) {
            return last.element;
        }
        if (index <= size / 2) {
            int count = 0;
            Node<T> node = first;
            while (count != index) {
                node = node.next;
                count++;
            }
            return node.element;
        } else {
            int count = size - 1;
            Node<T> node = last;
            while (count != index) {
                node = node.prev;
                count--;
            }
            return node.element;
        }
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> currentNode;
        if (index <= size / 2) {
            int count = 0;
            currentNode = first;
            while (count != index) {
                currentNode = currentNode.next;
                count++;
            }
        } else {
            int count = size - 1;
            currentNode = last;
            while (count != index) {
                currentNode = currentNode.prev;
                count--;
            }
        }
        T oldValue = currentNode.element;
        currentNode.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0 && size == 1) {
            T element = first.element;
            size--;
            return element;
        }
        if (index == 0 && size > 1) {
            T element = first.element;
            first = first.next;
            size--;
            return element;
        }
        if (index == size - 1) {
            last.prev.next = null;
            last = last.prev;
            T t = last.element;
            size--;
            return t;
        }
        Node<T> currentNode;
        if (index <= size / 2) {
            int count = 0;
            currentNode = first;
            while (count != index) {
                currentNode = currentNode.next;
                count++;
            }
        } else {
            int count = size - 1;
            currentNode = last;
            while (count != index) {
                currentNode = currentNode.prev;
                count--;
            }
        }
        currentNode.prev.next = currentNode.next;
        currentNode.next.prev = currentNode.prev;
        size--;
        return currentNode.element;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        while (currentNode != null) {
            if (object == null) {
                if (currentNode.element == null) {
                    removeNode(currentNode);
                    return true;
                }
            } else {
                if (object.equals(currentNode.element)) {
                    removeNode(currentNode);
                    return true;
                }
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

    private void addFirstNode(T element) {
        first = new Node<>(element, null, null);
        last = first;
        size++;
    }

    private void addToTop(T value) {
        Node<T> newNode = new Node<>(value, first, null);
        first.prev = newNode;
        first = newNode;
        size++;
    }

    private void addNodeToEnd(T value) {
        Node<T> newNode = new Node<>(value, null, last);
        last.next = newNode;
        last = newNode;
        size++;
    }

    private void removeNode(Node<T> node) {
        if (node.prev == null) {
            first = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            last = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
    }
}
