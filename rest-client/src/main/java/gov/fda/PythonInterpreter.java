package gov.fda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

import jep.Jep;
import jep.JepConfig;
import jep.JepException;

public final class PythonInterpreter {
	private static final String[] sharedModules = { "numpy" };

	public PythonInterpreter() {

	}

	public void runScript(String script) {
		try {
			if (script == null) {
				throw new Exception("script file not found.");
			}
			script = script.replaceAll("/", "");
			script = script.replaceAll("\\\\", "");
			script = script.replaceAll("\\.", "");
			script = String.format("python/%s.py", script);
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(script);
			runScript(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void runScript(InputStream script) throws JepException, InterruptedException, IOException {
		try (Interpreter interpreter = new Interpreter()) {

			String execString = new BufferedReader(new InputStreamReader(script)).lines().parallel()
					.collect(Collectors.joining("\n"));
			interpreter.exec(execString);
			interpreter.close();
		}
	}

	class Interpreter extends Jep {
		Interpreter() throws JepException {
			super(new JepConfig().addSharedModules(sharedModules)
					.setClassLoader(Thread.currentThread().getContextClassLoader()), false);
		}
	}

}