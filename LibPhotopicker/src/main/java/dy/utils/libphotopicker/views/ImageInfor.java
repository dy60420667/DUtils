package dy.utils.libphotopicker.views;

import java.io.Serializable;

/**
 * Created by fy on 2016/9/13.
 */
public class ImageInfor implements Serializable {
    private String newPath;
    private String oldPath;

    public ImageInfor(){
        super();
    }

    public ImageInfor(String oldPath, String newPath){
        this.newPath = newPath;
        this.oldPath = oldPath;
    }

    public void setNewPath(String newPath) {
        this.newPath = newPath;
    }

    public void setOldPath(String oldPath) {
        this.oldPath = oldPath;
    }

    public String getNewPath() {
        return newPath;
    }

    public String getOldPath() {
        return oldPath;
    }
}
