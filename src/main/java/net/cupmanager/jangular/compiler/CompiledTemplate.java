package net.cupmanager.jangular.compiler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import net.cupmanager.jangular.Evaluatable;
import net.cupmanager.jangular.Scope;
import net.cupmanager.jangular.exceptions.EvaluationException;
import net.cupmanager.jangular.injection.EvaluationContext;
import net.cupmanager.jangular.nodes.JangularNode;

public class CompiledTemplate extends Evaluatable {
	private JangularNode node;
	private long durationMs;
	private List<String> warnings;
	
	public CompiledTemplate(JangularNode node) {
		this.node = node;
	}

	public JangularNode getNode() {
		return node;
	}

	@Override
	public void eval(Scope scope, StringBuilder sb, EvaluationContext context) 
			throws EvaluationException{
		node.eval(scope, sb, context);
	}

	void setDuration(long time, TimeUnit unit) {
		this.durationMs = unit.toMillis(time);
	}
	
	public long getCompileDuration(TimeUnit unit) {
		return TimeUnit.MILLISECONDS.convert(durationMs, unit);
	}

	void setWarnings(List<String> warnings) {
		this.warnings = warnings;
	}
	
	public ArrayList<String> getWarnings() {
		return new ArrayList<>(warnings);
	}
	public void printWarnings() {
		if (!warnings.isEmpty()) {
			System.err.println("The Jangular Compiler encountered the following warnings:\n");
			int i = 1;
			for( String warning : warnings ){
				System.err.println(i + " - "+warning);
				i++;
			}
		}
	}
	

	
	/*
    t.warnings()
    t.usedDirectives()
    t.loadedTemplates()
    t.generatedClasses()
	*/
}
