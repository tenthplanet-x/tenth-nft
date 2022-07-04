package com.tenth.nft.app.security;

public class SimpleLockManger {

    private SimpleLock[] locks;

    public SimpleLockManger(int locks) {
        this.locks = new SimpleLock[locks];
        for(int i = 0; i < locks; i++){
            this.locks[i] = new SimpleLock();
        }
    }

    public SimpleLock get(Long key) {
        int factor = (int)(key % locks.length);
        return locks[factor];
    }
}
