package interfaz_usuarios;

import java.io.Serial;
import java.io.Serializable;

public class Usuario implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private String nombre;
	private String edad;
	private String usuario;
	private String contrasena;

	public Usuario(String nombre, String edad, String usuario, String contrasena) {
		this.nombre = nombre;
		this.edad = edad;
		this.usuario = usuario;
		this.contrasena = contrasena;
	}

	public Usuario() {
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEdad() {
		return edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", edad=" + edad + ", usuario=" + usuario + ", contrasena=" + contrasena + "]";
	}
}
