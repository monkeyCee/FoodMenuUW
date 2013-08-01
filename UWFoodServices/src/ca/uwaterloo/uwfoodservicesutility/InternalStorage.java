package ca.uwaterloo.uwfoodservicesutility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.util.Log;

public class InternalStorage{

    private InternalStorage() {

    }

    public static boolean cacheExists(Context context, String key) throws IOException, ClassNotFoundException{

        File file = new File(context.getFilesDir(), key);
        if(!file.isFile() && !file.canRead()){
            return false; 
        } else {
            return true;
        }

    }

    public static void writeObject(Context context, String key, Object object) throws IOException {
        FileOutputStream fileOutputStream = context.openFileOutput(key, Context.MODE_PRIVATE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(object);
        objectOutputStream.close();
        fileOutputStream.close();
    }

    public static Object readObject(Context context, String key) throws IOException,
    ClassNotFoundException {
        if(cacheExists(context, key)){
            FileInputStream fileInputStream = context.openFileInput(key);
            ObjectInputStream objectInputSteam = new ObjectInputStream(fileInputStream);
            Object object = objectInputSteam.readObject();
            return object;
        } else {
            return null;
        }
    }

    public static boolean deleteObject(Context context, String key) {
        File file = new File(context.getFilesDir(), key);
        if(!file.isFile() && !file.canRead()){
            Log.d("File", "No such File");
            return false;
        } else {
            return file.delete();
        }
    }
}