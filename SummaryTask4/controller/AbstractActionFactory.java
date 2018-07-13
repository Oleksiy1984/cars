package ua.nure.orlovskyi.SummaryTask4.controller;

public class AbstractActionFactory {
	private final static ActionFactory instance = new ActionFactory();

	public static ActionFactory getInstance() {
		return instance;
	}
}
