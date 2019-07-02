package com.jialing.swift.hashmap;

import java.util.ArrayList;
import java.util.List;

public class MyHashMap<K, V> implements MyMap<K, V> {

    private static int defaultLength = 1 << 3;
    private static double defaultAddSizeFactor = 0.75;
    private int useSize;

    private Entry<K, V>[] table = null;

    public MyHashMap() throws IllegalAccessException {
        this(defaultLength, defaultAddSizeFactor);
    }

    public MyHashMap(int length, double defaultAddSizeFactor) throws IllegalAccessException {

        if (length < 0) {
            throw new IllegalAccessException("参数不能为负数：" + length);
        }
        if (defaultAddSizeFactor <= 0 || Double.isNaN(defaultAddSizeFactor)) {
            throw new IllegalAccessException("扩容标准必须是大于0的数字：" + defaultAddSizeFactor);
        }
        this.defaultLength = length;
        this.defaultAddSizeFactor = defaultAddSizeFactor;
        table = new Entry[defaultLength];
    }

    @Override
    public V put(K k, V v) {
        if(useSize > defaultAddSizeFactor * defaultLength) {
            up2Size();
        }
        int index = getIndex(k, table.length);
        Entry<K, V> entry = table[index];
        if (null == entry) {
            table[index] = new Entry<>(k, v, null);
            useSize++;
        } else if (null != entry) {
            table[index] = new Entry<>(k, v, entry);
        }
        return table[index].getValue();
    }


    private void up2Size() {
        System.out.println();
        print();
        System.out.println("----------------reset-----------");
        Entry<K, V>[] newTable = new Entry[2 * defaultLength];
        againHash(newTable);
    }

    private void againHash(MyHashMap<K, V>.Entry<K, V>[] newTable) {
        List<Entry<K, V>> entryList = new ArrayList<>();
        for (int i = 0; i < table.length; i++) {
            if (null == table[i]) {
                continue;
            }
            findEntry(entryList, table[i]);
        }
        if (entryList.size() <= 0) {
            return;
        }
        defaultLength = 2 * defaultLength;
        table = newTable;
        useSize = 0;
        for (Entry<K, V> entry : entryList) {
            this.put(entry.k, entry.v);
        }
    }

    private void findEntry(List<Entry<K, V>> entryList, Entry<K, V> entry) {
        entryList.add(entry);
        if (null == entry.next) {
            return;
        }
        findEntry(entryList, entry.next);
    }

    private void findEntry(Entry<K, V> entry) {
        System.out.print(entry.k + "->" + entry.v + ", ");
        if (null == entry.next) {
            return;
        }
        findEntry(entry.next);
    }


    @Override
    public V get(K k) {
        int index = getIndex(k, table.length);
        if (table[index] == null) {
            throw new NullPointerException();
        }
        return findValueByEqualKey(k, table[index]);
    }

    private V findValueByEqualKey(K k, MyHashMap<K, V>.Entry<K, V> entry) {
        if (k == entry.getKey() || k.equals(entry.getKey())) {
            return entry.getValue();
        } else if (entry.next != null) {
            return findValueByEqualKey(k, entry.next);
        }
        return null;
    }


    private int hash(int hashCode) {
        hashCode = hashCode ^ ((hashCode >>> 20) ^ (hashCode >>> 12));
        return hashCode ^ ((hashCode >>> 7) ^ (hashCode >>> 4));
    }

    public void print() {
        System.out.println();
        System.out.println("table.length:" + table.length + ", useSize:" + useSize);
        for (int i = 0; i < table.length; i++) {
            if (null == table[i]) {
                continue;
            }
            System.out.println();
            System.out.println("index:" + i + ", hashCode:" + getIndex(table[i].k, table.length));
            findEntry(table[i]);
        }
    }

    private int getIndex(K k, int length) {
        int m = length - 1;
        int index = hash(k.hashCode()) & m;
        return index;
    }

    class Entry<K, V> implements MyMap.Entry<K, V> {
        K k;
        V v;

        Entry<K, V> next;

        public Entry(K k, V v, Entry<K, V> next) {
            this.k = k;
            this.v = v;
            this.next = next;
        }

        @Override
        public K getKey() {
            return k;
        }

        @Override
        public V getValue() {
            return v;
        }
    }
}
