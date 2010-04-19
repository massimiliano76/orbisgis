package org.orbisgis.core.ui.pluginSystem.workbench;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.orbisgis.core.ui.editors.map.tool.Automaton;
import org.orbisgis.core.ui.pluginSystem.AbstractPlugIn;
import org.orbisgis.core.ui.pluginSystem.PlugIn;
import org.orbisgis.core.ui.pluginSystem.PlugInContext;

public abstract class WorkbenchContext extends Observable {

	private String lastAction;
	private List<Observer> popupPlugInObservers = new ArrayList<Observer>();
	private List<Observer> viewsPlugInObservers = new ArrayList<Observer>();

	public List<Observer> getViewsPlugInObservers() {
		return viewsPlugInObservers;
	}

	public List<Observer> getPopupPlugInObservers() {
		return popupPlugInObservers;
	}

	public String getLastAction() {
		return lastAction;
	}	

	public OrbisWorkbench getWorkbench() {
		return null;
	}

	public PlugInContext createPlugInContext(AbstractPlugIn plugIn) {
		return new PlugInContext(this,plugIn);
	}
	
	public PlugInContext createPlugInContext(PlugIn plugIn) {
		return new PlugInContext(this,plugIn);
	}
	
	public PlugInContext createPlugInContext() {
		return new PlugInContext(this);
	}

	public void setLastAction(String lastAction) {		
		this.lastAction = lastAction;
		//System.out.println("#############################"+lastAction+":"+countObservers()+"#################");
		addViewsPlugInObservers();
		addToolsPlugInObservers();
		addTableObservers();
		//System.out.println("#############################"+countObservers()+"#################");
		setChanged();
		notifyObservers(lastAction);
		removeToolsPlugInObservers();
		removeViewsPlugInObservers();
		removeTableObservers();
		//System.out.println("#############################"+countObservers()+"#################");		
	}

	public void setRowSelected(MouseEvent e) {
		addToolsPlugInObservers();
		addTableObservers();
		setChanged();
		notifyObservers(e);
		removeToolsPlugInObservers();
		removeTableObservers();
	}	

	public void setHeaderSelected(int selectedColumn) {
		addToolsPlugInObservers();
		addTableObservers();
		setChanged();
		notifyObservers(selectedColumn);
		removeToolsPlugInObservers();
		removeTableObservers();
	}	
	
	private void removeTableObservers() {
		for (int i = 0; i < popupPlugInObservers.size(); i++)									
			deleteObserver(popupPlugInObservers.get(i));		
	}
	
	private void addTableObservers() {
		for (int i = 0; i < popupPlugInObservers.size(); i++)									
			addObserver(popupPlugInObservers.get(i));		
	}
	
	private void removeViewsPlugInObservers() {
		for (int i = 0; i < viewsPlugInObservers.size(); i++)									
			deleteObserver(viewsPlugInObservers.get(i));		
	}
	
	private void addViewsPlugInObservers() {
		for (int i = 0; i < viewsPlugInObservers.size(); i++)									
			addObserver(viewsPlugInObservers.get(i));		
	}
	
	private void addToolsPlugInObservers(){	
		Map<String, WorkbenchToolBar> bars = getWorkbench().getFrame().getWorkbenchToolBar().getToolbars();
		Iterator<WorkbenchToolBar> it = bars.values().iterator();						
		List<Observer> observers = new ArrayList<Observer>();
		while(it.hasNext()){	
			WorkbenchToolBar wb = it.next();			
			observers = wb.getToolsPlugInObservers();
			addObserver(wb);
			for (int i = 0; i < observers.size(); i++)									
				addObserver(observers.get(i));
		}
	}
	
	private void  removeToolsPlugInObservers(){
		Map<String, WorkbenchToolBar> bars = getWorkbench().getFrame().getWorkbenchToolBar().getToolbars();
		Iterator<WorkbenchToolBar> it = bars.values().iterator();						
		List<Observer> observers = new ArrayList<Observer>();
		while(it.hasNext()){	
			WorkbenchToolBar wb = it.next();						
			observers = wb.getToolsPlugInObservers();
			for (int i = 0; i < observers.size(); i++)
				deleteObserver(observers.get(i));
			deleteObserver(wb);			
		}
	}

}