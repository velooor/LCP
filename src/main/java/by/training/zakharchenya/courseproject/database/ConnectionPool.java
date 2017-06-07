package by.training.zakharchenya.courseproject.database;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** Class is responsible for presenting connections to the application.
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class ConnectionPool {
    private static final Logger LOG = LogManager.getLogger();

    private static ConnectionPool pool;
    private static DBInitializer initializer;
    private Queue<ProxyConnection> connections;
    private static AtomicBoolean instanceCreated = new AtomicBoolean(false);
    private static AtomicBoolean poolClosed = new AtomicBoolean(false);
    private static Lock singletonLock = new ReentrantLock(true);
    private static Lock closePoolLock = new ReentrantLock(true);
    private static int realPoolSize = 0;

    /**
     * Private constructor. Is unavailable outside the class
     */
    private ConnectionPool() {
        initializer = new DBInitializer();
        connections = new ArrayBlockingQueue<>(initializer.POOL_SIZE, true);
        try {
            Class.forName(initializer.DRIVER).newInstance();
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException e) {
            LOG.log(Level.FATAL, "Can't load database driver.", e);
            throw new RuntimeException(e);
        }
        try{
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch(SQLException e){
            LOG.log(Level.ERROR, "Problems with driver registration.",e);
        }

        connect();
        if (realPoolSize < initializer.POOL_SIZE) {
            reconnect();
        }
        if (realPoolSize < 1) {
            LOG.log(Level.FATAL, "Problems with database. Can't initialize connections.");
            throw new RuntimeException("Problems with database. Can't initialize connections.");
        }
        LOG.log(Level.INFO, "Real connection pool size : " + realPoolSize);
    }

    /**
     * Process reconnection to database.
     */
    private void reconnect() {
        try {
            for (int i = 0; i < initializer.RECONNECTION_AMOUNT && realPoolSize < initializer.POOL_SIZE; ++i) {
                TimeUnit.MILLISECONDS.sleep(initializer.RECONNECTION_TIMEOUT);
                LOG.log(Level.INFO, i + " reconnection.");
                connect();
            }
        } catch (InterruptedException e) {
            LOG.log(Level.ERROR, "Problems with reconnection to database.", e);
        }
    }

    /**
     * Process connection to database.
     */
    private void connect() {
        int needConnections = initializer.POOL_SIZE - realPoolSize;
        for (int i = 0; i < needConnections; ++i) {
            try {
                Connection connection = DriverManager.getConnection(initializer.URL, initializer.LOGIN, initializer.PASSWORD);
                ++realPoolSize;
                connections.add(new ProxyConnection(connection));
                LOG.log(Level.INFO, realPoolSize + " connection is initialized in connection pool.");

            } catch (SQLException e) {
                LOG.log(Level.ERROR, "Connection haven't been added to the connection pool.",e);
            }
        }
    }

    /**
     * Realize this class as singleton.
     * @return Connection pool object
     */
    public static ConnectionPool getInstance() {
        if (!instanceCreated.get()) {
            singletonLock.lock();
            try {
                if (pool == null) {
                    pool = new ConnectionPool();
                    instanceCreated.set(true);
                }
            } finally {
                singletonLock.unlock();
            }
        }
        return pool;
    }

    /**Gives connection from connection pool.
     * @return Connection object
     */
    public Connection getConnection() {
        Connection connection = null;
        if (!poolClosed.get()) {
            closePoolLock.lock();
            if (!poolClosed.get()) {
                connection = connections.poll();
                closePoolLock.unlock();
            }
        }
        return connection;
    }

    /**
     * Returns connection to pool.
     */
    void closeConnection(ProxyConnection connection) {
        connections.add(connection);
    }


    /**
     * Closes pool.
     */
    public void closePool() {
        if (!poolClosed.get()) {
            closePoolLock.lock();
            try {
                if (!poolClosed.get()) {
                    poolClosed.set(true);
                    for (int i = 0; i < realPoolSize; ++i) {
                        connections.poll().finalClose();
                        LOG.log(Level.INFO, i + 1 + " connection is closed.");
                    }
                }
            } catch (SQLException  e) {
                LOG.log(Level.ERROR, "Problems with closing pool of connections.", e);
            } finally {
                closePoolLock.unlock();
            }
        }
    }

}
