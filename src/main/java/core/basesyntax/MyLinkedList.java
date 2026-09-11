package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size = 0;
    
    static class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private T value;
        
        public Node(Node<T> prev, Node<T> next, T value) {
            this.next = next;
            this.prev = prev;
            this.value = value;
        }
    }
    
    @Override
    public void add(T value) {
        Node<T> node;
        
        if (size == 0) {
            node = new Node<>(null, null, value);
            first = node;
        } else {
            node = new Node<>(last, null, value);
            last.next = node;
        }
        
        last = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (isIndexIsOutOfBounds(index)) {
            throw new IndexOutOfBoundsException();
        }
        
        if (index == size) {
            add(value);
            return;
        }
        
        Node<T> node;
        
        if (index == 0) {
            node = new Node<>(null, first, value);
            first.prev = node;
            first = node;
            size++;
            return;
        }
        
        Node<T> current = getNodeByIndex(index);
        
        node = new Node<>(current.prev, current,value);
        current.prev.next = node;
        current.prev = node;
        size++;
    }
    
    @Override
    public void addAll(List<T> list) {
        for (T item: list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        if (isElementIndexOutOfBounds(index)) {
            throw new IndexOutOfBoundsException();
        }
        
        Node<T> node = getNodeByIndex(index);
        
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        if (isElementIndexOutOfBounds(index)) {
            throw new IndexOutOfBoundsException();
        }
        
        Node<T> current = getNodeByIndex(index);
        
        T removedValue = current.value;
        current.value = value;
        
        return removedValue;
    }

    @Override
    public T remove(int index) {
        if (isElementIndexOutOfBounds(index)) {
            throw new IndexOutOfBoundsException();
        }
        
        Node<T> current = getNodeByIndex(index);
        T removedValue = current.value;
        unlink(current);
        size--;
        
        return removedValue;
    }
    
    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        
        for (int i = 0; i < size; i++) {
            if (Objects.equals(current.value, object)) {
                unlink(current);
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
    
    private boolean isElementIndexOutOfBounds(int index) {
        return index >= size || index < 0;
    }
    
    private boolean isIndexIsOutOfBounds(int index) {
        return index > size || index < 0;
    }
    
    private void unlink(Node<T> node) {
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
    }
    
    private Node<T> getNodeByIndex(int index) {
        if (index < size / 2) {
            Node<T> node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            
            return node;
        } else {
            Node<T> node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            
            return node;
        }
    }
    
}
