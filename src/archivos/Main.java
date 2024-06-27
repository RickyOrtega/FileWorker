package archivos;

import interfaz_usuarios.Usuario;

import java.util.List;

public class Main {
	public static void main(String[] args) {

		// Crear un archivo
		List<Usuario> usuariosGuardados = List.of(
				new Usuario("Juan", "20", "juan", "1234"),
				new Usuario("Pedro", "30", "pedro", "1234"),
				new Usuario("Maria", "40", "maria", "1234")
		);

		try {
			FileWorker fileWorker = new FileWorker("usuarios.bin");

			if (!fileWorker.exists()) {
				fileWorker.create();
			}

			fileWorker.write(usuariosGuardados.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}


		// Leer un archivo

		try {
			FileWorker fileWorker = new FileWorker("usuarios.bin");
			List<Usuario> usuariosLeidos = fileWorker.read();

			System.out.println("Usuarios:");

			for (Usuario usuario : usuariosLeidos) {
				System.out.println(usuario);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// probamos la eliminacion de un archivo:

//		FileWorker fileWorker = new FileWorker("usuarios.bin");
//
//		if (fileWorker.exists()) {
//			fileWorker.delete();
//		}
	}
}
