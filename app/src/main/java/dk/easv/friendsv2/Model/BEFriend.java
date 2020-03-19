package dk.easv.friendsv2.Model;



import java.io.Serializable;

public class BEFriend implements Serializable {

    private int m_id;
    private String m_name;
    private String m_phone;
    private Boolean m_isFavorite;
    private String m_image;

    public BEFriend(int id, String name, String phone, String image) {
        this(id, name, phone, false, image);
    }

    public BEFriend(String name, String phone, String image) {
        this(name, phone, false, image);
    }

    public BEFriend(int id, String name, String phone, Boolean isFavorite, String image) {
        m_id = id;
        m_name = name;
        m_phone = phone;
        m_isFavorite = isFavorite;
        m_image = image;
    }

    public BEFriend(String name, String phone, Boolean isFavorite, String image) {
        m_name = name;
        m_phone = phone;
        m_isFavorite = isFavorite;
        m_image = image;
    }

    public int getId() {
        return m_id;
    }

    public String getPhone() {
        return m_phone;
    }

    public String getImage() {
        return m_image;
    }

    public String getName() {
        return m_name;
    }

    public Boolean isFavorite() {
        return m_isFavorite;
    }

    public void setImage(String image) {
        this.m_image = image;
    }

    public int getId(){
        return m_id;
    }

    public void setId(int id){
        this.m_id = id;
    }

    public void setPhone(String phone) {
        this.m_phone = phone;
    }

    public void setName(String name) {
        this.m_name = name;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.m_isFavorite = isFavorite;
    }


}
