/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lunawrapper;

import java.util.*;
import com.safenetinc.luna.*;
import com.safenetinc.luna.provider.LunaProvider;
import java.security.*;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec; 
import com.safenetinc.luna.LunaSlotManager;
import com.safenetinc.luna.provider.param.LunaGmacParameterSpec;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.io.ByteArrayInputStream;
import javax.crypto.Cipher;
import java.util.Random;
import javax.crypto.Mac;
/**
 *
 * @author Maciej
 */
public class HSMConnection 
{
    
   public LunaSlotManager slot_manager = null; 
   public String partition_password = "Pass12345";
   public String partition_label = "MAC"; 
   public KeyStore luna_keystore;
   public int slot = 0;
   
   public KeyGenerator keyGen = null;
   SecretKey aesKey = null;
   
   SecretKey wrapKey = null;
   
   Key fileKey = null;
   Key unwarapped_key = null;
   
   public  static final byte[] FIXED_12B8IT_IV_FOR_TESTS = LunaUtils.hexStringToByteArray("DEADD00D8BADF00DDEADBABED15EA5ED");
   
   
   private static LunaGmacParameterSpec getParameterSpec()
    {
        LunaSlotManager lsm = LunaSlotManager.getInstance();
        byte [] iv = null;
        if (!lsm.isFIPSEnabled())
        {
            iv = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        }

        byte [] aad = {0x55, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55};

        return new LunaGmacParameterSpec(iv, aad);
    }
   
   public void test()       
   {
       try
       {
           System.out.println("\n\n");
           Key MACKey = luna_keystore.getKey("ToWrap", null);
           
           LunaProvider abc = new LunaProvider();
       //    System.out.println(abc.getServices());
       
       
       
       
           Signature sig = null;
         //
         //   Mac mac = null;
        LunaGmacParameterSpec spec = null;     
        Mac mac = Mac.getInstance("HmacSHA256", "LunaProvider");
        spec = new LunaGmacParameterSpec(new byte[]{0x00, 0x00, 0x00, 0x00});
         
           
           
            System.out.println("Initializing GMAC engine.");
            
            
          //  mac = Mac.getInstance("HmacMD5", "LunaProvider");
           
            
           // spec = new LunaGmacParameterSpec(new byte[]{0x01, 0x01, 0x01, 0x02});
            //mac.init(MACKey, spec);
            
            mac.init(MACKey);
             mac.reset();
            
            
            String message = "ABC";
            
         
            byte [] digest = mac.doFinal(message.getBytes());
 
           
                        for (int i = 0 ; i < digest.length ; i++)
            {
                
                System.out.print(digest[i] & 0xff);
                        
                
            }
             
                       
          
     
       
       }
       catch (Exception e)
       {
            
            System.out.println("Error: " + e.getMessage());
            
            
            
      }
   }
   
   
   public void verify(String to_WRAP, String from_File )
   {
       try
       {
   
          

      
       
       }
       catch (Exception e)
       {
            
            System.out.println("Error: " + e.getMessage());
            
            
            
      }
   
   }
   
   
   
   
   public void unwrap(byte[] to_unwrap_key, String wrapping_key, String key_type)
   {
       try
       {
           
       
           //Cipher wrappingCipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "LunaProvider");
           Cipher wrappingCipher = Cipher.getInstance("AES/ECB/NoPadding", "LunaProvider");
          // AlgorithmParameters algParams = AlgorithmParameters.getInstance("IV", "LunaProvider");
          
           
        //  algParams.init(new IvParameterSpec(FIXED_12B8IT_IV_FOR_TESTS)); 
           wrappingCipher.init(Cipher.UNWRAP_MODE, luna_keystore.getKey(wrapping_key, null));
           
           
        
        //   wrappingCipher.unwrap(FIXED_12B8IT_IV_FOR_TESTS, to_wrap, slot)
           unwarapped_key =  wrappingCipher.unwrap(to_unwrap_key, key_type , Cipher.SECRET_KEY);
           
           luna_keystore.setKeyEntry("Unwrapped" , unwarapped_key , null, (java.security.cert.Certificate[]) null);



        
        }
       catch (Exception e)
       {
            
            System.out.println("Error: " + e.getMessage());
          
            
            
      }
   }
   
   
   public byte[]   wrap(String wraping_key, String to_wrap)
   {
   
       
       try
       {
      
          // Cipher wrappingCipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "LunaProvider");
           Cipher wrappingCipher = Cipher.getInstance("AES/ECB/NoPadding", "LunaProvider");
          
           
        
           
           wrappingCipher.init(Cipher.WRAP_MODE, luna_keystore.getKey(wraping_key, null)  );
           
           Key abc = luna_keystore.getKey(wraping_key, null);
           
           LunaTokenObject lto = LunaTokenObject.LocateKeyByAlias("GIBON-CA");
          System.out.println("wrap " +   lto.GetSmallAttribute(     LunaAPI.CKA_WRAP));
          System.out.println("unwrap " +   lto.GetSmallAttribute(     LunaAPI.CKA_UNWRAP));
System.out.println("enc " +   lto.GetSmallAttribute(     LunaAPI.CKA_ENCRYPT));
System.out.println("dec " +   lto.GetSmallAttribute(     LunaAPI.CKA_DECRYPT));
System.out.println("token " +   lto.GetSmallAttribute(     LunaAPI.CKA_TOKEN));
System.out.println("type " +   lto.GetSmallAttribute(     LunaAPI.CKA_KEY_TYPE));
System.out.println("private " +   lto.GetSmallAttribute(     LunaAPI.CKA_PRIVATE));                     
          
   

System.out.println("label " +   lto.GetSmallAttribute(     LunaAPI.CKA_LABEL));
System.out.println("sing " +   lto.GetSmallAttribute(     LunaAPI.CKA_SIGN));
System.out.println("ver " +   lto.GetSmallAttribute(     LunaAPI.CKA_VERIFY));
System.out.println("derive " +   lto.GetSmallAttribute(     LunaAPI.CKA_DERIVE));
System.out.println("extr " +   lto.GetSmallAttribute(     LunaAPI.CKA_EXTRACTABLE));    


System.out.println("finger " +   lto.GetSmallAttribute(     LunaAPI.CKA_FINGERPRINT));
System.out.println("id " +   lto.GetSmallAttribute(     LunaAPI.CKA_ID));
System.out.println("type " +   lto.GetSmallAttribute(     LunaAPI.CKA_KEY_TYPE));    


//System.out.println("modulus " +   lto.GetSmallAttribute(     LunaAPI.CKA_MODULUS));
//System.out.println("count " +   lto.GetSmallAttribute(     LunaAPI.CKA_USAGE_COUNT));
//System.out.println("type " +   lto.GetSmallAttribute(     LunaAPI.CKA_KEY_TYPE));



           return wrappingCipher.wrap( luna_keystore.getKey(to_wrap, null ));
      
           
           
           
           
       
       }
       catch (Exception e)
       {
            
            System.out.println("Error: " + e.getMessage());
            return null;
            
            
      }
   }
   
   
   
