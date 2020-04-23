package com.gmail.goyter012.Computing_Processes;

import com.gmail.goyter012.Computing_Processes.Lab1.Memory;
import com.gmail.goyter012.Computing_Processes.Lab1.MemoryAllocator;

public class Main {
    public static void main(String[] args) {

        Memory memory = new Memory(1000);
        MemoryAllocator allocator = new MemoryAllocator(memory);
        System.out.println("Пам'ять на початку досліджень");
        allocator.memAlloc(5);
        int addressToFree = allocator.memAlloc(10);
        int addressToRealloc = allocator.memAlloc(10);
        System.out.println("Результат Виділення пам'яті");
        allocator.getMemory().toString();
        allocator.memFree(addressToFree);
        System.out.println("Результат Звільнення пам'яті по адресі " + addressToFree);
        allocator.getMemory().toString();
        allocator.memRealloc(addressToRealloc, 5);
        System.out.println("Результат Змінення блоку пам'яті по адресі " + addressToRealloc);
        allocator.getMemory().toString();



    }
}
