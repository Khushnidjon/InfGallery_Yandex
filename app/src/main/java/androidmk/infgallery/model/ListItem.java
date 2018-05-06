package androidmk.infgallery.model;

/**
 * Created by Xushnidjon on 4/28/2018.
 */

public class ListItem {
    private String Description;
    private String Size;
    private String ImageURL;

    public ListItem() {
    }

    public ListItem(String description, String size, String imageURL) {
        Description = description;
        Size = imageSize(Long.parseLong(size));
        ImageURL = imageURL;
    }

    //setters
    public void setDescription(String description) {
        Description = description;
    }

    public void setSize(String size) {
        Size = size;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    //getters
    public String getDescription() {
        return Description;
    }

    public String getSize() {
        return Size;
    }

    public String getImageURL() {
        return ImageURL;
    }

    //Finding size image accurately
    private static String imageSize(long trafficPrint) {

        if ((trafficPrint / 1000000) > 0) {
            return (trafficPrint / 1000000 + " MB");
        } else if ((trafficPrint / 1000) > 0) {
            return (trafficPrint / 1000 + " kB");
        } else {
            return (trafficPrint + " byte");
        }
    }
}
