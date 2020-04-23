package com.gmail.goyter012.Computing_Processes.Lab1;

public class MemoryAllocator {
    private Memory mem;

    public Memory getMemory() {
        return mem;
    }

    public MemoryAllocator(Memory mem) {

        if (mem.getCapacity() < 9) {
            System.out.println("Error! Memory should be mote than 9.");
        } else {
            this.mem = mem;
            for (int i = 0; i < mem.getCapacity(); i++) {
                mem.getRam()[i] = (int) (Math.random() * 10);
            }
            initFirstBlock();
            System.out.println("Memory allocator created");
        }
    }

    public int memAlloc(int size) {
        int tmpMemPoint = 3;
        while (tmpMemPoint < mem.getCapacity() - mem.getHeadersCapacity()) {
            if (mem.getRam()[tmpMemPoint] == 1) {
                if ((mem.getRam()[tmpMemPoint + 2] == size)
                        || (mem.getRam()[tmpMemPoint + 2] >= size + mem.getHeadersCapacity())) {
                    mem.getRam()[tmpMemPoint] = 0; // busy
                    if (mem.getRam()[tmpMemPoint + 2] == size) {
                        break;
                    }
                    mem.getRam()[tmpMemPoint + size + mem.getHeadersCapacity()] = 1;
                    mem.getRam()[tmpMemPoint + size + mem.getHeadersCapacity() + 1] = size;
                    mem.getRam()[tmpMemPoint + size + mem.getHeadersCapacity() + 2] = mem.getRam()[tmpMemPoint + 2]
                            - size - 3;

                    mem.getRam()[tmpMemPoint + mem.getRam()[tmpMemPoint + 2] + 4] = mem.getRam()[tmpMemPoint + 2]
                            - size - mem.getHeadersCapacity();

                    mem.getRam()[tmpMemPoint + 2] = size;
                    break;
                } else {
                    tmpMemPoint += mem.getRam()[tmpMemPoint + 2] + mem.getHeadersCapacity();
                }
            } else {
                tmpMemPoint += mem.getRam()[tmpMemPoint + 2] + mem.getHeadersCapacity();
            }
        }
        if (tmpMemPoint < mem.getCapacity() - mem.getHeadersCapacity()) {
            return tmpMemPoint;
        } else {
            return -1;
        }
    }

    public void memFree(int addr) {
        if (mem.getRam()[addr] == 0) {
            mem.getRam()[addr] = 1;
            if (mem.getRam()[addr - mem.getRam()[addr + 1] - mem.getHeadersCapacity()] == 1) {
                mem.getRam()[addr - mem.getRam()[addr + 1] - 1] += mem.getRam()[addr + 2] + mem.getHeadersCapacity();
                mem.getRam()[addr + mem.getRam()[addr + 2] + mem.getHeadersCapacity() + 2] = mem.getRam()[addr
                        - mem.getRam()[addr + 1] - 1];
                addr = addr - mem.getRam()[addr + 1] - mem.getHeadersCapacity();
            }
            if (mem.getRam()[addr + mem.getRam()[addr + 2] + mem.getHeadersCapacity()] == 1) {
                mem.getRam()[addr + 2] += mem.getRam()[addr + mem.getRam()[addr + 2] + mem.getHeadersCapacity() + 2]
                        + mem.getHeadersCapacity();
                mem.getRam()[addr + mem.getRam()[addr + 2] + mem.getHeadersCapacity() + 2] = mem.getRam()[addr + 2];
            }
        }
    }



    public int memRealloc(int addr, int size) {
        int oldSize = mem.getRam()[addr + 2];
        if (addr == -1) {
            return memAlloc(size);
        } else {
            if (size == mem.getRam()[addr + 2]) {
                return addr;
            } else {

                if (size <= mem.getRam()[addr + 2] - mem.getHeadersCapacity()) {

                    mem.getRam()[addr + size + mem.getHeadersCapacity()] = 1;
                    mem.getRam()[addr + size + mem.getHeadersCapacity() + 1] = size;
                    mem.getRam()[addr + size + mem.getHeadersCapacity() + 2] = mem.getRam()[addr + 2] - size - mem.getHeadersCapacity();
                    mem.getRam()[addr + mem.getRam()[addr + 2] + mem.getHeadersCapacity() + 1] = mem.getRam()[addr + 2]
                            - size - mem.getHeadersCapacity();
                    mem.getRam()[addr + 2] = size;
                    memFree(addr + mem.getRam()[addr + 2] + mem.getHeadersCapacity());
                    return addr;
                } else {

                    if ((mem.getRam()[addr + mem.getRam()[addr + 2] + mem.getHeadersCapacity()] == 1)
                            && (size <= mem.getRam()[addr + 2]
                            + mem.getRam()[addr + mem.getRam()[addr + 2] + mem.getHeadersCapacity() + 2])) {
                        mem.getRam()[addr + 2] += mem.getRam()[addr + mem.getRam()[addr + 2] + mem.getHeadersCapacity()
                                + 2]
                                + mem.getHeadersCapacity();
                        mem.getRam()[addr + mem.getRam()[addr + 2] + mem.getHeadersCapacity() + 1] = mem.getRam()[addr + 2];
                        mem.getRam()[addr + size + mem.getHeadersCapacity()] = 1;
                        mem.getRam()[addr + size + mem.getHeadersCapacity() + 1] = size;

                        mem.getRam()[addr + size + mem.getHeadersCapacity() + 2] = mem.getRam()[addr + 2] - size
                                - mem.getHeadersCapacity();
                        mem.getRam()[addr + mem.getRam()[addr + 2] + mem.getHeadersCapacity() + 2] = mem.getRam()[addr + 2]
                                - size - mem.getHeadersCapacity();
                        mem.getRam()[addr + 2] = size;
                        memFree(addr + mem.getRam()[addr + 2] + mem.getHeadersCapacity());
                        return addr;
                    } else {
                        memFree(addr);
                        int newAddr = memAlloc(size); // виділяємо новий блок
                        if (newAddr != -1) {
                            for (int i = 0; i < oldSize; i++) {
                                mem.getRam()[newAddr + i + mem.getHeadersCapacity()] = mem.getRam()[addr + i + mem.getHeadersCapacity()];
                            }
                            return newAddr;
                        } else {
                            if (mem.getRam()[addr + oldSize + mem.getHeadersCapacity()] == 1) {
                                mem.getRam()[addr + mem.getRam()[addr + 2] + mem.getHeadersCapacity() + 2] = mem.getRam()[addr + 2]
                                        - oldSize - mem.getHeadersCapacity();
                                mem.getRam()[addr + 2] = oldSize;
                            }
                            if (mem.getRam()[addr - mem.getRam()[addr + 1] - 2] == 1) {
                                mem.getRam()[addr - mem.getRam()[addr + 1] - 1] = oldSize;
                            }
                            return -1;
                        }
                    }
                }
            }
        }
    }

    private void initFirstBlock(){
        mem.getRam()[0] = 0;
        mem.getRam()[1] = 0;
        mem.getRam()[2] = 0;
        mem.getRam()[3] = 1;
        mem.getRam()[4] = 0;
        mem.getRam()[5] = mem.getCapacity() - 3 * mem.getHeadersCapacity();
        mem.getRam()[mem.getCapacity() - 3] = 0;
        mem.getRam()[mem.getCapacity() - 2] = mem.getCapacity() - 3 * mem.getHeadersCapacity();
        mem.getRam()[mem.getCapacity() - 1] = 0;
    }

}