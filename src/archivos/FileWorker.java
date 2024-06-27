/**
 * FileWorker.java
 *
 * Clase que permite manipular archivos de manera sencilla.
 *
 * Se pueden realizar las siguientes operaciones:
 *
 * - Verificar si un archivo existe.
 * - Crear un archivo.
 * - Eliminar un archivo.
 * - Renombrar un archivo.
 * - Mover un archivo.
 * - Copiar un archivo.
 * - Verificar si un archivo es un directorio.
 * - Escribir en un archivo.
 * - Leer de un archivo.
 *
 * Para escribir y leer objetos en un archivo, se utiliza la clase SealedObject
 * para cifrar y descifrar los objetos.
 *
 * Se utiliza el algoritmo de cifrado AES para cifrar y descifrar los objetos.
 *
 * @version 1.0
 *
 * @autor Ing. Ricky Ortega
 *
 * */

package archivos;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class FileWorker {
	private File file;
	private String path;

	public FileWorker(String path) {
		this.path = path;
		this.file = new File(path);
	}

	public FileWorker() {

	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean exists() {
		return file.exists();
	}

	public boolean create() {
		try {
			return file.createNewFile();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean delete() {
		return file.delete();
	}

	public boolean rename(String newPath) {
		File newFile = new File(newPath);
		return file.renameTo(newFile);
	}

	public boolean move(String newPath) {
		File newFile = new File(newPath);
		return file.renameTo(newFile);
	}

	public boolean copy(String newPath) {
		File newFile = new File(newPath);
		return file.renameTo(newFile);
	}

	public boolean isDirectory() {
		return file.isDirectory();
	}

	/**
	 *
	 * Método que permite escribir objetos en un archivo.
	 *
	 * @param data Arreglo de objetos a escribir en el archivo.
	 *             Los objetos deben ser serializables.
	 *
	 *             Ejemplo:
	 *
	 *             List<Usuario> usuariosGuardados = List.of(
	 *             		new Usuario("Juan", "20", "juan", "1234"),
	 *             		new Usuario("Pedro", "30", "pedro", "1234"),
	 *             		new Usuario("Maria", "40", "maria", "1234")
	 *             );
	 *
	 *             fileWorker.write(usuariosGuardados.toArray());
	 *
	 *             En este ejemplo, se escriben los objetos de la lista usuariosGuardados en el archivo.
	 *
	 * */
	public boolean write(Object[] data) {
		try {
			Cipher cipher = Cipher.getInstance("AES");
			SecretKeySpec keySpec = new SecretKeySpec("MySecretKey12345".getBytes(), "AES");
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);

			SealedObject sealedObject;
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));

			for (Object object : data) {
				sealedObject = new SealedObject((Serializable) object, cipher);
				objectOutputStream.writeObject(sealedObject);
			}

			objectOutputStream.close();

		} catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
				 IllegalBlockSizeException e) {
			System.out.println("Error al escribir en el archivo");
			return false;
		}

		return true;
	}

	/**
	 *
	 * Método que permite leer objetos de un archivo.
	 *
	 * @return Lista de objetos leídos del archivo.
	 *
	 * */
	public <T> List<T> read() {
		List<T> data = new ArrayList<>();

		try {
			Cipher cipher = Cipher.getInstance("AES");
			SecretKeySpec keySpec = new SecretKeySpec("MySecretKey12345".getBytes(), "AES");
			cipher.init(Cipher.DECRYPT_MODE, keySpec);

			ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
			SealedObject sealedObject;

			while (true) {
				try {
					sealedObject = (SealedObject) objectInputStream.readObject();
					T object = (T) sealedObject.getObject(cipher);
					data.add(object);
				} catch (EOFException | BadPaddingException e) {
					break;
				}
			}

			objectInputStream.close();

		} catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException e) {
			System.out.println("Error al leer el archivo");
			e.printStackTrace();
		}

		return data;
	}

}
