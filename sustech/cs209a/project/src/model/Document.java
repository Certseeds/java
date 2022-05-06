package model;

/**
 * The type Document.
 * This class mainly use for transfer the md5 and it's file_path and corresponding with database.
 */
public class Document {

    /**
     * The Hash md .
     */
    String Hash_MD5;
    /**
     * The Local path.
     */
    String local_Path;

    /**
     * Instantiates a new Document.
     */
    public Document() {
        this.Hash_MD5 = "";
        this.local_Path = "";
    }

    /**
     * Instantiates a new Document.
     *
     * @param Md5 the md5 String.
     */
    public Document(String Md5) {
        this.Hash_MD5 = Md5;
        this.local_Path = "";
    }

    /**
     * Instantiates a new Document.
     *
     * @param Md5        the md 5, @NotNull
     * @param local_path the local path, @NotNull
     */
    public Document(String Md5, String local_path) {
        this.Hash_MD5 = Md5;
        this.local_Path = local_path;
    }

    /**
     * Gets hash md 5.
     *
     * @return the hash md 5
     */
    public String getHash_MD5() {
        return Hash_MD5;
    }

    /**
     * Sets hash md 5.
     *
     * @param hash_MD5 the hash md 5
     */
    private void setHash_MD5(String hash_MD5) {
        Hash_MD5 = hash_MD5;
    }

    /**
     * Gets local path.
     *
     * @return the local path
     */
    public String getLocal_Path() {
        return local_Path;
    }

    /**
     * Sets local path.
     *
     * @param local_Path the local path
     */
    private void setLocal_Path(String local_Path) {
        this.local_Path = local_Path;
    }

    @Override
    public String toString() {
        return "Document{" +
                "Hash_MD5='" + this.getHash_MD5() + '\'' +
                ", local_Path=" + this.getLocal_Path() + '}';

    }


}
