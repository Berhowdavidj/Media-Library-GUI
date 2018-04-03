package project2_berhow;

import java.util.Comparator;

public class DateLoanedComparator implements Comparator<MediaItem>{

    @Override
    public int compare(MediaItem t, MediaItem t1) {
        if (t.getloan()&& t1.getloan()){
            int dateComp = t.getDate().compareTo(t1.getDate());
            if (dateComp != 0){
                return dateComp;
            } else {
                return t.getMediaName().compareTo(t1.getMediaName());
            }
        }   
        return t.getMediaName().compareTo(t1.getMediaName());
    }
    
    
}
