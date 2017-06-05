package by.training.zakharchenya.courseproject.dao;

import java.sql.Connection;

/** Super class for all DAO.
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public abstract class AbstractDAO {
    protected Connection connection;
    /**@param connection java.sql.Connection
     */
    public AbstractDAO(Connection connection) {
        this.connection = connection;
    }

}
