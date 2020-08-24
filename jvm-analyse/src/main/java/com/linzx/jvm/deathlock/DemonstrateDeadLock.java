package com.linzx.jvm.deathlock;

import java.util.Random;

/**
 * 死锁
 */
public class DemonstrateDeadLock {

    private static final int NUM_THREADS = 5;
    private static final int NUM_ACCOUNTS = 5;
    private static final int NUM_ITERATIONS = 100000;

    /**
     * 线程死锁日志分析
     * Found one Java-level deadlock:
     * =============================
     * "Thread-4":
     *   waiting to lock monitor 0x000000002a9ba9e8 (object 0x0000000716168188, a com.linzx.jvm.deathlock.Account),
     *   which is held by "Thread-3"
     * "Thread-3":
     *   waiting to lock monitor 0x000000002e6602e8 (object 0x0000000716168158, a com.linzx.jvm.deathlock.Account),
     *   which is held by "Thread-1"
     * "Thread-1":
     *   waiting to lock monitor 0x000000002a9ba9e8 (object 0x0000000716168188, a com.linzx.jvm.deathlock.Account),
     *   which is held by "Thread-3"
     *
     * Java stack information for the threads listed above:
     * ===================================================
     * "Thread-4":
     *         at com.linzx.jvm.deathlock.DemonstrateDeadLock.transferMoney(DemonstrateDeadLock.java:50)
     *         - waiting to lock <0x0000000716168188> (a com.linzx.jvm.deathlock.Account)
     *         at com.linzx.jvm.deathlock.DemonstrateDeadLock$1TransferThread.run(DemonstrateDeadLock.java:33)
     * "Thread-3":
     *         at com.linzx.jvm.deathlock.DemonstrateDeadLock.transferMoney(DemonstrateDeadLock.java:52)
     *         - waiting to lock <0x0000000716168158> (a com.linzx.jvm.deathlock.Account)
     *         - locked <0x0000000716168188> (a com.linzx.jvm.deathlock.Account)
     *         at com.linzx.jvm.deathlock.DemonstrateDeadLock$1TransferThread.run(DemonstrateDeadLock.java:33)
     * "Thread-1":
     *         at com.linzx.jvm.deathlock.DemonstrateDeadLock.transferMoney(DemonstrateDeadLock.java:52)
     *         - waiting to lock <0x0000000716168188> (a com.linzx.jvm.deathlock.Account)
     *         - locked <0x0000000716168158> (a com.linzx.jvm.deathlock.Account)
     *         at com.linzx.jvm.deathlock.DemonstrateDeadLock$1TransferThread.run(DemonstrateDeadLock.java:33)
     *
     * Found 1 deadlock.
     */
    public static void main(String[] args) {
        final Random rnd = new Random();
        final Account[] accounts = new Account[NUM_ACCOUNTS];

        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = new Account();
        }
        class TransferThread extends Thread {
            @Override
            public void run() {
                for (int i = 0; i < NUM_ITERATIONS; i++) {
                    int fromAcct = rnd.nextInt(NUM_ACCOUNTS);
                    int toAcct = rnd.nextInt(NUM_ACCOUNTS);
                    int amount = rnd.nextInt(100);
                    try {
                        transferMoney(accounts[fromAcct], accounts[toAcct], amount, fromAcct, toAcct);
                    } catch (Exception e) {
                        System.out.println("发生异常-------" + e);
                    }
                }
            }
        }

        for (int i = 0; i < NUM_THREADS; i++) {
            new TransferThread().start();
        }
    }

    public static void transferMoney(Account fromAccount,Account toAccount,int amount,int from_index,int to_index) throws Exception {
        System.out.println("账户 "+  from_index+"~和账户~"+to_index+" ~请求锁");

        synchronized (fromAccount) {
            System.out.println("	账户 >>>"+from_index+" <<<获得锁");
            synchronized (toAccount) {
                System.out.println("		    账户     "+from_index+" & "+to_index+"都获得锁");
                if (fromAccount.compareTo(amount) < 0) {
                    throw new Exception();
                }else {
                    fromAccount.debit(amount);
                    toAccount.credit(amount);
                }
            }
        }
    }
}
