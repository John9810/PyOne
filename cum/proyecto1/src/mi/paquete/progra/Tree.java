package mi.paquete.progra;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Tree<T> {
	public void insert(T data, Curso curso);
	public void traverse(String fileName) throws FileNotFoundException, IOException;
	public void delete(T data);
}
