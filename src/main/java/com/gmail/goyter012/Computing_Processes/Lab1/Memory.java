package com.gmail.goyter012.Computing_Processes.Lab1;

import java.util.Arrays;

public class Memory {
    private int capacity;
    private int[] ram;
    private int headersCapacity = 3;

    public Memory(int capacity) {
        this.capacity = capacity;
        this.ram = new int[capacity];
    }

    public Memory() {
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int[] getRam() {
        return ram;
    }

    public void setRam(int[] ram) {
        this.ram = ram;
    }

    public int getHeadersCapacity() {
        return headersCapacity;
    }

    public void setHeadersCapacity(int headersCapacity) {
        this.headersCapacity = headersCapacity;
    }

    @Override
    public String toString() {
        int temp = 0;
        for (int i = 0; i < capacity; i++) {
            System.out.print(i + ") ");
            if (temp == i) {
                System.out.print("Зайнятий(0)/Вільний(1) ");
            } else {
                if (temp + 1 == i) {
                    System.out.print("Розмір попереднього блоку ");
                } else {
                    if (temp + 2 == i) {
                        System.out.print("Розмір наступного блоку ");
                        temp += ram[temp + 2] + headersCapacity;
                    } else {
                        System.out.print("  ");
                    }
                }
            }
            System.out.println("  " + ram[i]);
        }
        return "Memory{" +
                "capacity=" + capacity +
                ", ram=" + Arrays.toString(ram) +
                ", headersCapacity=" + headersCapacity +
                '}';
    }
}
