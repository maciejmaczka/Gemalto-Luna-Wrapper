/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lunawrapper;


import com.safenetinc.luna.*;
import com.safenetinc.luna.provider.LunaProvider;
import java.security.*;
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
public class LunaWrapper {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Security.addProvider(new com.safenetinc.luna.provider.LunaProvider()); 
       
        HSMConnection connection = new HSMConnection();
        file_handler fh = new file_handler();
        
        
        
        
        connection.initialize_connection();
        connection.key_store();
        
       
        System.out.println("LUNA:  New Key that will be wrapped ToWrap:  ");
      //  connection.key_gen("ToWrap", "AES");
        
       
      //  System.out.println("Java: New wrapping Key ToWrap:  ");
      //  connection.key_gen_software("WRAPPING Key");
    
        
        
        //connection.wrap("WRAPPING Key", "ToWrap");
        
        byte[] wrapped_key;
        byte[] key_from_file;
        wrapped_key = connection.wrap( "ToWrap" ,"ToWrap" );
        
        System.out.println("\nWRAPPED KEY: ");
        
         
        for (int i = 0 ; i < wrapped_key.length ; i++)
        {
            
            
            System.out.print( wrapped_key[i]); 
          
            
        }
        
    //    System.out.println("\nto Unwrap KEY: ");
        

       fh.write_to_file("WWW", wrapped_key);
       key_from_file = fh.read_from_file("WWW");
   
        
     
       
       for (int i = 0 ; i < key_from_file.length ; i++)
        {
            
        
            System.out.print( key_from_file[i]); 
          
            
        }

       
       connection.unwrap(key_from_file,  "ToWrap"  , "AES128");
       
 
       connection.test();
         
        connection.test();
    }
        
    
    
    
    
}
