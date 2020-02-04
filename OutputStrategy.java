import java.io.IOException;
import java.io.OutputStream;

public interface OutputStrategy {

	/**
	 * Implemente esse método para definir como criar alguma saída.
	 * 
	 * @throws IOException
	 */
	public void build(String tag, OutputStream os) throws IOException;
}