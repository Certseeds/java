package dao;

import model.Document;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

/**
 * The type Text dao.
 */
public class TextDao {

    /**
     * The Sql2o's database connection.
     * build once can use multiply times, save time.
     */
    static Sql2o sql2o = new Sql2o("jdbc:sqlite:demo.sqlite3", null, null);

    /**
     * Initial.
     * create table if not exists.
     *
     * @throws ClassNotFoundException the class not found exception
     */
    public static void initial() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        final String initial_sql = "CREATE TABLE IF NOT EXISTS DOCUMENTS (" +
                "Hash_MD5 TEXT NOT NULL UNIQUE," +
                "local_Path TEXT NOT NULL);";
        try (Connection con = sql2o.open()) {
            con.createQuery(initial_sql).executeUpdate();
        }
    }

    /**
     * Insert a document to database.
     *
     * @param doc the Document that will insert to database.
     */
    public static void insert(Document doc) {
        final String insertSQL = "INSERT INTO DOCUMENTS (Hash_MD5,local_Path) values (:md5, :path);";
        try (Connection con = sql2o.open()) {
            con.createQuery(insertSQL)
                    .addParameter("md5", doc.getHash_MD5())
                    .addParameter("path", doc.getLocal_Path())
                    .executeUpdate();
            // Then replace the variables with actual values
        }
    }

    /**
     * Find md5 exists or not.
     *
     * @param md5 the md5.
     * @return the boolean<br></br>
     * <p>if the md5 in database, return false<br></br>
     * if the md5 not in database, return true.
     */
    public static boolean find_md5(String md5) {
        String findSQL = "SELECT Hash_MD5,local_Path FROM DOCUMENTS WHERE Hash_MD5 = :md5;";
        List<Document> doc_list = null;
        try (Connection con = sql2o.open()) {
            doc_list = con.createQuery(findSQL)
                    .addParameter("md5", md5)
                    .executeAndFetch(Document.class);
        }
        return doc_list.isEmpty();
    }

    /**
     * Gets the Document that have the md5.
     *
     * @param md5 the md5, A String.
     * @return the Document that match the md5
     * Can be null, but after test it will not be null.
     */
    public static Document get_md5(String md5) {
        String findSQL = "SELECT Hash_MD5,local_Path FROM DOCUMENTS WHERE Hash_MD5 = :md5;";
        List<Document> doc_list = null;
        try (Connection con = sql2o.open()) {
            doc_list = con.createQuery(findSQL)
                    .addParameter("md5", md5)
                    .executeAndFetch(Document.class);
        }
        if (doc_list.isEmpty()) {
            return null;
        }
        return doc_list.get(0);
    }

    /**
     * static method,
     * Gets all documents from database.
     *
     * @return the List of all Documents in database.
     * it can not be null, even is empty.
     */
    public static List<Document> get_files() {
        String find_all_SQL = "SELECT Hash_MD5,local_Path FROM DOCUMENTS;";
        List<Document> doc_list = null;
        try (Connection con = sql2o.open()) {
            doc_list = con.createQuery(find_all_SQL)
                    .executeAndFetch(Document.class);
        }
        return doc_list;
    }

    /**
     * Delete sql.
     * used for test files, do not use it at any other case!!!.
     */
    public static void delete_sql() {
        String delete_database_SQL = "DROP TABLE IF EXISTS DOCUMENTS ;";
        try (Connection con = sql2o.open()) {
            con.createQuery(delete_database_SQL).executeUpdate();
        }
    }
}
