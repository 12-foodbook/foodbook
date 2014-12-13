package th.ac.kmitl.it.foodbook.beans;

public class Kitchenware {
    
    private long kitchenware_id;
    private String name;
    private String photo_url;
    
    public String getPhoto_url() {
        return photo_url;
    }
    
    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }
    
    public long getKitchenware_id() {
        return kitchenware_id;
    }
    
    public void setKitchenware_id(long kitchenware_id) {
        this.kitchenware_id = kitchenware_id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
}
