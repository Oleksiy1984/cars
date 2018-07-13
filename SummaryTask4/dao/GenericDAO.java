package ua.nure.orlovskyi.SummaryTask4.dao;

import java.sql.Connection;


public abstract class GenericDAO<T> {

    protected final String tableName;
    protected Connection con;

    protected GenericDAO(Connection con, String tableName) {
        this.tableName = tableName;
        this.con = con;
    }

}
