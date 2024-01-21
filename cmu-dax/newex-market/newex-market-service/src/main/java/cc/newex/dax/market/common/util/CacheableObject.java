package cc.newex.dax.market.common.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author newex-team
 * @date 2018/03/18
 */
public abstract class CacheableObject<T> {

    private volatile long lastModifyTime;

    private long validMillSeconds = 1000;

    private volatile T object;

    private final Lock writeLock = new ReentrantLock();

    public CacheableObject() {
    }

    public CacheableObject(final long validMillSeconds) {
        this.validMillSeconds = validMillSeconds;
    }

    protected boolean invalid() {
        return System.currentTimeMillis() - this.lastModifyTime > this.validMillSeconds;
    }

    private void build() {
        this.object = this.buildObject();
        this.lastModifyTime = System.currentTimeMillis();
    }

    protected abstract T buildObject();

    public T getObject() {
        if (!this.invalid()) {
            return this.object;
        }

        //init block lock
        if (this.object == null) {
            this.writeLock.lock();
            try {
                if (this.object == null) {
                    this.build();
                }
            } finally {
                this.writeLock.unlock();
            }
            return this.object;
        }

        //try lock
        if (!this.writeLock.tryLock()) {
            return this.object;
        }
        try {
            if (this.invalid()) {
                this.build();
            }
        } finally {
            this.writeLock.unlock();
        }
        return this.object;
    }

}
