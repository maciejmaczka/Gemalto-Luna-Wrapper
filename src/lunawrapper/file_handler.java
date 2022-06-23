/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lunawrapper;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.*;
import java.lang.*;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
/**
 *
 * @author Maciej
 */
public class file_handler {
    
    public void write_to_file(String file, byte[] wrapped_key)
    {
        
        try
        {
            
            
        FileOutputStream fos = new FileOutputStream(file);
        int abc = 0;
        for (int i = 0 ; i < wrapped_key.length ; i++)
        {
            
            abc = wrapped_key[i];
            System.out.print(abc); 
            fos.write( wrapped_key[i]);
            
        }
        
        System.out.println("");
        fos.close();
        
        
        
        
        }
        catch (Exception e)
       {
            
            System.out.println("Error: " + e.getMessage());
           
            
      }
        
        
        
        
    }
    
    
    public byte[] read_from_file(String file)
    {
        try
        {
             
        int data = 0;
        int key_length = 0;
        byte[] key_from_file = new byte[16];
        
        FileInputStream fis = new FileInputStream(file);
        key_length = fis.read(key_from_file);
        
        
        
        
         for (int i = 0 ; i < key_length ; i++)
        {
            
            data = key_from_file[i] ;
            System.out.print(data ); 
          
            
        }
         
        
        
       
        System.out.println("");
        return key_from_file;
        
        }
        catch (Exception e)
        {
            
            System.out.println("Error: " + e.getMessage());
         
            return null;
            
        }
        
        
   
    }
    
    
    
}
