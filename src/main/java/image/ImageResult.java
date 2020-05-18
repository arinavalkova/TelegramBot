
package image;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageResult {

    @SerializedName("result")
    @Expose
    private Result result;

    public Result getResult() {
        return result;
    }

}
