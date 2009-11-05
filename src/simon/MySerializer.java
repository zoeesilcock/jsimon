package simon;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class MySerializer<T> implements Serializable{
	private static final long serialVersionUID = -217624671267056956L;
	private String filePath;
	
	public MySerializer(String path){
		this.filePath = path;
	}
	
	public void saveObject(T object){
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try{
			fos = new FileOutputStream(this.getClass().getResource(filePath).getPath());
		 	out = new ObjectOutputStream(fos);
		 	out.writeObject(object);
		 	out.close();
		 }catch(IOException ex){
			 ex.printStackTrace();
		 }
	}
	
	@SuppressWarnings("unchecked")
	public T loadObject(){
		ObjectInputStream in = null;
		Object object = null;
		try{
			InputStream fileStream = this.getClass().getResourceAsStream(filePath);
			if(fileStream != null){
				in = new ObjectInputStream(fileStream);
				object = in.readObject();
				in.close();
			}
		}catch(FileNotFoundException e){
			
		}catch (EOFException e){
			
		}catch (ClassNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return (T)object;
	}
}
