package project2_berhow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Library implements Serializable {

//    private ArrayList<MediaItem> library = new ArrayList<>();
    private ObservableList<MediaItem> library = FXCollections.observableArrayList();
    
    public ObservableList<MediaItem> getCollection(){
         return library;
     }
    
    //Makes a file if one does not exist, if one does exist, it loads it into ArrayList<MediaItem> library
    public Library() throws Exception {
        File libraryFile = new File("Library.bin");
        if(libraryFile.exists()){
            try{
            readBinFile();
            }catch(Exception e){
                throw new Exception(e.getMessage());
            }
        }
    }


    public void readBinFile() throws Exception {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("Library.bin")));
        ArrayList temp = (ArrayList<MediaItem>) ois.readObject();
        library.addAll(temp);
        ois.close();
    } 

    public void writeBinFile() throws Exception {
        ArrayList<MediaItem> temp = new ArrayList<>();
        temp.addAll(this.library);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("Library.bin")));
        oos.writeObject(temp);
        oos.close();
    }
    
    //adds new media to the arrayList                                           
    public void newMedia(MediaItem media) throws Exception {
        if (!library.contains(media) && !(media.getMediaName().equals("")) && !(media.getMediaType().equals(""))) {
            library.add(media);
//            writeBinFile(library);
        } else if(library.contains(media)){
            throw new Exception(media.getMediaName() + " already exists");
        } else if(media.getMediaName().equals("")){
            throw new Exception("Please enter a media name");
        } else{
            throw new Exception("Please enter media type");
        }
    }

    //loan media 
    public void loanMedia(MediaItem media, String rentersName, String date) throws Exception {//not working fix exception
        for (int i = 0; i < library.size(); i++) {
            if (library.get(i).getMediaName().startsWith(media.getMediaName())) {
                if (!library.get(i).getloan() && !date.equals("")) {
                    library.get(i).setDate(date);
                    library.get(i).setLoan(true);
                    library.get(i).setRentersName(rentersName);
                    return;
                } else if (library.get(i).getloan()){
                    throw new Exception(media.getMediaName() + " is already on loan");
                } else if(rentersName.equals("")){
                    throw new Exception("You must enter the renters name");
                }
                
                else{
                    throw new Exception("You must enter a date");
                }
            }
        }
    }

    //return media to the arrayList
    public void returnMedia(MediaItem media) throws Exception {
        for (int i = 0; i < library.size(); i++) {
            if (library.get(i).getMediaName().startsWith(media.getMediaName())) {
                if (library.get(i).getloan()) {
                    library.get(i).setDate(null);
                    library.get(i).setLoan(false);
                    library.get(i).setRentersName(null);
                    return;
                } else {
                    throw new Exception(media.getMediaName() + " is not on loan");
                }
            }
        }
    }

    //display the arrayList
    public void displayMediaLibrary() {
        System.out.println("\n\n__________________________________________________________\nCurrent Media:\n");
        for (int i = 0; i < library.size(); i++) {
            System.out.println("\t" + library.get(i));
        }
        System.out.println("__________________________________________________________\n\n");
    }

    //removes media from the arrayList                                          
    public void removeMedia(MediaItem media) throws Exception {                 
        if (library.contains(media)) {
            library.remove(media);
        } else{
            throw new Exception(media.getMediaName() + " does not exist.");
        }
    }

                              


}