   public void key_gen_software(String key_to_wrap)
   {
       
      try
      {
       
      keyGen = KeyGenerator.getInstance("AES" , "SunJCE");
      keyGen.init(128);
      
      wrapKey = keyGen.generateKey();
      luna_keystore.setKeyEntry(key_to_wrap , aesKey, null, (java.security.cert.Certificate[]) null);
      
      System.out.println( luna_keystore.getKey(key_to_wrap, null));
       
      }
      catch (Exception e)
      {
            
            System.out.println("Error: " + e.getMessage());
         
            
            
      }
       
       
   }
   
   
   
   
   public void key_delete(String label)
   {
       
       try
       {
       
            luna_keystore.deleteEntry(label);
       
       }
       catch (Exception e)
        {
            
            System.out.println("Error: " + e.getMessage());
         
            
            
        }
       
   }
   
   public void key_gen(String label, String key_type)
   {
       
       try
       {
        
         
           
        keyGen = KeyGenerator.getInstance(key_type , "LunaProvider");
        keyGen.init(128);
        
        aesKey = keyGen.generateKey();
        
        luna_keystore.setKeyEntry(label , aesKey, null, (java.security.cert.Certificate[]) null);
        
        
//LunaTokenObject obj = LunaTokenObject.LocateObjectByHandle(kek.GetKeyHandle());
//obj.SetBooleanAttribute(LunaAPI.CKA_ENCRYPT, false);
//obj.SetBooleanAttribute(LunaAPI.CKA_DECRYPT, false);
        
        
       }
        catch (Exception e)
        {
            
            System.out.println("Error: " + e.getMessage());
         
            
            
        }
   }
   
   
   public void key_store()
   {
       try
       {
         ByteArrayInputStream is1 = new ByteArrayInputStream(("slot:" + slot).getBytes());
         
        
        
        luna_keystore = KeyStore.getInstance("LUNA");
        luna_keystore.load(is1, partition_password.toCharArray());
        
            
       }
       
        catch (Exception e)
        {
            System.out.println("Error1: " + e.getMessage());
         
            
            
        }
       
   }
   
   
   public Boolean initialize_connection()
   {
        
       // inicjalizacja 
       
        System.out.print("Initializing ... ");
        slot_manager = LunaSlotManager.getInstance();
        String tokenlabel = "";
       
       
        // lista slotow
        
        try
        {
        
        int[] activeSlots = slot_manager.getSlotList();
               
        System.out.println(" slots found: " + activeSlots.length);
        
        
        for (int slot : activeSlots)
        {

                if (slot_manager.isTokenPresent(slot))
                {
                    tokenlabel = slot_manager.getTokenLabel(slot);

                    
                    
                   System.out.println("Slot: " + slot + " token label: " + tokenlabel);
                }
                
        }
        
        
        // logowanie
        
    //    slot_manager.login(slot_manager.findSlotFromLabel(partition_label), UserRole.CRYPTO_OFFICER, partition_password);
        
          slot_manager.login(0, UserRole.CRYPTO_OFFICER, partition_password);
    
        
        slot_manager.setSecretKeysExtractable(true);
        
        slot_manager.setDefaultSlot(0);
        System.out.println("Using slot: " + slot_manager.getDefaultSlot());
        
        
        
        
        if (slot_manager.isLoggedIn())
        {
            
            System.out.println("Logged in");
            System.out.println("-------------------\n");
            return true;
            
        }
        else
        {
            
             System.out.println("Not Logged in");
             return false;
             
        }
        
        
        
        
        }
        catch (Exception e)
        {
            System.out.println("Error2: " + e.getMessage());
            return false;
            
            
        }
   }
    
}


