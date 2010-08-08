package mobi.econceptions.js;


public class AccessUtils {
	public static StatementMutator mutator(Statement stmt){ return stmt.getMutator(); }
	public static JavascriptMutator mutator(Javascript js){ return js.getMutator(); }

}
