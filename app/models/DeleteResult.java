package models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Reist on 25.11.15.
 */
public class DeleteResult {

    @JsonProperty("num_of_deleted")
    private int numOfDeleted;

    public int getNumOfDeleted() {
        return numOfDeleted;
    }

    public void setNumOfDeleted(int numOfDeleted) {
        this.numOfDeleted = numOfDeleted;
    }

}
