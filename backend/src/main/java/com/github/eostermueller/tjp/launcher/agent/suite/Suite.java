package com.github.eostermueller.tjp.launcher.agent.suite;

import java.util.List;

import com.github.eostermueller.tjp.launcher.agent.ProcessKey;
import com.github.eostermueller.tjp.launcher.agent.StateMachine;

public interface Suite extends StateMachine {
	void addRunnerInOrder(StateMachine r);
	StateMachine findNextRunner(ProcessKey processKey);
	StateMachine find(ProcessKey processKey);
	
	List<StateMachine> getRunners();
	void setRunners(List runners);
	boolean isFinalRunner(ProcessKey pk);
	boolean isFirstRunner(ProcessKey pk);
	
}
