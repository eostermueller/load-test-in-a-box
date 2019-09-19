package com.github.eostermueller.tjp.launcher;

import java.util.List;

public interface Suite extends StateMachine {
	void addRunnerInOrder(StateMachine r);
	StateMachine findNextRunner(ProcessKey processKey);
	StateMachine find(ProcessKey processKey);
	
	List<StateMachine> getRunners();
	void setRunners(List runners);
	boolean isFinalRunner(ProcessKey pk);
	boolean isFirstRunner(ProcessKey pk);
	
}
