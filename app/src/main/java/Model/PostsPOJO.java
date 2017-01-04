package Model;

import java.io.Serializable;

/**
 * Created by kuush on 1/4/2017.
 */

public class PostsPOJO implements Serializable {

    public String PostId;
    public String PostName;

    public String getPostName() {
        return PostName;
    }

    public void setPostName(String postName) {
        PostName = postName;
    }

    public String getPostId() {
        return PostId;
    }

    public void setPostId(String postId) {
        PostId = postId;
    }





}
