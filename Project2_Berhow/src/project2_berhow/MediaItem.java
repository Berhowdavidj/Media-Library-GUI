package project2_berhow;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class MediaItem implements Serializable, Comparable<MediaItem> {

    private String mediaName;
    private String mediaType;
//    private Date dateModified;
    private String dateModified;
    private boolean loan;
    private String rentersName = null;

    public MediaItem(String mediaName/*, String mediaType*/) {
        this.mediaName = mediaName;
        
    }

    public String getMediaName() {
        return mediaName;
    }
    
    public String getMediaType(){
        return mediaType;
    }

//    public Date getDate() {
//        return dateModified;
//    }
    public String getDate() {
        return dateModified;
    }

    public boolean getloan() {
        return loan;
    }

    public String getRentersName() {
        return rentersName;
    }

    public void setDate(String date) {
//        this.dateModified = new Date();
        this.dateModified = date;
    }

    public void setLoan(boolean value) {
        this.loan = value;
    }

    public void setRentersName(String rentersName) {
        this.rentersName = rentersName;
    }
    
    public void setMediaType(String mediaType){
        this.mediaType = mediaType;
    }

    @Override
    public String toString() {                                                  //need to fix the toSring
        if (loan == true && rentersName != null){
        return mediaName + "- " + mediaType + " (" + dateModified + " " + rentersName + ")";
        }else{
             return mediaName + "- " + mediaType;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.mediaName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
                
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MediaItem other = (MediaItem) obj;
        if (!Objects.equals(this.mediaName, other.mediaName)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(MediaItem t) {
        return this.mediaName.compareTo(t.getMediaName());
    }
    
    
}
