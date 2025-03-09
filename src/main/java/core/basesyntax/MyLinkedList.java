package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size = 0;

    @Override
    public void add(T value) {
        Node<T> newNode;
        if (last == null && first == null) {
            newNode = new Node<>(null, value, null);
            first = newNode;
        } else {
            newNode = new Node<>(last, value, null);
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (checkValidIndex(index)) {
            Node<T> newNode;
            if (index != 0) {
                Node<T> node = findByIndex(index);
                newNode = new Node<>(node.prev, value, node);
                node.prev.next = newNode;
                node.prev = newNode;
            } else {
                newNode = new Node<>(null, value, first);
                first.prev = newNode;
                first = newNode;
            }
            size++;
        } else {
            if (size == index) {
                add(value);
            } else {
                throw new IndexOutOfBoundsException();
            }
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list != null) {
            for (T element : list) {
                add(element);
            }
        }
    }

    @Override
    public T get(int index) {
        if (checkValidIndex(index)) {
            Node<T> node = findByIndex(index);
            return node.item;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public T set(T value, int index) {
        T t = null;
        if (checkValidIndex(index)) {
            Node<T> node = findByIndex(index);
            t = node.item;
            node.item = value;
        } else {
            throw new IndexOutOfBoundsException();
        }
        return t;
    }

    @Override
    public T remove(int index) {
        if (checkValidIndex(index)) {
            Node<T> node = findByIndex(index);
            if (size == 1) {
                first = null;
                last = null;
            }
            if (first != null) {
                if (!first.equals(node)) {
                    node.prev.next = node.next;
                } else {
                    node.next.prev = node.prev;
                    first = node.next;
                }
            }
            if (last != null) {
                if (!last.equals(node)) {
                    node.next.prev = node.prev;
                } else {
                    node.prev.next = node.next;
                    last = node.prev;
                }
            }
            size--;
            return node.item;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public boolean remove(T object) {
        if (first != null) {
            if (last == first) {
                remove(0);
                return true;
            }
            Node<T> currentNode = first;
            boolean isFound = false;
            for (int i = 0; i < size; i++) {
                if ((object == null && currentNode.item == null)) {
                    isFound = true;
                }
                if ((object != null) && object.equals(currentNode.item)) {
                    isFound = true;
                }
                if (isFound) {
                    if (!currentNode.equals(first)) {
                        currentNode.prev.next = currentNode.next;
                    } else {
                        currentNode.next.prev = currentNode.prev;
                        first = currentNode.next;
                    }
                    if (!currentNode.equals(last) && last != null) {
                        currentNode.next.prev = currentNode.prev;
                    } else {
                        currentNode.prev.next = currentNode.next;
                        last = currentNode.prev;
                    }
                    size--;
                    return true;
                } else {
                    currentNode = currentNode.next;
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
        return size <= 0;
    }

    private boolean checkValidIndex(int index) {
        return (index >= 0) && (index < size);
    }

    private Node<T> findByIndex(int index) {
        if (index == 0) {
            return first;
        }
        if (index == size - 1) {
            return last;
        }
        Node<T> currentNode = first;
        int i = 0;
        while (i < index) {
            currentNode = currentNode.next;
            i++;
        }
        return currentNode;
    }

    public static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
