package com.cheng.views;

import java.io.File;  
import java.util.ArrayList;  
import java.util.List;  
  
import javax.swing.AbstractListModel;  
  
public class ImageListModel extends AbstractListModel<File> {  
  
    private static final long serialVersionUID = 1L;  
      
    private List<File> imageFile = new ArrayList<File>();  
  
    public void addElement(File file){  
        this.imageFile.add(file);  
    }  
      
    public int getSize() {  
        return imageFile.size();  
    }  
  
    public File getElementAt(int index) {  
        return imageFile.get(index);  
    }  
  
}  