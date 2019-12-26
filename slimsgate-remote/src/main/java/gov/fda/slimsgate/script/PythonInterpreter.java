package gov.fda.slimsgate.script;

import java.io.File;

import org.springframework.stereotype.Component;

import jep.Jep;
import jep.JepConfig;
import jep.JepException;

@Component
public final class PythonInterpreter {

	public PythonInterpreter(){

	}

	public void runScript(String id){

		File script = new File("src/main/python/main.py");
		try {
			runScript(script);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void runScript(File script) throws JepException, InterruptedException {
		try(Interpreter interpreter = new Interpreter()){
			interpreter.runScript(script.getPath());
		}
	}
	class Interpreter extends Jep{
		Interpreter() throws JepException{
			super(new JepConfig().setClassLoader(Thread.currentThread().getContextClassLoader()), false);
		}
	}

}