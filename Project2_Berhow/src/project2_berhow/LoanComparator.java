package project2_berhow;

import java.util.Comparator;

public class LoanComparator implements Comparator<MediaItem> {
        @Override
    public int compare(MediaItem t, MediaItem t1) {
        if ((t.getloan()&& t1.getloan()) || (!t.getloan() && !t1.getloan()))
            return 0;
        else if (t.getloan() && !t1.getloan())
            return -1;
        else return 1;
    }
    
}
