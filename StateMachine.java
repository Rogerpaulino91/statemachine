import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class StateMachine<T extends Enum<?>> {

	private final byte[] inputBuffer = new byte[32768];

	private T currentState;

	private final Map<T, AbstractAction<T>> stateActionMap = new HashMap<T, AbstractAction<T>>();

	public StateMachine(T startState) {
		this.currentState = startState;
	}

	/**
	 * Método principal que circula e processa o fluxo de entrada
	 */
	public void processStream(InputStream in) {

		// Loop externo - encha continuamente o buffer até que não haja nada
		// Deixado pra ler.
		try {
			processBuffers(in);
			terminate();
		} catch (Exception ioe) {
			throw new StateMachineException("Error processing input stream: "
					+ ioe.getMessage(), ioe);
		}
	}

	private void processBuffers(InputStream in) throws Exception {
		for (int len = in.read(inputBuffer); (len != -1); len = in
				.read(inputBuffer)) {

			// Loop interno - processa o conteúdo do buffer
			for (int i = 0; i < len; i++) {
				processByte(inputBuffer[i]);
			}
		}
	}

	/**
	 * lidando com cada byte individual no buffer.
	 */
	private void processByte(byte b) throws Exception {

		// Obtenha o conjunto de ações associadas a este estado
		AbstractAction<T> action = stateActionMap.get(currentState);
		// faça a ação obtenha o próximo estado.
		currentState = action.processByte(b, currentState);
	}

	/**
	 * o buffer está vazio
	 */
	private void terminate() throws Exception {
		AbstractAction<T> action = stateActionMap.get(currentState);
		action.terminate(currentState);
	}

	/**
	 * 
	 */
	public void addAction(T state, AbstractAction<T> action) {

		stateActionMap.put(state, action);
	}

	/**
	 * Remover uma ação da máquina de estado
	 */
	public void removeAction(AbstractAction<T> action) {

		stateActionMap.remove(action); // Remover uma ação da máquina de estado
	}
}